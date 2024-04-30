package com.vonage.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vonage.core.config.OneTrustConfig;
import com.vonage.core.services.OneTrustService;

/**
 * osgi servcie impl
 */
@Component(immediate = true, service = OneTrustService.class)
@Designate(ocd = OneTrustConfig.class)
public class OneTrustServiceImpl implements OneTrustService {
    /**
    * logger
    */
    private static final Logger LOG = LoggerFactory.getLogger(OneTrustServiceImpl.class);
    /**
    * osgi servcie oneTrustCode
    */
    private String[] oneTrustCode;

    /**
    * activate method
    * @param config config
    */
    @Activate
    public final void  activate(final OneTrustConfig config) {
        oneTrustCode = config.oneTrustCode();
    }
    /**
    * oneTrustCode
    * @return oneTrustCode
    */
    @Override
    public final String[] getOneTrustCode() {
        return oneTrustCode;
    }
    /**
    * getCodePerLcoale
    * @return getCodePerLcoale
    */

    @Override
    public final String getCodePerLocale(final String locale) {
        int codeLength = oneTrustCode.length;

        for (int i = 0; i < codeLength; i++) {
            if (oneTrustCode[i].contains(locale)) {
                String[] assetValue = oneTrustCode[i].split(":");
                return assetValue[1];
            }
        }

        return null;
    }
}
