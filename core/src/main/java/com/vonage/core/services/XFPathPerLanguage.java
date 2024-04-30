package com.vonage.core.services;

import java.util.HashMap;

import org.apache.sling.api.resource.Resource;



/**
 * Base class to reading careers data from JCR
 *
 * @author Vonage
 *
 */
public interface XFPathPerLanguage {


    /**
     * @param formPath formPath
     * @param confMessagePath confMessagePath
     * @param resource resource
     * @return Hasmap containing language specific XF paths
     */
     HashMap<String, String> getLocaleSpecificXFPath(String formPath, String confMessagePath, Resource resource);

}


