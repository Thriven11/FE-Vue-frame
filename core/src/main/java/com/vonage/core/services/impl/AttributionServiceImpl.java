package com.vonage.core.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.services.AttributionService;
import com.vonage.core.utils.ServiceUtils;

/**
 * Service for attribution
 * <p>
 * This service is called to get attribution
 * </p>
 *
 * @author Vonage
 */
@Component(immediate = true, service = AttributionService.class)
public class AttributionServiceImpl implements AttributionService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AttributionServiceImpl.class);

    /**
     * Resource Resolver Factory
     */
    @Reference
    private ResourceResolverFactory resolverFactory;

    /**
     * Get a campaign
     *
     * @param name - The campaign name
     * @return campaign - The campaign info (or null if not found)
     * @throws IOException - IOException
     */
    @Override
    public final HashMap<String, String> getCampaign(final String name) throws IOException {
        if (null == name || name.trim().isEmpty()) {
            return null;
        }

        // Initialize our return value
        HashMap<String, String> campaign = new HashMap<>();

        // Get the resource resolver
        ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);

        // Query by campaign name
        if (null != resourceResolver) {
            QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
            Session session = resourceResolver.adaptTo(Session.class);

            if (StringUtils.contains(name, "-api-")) {

                campaign.put("name", name);
                campaign.put("id", "7011O000002cfuc");
                campaign.put("tfn", "1.844.365.9460");

            } else {

                Map<String, String> predicateMap = new HashMap<>();
                predicateMap.put("path", VonageConstants.ATTRIBUTION_CAMPAIGNS_PATH);
                predicateMap.put("type", JcrConstants.NT_UNSTRUCTURED);
                predicateMap.put("property", JcrConstants.JCR_TITLE);
                predicateMap.put("property.value", name);
                predicateMap.put("property.operation", "equal");
                predicateMap.put("p.limit", "1");
                Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
                SearchResult result = query.getResult();
                List<Hit> list = result.getHits();
                Hit hit = null;
                if (!list.isEmpty()) {
                    hit = list.get(0);
                }

                // Get the campaign info to output
                if (hit != null) {
                    try {
                        Resource res = hit.getResource();
                        if (res != null) {
                            ValueMap properties = res.adaptTo(ValueMap.class);
                            if (null != properties) {
                                // remove carriage return and trim whitespace
                                String id = properties.get("campaignId", (String) null).replaceAll("\\\\r", "").trim();
                                String tfn = properties.get("campaignTfn", (String) null);

                                campaign.put("name", name);
                                campaign.put("id", id);
                                campaign.put("tfn", tfn);
                            }
                        }
                    } catch (RepositoryException e) {
                        LOGGER.error("Can't get attribution! Reason: Exception- {}", e.getMessage());
                    } finally {
                        if (resourceResolver.isLive()) {
                            resourceResolver.close();
                        }
                    }
                }

            }



            // Return the campaign
            if (!campaign.isEmpty()) {
                return campaign;
            }
        } else {
            LOGGER.error("Can't get attribution! Reason: ResourceResolver- null, Unknown- {}",
                    VonageConstants.WRITE_SUBSERVICE);
        }

        return null;
    }

}
