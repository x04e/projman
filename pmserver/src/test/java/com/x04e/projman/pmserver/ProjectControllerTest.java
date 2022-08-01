package com.x04e.projman.pmserver;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x04e.projman.pmserver.dto.Project;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidProject_whenCreatingProject_shouldReturn201CREATED() throws Exception {
        this.mockMvc.perform(
                post("/projects")
                .contentType("application/json")
                .content(
                    new ObjectMapper()
                    .writeValueAsString(new Project(1, "myproject"))
                )
            )
            .andExpect(status().isCreated());
    }


    @Test
    public void givenProject_whenGET_shouldReturn200AndProjectJson() throws Exception {
        this.mockMvc.perform(
                post("/projects")
                .contentType("application/json")
                .content(
                    new ObjectMapper()
                    .writeValueAsString(new Project(1, "myproject")))
                );

        this.mockMvc.perform(get("/projects/1")).andExpectAll(
                status().isOk(),
                content().string(containsString("myproject"))
            );
    }

    @Test
    public void givenProjects_whenGettingAllProjects_shouldReturn200AndAllProjects() throws Exception {
        Project[] projects = new Project[]{
            new Project(1, "my first project"),
            new Project(2, "my second project"),
            new Project(3, "my third project"),
            new Project(4, "my fourth project")
        };

        for(Project project : projects){
            this.mockMvc.perform(post("/projects")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(project)));
        }

        this.mockMvc
            .perform(get("/projects"))
            .andExpectAll(
                status().isOk(),
                content().string(containsString("my first project")),
                content().string(containsString("my second project")),
                content().string(containsString("my third project")),
                content().string(containsString("my fourth project"))
            );
    }
}
