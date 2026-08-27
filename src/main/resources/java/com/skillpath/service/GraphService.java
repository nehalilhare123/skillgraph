package com.skillpath.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.skillpath.repository.GraphRepository;

@Service
public class GraphService {

    private final GraphRepository graphRepository;

    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }


    // =========================================================
    // STUDENT
    // =========================================================

    public void createStudent(
            String id,
            String name,
            String email) {

        graphRepository.createStudent(
            id,
            name,
            email
        );
    }


    public List<Map<String, Object>> getStudents() {

        return graphRepository.getStudents();
    }


    // =========================================================
    // SKILL
    // =========================================================

    public void createSkill(
            String id,
            String name,
            String level) {

        graphRepository.createSkill(
            id,
            name,
            level
        );
    }


    public List<Map<String, Object>> getSkills() {

        return graphRepository.getSkills();
    }


    public List<Map<String, Object>> getStudentSkills(
            String studentId) {

        return graphRepository.getStudentSkills(
            studentId
        );
    }


    // =========================================================
    // JOB ROLE
    // =========================================================

    public void createJobRole(
            String id,
            String title) {

        graphRepository.createJobRole(
            id,
            title
        );
    }


    public List<Map<String, Object>> getJobRoles() {

        return graphRepository.getJobRoles();
    }


    // =========================================================
    // COURSE
    // =========================================================

    public void createCourse(
            String id,
            String title) {

        graphRepository.createCourse(
            id,
            title
        );
    }


    public List<Map<String, Object>> getCourses() {

        return graphRepository.getCourses();
    }


    // =========================================================
    // RELATIONSHIPS
    // =========================================================

    public void connectStudentToSkill(
            String studentId,
            String skillId) {

        graphRepository.connectStudentToSkill(
            studentId,
            skillId
        );
    }


    public void connectSkillToRole(
            String skillId,
            String roleId) {

        graphRepository.connectSkillToRole(
            skillId,
            roleId
        );
    }


    public void connectRoleToCourse(
            String roleId,
            String courseId) {

        graphRepository.connectRoleToCourse(
            roleId,
            courseId
        );
    }


    // =========================================================
    // RECOMMENDATIONS
    // Student -> Skill -> JobRole -> Course
    // =========================================================

    public List<Map<String, Object>> getRecommendations(
            String studentId) {

        return graphRepository.getRecommendations(
            studentId
        );
    }
}