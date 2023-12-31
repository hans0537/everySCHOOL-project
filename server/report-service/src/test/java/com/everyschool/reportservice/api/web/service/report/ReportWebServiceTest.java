package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.ReportType;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static com.everyschool.reportservice.error.ErrorMessage.NO_SUCH_REPORT;
import static org.assertj.core.api.Assertions.*;

class ReportWebServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReportWebService reportWebService;

    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("등록되지 않은 신고의 진행 상태를 수정하는 경우 예외가 발생한다.")
    @Test
    void editStatusUnregisteredReport() {
        //given

        //when //then
        assertThatThrownBy(() -> reportWebService.editStatus(1L, PROCESS.getCode()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage(NO_SUCH_REPORT.getMessage());
    }

    @DisplayName("신고 아이디와 상태 코드를 입력 받아 진행 상태를 수정할 수 있다.")
    @Test
    void editStatus() {
        //given
        Report report = saveReport();

        //when
        EditReportResponse response = reportWebService.editStatus(report.getId(), PROCESS.getCode());

        //then
        assertThat(response.getStatus()).isEqualTo(PROCESS.getText());
    }

    @DisplayName("등록되지 않은 신고의 처리 결과를 수정하는 경우 예외가 발생한다.")
    @Test
    void editResultUnregisteredReport() {
        //given

        //when //then
        assertThatThrownBy(() -> reportWebService.editResult(1L, "result"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage(NO_SUCH_REPORT.getMessage());
    }

    @DisplayName("신고 아이디와 처리 결과를 입력 받아 처리 결과를 수정할 수 있다.")
    @Test
    void editResult() {
        //given
        Report report = saveReport();

        //when
        EditReportResponse response = reportWebService.editResult(report.getId(), "result");

        //then
        assertThat(response.getResult()).isEqualTo("result");
    }

    private Report saveReport() {
        ReportContent content = ReportContent.builder()
            .reportWho("who")
            .reportWhen("when")
            .reportWhere("where")
            .reportWhat("what")
            .reportHow("how")
            .reportWhy("why")
            .build();

        Report report = Report.builder()
            .witness("witness")
            .description("description")
            .content(content)
            .schoolYear(2023)
            .typeId(ReportType.VIOLENCE.getCode())
            .schoolId(100000L)
            .userId(1L)
            .build();

        return reportRepository.save(report);
    }
}