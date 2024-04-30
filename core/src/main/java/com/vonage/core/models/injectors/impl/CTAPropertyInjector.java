package com.vonage.core.models.injectors.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

import com.vonage.core.beans.CTA;
import com.vonage.core.beans.impl.CTAImpl;
import com.vonage.core.models.injectors.annotations.CTAProperty;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.osgi.service.component.annotations.Component;

/**
 * Injector class used by Sling Model properties with the {@link CTAProperty}
 * attribute.
 *
 * @author Vonage
 *
 */
@Component
public final class CTAPropertyInjector implements Injector {

    /** name of injector */
    public static final String INJECTOR_NAME = "cta-property-injection";

    @Override
    public String getName() {
        return INJECTOR_NAME;
    }

    @Override
    public Object getValue(final Object adaptable, final String name, final Type declaredType,
            final AnnotatedElement element, final DisposalCallbackRegistry callback) {

        if (!element.isAnnotationPresent(CTAProperty.class) || !(adaptable instanceof Resource)
                || declaredType != CTA.class) {
            return null;
        }

        final CTAProperty annotation = element.getAnnotation(CTAProperty.class);

        if (StringUtils.isBlank(annotation.linkPropertyName()) || StringUtils.isBlank(annotation.labelPropertyName())
                || StringUtils.isBlank(annotation.targetPropertyName())
                || StringUtils.isBlank(annotation.typePropertyName())) {
            return null;
        }

        final Resource resource = (Resource) adaptable;
        final ValueMap valueMap = resource.getValueMap();
        final Boolean checkDefault = false;
        final String link = valueMap.get(annotation.linkPropertyName(), StringUtils.EMPTY);
        final String label = valueMap.get(annotation.labelPropertyName(), annotation.labelValue());
        final String ctaBackgroundColor = valueMap.get(annotation.ctaBackgroundColorPropertyName(),
                annotation.ctaBackgroundColorPropertyValue());
        final String target = valueMap.get(annotation.targetPropertyName(), annotation.targetValue());
        final String type = valueMap.get(annotation.typePropertyName(), annotation.typeValue());
        final String ariaLabel = valueMap.get(annotation.ariaLabelPropertyName(), annotation.ariaLabelValue());
        final String staticLabel = valueMap.get(annotation.staticLabelPropertyName(), annotation.staticLabelValue());
        final String dataVideoId = valueMap.get(annotation.dataVideoIdPropertyName(), annotation.dataVideoIdValue());
        return new CTAImpl(link, label, target, type, ariaLabel, staticLabel, dataVideoId, ctaBackgroundColor,
                checkDefault);
    }
}
