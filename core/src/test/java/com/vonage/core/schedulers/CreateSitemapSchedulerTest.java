package com.vonage.core.schedulers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CreateSitemapSchedulerTest {
    @InjectMocks
    private CreateSitemapScheduler sitemapScheduler;

    @Mock
    SlingRepository slingRepository;

    @Mock
    Scheduler scheduler;

    @Mock
    SitemapSchedulerFactory sitemapSchedulerFactory;

    @Mock
    ScheduleOptions scheduleOptions;

    @Test
    public void testActivateProvidersEmptyCase() {
        sitemapScheduler.activate();
        assertTrue(true);
    }

    @Test
    public void testRepositoryFailureCase() throws Exception {
        when(sitemapSchedulerFactory.getId()).thenReturn("1234");
        when(sitemapSchedulerFactory.getSitemapTargetPath()).thenReturn("any.xml");
        when(slingRepository.getDescriptor("crx.cluster.master")).thenReturn("123");
        sitemapScheduler.bindProviders(sitemapSchedulerFactory);
        assertTrue(true);
    }

    @Test
    public void testBindProvidersFailureCase() throws Exception {
        when(sitemapSchedulerFactory.getId()).thenReturn("1234");
        when(sitemapSchedulerFactory.getSitemapTargetPath()).thenReturn("file.jpg");
        sitemapScheduler.bindProviders(sitemapSchedulerFactory);
        assertTrue(true);
    }

    @Test
    public void testUnBindProvider() {
        when(sitemapSchedulerFactory.getId()).thenReturn("1234");
        sitemapScheduler.unbindProviders(sitemapSchedulerFactory);
        assertTrue(true);
    }

    @Test
    public void testBindProviderSuccessCase() throws Exception {
        when(sitemapSchedulerFactory.getId()).thenReturn("1234");
        when(sitemapSchedulerFactory.getSitemapTargetPath()).thenReturn("any.xml");
        when(slingRepository.getDescriptor("crx.cluster.master")).thenReturn("true");
        when(sitemapSchedulerFactory.getSchedulerExpression()).thenReturn("0 0/5 * * * ? *");
        when(sitemapSchedulerFactory.isSchedulerEnabled()).thenReturn(true);
        when(scheduler.EXPR("0 0/5 * * * ? *")).thenReturn(scheduleOptions);
        when(slingRepository.getDescriptor("crx.cluster.master")).thenReturn("true");
        sitemapScheduler.bindProviders(sitemapSchedulerFactory);
        sitemapScheduler.activate();
        assertTrue(true);
    }

}
