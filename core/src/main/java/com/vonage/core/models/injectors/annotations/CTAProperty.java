package com.vonage.core.models.injectors.annotations;

import com.vonage.core.models.injectors.impl.CTAPropertyInjector;
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
@Source(CTAPropertyInjector.INJECTOR_NAME)
public @interface CTAProperty {

    /** @return link property name */
    String linkPropertyName() default "href";

    /** @return link label property name */
    String labelPropertyName() default "label";

     /** @return ctaBackgroundColor property name */
     String ctaBackgroundColorPropertyName() default "ctaBackgroundColor";

    /** @return link target property name */
    String targetPropertyName() default "target";

    /** @return link type property name */
    String typePropertyName() default "type";

    /** @return ariaLabel property name */
    String ariaLabelPropertyName() default "ariaLabel";

    /** @return ariaLabel property name */
    String staticLabelPropertyName() default "staticLabel";

    /** @return dataVideoId property name */
    String dataVideoIdPropertyName() default "dataVideoId";

    /** @return default value for label */
    String labelValue() default "";

    /** @return default value for label */
    String ctaBackgroundColorPropertyValue() default "default";

    /** @return default value for target */
    String targetValue() default "";

    /** @return default value for type */
    String typeValue() default "";

    /** @return default value for ariaLabel */
    String ariaLabelValue() default "";

    /** @return default value for staticLabel */
    String staticLabelValue() default "";

    /** @return default value for dataVideoId */
    String dataVideoIdValue() default "";
}
