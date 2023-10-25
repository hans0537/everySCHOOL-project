package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.*;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * 회원 API
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입 API
     *
     * @param request 회원 가입시 필요한 회원 정보
     * @return 가입에 성공한 회원의 기본 정보
     */
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> join(@Valid @RequestBody JoinUserRequest request) {
        log.debug("call UserController#join");
        log.debug("JoinUserRequest={}", request);

        UserResponse response = userService.createUser(request.toDto());
        log.debug("UserResponse={}", response);

        return ApiResponse.created(response);
    }

    @PatchMapping("/v1/pwd")
    public ApiResponse<String> editPwd(@RequestBody EditPwdRequest request) {
        // TODO: 2023-10-25 임우택 JWT 복호화 기능 구현
        String email = "ssafy@gmail.com";

        UserResponse response = userService.editPwd(email, request.getCurrentPwd(), request.getNewPwd());

        return ApiResponse.of(HttpStatus.OK, "비밀번호가 변경되었습니다.", null);
    }

    @PostMapping("/v1/withdrawal")
    public ApiResponse<WithdrawalResponse> withdrawal(@RequestBody WithdrawalRequest request) {
        // TODO: 2023-10-25 임우택 JWT 복호화 기능 구현
        String email = "ssafy@gmail.com";

        WithdrawalResponse response = userService.withdrawal(email, request.getPwd());

        return ApiResponse.of(HttpStatus.OK, "회원 탈퇴가 되었습니다.", response);
    }
}
