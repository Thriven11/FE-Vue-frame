package com.vonage.core.models.ecommerce.solutionfinder;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

/**
 * Model class for solution finder
 */
@Model(adaptables = Resource.class)
public interface SolutionFinderModel {

    /**
     * @return Choose Device Model
     */
    @ChildResource(name = "chooseDevice", injectionStrategy = InjectionStrategy.OPTIONAL)
    ChooseDeviceModel getChooseDevice();

    /**
     * @return Company Size Model
     */
    @ChildResource(name = "companySize", injectionStrategy = InjectionStrategy.OPTIONAL)
    CompanySizeModel getCompanySize();

    /**
     * @return Goals Model
     */
    @ChildResource(name = "goals", injectionStrategy = InjectionStrategy.OPTIONAL)
    GoalsModel getGoals();

    /**
     * @return Plan Recommendation Model
     */
    @ChildResource(name = "planRecommendation", injectionStrategy = InjectionStrategy.OPTIONAL)
    PlanRecommendationModel getPlanRecommendation();

    /**
     * @return Phones And Accesories Model
     */
    @ChildResource(name = "phonesAndAccesories", injectionStrategy = InjectionStrategy.OPTIONAL)
    PhonesAndAccesoriesModel getPhonesAndAccesories();

    /**
     * @return Addons Model
     */
    @ChildResource(name = "addons", injectionStrategy = InjectionStrategy.OPTIONAL)
    GoalsModel getAddons();
}
