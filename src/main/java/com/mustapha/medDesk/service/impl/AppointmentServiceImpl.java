package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentSlotResponse;
import com.mustapha.medDesk.dto.response.Appointment.DayScheduleResponse;
import com.mustapha.medDesk.enums.DayOfWeek;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.AppointmentMapper;
import com.mustapha.medDesk.model.Appointment;
import com.mustapha.medDesk.model.WorkingHours;
import com.mustapha.medDesk.repository.AppointmentReposiotry;
import com.mustapha.medDesk.repository.PatientRepository;
import com.mustapha.medDesk.repository.WorkingHoursRepository;
import com.mustapha.medDesk.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentReposiotry appointmentReposiotry;
    private final AppointmentMapper appointmentMapper;
    private final WorkingHoursRepository workingHoursRepository;
    private final PatientRepository patientRepository;

    @Override
    public AppointmentDtoResponse create(AppointmentDtoRequest dto) {

        //get Appointment date from dto request
        java.time.DayOfWeek jdkDay = dto.getAppointmentDate().getDayOfWeek();

        // Transform date to my Customize date form monday-12-2026 to MONDAY
        DayOfWeek customDay = DayOfWeek.valueOf(jdkDay.name());

        // check if te day in request is exist or not
        WorkingHours workingHours = workingHoursRepository
                .findByDayOfWeek(customDay)
                .orElseThrow(() -> new ValidationException("No working hours set for this day"));
        // if day exist get gonna got that day StartTime and endTime
        LocalTime workStart = workingHours.getStartTime();
        LocalTime workEnd = workingHours.getEndTime();

        // we valid the appointment like start time should be before end time --Logic
        if (dto.getScheduleTimeStart().isBefore(workStart) ||
                dto.getScheduleTimeStart().isAfter(workEnd.minusMinutes(30))) {
            throw new ValidationException("Appointment time must be within working hours");
        }


        /**
         * here we go through all that day Times
         */
        LocalTime slotStart = workStart;
        boolean slotFound = false;
        Appointment newAppointment = null;

        while (!slotStart.isAfter(workEnd.minusMinutes(30))) {

            // here we take the start time and end time (endTime is startTime + 30min)
            LocalTime slotEnd = slotStart.plusMinutes(30);

            // i pass start time and endTime --> to check if exist already in dabatabse
            boolean exists = appointmentReposiotry.existsByAppointmentDateAndScheduleTimeStartAndScheduleTimeEnd(
                    dto.getAppointmentDate(), slotStart, slotEnd
            );

            // if not exist we create new appointment
            if (!exists && slotStart.equals(dto.getScheduleTimeStart())) {
                newAppointment = new Appointment();


                newAppointment.setAppointmentDate(dto.getAppointmentDate());

                newAppointment.setScheduleTimeStart(slotStart);
                newAppointment.setScheduleTimeEnd(slotEnd);
                newAppointment.setPatient(
                        patientRepository.findById(dto.getPatientId())
                                .orElseThrow(() -> new ValidationException("Patient not found"))
                );
                newAppointment.setAppointmentStatus(dto.getAppointmentStatus());

                // we save created appointment in db
                appointmentReposiotry.save(newAppointment);
                 // we set slotFound true to stop whileLoop
                slotFound = true;
                break;
            }

            slotStart = slotStart.plusMinutes(30);
        }

        if (!slotFound) {
            throw new ValidationException("No available appointment slots at this time");
        }

        return appointmentMapper.toDto(newAppointment);
    }



    @Override
    public AppointmentDtoResponse update(Long id, AppointmentDtoRequest dto) {

        Appointment appointment = appointmentReposiotry.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        appointment.setAppointmentStatus(dto.getAppointmentStatus());
        Appointment saved = appointmentReposiotry.save(appointment);
        return appointmentMapper.toDto(saved);
    }

    @Override
    public Page<AppointmentDtoResponse> findAll(int page, int size) {

        return appointmentReposiotry.findAll(PageRequest.of(page, size))
                .map(appointmentMapper::toDto);
    }

    @Override
    public AppointmentDtoResponse getById(Long id) {
        return appointmentReposiotry.findById(id)
                .map(appointmentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    @Override
    public void delete(Long id) {

        if (!appointmentReposiotry.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        appointmentReposiotry.deleteById(id);
    }


    // This is the responsible to return the all available or not available in certain date
    public List<AppointmentSlotResponse> getSlotsByDate(LocalDate date) {

        DayOfWeek customDay = DayOfWeek.valueOf(date.getDayOfWeek().name());
        WorkingHours hours = workingHoursRepository.findByDayOfWeek(customDay)
                .orElse(null);

        if (hours == null) return new ArrayList<>();

        List<AppointmentSlotResponse> allSlots = new ArrayList<>();
        LocalTime current = hours.getStartTime();

        while (!current.isAfter(hours.getEndTime().minusMinutes(30))) {
            LocalTime next = current.plusMinutes(30);

            boolean isBooked = appointmentReposiotry.existsByAppointmentDateAndScheduleTimeStartAndScheduleTimeEnd(
                    date, current, next
            );

            allSlots.add(AppointmentSlotResponse.builder()
                    .startTime(current)
                    .endTime(next)
                    .isAvailable(!isBooked)
                    .build());

            current = next;
        }
        return allSlots;
    }





    // method responsible to return all days and available and unVailable appointments
    @Override
    public List<DayScheduleResponse> getWeeklyAvailability() {

        List<WorkingHours> allWorkingHours = workingHoursRepository.findAll();
        List<DayScheduleResponse> weeklySchedule = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (WorkingHours hours : allWorkingHours) {
        /*
        CALCULATING THE CALENDAR DATE
        Since your database only stores the name of the day we must
        calculate the actual calendar date for the upcoming occurrence
        of that day so we can check for real appointments in the
        appointment table.
        */
            DayOfWeek customDay = hours.getDayOfWeek();
            java.time.DayOfWeek targetDay = java.time.DayOfWeek.valueOf(customDay.name());

            LocalDate nextOccurence = today;
            while (nextOccurence.getDayOfWeek() != targetDay) {
                nextOccurence = nextOccurence.plusDays(1);
            }

        /*
        GENERATING SLOTS FOR THIS SPECIFIC DAY
        We reuse the slot generation logic. We loop from the opening
        time to the closing time for this specific day and check
        if any appointments exist on that calculated date.
        */
            List<AppointmentSlotResponse> slotsForDay = new ArrayList<>();
            LocalTime current = hours.getStartTime();

            while (!current.isAfter(hours.getEndTime().minusMinutes(30))) {
                LocalTime next = current.plusMinutes(30);

                boolean booked = appointmentReposiotry.existsByAppointmentDateAndScheduleTimeStartAndScheduleTimeEnd(
                        nextOccurence, current, next
                );

                slotsForDay.add(AppointmentSlotResponse.builder()
                        .startTime(current)
                        .endTime(next)
                        .isAvailable(!booked)
                        .build());

                current = next;
            }

        /*
        MAPPING TO THE WEEKLY RESPONSE
        We bundle the day name the calculated date and all the
        generated slots into one object to be sent to the frontend.
        */
            weeklySchedule.add(DayScheduleResponse.builder()
                    .dayOfWeek(customDay)
                    .date(nextOccurence)
                    .slots(slotsForDay)
                    .build());
        }

        return weeklySchedule;
    }
}