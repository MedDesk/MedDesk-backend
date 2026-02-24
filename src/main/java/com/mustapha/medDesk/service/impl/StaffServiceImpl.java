package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.staff.StaffDtoRequest;
import com.mustapha.medDesk.dto.response.Staff.StaffDtoResponse;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.StaffMapper;
import com.mustapha.medDesk.mapper.UserMapper;
import com.mustapha.medDesk.model.Staff;
import com.mustapha.medDesk.repository.StaffRepository;
import com.mustapha.medDesk.service.StaffService;
import com.mustapha.medDesk.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final UserMapper userMapper;


    @Override
    public StaffDtoResponse create(StaffDtoRequest dto) {
        boolean isExists = this.staffRepository.findByEmail(dto.getEmail()).isPresent();
        if(isExists){
            throw  new ValidationException("account with this email is already exists");
        }
        Staff staff = staffMapper.toEntity(dto);
        staff.setPassword(PasswordUtil.hash(dto.getPassword()));

        Staff savedStaff  = staffRepository.save(staff);

        return staffMapper.toDto(savedStaff);

    }

    @Override
    public Page<StaffDtoResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return staffRepository.findAll(pageable).map(
                staff -> staffMapper.toDto(staff)
        );
    }

    @Override
    public StaffDtoResponse update(long id, StaffDtoRequest staffDtoRequest) {
        Staff staff = staffRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("this user notFount"));
        staffMapper.updateStaffFromDto(staffDtoRequest, staff);
        staffRepository.save(staff);
        return staffMapper.toDto(staff);
    }

    @Override
    public StaffDtoResponse getById(Long id) {

        Staff staff = staffRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user notFount"));
        return staffMapper.toDto(staff);
    }

    @Override
    public void delete(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        this.staffRepository.delete(staff);
    }
}
