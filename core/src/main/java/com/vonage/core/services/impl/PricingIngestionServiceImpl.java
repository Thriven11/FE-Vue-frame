package com.vonage.core.services.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vonage.core.constants.PricingConstants;
import com.vonage.core.services.PricingIngestionService;
import com.vonage.core.utils.ServiceUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Pricing ingestion service
 *
 * @author Vonage
 *
 */
@Component(immediate = true, service = PricingIngestionService.class)
public class PricingIngestionServiceImpl implements PricingIngestionService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PricingIngestionServiceImpl.class);

    /**
     * Resource Resolver Factory
     */
    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public final boolean ingestPrice(final JsonObject pricingObject, final String relPath) {
        if (null != pricingObject) {
            ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
            if (null != resourceResolver) {
                Session session = resourceResolver.adaptTo(Session.class);
                String countryFolder = PricingConstants.PRODUCTS_BASE_PATH.concat(relPath);
                try {
                    if (null != session && session.itemExists(PricingConstants.PRODUCTS_BASE_PATH)) {
                        Map<String, String> propertiesMap = new HashMap<>();
                        if (relPath.startsWith("/messaging/")) {
                            propertiesMap.putAll(fetchMessagingProperties(pricingObject));
                        } else if (relPath.startsWith("/voice/")) {
                            propertiesMap.putAll(fetchVoiceProperties(pricingObject));
                        } else if (relPath.startsWith("/verify/")) {
                            propertiesMap.putAll(fetchVerifyProperties(pricingObject));
                        }
                        return ServiceUtils.createProductNode(resourceResolver, countryFolder, propertiesMap);
                    }
                } catch (RepositoryException e) {
                    LOGGER.error("Unable to update prices. Error: {}", e.getMessage(), e);
                }
            }
        }
        return false;
    }

    @Override
    public final boolean ingestRates(final JsonObject exchangeObject) {
        if (null != exchangeObject) {
            ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
            if (null != resourceResolver) {
                Session session = resourceResolver.adaptTo(Session.class);
                try {
                    if (null != session && session.itemExists(PricingConstants.PRODUCTS_BASE_PATH)) {
                        return ServiceUtils.createProductNode(resourceResolver, PricingConstants.PRODUCTS_BASE_PATH,
                                fetchExchangeRateProperties(exchangeObject));
                    }
                } catch (RepositoryException e) {
                    LOGGER.error("Unable to update exchange rate. Error: {}", e.getMessage(), e);
                }
            }
        }
        return false;
    }

    /**
     * Fetch all exchange rates as a map
     *
     * @param rates - exchange rates object from API response
     * @return map of properties
     */
    private Map<String, String> fetchExchangeRateProperties(final JsonObject rates) {
        Map<String, String> properties = new HashMap<>();
        Iterator<Entry<String, JsonElement>> it = rates.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, JsonElement> rate = it.next();
            properties.put(rate.getKey(), rate.getValue().getAsString());
        }
        return properties;
    }

    /**
     * Set ingestion properties
     *
     * @param object - JSON object
     * @return map - the properties map
     */
    private Map<String, String> fetchMessagingProperties(final JsonObject object) {
        Map<String, String> properties = new HashMap<>();
        JsonObject outbound = object.getAsJsonObject("messaging").getAsJsonObject("outbound");
        if (object.has("name")) {
            properties.put(JcrConstants.JCR_TITLE, object.get("name").getAsString());
        }
        if (outbound.has("minMobilePrice")) {
            properties.put(PricingConstants.PN_OUTGOING, outbound.get("minMobilePrice").getAsString());
        } else if (outbound.has(PricingConstants.PN_FLAT_MOBILE_PRICE)) {
            properties.put(PricingConstants.PN_OUTGOING,
                    outbound.get(PricingConstants.PN_FLAT_MOBILE_PRICE).getAsString());
        }
        JsonArray numbers = object.getAsJsonObject("messaging").getAsJsonObject("inbound").getAsJsonArray("numbers");
        if (null != numbers && numbers.size() > 0) {
            numbers.forEach(e -> {
                if (e.isJsonObject()) {
                    final JsonObject dtObj = e.getAsJsonObject();
                    if (dtObj.get("type").getAsString().equals("mobile")) {
                        if (dtObj.has(PricingConstants.PN_MESSAGING_TRAFFIC)) {
                            properties.put(PricingConstants.PN_VIRTUAL_INCOMING,
                                    dtObj.get(PricingConstants.PN_MESSAGING_TRAFFIC).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && ("SMS,VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString()))) {
                          properties.put(PricingConstants.PN_VIRTUAL_RENTAL,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && "VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                          properties.put(PricingConstants.PN_VIRTUAL_RENTAL_VOICE,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && "SMS".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                          properties.put(PricingConstants.PN_VIRTUAL_RENTAL_SMS,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                    } else if (dtObj.get("type").getAsString().equals("tollfree")) {
                        if (dtObj.has(PricingConstants.PN_MESSAGING_TRAFFIC)) {
                            properties.put(PricingConstants.PN_TOLLFREE_INCOMING,
                                    dtObj.get(PricingConstants.PN_MESSAGING_TRAFFIC).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && ("SMS,VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString()))) {
                          properties.put(PricingConstants.PN_TOLLFREE_RENTAL,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && "VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                          properties.put(PricingConstants.PN_TOLLFREE_RENTAL_VOICE,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && "SMS".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                          properties.put(PricingConstants.PN_TOLLFREE_RENTAL_SMS,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                    }
                }
            });
        }
        return properties;
    }

    /**
     * Set ingestion properties
     *
     * @param object - JSON object
     * @return map - the properties map
     */
    private Map<String, String> fetchVoiceProperties(final JsonObject object) {
        Map<String, String> properties = new HashMap<>();
        JsonObject voice = object.getAsJsonObject("voice");
        JsonObject outbound = voice.getAsJsonObject("outbound");
        if (object.has("name")) {
            properties.put(JcrConstants.JCR_TITLE, object.get("name").getAsString());
        }
        if (outbound.has("flatPrice")) {
            properties.put(PricingConstants.PN_OUTGOING, outbound.get("flatPrice").getAsString());
        }
        if (outbound.has(PricingConstants.PN_FLAT_MOBILE_PRICE)) {
            properties.put(PricingConstants.PN_OUTGOING_MOBILE,
                    outbound.get(PricingConstants.PN_FLAT_MOBILE_PRICE).getAsString());
            if (outbound.has("minLandlinePrice")) {
              properties.put(PricingConstants.PN_OUTGOING_LANDLINE, outbound.get("minLandlinePrice").getAsString());
            }
        }
        if (outbound.has("flatLandlinePrice")) {
            properties.put(PricingConstants.PN_OUTGOING_LANDLINE, outbound.get("flatLandlinePrice").getAsString());
            if (outbound.has("minMobilePrice")) {
              properties.put(PricingConstants.PN_OUTGOING_MOBILE,
                outbound.get("minMobilePrice").getAsString());
            }
        }
        JsonArray numbers = voice.getAsJsonObject("inbound").getAsJsonArray("numbers");
        if (null != numbers && numbers.size() > 0) {
            numbers.forEach(e -> {
                if (e.isJsonObject()) {
                    final JsonObject dtObj = e.getAsJsonObject();
                    if (dtObj.get("type").getAsString().equals("mobile")) {
                        if (dtObj.has(PricingConstants.PN_VOICE_TRAFFIC)) {
                            properties.put(PricingConstants.PN_VIRTUAL_INCOMING,
                                    dtObj.get(PricingConstants.PN_VOICE_TRAFFIC).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                                && ("SMS,VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString()))) {
                            properties.put(PricingConstants.PN_VIRTUAL_RENTAL,
                                    dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                                && "VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                            properties.put(PricingConstants.PN_VIRTUAL_RENTAL_VOICE,
                                    dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && "SMS".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                          properties.put(PricingConstants.PN_VIRTUAL_RENTAL_SMS,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                    } else if (dtObj.get("type").getAsString().equals("tollfree")) {
                        if (dtObj.has(PricingConstants.PN_VOICE_TRAFFIC)) {
                            properties.put(PricingConstants.PN_TOLLFREE_INCOMING,
                                    dtObj.get(PricingConstants.PN_VOICE_TRAFFIC).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                                && "SMS,VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                            properties.put(PricingConstants.PN_TOLLFREE_RENTAL,
                                    dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                                && "VOICE".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                            properties.put(PricingConstants.PN_TOLLFREE_RENTAL_VOICE,
                                    dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                        if (dtObj.has(PricingConstants.PN_MONTHLY_FEE) && dtObj.has(PricingConstants.PN_FEATURES)
                          && "SMS".equals(dtObj.get(PricingConstants.PN_FEATURES).getAsString())) {
                          properties.put(PricingConstants.PN_TOLLFREE_RENTAL_SMS,
                            dtObj.get(PricingConstants.PN_MONTHLY_FEE).getAsString());
                        }
                    }
                }
            });
        }
        return properties;
    }

    /**
     * Set ingestion properties
     *
     * @param object - JSON object
     * @return map - the properties map
     */
    private Map<String, String> fetchVerifyProperties(final JsonObject object) {
        Map<String, String> properties = new HashMap<>();
        JsonObject verify = object.getAsJsonObject("verify");
        if (object.has("name")) {
            properties.put(JcrConstants.JCR_TITLE, object.get("name").getAsString());
        }
        if (verify.has(PricingConstants.PN_FLAT_PRICE)) {
            properties.put(PricingConstants.PN_FLAT_PRICE, verify.get(PricingConstants.PN_FLAT_PRICE).getAsString());
        }
        if (verify.has(PricingConstants.PN_FLAT_PUSH_PRICE)) {
            properties.put(PricingConstants.PN_FLAT_PUSH_PRICE,
                    verify.get(PricingConstants.PN_FLAT_PUSH_PRICE).getAsString());
        }
        if (verify.has(PricingConstants.PN_FLAT_FAIL_PRICE)) {
            properties.put(PricingConstants.PN_FLAT_FAIL_PRICE,
                    verify.get(PricingConstants.PN_FLAT_FAIL_PRICE).getAsString());
        }
        return properties;
    }

}
