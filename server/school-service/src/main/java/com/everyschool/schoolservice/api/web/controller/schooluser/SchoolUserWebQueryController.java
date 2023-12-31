package com.everyschool.schoolservice.api.web.controller.schooluser;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.Result;
import com.everyschool.schoolservice.api.web.controller.schooluser.response.MyClassParentResponse;
import com.everyschool.schoolservice.api.web.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 웹 학교 유저 조회용 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/web/{schoolYear}/schools/{schoolId}/classes/{schoolClassId}")
public class SchoolUserWebQueryController {

    private final SchoolUserQueryService schoolUserQueryService;
    private final TokenUtils tokenUtils;

    /**
     * 반 학생 정보 조회 API
     *
     * @param schoolYear    학년도
     * @param schoolId      학교 아이디
     * @param schoolClassId 학급 아이디
     * @return 조회된 반 학생 정보 목록
     */
    @GetMapping("/students")
    public ApiResponse<Result<MyClassStudentResponse>> searchMyClassStudents(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolClassId
    ) {
        log.debug("call SchoolUserQueryController#searchMyClassStudents");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<MyClassStudentResponse> responses = schoolUserQueryService.searchMyClassStudents(userKey, schoolYear);
        log.debug("results={}", responses);

        return ApiResponse.ok(Result.of(responses));
    }

    /**
     * 반 학부모 정보 조회 API
     *
     * @param schoolYear    학년도
     * @param schoolId      학교 아이디
     * @param schoolClassId 학급 아이디
     * @return 조회된 반 학부모 정보 목록
     */
    @GetMapping("/parents")
    public ApiResponse<Result<MyClassParentResponse>> searchMyClassParents(
        @PathVariable Integer schoolYear,
        @PathVariable Long schoolId,
        @PathVariable Long schoolClassId
    ) {
        log.debug("call SchoolUserQueryController#searchMyClassParents");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<MyClassParentResponse> responses = schoolUserQueryService.searchMyClassParents(userKey, schoolYear);
        log.debug("results={}", responses);

        return ApiResponse.ok(Result.of(responses));
    }
}
