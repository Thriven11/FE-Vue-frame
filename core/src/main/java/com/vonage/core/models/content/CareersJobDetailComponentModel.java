package com.vonage.core.models.content;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.commons.lang.StringEscapeUtils;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;

import com.google.gson.JsonObject;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.services.CareersService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;


//import com.vonage.core.constants.VonageImageConstants;

/**
 * Sling Model for Careers Job Detail Component
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class CareersJobDetailComponentModel {

    /**
     * log variable
     */
    private static final Logger LOG = LoggerFactory.getLogger(CareersJobDetailComponentModel.class);

    /** careers data path - add to vonage constants - remove node job id */
    private static final String CAREERS_JOB_PATH = "/var/vonage/careers/jobs/";

    /** ID var */
    private String id;

    /** requisitionID var */
    private String requisitionID;

    /** jobData var */
    private String jobData;

    /** jobTitle var */
    private String jobTitle;

    /** career var */
    private String career;

    /** jobLocation var */
    private String jobLocation;

    /** offices list var */
    private List<String> officesList;

    /** jobLocation var */
    private String datePosted;

    /** applyURL var */
    private String applyURL;

    /** jobDescription var */
    private String jobDescription;

    /**
     * SlingHttpServletRequest
     */
    @Inject
    private SlingHttpServletRequest request;

    /**
     * resource resolver
     */
    @Inject
    private ResourceResolver resourceResolver;

     /**
   * Careers Service
   */
    @OSGiService
    private CareersService careersService;

    /**
     * Externalizer
     */
    @Inject // OSGi Service
    private Externalizer externalizer;

    /**
     * category variable
     */
    private String browserUrl;

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
        setJobValues();
        setBrowserUrl();
    }

    /**
     * browser url
     */
    private void setBrowserUrl() {
        this.browserUrl = externalizer.publishLink(resourceResolver, "https", this.currentPage.getPath());
        this.browserUrl = this.browserUrl + this.id + AppConstants.SLASH;
    }

    /**
     * Set the job detail values
     */
    private void setJobValues() {
        LOG.info("------ JOB DETAIL MODEL START -------");

        try {

            final RequestPathInfo requestPathInfo = request.getRequestPathInfo();
            final String selectorID = requestPathInfo.getSelectorString();

            if (selectorID != null) {

                // put location data into object
                final JsonObject jobDetailObject = careersService.getJobResourceAsJsonObject(selectorID);
                if (null != jobDetailObject) {
                    final JsonObject jobLocationObject = jobDetailObject.getAsJsonObject("location");

                    // offices array
                    this.officesList = new ArrayList<String>();
                    final JsonArray jobOfficesArray = jobDetailObject.getAsJsonArray("offices");
                    // loop offices and add to offices list
                    for (JsonElement offices : jobOfficesArray) {
                        final JsonObject officesObject = offices.getAsJsonObject();
                        final String officeName = officesObject.get("name").getAsString();
                        officesList.add(officeName);
                    }
                    LOG.info("OFFICES LIST: " + officesList);

                    // put departments array into object
                    final JsonArray  jobDepartmentsArray = jobDetailObject.getAsJsonArray("departments");
                    final JsonObject  jobDepartmentsObject = jobDepartmentsArray.get(0).getAsJsonObject();
                    // set the values from the json objects
                    this.id = jobDetailObject.get("id").getAsString();
                    this.requisitionID = jobDetailObject.get("requisition_id").getAsString();
                    this.jobTitle = jobDetailObject.get("title").getAsString();
                    this.career = jobDepartmentsObject.get("name").getAsString();
                    //this.jobLocation = jobLocationObject.get("name").getAsString();
                    this.jobLocation = jobLocationObject.get("name").getAsString();

                    // convert date from string value
                    //data format 2020-04-12T15:10:52-04:00
                    String dateString = jobDetailObject.get("updated_at").getAsString();
                    SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy");
                    Date parsedDate = dateParser.parse(dateString);
                    this.datePosted = dateFormatter.format(parsedDate);
                    this.applyURL = jobDetailObject.get("absolute_url").getAsString();
                    // unescape html then set job description from converted string
                    final String jobContentStr = jobDetailObject.get("content").getAsString();
                    this.jobDescription = StringEscapeUtils.unescapeHtml(jobContentStr);
                } else {
                    this.jobTitle = "NO JOB FOUND";
                }

            } else {
                this.jobTitle = "NO JOB FOUND";
                LOG.info("------ NO MATCHING SELECTOR ID -------");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        LOG.info("------ JOB DETAIL MODEL END -------");
    }

   /**
     * Get id
     *
     * @return id
     */
    public final String getID() {
        return id;
    }

   /**
     * Get jobReID
     *
     * @return requisitionID
     */
    public final String getRequisitionID() {
        return requisitionID;
    }


   /**
     * Get jobData
     *
     * @return jobData
     */
    public final String getJobData() {
        return jobData;
    }


   /**
     * Get jobTitle
     *
     * @return jobTitle
     */
    public final String getJobTitle() {
        return jobTitle;
    }

   /**
     * Get career
     *
     * @return career
     */
    public final String getCareer() {
        return career;
    }

   /**
     * Get jobLocation
     *
     * @return jobLocation
     */
    public final String getJobLocation() {
        return jobLocation;
    }

   /**
     * Get officesList
     *
     * @return officesList
     */
    public final List<String> getOfficesList() {
        return officesList;
    }


   /**
     * Get datePosted
     *
     * @return datePosted
     */
    public final String getDatePosted() {
        return datePosted;
    }

   /**
     * Get applyURL
     *
     * @return applyURL
     */
    public final String getApplyURL() {
        return applyURL;
    }

   /**
     * Get jobDescription
     *
     * @return jobDescription
     */
    public final String getJobDescription() {
        return jobDescription;
    }

    /**
     * Get Browser URL
     * @return browser url
     */
    public final String getBrowserUrl() {
        return this.browserUrl;
    }


}
