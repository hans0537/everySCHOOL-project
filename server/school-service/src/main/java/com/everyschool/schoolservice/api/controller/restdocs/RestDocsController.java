package com.everyschool.schoolservice.api.controller.restdocs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    @GetMapping("/school-service/v1/index")
    public String restDocs() {
        return "index";
    }
}
