package com.vonage.core.models.structure.resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;
import com.vonage.core.models.CTAModel;

/**
 * Sling model for L3 Navigation List
 *
 * @author Vonage
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class L3NavigationComponentModel {

    /**
     * Link List Title
     *
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String title;

    /**
     * Link List
     *
     * @return Link List
     */
    @ChildResource(name = "links", injectionStrategy = InjectionStrategy.OPTIONAL)
    private List<CTAModel> links;

    /**
     * L3 categories label
     *
     * @return categories label
     */
    @ValueMapValue(name = "categoriesLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String categoriesLabel;

    /**
     * currentPage variable
     */
    @Inject
    private Page currentPage;

    /**
     * currentCategoryTitle
     */
    private String currentCategoryTitle = StringUtils.EMPTY;

    /**
     * init method
     */
    @PostConstruct
    protected final void init() {
        if (null != links && null != currentPage) {
            final String pagePath = currentPage.getPath();
            CTAModel activeCategoryCTA = links.stream().filter(cta -> pagePath.equals(cta.getLink().getPath()))
                    .findAny().orElse(null);
            if (null != activeCategoryCTA) {
                currentCategoryTitle = activeCategoryCTA.getLink().getLabel();
            }
        }
    }

    /**
     * Get title
     *
     * @return title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * Get categoriesLabel
     *
     * @return categoriesLabel
     */
    public final String getCategoriesLabel() {
        return categoriesLabel;
    }

    /**
     * Get links
     *
     * @return links
     */
    public final List<CTAModel> getLinks() {
        return new ArrayList<>(links);
    }

    /**
     * Get currentCategoryTitle
     *
     * @return currentCategoryTitle
     */
    public final String getCurrentCategoryTitle() {
        return currentCategoryTitle;
    }

}
