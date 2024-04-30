package com.vonage.core.services;

import com.adobe.granite.ui.components.ds.DataSource;

/**
 * I18NCountryDropdownService Service
 *
 * @author Vonage
 */
public interface I18NCountryDropdownService {

    /**
     * Abstract method to get all I18N nodes
     *
     * @return i18NLanguages - The language info
     */
    DataSource getI18NLanguages();

}
