package com.mustapha.medDesk.controller;

import com.mustapha.medDesk.dto.request.appointment.AppointmentDtoRequest;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentDtoResponse;
import com.mustapha.medDesk.dto.response.Appointment.AppointmentSlotResponse;
import com.mustapha.medDesk.dto.response.Appointment.DayScheduleResponse;
import com.mustapha.medDesk.service.impl.AppointmentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDtoResponse> create(@Valid @RequestBody AppointmentDtoRequest dto) {
        /*
        CREATING A NEW BOOKING
        This endpoint is used when a patient selects a time and submits
        the form. It validates the slot availability before saving.
        */
        return new ResponseEntity<>(appointmentService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<AppointmentSlotResponse>> getAvailability(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        /*
        VIEWING THE DAILY SCHEDULE
        The frontend calls this endpoint to get a list of all possible
        slots for a chosen day. The response tells the frontend which
        buttons should be clickable and which should be disabled.
        */
        return ResponseEntity.ok(appointmentService.getSlotsByDate(date));
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentDtoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        /*
        RETRIEVING ALL BOOKINGS
        This returns a list of all appointments for admin use.
        */
        return ResponseEntity.ok(appointmentService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDtoResponse> getById(@PathVariable Long id) {
        /*
        FETCHING APPOINTMENT DETAILS
        Retrieves one specific appointment by its id.
        */
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDtoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDtoRequest dto) {
        /*
        MODIFYING AN APPOINTMENT
        Updates an existing appointment status or record.
        */
        return ResponseEntity.ok(appointmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        /*
        CANCELING AN APPOINTMENT
        Removes the appointment from the system.
        */
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/weekly-availability")
    public ResponseEntity<List<DayScheduleResponse>> getWeeklyAvailability() {
        return ResponseEntity.ok(appointmentService.getWeeklyAvailability());
    }


    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {
        /*
        TOGGLING SLOT AVAILABILITY
        This endpoint updates the status of an existing appointment.
        If 'available' is true -> Status becomes CANCELED (Slot opens up)
        If 'available' is false -> Status becomes SCHEDULED (Slot is blocked)
        */
        appointmentService.updateAvailability(id, available);
        return ResponseEntity.ok().build();
    }




    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDtoResponse>> getByPatientId(@PathVariable Long patientId) {
        /*
        FETCHING PATIENT HISTORY
        This returns all appointments for a specific patient.
        Ideal for showing a patient their own booking history.
        */
        List<AppointmentDtoResponse> history = appointmentService.getByPatientId(patientId);
        return ResponseEntity.ok(history);
    }


}