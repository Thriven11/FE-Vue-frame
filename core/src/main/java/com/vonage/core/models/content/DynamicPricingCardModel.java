package com.vonage.core.models.content;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import com.day.cq.wcm.api.Page;


/**
 * Sling Model for Country List
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicPricingCardModel {

    /**
     * inject CurrentPage
     */
    @Inject
    private Page currentPage;

    /**
     * resourceResolver variable
     */
    @Inject
    private ResourceResolver resourceResolver;

    /**
     * usVirtal
     */

    private String usVirtaul;

     /**
     * ussms
     */

    private String usSMS;

    /**
     * usTollFree
     */

    private String usTollFree;

     /**
     * caVirtal
     */

    private String caVirtaul;


     /**
     * Default path for us virtal constants
     */

    private final String defusvirtual = "/content/experience-fragments/vonage/en-us/pricing"
                                          + "/api---additional-carrier-fees-us-sms-virtual/master/jcr:content/root";
    /**
     * efault path for us SMS constants
     */
    private final String defussms = "/content/experience-fragments/vonage/en-us/pricing"
                                        + "/api---additional-carrier-fees-us-sms-short-code/master/jcr:content/root";
    /**
     * default path for us Tollfree constants
     */
    private final String defustollfree = "/content/experience-fragments/vonage/en-us/pricing"
                                        + "/api---additional-carrier-fees-us-sms-tollfree/master/jcr:content/root";
    /**
     * default path for ca virtal constants
     */
    private final String defcavirtual = "/content/experience-fragments/vonage/en-us/pricing"
                                       +  "/api---additional-carrier-fees-ca-sms-virtual/master/jcr:content/root";
    /**
     * init method
     */
    @PostConstruct
    protected final void init() {
      String countryCode = currentPage.getLanguage().getCountry().toLowerCase();
      String languageCode = currentPage.getLanguage().getLanguage().toLowerCase();
      String virtualUS = "/content/experience-fragments/vonage/"
                          + languageCode
                          + "-"
                          + countryCode
                          + "/pricing/api---additional-carrier-fees-us-sms-virtual/master/jcr:content/root";
      String smsUS = "/content/experience-fragments/vonage/"
                          + languageCode
                          + "-"
                          + countryCode
                          + "/pricing/api---additional-carrier-fees-us-sms-short-code/master/jcr:content/root";
      String tollFreeUS = "/content/experience-fragments/vonage/"
              + languageCode
              + "-"
              + countryCode
              + "/pricing/api---additional-carrier-fees-us-sms-tollfree/master/jcr:content/root";
      String virtualCA = "/content/experience-fragments/vonage/"
                          + languageCode
                          + "-"
                          + countryCode
                          + "/pricing/api---additional-carrier-fees-ca-sms-virtual/master/jcr:content/root";

      Resource resourceVirtualUS  = resourceResolver.getResource(virtualUS);
      if (resourceVirtualUS != null) {
        usVirtaul = virtualUS;
      } else {
        usVirtaul = defusvirtual;
      }

      Resource resourcesmsUSUS  = resourceResolver.getResource(smsUS);
      if (resourcesmsUSUS != null) {
        usSMS = smsUS;
      } else {
        usSMS = defussms;
      }

      Resource resourceTollfreeUS  = resourceResolver.getResource(tollFreeUS);
      if (resourceTollfreeUS != null) {
           usTollFree = tollFreeUS;
      } else {
           usTollFree = defustollfree;
      }


      Resource resourceVirtualCA  = resourceResolver.getResource(virtualCA);
      if (resourceVirtualCA != null) {
        caVirtaul = virtualCA;
      } else {
        caVirtaul = defcavirtual;
      }


    }


  /**
   *  @return path of ca virtual
   *
   */
  public final String getCaVirtaul() {
    System.out.println("WelcomeCA" + caVirtaul);
      return caVirtaul;
    }

    /**
   *   @return path of us sms
   *
   */
  public final String getUsSMS() {
    System.out.println("WelcomeusSMS" + usSMS);
    return usSMS;
  }

  /**
   *  @return path of us virtual
   *
   */
  public final String getUsVirtaul() {
    System.out.println("WelcomeusVirtaul"  + usVirtaul);
    return usVirtaul;
  }

  /**
   *  @return path of us tollfree
   *
   */
  public final String getUsTollFree() {
    System.out.println("WelcomeusTollfree"  + usTollFree);
    return usTollFree;
  }

}
