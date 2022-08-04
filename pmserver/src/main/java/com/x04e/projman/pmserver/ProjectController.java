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

@RestController
@RequestMapping("projects")
public class ProjectController {

    private static Map<Long, Project> projects = new HashMap<Long, Project>();
    private static long nextProjectId = 1;

    @Secured({"ROLE_USER"})
    @GetMapping
    public Project[] getProjects(){
        return projects.values().toArray(new Project[0]);
    }

    @Secured({"ROLE_USER"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postProject(@RequestBody Project project){
        project.setId(nextProjectId++);
        projects.put(project.getId(), project);
    }

    @Secured({"ROLE_USER"})
    @GetMapping("{id}")
    public Project getProject(@PathVariable Long id){
        return projects.get(id);
    }
}
