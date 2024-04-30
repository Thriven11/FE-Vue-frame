package com.vonage.core.models.content;

import com.vonage.core.models.CTAModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling model for Customer Story Component
 *
 * @author Vonage
 */
@Model(adaptables = {Resource.class})
public interface CustomerStorySummaryComponentModel {

    /**
     * Link List Title
     *
     * @return title
     */
    @ValueMapValue(name = "title",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     * Link List
     *
     * @return Link List
     */
    @ChildResource(name = "storyList1",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<CTAModel> getStoryList1();

    /**
     * Link List
     *
     * @return Link List
     */
    @ChildResource(name = "storyList2",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<CTAModel> getStoryList2();

}
