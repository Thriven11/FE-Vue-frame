package com.vonage.core.models.content;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * Sling Model for Faq Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface FaqComponentModel {

    /**
     * Faq Question
     * @return question
     */
    @Inject @Default(values = "This is the question")
    String getQuestion();

    /**
     * Lbael for Faq Question
     * @return questionLabel
     */
    @Inject @Default(values = "")
    String getQuestionLabel();

    /**
     * Faq Answer
     * @return answer
     */
    @Inject @Default(values = "This is the answer")
    String getAnswer();

    /**
     * Faq Answer
     * @return answerLabel
     */
    @Inject @Default(values = "")
    String getAnswerLabel();

}
