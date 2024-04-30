package com.vonage.core.models.structure.resources;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
//import java.util.Arrays;
//import java.util.stream.Collectors;

/**
 * Sling Model for Swiftype Tags
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class DriftChatScriptModel {


    /** string to remove constant */
    private static final String PREFIX_TO_REMOVE = "vonage:";

    /**
    * tagsArray variable
    */
    @ValueMapValue(name = "cq:tags", injectionStrategy = InjectionStrategy.OPTIONAL)
    private final List<String> pageTagsList = new ArrayList<>();

    /**
     * test tag variable
     */
    private String tagValues;

    /**
     * pagePath string variable
     */
    private String pagePath;

    /**
     * pageName variable
     */
    private String pageName;

    /**
     * API script variable
     */
    private boolean apiScript;

    /**
     * appplications sctipt variable
     */
    private boolean applicationsScript;

    /**
     * Page
     */
    @Inject
    private Page currentPage;



    /**
    * init method
    */
    @PostConstruct
    protected final void init() {
        setDriftChatScript();
    }


    /**
    * multimap
    */
    final void setDriftChatScript() {
        /**
         * CHECK FOR ANY OF THE FOLLOWING TAGES
         * Vonage : Product / Communications APIs (Drift script for API)
         * Vonage : Product / Unified Communications (Drift script for Applications)
         * Vonage : Product / Contact Centers (Drift script for Applications)
         */
        if (pageTagsList != null) {
            for (int i = 0; i < pageTagsList.size(); i++) {
                String tagString = pageTagsList.get(i);
                if (tagString.startsWith(PREFIX_TO_REMOVE)) {
                    // replace string instances
                    tagString = tagString.replace(PREFIX_TO_REMOVE, "");
                }
                final String[] parts = tagString.split("/");
                // check for product in 2nd leaf
                if (parts.length > 1) {
                    if (parts[0].contains("product")) {
                        if (parts[1].contains("communications-apis")) {
                            apiScript = true;
                        } else {
                            applicationsScript = true;
                        }
                    }
                }
            }
        }

        try {
            Node currentPageNode = this.currentPage.adaptTo(Node.class);
            if (currentPageNode != null) {
                pageName = currentPageNode.getName();
                // single page only to add aplication script
                // if we need alternative scripts, create a seperate list for each
                // This will override the logic previously set based on tagging
                String[] singlePages = new String[]{"homepage", "straightforward"};
                List<String> singlePagesList = Arrays.asList(singlePages);
                if (singlePagesList.contains(pageName)) {
                    applicationsScript = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // check parent directory for all sub pages to have a script added
        pagePath = this.currentPage.getPath();

        // check if either is false as the tags superseed the logic for page path conditions
        if (!apiScript && !applicationsScript) {
            if (pagePath.contains("/resources")) {
                applicationsScript = true;
            }
            if (pagePath.contains("/about-us")) {
                applicationsScript = true;
            }
            if (pagePath.contains("/unified-communications")) {
                applicationsScript = true;
            }
            if (pagePath.contains("/contact-centers")) {
                applicationsScript = true;
            }
            if (pagePath.contains("/communications-apis")) {
                apiScript = true;
            }
        }
    }


    /**
     * Getter pageTagsList
     * @return pageTagsList
     */
    public final List<String> getPageTagList() {
        return new ArrayList<>(pageTagsList);
    }

    /**
   * Get API Script
   * @return apiScript
   */
    public final boolean getApiScript() {
        return apiScript;
    }

    /**
   * Get Applications Script
   * @return applicationsScript
   */
    public final boolean getApplicationsScript() {
        return applicationsScript;
    }

    /**
    * Get Tag Values
    * @return tagValues
    */
    public final String getTagValues() {
        return tagValues;
    }
    /**
    * Get pagePath
    * @return pagePath
    */
    public final String getPagePath() {
        return pagePath;
    }

    /**
    * Get pageName
    * @return pageName
    */
    public final String getPageName() {
        return pageName;
    }
}
