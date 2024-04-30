package com.vonage.core.models.content;

import com.vonage.core.beans.FormattedDate;
import com.vonage.core.models.injectors.annotations.FormattedDateProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;


/**
 * Sling model for Copy Right
 */
@Model(adaptables =  Resource.class)
public interface CopyRightComponentModel {

    /**
     * get current year
     * @return current year from server
     */
    @FormattedDateProperty(yearFormat = "YYYY")
    @Optional
    FormattedDate getSystemDate();
}
