package com.skillpath.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.skillpath.service.GraphService;

@RestController
public class StudentController {

    private final GraphService graphService;

    public StudentController(GraphService graphService) {
        this.graphService = graphService;
    }


    @GetMapping("/api/students")
    public List<Map<String, Object>> getStudents() {

        return graphService.getStudents();
    }


    @GetMapping("/api/students/{studentId}/skills")
    public List<Map<String, Object>> getStudentSkills(
            @PathVariable String studentId) {

        return graphService.getStudentSkills(
            studentId
        );
    }
}