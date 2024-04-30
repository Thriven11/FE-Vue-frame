package com.vonage.core.services;


/**
 *
 * @author
 *
 */
public interface OneTrustService {
     /**
    * oneTrustCode
    * @return oneTrustCode
    */
    String[] getOneTrustCode();
     /**
    * oneTrustCode
    * @param  locale locale
    * @return oneTrustCode
    */
    String getCodePerLocale(final String locale);

}
