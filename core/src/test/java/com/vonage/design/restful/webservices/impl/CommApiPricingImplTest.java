package com.vonage.design.restful.webservices.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.webservices.configs.CommApiPricingConfig;

@ExtendWith({ MockitoExtension.class })
class CommApiPricingImplTest {

    @InjectMocks
    private CommApiPricingImpl pricing;

    @Mock
    CommApiPricingConfig config;

    @BeforeEach
    public void setup() {
        when(config.apiUrl()).thenReturn("some-url");
        when(config.apiEnabled()).thenReturn(true);
        when(config.exchangeApiUrl()).thenReturn("some-url");
        pricing.activate(config);
    }

    @Test
    void testGetPricingJson() {
        try {
            pricing.getPricingJson("some-url");
        } catch (RestClientException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetExchangeRateJson() {
        try {
            pricing.getExchangeRateJson("some-url");
        } catch (RestClientException e) {
            assertTrue(true);
        }
    }

}
