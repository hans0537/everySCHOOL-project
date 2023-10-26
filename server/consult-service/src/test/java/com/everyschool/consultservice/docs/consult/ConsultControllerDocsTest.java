package com.everyschool.consultservice.docs.consult;

import com.everyschool.consultservice.api.controller.consult.ConsultController;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultScheduleRequest;
import com.everyschool.consultservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultControllerDocsTest extends RestDocsSupport {

    @DisplayName("상담 예약 API")
    @Test
    void createConsult() throws Exception {
        CreateConsultRequest request = CreateConsultRequest.builder()
            .startTime(LocalDateTime.of(2023, 10, 15, 10, 30))
            .content("우리아이 진로 상담 요청 드려요")
            .codeId(1)
            .build();

        mockMvc.perform(
                post("/consult-service/v1/consult")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-consult",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("startTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("상담 신청 일시"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .optional()
                        .description("상담 내용"),
                    fieldWithPath("codeId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("진행상태 코드")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터(상담 PK)")
                )
            ));
    }

    @DisplayName("(교사용) 상담 가능 시간 등록 API")
    @Test
    void createConsultSchedule() throws Exception {
        CreateConsultScheduleRequest request = CreateConsultScheduleRequest.builder()
            .startTime(LocalTime.NOON)
            .endTime(LocalTime.MIDNIGHT)
            .build();

        mockMvc.perform(
                post("/consult-service/v1/consult/schedule")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-consult-schedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("startTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("상담 가능 시작 시간"),
                    fieldWithPath("endTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("상담 가능 종료 시간")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터(상담 일정 PK)")
                )
            ));
    }

//    @DisplayName("(교사용) API")
//    @Test
//    void createConsultSchedule() throws Exception {
//
//        mockMvc.perform(
//                get("/consult-service/v1/boards/{schoolId}/{userKey}", 1L, UUID.randomUUID().toString())
//                    .param("limit", "4")
//                    .param("categoryId", "1")
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("search-board-list",
//                preprocessResponse(prettyPrint()),
//                requestParameters(
//                    parameterWithName("limit")
//                        .description("출력할 목록 수"),
//                    parameterWithName("categoryId")
//                        .description("카테고리 코드")
//                ),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.ARRAY)
//                        .description("응답 데이터"),
//                    fieldWithPath("data[].boardId").type(JsonFieldType.NUMBER)
//                        .description("교내 공지 PK"),
//                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
//                        .description("교내 공지 제목"),
//                    fieldWithPath("data[].createDate").type(JsonFieldType.STRING)
//                        .description("교내 공지 작성일")
//                )
//            ));
//    }
//
//    @DisplayName("교내 공지 상세 조회 API")
//    @Test
//    void searchBoard() throws Exception {
//
//        mockMvc.perform(
//                get("/consult-service/v1/boards/{schoolId}/{userKey}/{boardId}", 1L, UUID.randomUUID().toString(), 2L)
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("search-board",
//                preprocessResponse(prettyPrint()),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.OBJECT)
//                        .description("응답 데이터"),
//                    fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
//                        .description("교내 공지 PK"),
//                    fieldWithPath("data.title").type(JsonFieldType.STRING)
//                        .description("교내 공지 제목"),
//                    fieldWithPath("data.content").type(JsonFieldType.STRING)
//                        .description("교내 공지 내용"),
//                    fieldWithPath("data.userName").type(JsonFieldType.STRING)
//                        .description("작성자"),
//                    fieldWithPath("data.createDate").type(JsonFieldType.STRING)
//                        .description("교내 공지 작성일"),
//                    fieldWithPath("data.uploadFiles").type(JsonFieldType.ARRAY)
//                        .description("파일들")
//                )
//            ));
//    }
//
//    @DisplayName("교내 공지 수정 API")
//    @Test
//    void editBoard() throws Exception {
//
//        EditBoardRequest request = EditBoardRequest.builder()
//            .title("수업시간 외 학교 체육관 사용에 관한 공지")
//            .content("사용 명부를 작성한 사람만 사용 가능")
//            .categoryId(1L)
//            .uploadFiles(new ArrayList<>())
//            .build();
//        mockMvc.perform(
//                patch("/consult-service/v1/boards/{schoolId}/{userKey}/{boardId}", 1L, UUID.randomUUID().toString(), 2L)
//                    .content(objectMapper.writeValueAsString(request))
//                    .contentType(MediaType.MULTIPART_FORM_DATA)
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("edit-board",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestFields(
//                    fieldWithPath("title").type(JsonFieldType.STRING)
//                        .optional()
//                        .description("게시글 제목"),
//                    fieldWithPath("content").type(JsonFieldType.STRING)
//                        .optional()
//                        .description("게시글 내용"),
//                    fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
//                        .optional()
//                        .description("카테고리 코드"),
//                    fieldWithPath("uploadFiles").type(JsonFieldType.ARRAY)
//                        .description("이미지나 파일")
//                ),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.OBJECT)
//                        .description("응답 데이터"),
//                    fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
//                        .description("교내 공지 PK"),
//                    fieldWithPath("data.title").type(JsonFieldType.STRING)
//                        .description("교내 공지 제목"),
//                    fieldWithPath("data.content").type(JsonFieldType.STRING)
//                        .description("교내 공지 내용"),
//                    fieldWithPath("data.userName").type(JsonFieldType.STRING)
//                        .description("작성자"),
//                    fieldWithPath("data.createDate").type(JsonFieldType.STRING)
//                        .description("교내 공지 작성일"),
//                    fieldWithPath("data.uploadFiles").type(JsonFieldType.ARRAY)
//                        .description("파일들")
//                )
//            ));
//    }
//
//    @DisplayName("교내 공지 삭제 API")
//    @Test
//    void deleteBoard() throws Exception {
//
//        mockMvc.perform(
//                delete("/consult-service/v1/boards/{schoolId}/{userKey}/{boardId}", 1L, UUID.randomUUID().toString(), 2L)
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("delete-board",
//                preprocessResponse(prettyPrint()),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.STRING)
//                        .description("삭제 결과")
//                )
//            ));
//    }

    @Override
    protected Object initController() {
        return new ConsultController();
    }
}