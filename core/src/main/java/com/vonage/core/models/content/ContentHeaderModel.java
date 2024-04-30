package com.vonage.core.models.content;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

/**
 * Sling Model for Content Header
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentHeaderModel {

    /**
     * headlineText
     */
    @ValueMapValue(name = "headlineText", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String headlineText;

    /**
     * description
     */
    @ValueMapValue(name = "contentDate", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String contentDate;

    /**
     * currentPage variable
     */
    @Inject
    private Page currentPage;

    /**
     * log variable
     */
    private static final Logger LOG = LoggerFactory.getLogger(ContentHeaderModel.class);

    /**
     * init method
     */
    @PostConstruct
    protected final void init() {
        try {
            Locale pageLocale = currentPage.getLanguage();
            final Date datePublished = currentPage.getProperties().get("datePublished", Date.class);
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, pageLocale);
            if (datePublished != null) {
              DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
              contentDate = df.format(datePublished);
            } else if (StringUtils.isNotBlank(contentDate)) {
              Date date = new SimpleDateFormat("MMMM dd, yyyy").parse(contentDate);
              contentDate = dateFormat.format(date);
            }
        } catch (ParseException e) {
            LOG.error("There is an exception while parsing string into date", e);
        }
    }

    /**
     * get headlineText
     *
     * @return headlineText
     */
    public final String getHeadlineText() {
        return headlineText;
    }

    /**
     * get contentDate
     *
     * @return contentDate
     */
    public final String getContentDate() {
        return contentDate;
    }
}
