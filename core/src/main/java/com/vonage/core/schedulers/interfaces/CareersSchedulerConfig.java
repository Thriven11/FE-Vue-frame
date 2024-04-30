package com.vonage.core.schedulers.interfaces;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration file for Careers Scheduler
 *
 * @author Vonage
 *
 */
@ObjectClassDefinition(
  name = "Careers Scheduler",
  description = "Careers Data Sync Scheduler"
)
public @interface CareersSchedulerConfig {
  /**
   * schedulerName
   * @return String name
   */
  @AttributeDefinition(
    name = "Scheduler name",
    description = "Scheduler name",
    type = AttributeType.STRING
  )
  String schedulerName() default "Careers Scheduler";

  /**
   * schedulerConcurrent
   * @return schedulerConcurrent
   */
  @AttributeDefinition(
    name = "Concurrent",
    description = "Schedule task concurrently",
    type = AttributeType.BOOLEAN
  )
  boolean schedulerConcurrent() default true;

  /**
   * serviceEnabled
   * @return serviceEnabled
   */
  @AttributeDefinition(
    name = "Enabled",
    description = "Enable Scheduler",
    type = AttributeType.BOOLEAN
  )
  boolean serviceEnabled() default true;

  /**
   * schedulerExpression
   * @return schedulerExpression
   */
  @AttributeDefinition(
    name = "Expression",
    description = "Cron-job expression. Default: run every hour.",
    type = AttributeType.STRING
  )
  String schedulerExpression() default "0 0 0/1 1/1 * ? *";
}
