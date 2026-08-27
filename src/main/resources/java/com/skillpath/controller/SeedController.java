package com.skillpath.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillpath.service.GraphService;

@RestController
public class SeedController {

    private final GraphService graphService;

    public SeedController(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping("/api/seed")
    public String seed() {

        graphService.createStudent(
            "S001",
            "Rahul",
            "rahul@example.com"
        );

        graphService.createStudent(
            "S002",
            "Priya",
            "priya@example.com"
        );

        graphService.createStudent(
            "S003",
            "Arjun",
            "arjun@example.com"
        );

        graphService.createSkill(
            "SK001",
            "Java",
            "Intermediate"
        );

        graphService.createSkill(
            "SK002",
            "Spring Boot",
            "Intermediate"
        );

        graphService.createSkill(
            "SK003",
            "SQL",
            "Intermediate"
        );

        graphService.createSkill(
            "SK004",
            "React",
            "Beginner"
        );

        graphService.createSkill(
            "SK005",
            "Docker",
            "Beginner"
        );

        graphService.createJobRole(
            "R001",
            "Java Backend Developer"
        );

        graphService.createJobRole(
            "R002",
            "Full Stack Developer"
        );

        graphService.createJobRole(
            "R003",
            "DevOps Engineer"
        );

        graphService.createCourse(
            "C001",
            "Spring Boot Masterclass"
        );

        graphService.createCourse(
            "C002",
            "React Fundamentals"
        );

        graphService.createCourse(
            "C003",
            "Docker for Developers"
        );

        graphService.connectStudentToSkill("S001", "SK001");
        graphService.connectStudentToSkill("S001", "SK002");
        graphService.connectStudentToSkill("S001", "SK003");

        graphService.connectStudentToSkill("S002", "SK001");
        graphService.connectStudentToSkill("S002", "SK004");

        graphService.connectStudentToSkill("S003", "SK001");
        graphService.connectStudentToSkill("S003", "SK005");

        graphService.connectSkillToRole("SK001", "R001");
        graphService.connectSkillToRole("SK002", "R001");
        graphService.connectSkillToRole("SK003", "R001");

        graphService.connectSkillToRole("SK001", "R002");
        graphService.connectSkillToRole("SK004", "R002");

        graphService.connectSkillToRole("SK005", "R003");

        graphService.connectRoleToCourse("R001", "C001");
        graphService.connectRoleToCourse("R002", "C002");
        graphService.connectRoleToCourse("R003", "C003");

        return "Seed data created successfully";
    }
}