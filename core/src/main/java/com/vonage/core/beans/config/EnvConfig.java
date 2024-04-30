package com.vonage.core.beans.config;

/**
 * The Class EnvConfig.
 */
public final class EnvConfig {

    /** The runmodes text. */
    private String runModes;

    /** The Exact runmode text. */
    private String exactRunMode;

    /** The AEM mode text. */
    private String aemMode;

    /**
     * @return runModes
     */
    public String getRunModes() {
        return runModes;
    }

    /**
     * Set Run Modes
     * @param runModes actual server runmodes
     */
    public void setRunModes(final String runModes) {
        this.runModes = runModes;
    }

    /**
     * @return exactRunMode
     */
    public String getExactRunMode() {
        return exactRunMode;
    }

    /**
     * @param exactRunMode Cleaned runmode from actual runmode
     */
    public void setExactRunMode(final String exactRunMode) {
        this.exactRunMode = exactRunMode;
    }

    /**
     * @return aemMode
     */
    public String getAemMode() {
      return aemMode;
    }

    /**
     * @param aemMode Cleaned runmode from actual runmode
     */
    public void setAemMode(final String aemMode) {
      this.aemMode = aemMode;
    }

}
