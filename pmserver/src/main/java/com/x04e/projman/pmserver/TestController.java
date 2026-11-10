package com.x04e.projman.pmserver;

import java.util.HashMap;
import java.util.Map;

import com.x04e.projman.pmserver.dto.Project;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class TestController extends ApiController {

    @Value("${spring.datasource.password}")
    private String testValue;

    @Secured({"ROLE_USER"})
    @GetMapping("/test")
    public String get(){
        return testValue;
    }

}
