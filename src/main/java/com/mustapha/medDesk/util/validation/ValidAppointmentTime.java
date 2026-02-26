package com.mustapha.medDesk.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
/**
 * this used to define where we can use annotation is it top of the filed or top of the method or top of the class
 * --> in our case Type is in the top of class
 */


@Retention(RetentionPolicy.RUNTIME)
/**
 * This used to tell spring boot wich time should read this annotation in our case Runtime(when app is runing)
 */


@Constraint(validatedBy = AppointmentTimeValidator.class)
/**
 * ==== The important  line ===
 * this annotation means when u find this annotation use this class
 * in our cese use AppointmentTimeValidator class
 */


/**
 * any Custom Constraint should have those method below based on Jakarta validation Specification
 */
@Documented
public @interface ValidAppointmentTime {
    // default message if the validation failed
    String message() default "Start time must be before end time";

   // we use it if we wanna to dived validation to groups
    Class<?>[] groups() default {};

    // it rar when we use it bit for adding metadata
    Class<? extends Payload>[] payload() default {};
}




