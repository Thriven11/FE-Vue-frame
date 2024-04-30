package com.vonage.core.constants;

/**
 * Common Careers Constants Class for Vonage Careers specific Constants.
 */
public final class VngCareersConstants {

    /**
     * Static class should not be initialized.
     */
    private VngCareersConstants() {
    }

  /**
   * Careers jobs path where the jobs are saved in JCR
   */
  public static final String CAREERS_JOBS_PATH = "/var/vonage/careers/jobs";

  /**
   * Careers offices path where the jobs are saved in JCR
   */
  public static final String CAREERS_OFFICE_PATH = "/var/vonage/careers/offices";

  /**
   * Careers departments path where the jobs are saved in JCR
   */
  public static final String CAREERS_DEPARTMENTS_PATH = "/var/vonage/careers/departments";

    /**
   * Careers job details page url
   */
  public static final String CAREERS_JOB_DETAILS_URL = "/careers/job-details/";

}
