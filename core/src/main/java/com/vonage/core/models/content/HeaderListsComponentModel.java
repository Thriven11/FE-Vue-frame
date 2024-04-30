package com.vonage.core.models.content;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * Sling Model for Header Lists Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface HeaderListsComponentModel {

    /**
     * Card One List
     * @return cardOne
     */
    @Inject
    List<HeaderList> getCardOne();

    /**
     * Card Two List
     * @return cardTwo
     */
    @Inject
    List<HeaderList> getCardTwo();

    /**
     * Header Lists Title
     * @return title
     */
    @Inject
    String getTitle();

    /**
     * Header Lists Description
     * @return description
     */
    @Inject
    String getDescription();

    /**
     * Card One Title
     * @return cardOneTitle
     */
    @Inject
    String getCardOneTitle();

    /**
     * Card Two Title
     * @return cardTwoTitle
     */
    @Inject
    String getCardTwoTitle();

    /**
     * Sling model for Header List.
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public interface HeaderList {

        /**
         * @return listItem
         */
        @Inject
        String getListItem();
    }

}
