package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.request.AddAdminRequest;
import com.kkoch.admin.api.controller.admin.request.EditAdminRequest;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import com.kkoch.admin.api.service.admin.dto.EditAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequestMapping("/admin-service/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    //관계자 등록
    @PostMapping
    public ApiResponse<Long> addAdmin(@RequestBody AddAdminRequest request) {
        AddAdminDto dto = request.toAddAdminDto();
        Long adminId = adminService.addAdmin(dto);
        return ApiResponse.ok(adminId);

    }

    //관게자 수정
    @PatchMapping("/{adminId}")
    public ApiResponse<Long> setAdmin(@PathVariable Long adminId, @RequestBody EditAdminRequest request) {
        EditAdminDto dto = request.toEditAdminDto();
        Long setId = adminService.setAdmin(adminId, dto);
        return ApiResponse.ok(setId);

    }

    //관계자 삭제
    @DeleteMapping("/{adminId}")
    public ApiResponse<Long> removeAdmin(@PathVariable Long adminId) {
        Long deleteId = adminService.removeAdmin(adminId);
        return ApiResponse.of(MOVED_PERMANENTLY, "관계자 정보가 삭제되었습니다.", deleteId);
    }


}
