package com.mustapha.medDesk.util.validation;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.util.validation.ValidAppointmentTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * ConstrainValidator<A, T> menas:
 * A: annotation
 * T: type of the class we gonna use
 */
public class AppointmentTimeValidator implements ConstraintValidator<ValidAppointmentTime, AppointmentDtoRequest>{


    /**
     * important: Spring call isValid method automatically
     */
    @Override
    public boolean isValid(AppointmentDtoRequest dto,
                           ConstraintValidatorContext context) {

        if (dto.getScheduleTimeStart() == null ||
                dto.getScheduleTimeEnd() == null) {
            return true; // here we made a good point if it's null the @Valid handle @NotNull not the our customize validator
        }


        // the real condition
        /**
         * start date < end date retunr true
         * start date > end date return false
         */
        return dto.getScheduleTimeStart()
                .isBefore(dto.getScheduleTimeEnd());
    }
}

/*
request -> dto -> @Valid -> hibernate start validatoin(See ValidAppointmentTime annotation) -> find related to AppoinmentTimeValidator clas -> call isValid -> if return false -> retunr BadREquest 400
 */
//flow







