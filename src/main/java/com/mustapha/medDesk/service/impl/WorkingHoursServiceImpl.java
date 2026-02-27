package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.workingHour.WorkingHoursDtoRequest;
import com.mustapha.medDesk.dto.response.workingHours.WorkingHoursDtoResponse;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.WorkingHoursMapper;
import com.mustapha.medDesk.model.WorkingHours;
import com.mustapha.medDesk.repository.WorkingHoursRepository;
import com.mustapha.medDesk.service.WorkingHoursService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final WorkingHoursRepository workingHoursRepository;
    private final WorkingHoursMapper workingHoursMapper;


    @Override
    public WorkingHoursDtoResponse create(WorkingHoursDtoRequest dto) {
        if(workingHoursRepository.findByDayOfWeek(dto.getDayOfWeek()).isPresent()){
            throw  new ValidationException("Day is already exists");
        }
        if(dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())){
            throw  new ValidationException("Start time must be before end time");
        }
        
        WorkingHours workingHours = workingHoursMapper.toEntity(dto);
        WorkingHours savedWorkingHours = workingHoursRepository.save(workingHours);
        return workingHoursMapper.toDto(savedWorkingHours);

    }

    @Override
    public WorkingHoursDtoResponse update(Long id, WorkingHoursDtoRequest dto) {
        WorkingHours workingHours = workingHoursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This workingHours is not found"));


        LocalTime startTimeToCheck = dto.getStartTime() != null ? dto.getStartTime() : workingHours.getStartTime();
        LocalTime endTimeToCheck = dto.getEndTime() != null ? dto.getEndTime() : workingHours.getEndTime();


        if (!startTimeToCheck.isBefore(endTimeToCheck)) {
            throw new ValidationException("Start time must be before end time");
        }


        if (dto.getDayOfWeek() != null &&
                !dto.getDayOfWeek().equals(workingHours.getDayOfWeek()) &&
                workingHoursRepository.findByDayOfWeek(dto.getDayOfWeek()).isPresent()) {
            throw new ValidationException("Day already exists");
        }


        workingHoursMapper.updateWorkingHoursFromDto(dto, workingHours);

        WorkingHours savedWorkingHours = workingHoursRepository.save(workingHours);

        return workingHoursMapper.toDto(savedWorkingHours);
    }

    @Override
    public WorkingHoursDtoResponse getById(Long id) {
        WorkingHours workingHours = workingHoursRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("This workingHours is not exist")
        );
        return workingHoursMapper.toDto(workingHours);
    }

    @Override
    public List<WorkingHoursDtoResponse> getAll() {
        return workingHoursRepository.findAll().stream().map(
                workingHours -> workingHoursMapper.toDto(workingHours)
        ).toList();
    }

    @Override
    public void delete(Long id) {
        WorkingHours workingHours = workingHoursRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("not exists")
        );
        workingHoursRepository.delete(workingHours);
    }
}
