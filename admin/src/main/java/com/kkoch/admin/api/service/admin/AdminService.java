package com.kkoch.admin.api.service.admin;

import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.api.service.admin.dto.EditAdminDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public Long addAdmin(AddAdminDto dto) {
        Admin admin = dto.toEntity();
        Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin.getId();
    }

    //dto로 받아서 entity로 전달하기, pw, Admin
    public Long setAdmin(Long adminId, EditAdminDto dto) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()
                        -> new NoSuchElementException("존재하지 않는 관게자"));
        admin.changeAdmin(dto.getLoginPw(),dto.getTel());
        return admin.getId();
    }



}
