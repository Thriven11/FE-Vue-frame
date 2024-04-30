package com.vonage.core.models.injectors.impl;

import com.vonage.core.beans.FormattedDate;
import com.vonage.core.beans.impl.FormattedDateImpl;
import com.vonage.core.models.injectors.annotations.FormattedDateProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.osgi.service.component.annotations.Component;
import org.apache.sling.api.resource.Resource;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

/**
 * Injector class used by Sling Model properties with the
 * attribute.
 *
 * @author Vonage
 *
 */
@Component
public final class FormattedDatePropertyInjector implements Injector {

    /** name of injector */
    public static final String INJECTOR_NAME = "formatted-date-property-injection";

    @Override
    public String getName() {
        return INJECTOR_NAME;
    }

    @Override
    public Object getValue(final Object adaptable, final String name, final Type declaredType,
                           final AnnotatedElement element, final DisposalCallbackRegistry callback) {

        if (!element.isAnnotationPresent(FormattedDateProperty.class) || !(adaptable instanceof Resource)
                || !TypeUtils.isAssignable(declaredType, FormattedDate.class)) {
            return null;
        }

        final FormattedDateProperty annotation = element.getAnnotation(FormattedDateProperty.class);

        if (StringUtils.isEmpty(annotation.yearFormat())) {
            return null;
        }

        return new FormattedDateImpl(annotation.yearFormat());
    }

}
