package com.vonage.core.models.structure;
import java.util.List;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Company Companies
 * has a name and a list of companies
 */
@Model(
  adaptables = {Resource.class},
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface ListModel {
  /**
   *
   * @return title
   */
  @ValueMapValue(name = "title",
    injectionStrategy = InjectionStrategy.OPTIONAL)
  String getTitle();
  /**
   * Company companies
   * @return Company
   */
  @Inject
  List<Company> getList(); // the name `getList` corresponds to the multifield name="./list"
  /**
   * Company model
   * has a name and a list of departments
   */
  @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
  interface Company {

    /**
     * String ListItem
     * @return ListItem
     */
    @Inject
    String getListItem();
    /**
     * String Company BulletPoints
     * the name `getSubList` corresponds to the multifield name="./sublist"
     * @return Department object
     */
    @Inject
    List<SubListItem> getSubList();
  }
  /**
   * Department model
   * has a name and a manager
   */
  @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
  interface SubListItem {
    /**
     * String Sub list Item
     * @return Sub list Item
     */
    @Inject
    String getSubListItem();
  }
}
