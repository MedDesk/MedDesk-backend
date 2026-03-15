package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.workingHour.WorkingHoursDtoRequest;
import com.mustapha.medDesk.dto.response.workingHours.WorkingHoursDtoResponse;
import com.mustapha.medDesk.enums.DayOfWeek;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.WorkingHoursMapper;
import com.mustapha.medDesk.model.WorkingHours;
import com.mustapha.medDesk.repository.WorkingHoursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkingHoursServiceImplTest {

    @Mock
    private WorkingHoursRepository workingHoursRepository;

    @Mock
    private WorkingHoursMapper workingHoursMapper;

    @InjectMocks
    private WorkingHoursServiceImpl workingHoursService;

    private WorkingHoursDtoRequest dtoRequest;
    private WorkingHours workingHours;
    private WorkingHoursDtoResponse dtoResponse;

    @BeforeEach
    void setUp() {
        // prepare data before each test
        dtoRequest = new WorkingHoursDtoRequest();
        dtoRequest.setDayOfWeek(DayOfWeek.MONDAY);
        dtoRequest.setStartTime(LocalTime.of(9,0));
        dtoRequest.setEndTime(LocalTime.of(17,0));

        workingHours = new WorkingHours();
        workingHours.setId(1L);
        workingHours.setDayOfWeek(DayOfWeek.MONDAY);
        workingHours.setStartTime(LocalTime.of(9,0));
        workingHours.setEndTime(LocalTime.of(17,0));

        dtoResponse = new WorkingHoursDtoResponse();
        dtoResponse.setId(1L);
    }

    //  create
    @Test
    void create_ShouldWork_WhenDataIsValid() {
        // arrange: mock repository and mapper
        when(workingHoursRepository.findByDayOfWeek(any())).thenReturn(Optional.empty());
        when(workingHoursMapper.toEntity(any())).thenReturn(workingHours);
        when(workingHoursRepository.save(any())).thenReturn(workingHours);
        when(workingHoursMapper.toDto(any())).thenReturn(dtoResponse);

        // act: call the create method
        WorkingHoursDtoResponse response = workingHoursService.create(dtoRequest);

        // assert: check result and verify save was called
        assertNotNull(response);
        verify(workingHoursRepository, times(1)).save(any());
    }

    @Test
    void create_ShouldThrow_WhenDayAlreadyExists() {
        // arrange: day already exists in repository
        when(workingHoursRepository.findByDayOfWeek(any())).thenReturn(Optional.of(workingHours));

        // act & assert: check that exception is thrown
        assertThrows(ValidationException.class, () -> workingHoursService.create(dtoRequest));
    }

    @Test
    void create_ShouldThrow_WhenStartTimeAfterEndTime() {
        // arrange: startTime after endTime
        dtoRequest.setStartTime(LocalTime.of(18,0));
        dtoRequest.setEndTime(LocalTime.of(17,0));

        // act & assert: check that exception is thrown
        assertThrows(ValidationException.class, () -> workingHoursService.create(dtoRequest));
    }

    //  update
    @Test
    void update_ShouldWork_WhenDataIsValid() {
        // arrange: mock repository and mapper
        when(workingHoursRepository.findById(any())).thenReturn(Optional.of(workingHours));
        when(workingHoursRepository.findByDayOfWeek(any())).thenReturn(Optional.empty());
        when(workingHoursRepository.save(any())).thenReturn(workingHours);
        when(workingHoursMapper.toDto(any())).thenReturn(dtoResponse);

        // act: call the update method
        WorkingHoursDtoResponse response = workingHoursService.update(1L, dtoRequest);

        // assert: check result and verify save was called
        assertNotNull(response);
        verify(workingHoursRepository, times(1)).save(any());
    }

    @Test
    void update_ShouldThrow_WhenWorkingHoursNotFound() {
        // arrange: repository returns empty
        when(workingHoursRepository.findById(any())).thenReturn(Optional.empty());

        // act & assert: check exception
        assertThrows(ResourceNotFoundException.class, () -> workingHoursService.update(1L, dtoRequest));
    }

    @Test
    void update_ShouldThrow_WhenStartTimeAfterEndTime() {
        // arrange: startTime after endTime
        dtoRequest.setStartTime(LocalTime.of(18,0));
        dtoRequest.setEndTime(LocalTime.of(17,0));
        when(workingHoursRepository.findById(any())).thenReturn(Optional.of(workingHours));

        // act & assert
        assertThrows(ValidationException.class, () -> workingHoursService.update(1L, dtoRequest));
    }


    @Test
    void update_ShouldThrow_WhenDayAlreadyExists() {
        // arrange: another day exists in repository
        WorkingHours anotherDay = new WorkingHours();
        anotherDay.setId(2L);
        anotherDay.setDayOfWeek(DayOfWeek.FRIDAY);

        dtoRequest.setDayOfWeek(DayOfWeek.FRIDAY);

        when(workingHoursRepository.findById(any())).thenReturn(Optional.of(workingHours));
        when(workingHoursRepository.findByDayOfWeek(any())).thenReturn(Optional.of(anotherDay));

        // act & assert
        assertThrows(ValidationException.class, () -> workingHoursService.update(1L, dtoRequest));
    }

    //  getById
    @Test
    void getById_ShouldReturn_WhenExists() {
        // arrange
        when(workingHoursRepository.findById(any())).thenReturn(Optional.of(workingHours));
        when(workingHoursMapper.toDto(any())).thenReturn(dtoResponse);

        // act
        WorkingHoursDtoResponse response = workingHoursService.getById(1L);

        // assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getById_ShouldThrow_WhenNotExists() {
        // arrange
        when(workingHoursRepository.findById(any())).thenReturn(Optional.empty());

        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> workingHoursService.getById(1L));
    }

    //  getAll
    @Test
    void getAll_ShouldReturnList() {
        // arrange
        when(workingHoursRepository.findAll()).thenReturn(List.of(workingHours));
        when(workingHoursMapper.toDto(any())).thenReturn(dtoResponse);

        // act
        List<WorkingHoursDtoResponse> list = workingHoursService.getAll();

        // assert
        assertEquals(1, list.size());
        verify(workingHoursMapper, times(1)).toDto(any());
    }

    //  delete
    @Test
    void delete_ShouldWork_WhenExists() {
        // arrange
        when(workingHoursRepository.findById(any())).thenReturn(Optional.of(workingHours));
        doNothing().when(workingHoursRepository).delete(any());

        // act
        workingHoursService.delete(1L);

        // assert
        verify(workingHoursRepository, times(1)).delete(any());
    }

    @Test
    void delete_ShouldThrow_WhenNotExists() {
        // arrange
        when(workingHoursRepository.findById(any())).thenReturn(Optional.empty());

        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> workingHoursService.delete(1L));
    }
}