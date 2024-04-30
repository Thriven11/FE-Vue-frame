package com.vonage.core.models.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.i18n.I18n;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Sling model for Pricing Datasource
 */
@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PricingDatasourceModel {

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PricingDatasourceModel.class);

    /**
     * String countries
     */
    @RequestAttribute
    @Default(values = "")
    private Object country;

    /**
     * String List<Map<String, String>> countries
     */
    @Self
    private List<Map<String, String>> countries;

    /**
     * String List<Map<String, String>> updatedCountries
     */
    @Self
    private List<Map<String, String>> updatedCountries;

    /**
     * String resourceResolver
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * SlingHttpServletRequest request
     */
    @SlingObject
    private SlingHttpServletRequest request;

    /**
     * init method
     *
     * @throws Exception exception
     */
    @PostConstruct
    protected final void init() throws Exception {
        LOGGER.info("countries" + country);
        List<Object> list = new ArrayList<Object>();
        Gson gson = new Gson();
        String countyObjStr = gson.toJson(country);
        JsonElement jsonElement = new JsonParser().parse(countyObjStr);
        JsonArray jsonArray = gson.fromJson(jsonElement, JsonArray.class);

        updatedCountries = new ArrayList<>();

        for (JsonElement jsonElement2 : jsonArray) {
            JsonObject jsonObject = jsonElement2.getAsJsonObject();
            if (jsonObject.has("scriptable")) {
                JsonObject scriptable = jsonObject.get("scriptable").getAsJsonObject();
                if (scriptable.has("code") && scriptable.has("key")) {
                    String key = scriptable.get("key").getAsString();
                    String code = scriptable.get("code").getAsString();
                    Map<String, String> map = new HashMap<>();
                    map.put(key, code);
                    updatedCountries.add(map);
                }
            }
        }

        LOGGER.info("countries" + jsonArray);
        DataSource data = getDataSource(updatedCountries);
        request.setAttribute(DataSource.class.getName(), data);
    }

    /**
     * method
     * @param list - list
     * @return DataSource
     */
    protected final DataSource getDataSource(final List<Map<String, String>> list) {
        return new SimpleDataSource(new TransformIterator<>(list.iterator(), next -> {
            Map<String, String> map = (Map<String, String>) next;
            I18n i18n = new I18n(request);
            ValueMap vm = new ValueMapDecorator(new HashMap<>());
            for (String key : map.keySet()) {
                vm.put("value", map.get(key).toString());
                vm.put("text", I18n.get(request, key));
            }
            return new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, vm);
        }));
    }

    /**
     * method
     *
     * @return country
     */
    protected final Object getCountry() {
        return country;
    }

    /**
     * method
     *
     * @return updatedCountries
     */
    protected final List<Map<String, String>> getUpdatedCountries() {
        return updatedCountries;
    }

    /**
     * method
     *
     * @return countries
     */
    protected final List<Map<String, String>> getCountries() {
        return countries;
    }
}
