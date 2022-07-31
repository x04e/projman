package com.x04e.projman.pmserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.x04e.projman.pmserver.dto.Project;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenValidProject_whenPost_shouldGet() {
        HttpEntity<String> entity = new HttpEntity<>("{\"id\":1,\"name\":\"myproject\"}", headers);
        System.out.println("###### test1 : entity:"+ entity);
        restTemplate.postForEntity("/projects", entity, Project.class);

        ResponseEntity<Project> response = restTemplate.getForEntity("/projects/1", Project.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getName()).isEqualTo("myproject");
    }

    @Test
    public void givenProjects_whenPostingToProjects_shouldGetProjects(){
        HttpEntity<String> project1 = new HttpEntity<>("{\"name\":\"my first project\"}", headers);
        HttpEntity<String> project2 = new HttpEntity<>("{\"name\":\"my second project\"}", headers);
        HttpEntity<String> project3 = new HttpEntity<>("{\"name\":\"my third project\"}", headers);
        HttpEntity<String> project4 = new HttpEntity<>("{\"name\":\"my fourth project\"}", headers);

        restTemplate.postForEntity("/projects", project1, Project.class);
        restTemplate.postForEntity("/projects", project2, Project.class);
        restTemplate.postForEntity("/projects", project3, Project.class);
        restTemplate.postForEntity("/projects", project4, Project.class);

        ResponseEntity<Project[]> response = restTemplate.getForEntity("/projects", Project[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<String> projectNames = Arrays.asList(response.getBody()).stream().map(Project::getName).collect(Collectors.toList());
        assertThat(projectNames).contains("my first project");
        assertThat(projectNames).contains("my second project");
        assertThat(projectNames).contains("my third project");
        assertThat(projectNames).contains("my fourth project");
    }
}
