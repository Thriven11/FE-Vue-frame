package com.vonage.core.models.content;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.osgi.service.component.annotations.Component;

import com.adobe.granite.ui.components.ds.DataSource;
import com.vonage.core.services.I18NCountryDropdownService;

/**
 * Sling model for Datasource
 */
@Model(adaptables = { SlingHttpServletRequest.class })
@Component
public class I18NDatasourceModel {

    /**
     * I18NCountryDropdownService
     */
    @OSGiService
    private I18NCountryDropdownService countryDropdownService;

    /**
     * SlingHttpServletRequest Request
     */
    @Self
    private SlingHttpServletRequest request;

    /**
     * init method
     * @throws Exception exception
     */
    @PostConstruct
    protected final void init() throws Exception {
        DataSource data = countryDropdownService.getI18NLanguages();
        request.setAttribute(DataSource.class.getName(), data);
    }
}
