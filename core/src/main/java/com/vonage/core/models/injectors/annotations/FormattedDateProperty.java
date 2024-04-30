package com.vonage.core.models.injectors.annotations;

import com.vonage.core.models.injectors.impl.FormattedDatePropertyInjector;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vonage
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@InjectAnnotation
@Source(FormattedDatePropertyInjector.INJECTOR_NAME)
public @interface FormattedDateProperty {

    /** @return {@link java.text.SimpleDateFormat} date format */
    String yearFormat();

    /** @return defaultDate */
    String defaultDate() default StringUtils.EMPTY;

}
