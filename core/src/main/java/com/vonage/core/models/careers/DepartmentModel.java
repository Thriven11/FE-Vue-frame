package com.vonage.core.models.careers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Careers Departments
 *
 * @author Vonage
 */
@Model(adaptables = {Resource.class})
public interface DepartmentModel {

    /**
     * Id
     *
     * @return id
     */
    @ValueMapValue(name = "id")
    String getId();

    /**
     * Name
     *
     * @return String
     */
    @ValueMapValue(name = "name")
    String getName();
}
