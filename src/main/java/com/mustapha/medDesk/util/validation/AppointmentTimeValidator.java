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
public class AppointmentTimeValidator implements ConstraintValidator<ValidAppointmentTime, AppointmentDtoRequest> {


    /**
     * important: Spring call isValid method automatically
     */
    @Override
    public boolean isValid(AppointmentDtoRequest dto,
                           ConstraintValidatorContext context) {

        if (dto.getScheduleTimeStart() == null) {
            return true; // @NotNull
        }


        if (dto.getScheduleTimeEnd() == null) {
            dto.setScheduleTimeEnd(dto.getScheduleTimeStart().plusMinutes(30));
        }


        return dto.getScheduleTimeStart().isBefore(dto.getScheduleTimeEnd());
    }
}

/*
request -> dto -> @Valid -> hibernate start validatoin(See ValidAppointmentTime annotation) -> find related to AppoinmentTimeValidator clas -> call isValid -> if return false -> retunr BadREquest 400
 */
//flow







