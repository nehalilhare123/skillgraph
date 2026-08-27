package com.skillpath.repository;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Repository;

@Repository
public class GraphRepository {

    private final Driver driver;

    public GraphRepository(Driver driver) {
        this.driver = driver;
    }

    // =========================================================
    // STUDENTS
    // =========================================================

    public void createStudent(String id, String name, String email) {

        try (Session session = driver.session()) {

            session.run(
                """
                MERGE (s:Student {id: $id})
                SET
                    s.name = $name,
                    s.email = $email
                """,
                Map.of(
                    "id", id,
                    "name", name,
                    "email", email
                )
            );
        }
    }

    public List<Map<String, Object>> getStudents() {

        try (Session session = driver.session()) {

            return session.run(
                """
                MATCH (s:Student)
                RETURN
                    s.id AS id,
                    s.name AS name,
                    s.email AS email
                ORDER BY s.name
                """
            ).list(record -> record.asMap());
        }
    }


    // =========================================================
    // SKILLS
    // =========================================================

    public void createSkill(String id, String name, String level) {

        try (Session session = driver.session()) {

            session.run(
                """
                MERGE (s:Skill {id: $id})
                SET
                    s.name = $name,
                    s.level = $level
                """,
                Map.of(
                    "id", id,
                    "name", name,
                    "level", level
                )
            );
        }
    }


    // =========================================================
    // JOB ROLES
    // =========================================================

    public void createJobRole(String id, String title) {

        try (Session session = driver.session()) {

            session.run(
                """
                MERGE (r:JobRole {id: $id})
                SET
                    r.title = $title
                """,
                Map.of(
                    "id", id,
                    "title", title
                )
            );
        }
    }


    // =========================================================
    // COURSES
    // =========================================================

    public void createCourse(String id, String title) {

        try (Session session = driver.session()) {

            session.run(
                """
                MERGE (c:Course {id: $id})
                SET
                    c.title = $title
                """,
                Map.of(
                    "id", id,
                    "title", title
                )
            );
        }
    }


    // =========================================================
    // STUDENT -> SKILL
    // =========================================================

    public void connectStudentToSkill(
            String studentId,
            String skillId) {

        try (Session session = driver.session()) {

            session.run(
                """
                MATCH (s:Student {id: $studentId})
                MATCH (skill:Skill {id: $skillId})
                MERGE (s)-[:HAS_SKILL]->(skill)
                """,
                Map.of(
                    "studentId", studentId,
                    "skillId", skillId
                )
            );
        }
    }


    // =========================================================
    // SKILL -> JOB ROLE
    // =========================================================

    public void connectSkillToRole(
            String skillId,
            String roleId) {

        try (Session session = driver.session()) {

            session.run(
                """
                MATCH (skill:Skill {id: $skillId})
                MATCH (role:JobRole {id: $roleId})
                MERGE (skill)-[:REQUIRED_FOR]->(role)
                """,
                Map.of(
                    "skillId", skillId,
                    "roleId", roleId
                )
            );
        }
    }


    // =========================================================
    // JOB ROLE -> COURSE
    // =========================================================

    public void connectRoleToCourse(
            String roleId,
            String courseId) {

        try (Session session = driver.session()) {

            session.run(
                """
                MATCH (role:JobRole {id: $roleId})
                MATCH (course:Course {id: $courseId})
                MERGE (role)-[:RECOMMENDS]->(course)
                """,
                Map.of(
                    "roleId", roleId,
                    "courseId", courseId
                )
            );
        }
    }


    // =========================================================
    // MULTI-HOP CAREER RECOMMENDATION
    // Student -> Skill -> JobRole -> Course
    // =========================================================

    public List<Map<String, Object>> getRecommendations(
            String studentId) {

        try (Session session = driver.session()) {

            return session.run(
                """
                MATCH (s:Student {id: $studentId})
                      -[:HAS_SKILL]->(skill:Skill)
                      -[:REQUIRED_FOR]->(role:JobRole)
                      -[:RECOMMENDS]->(course:Course)

                RETURN
                    s.id AS studentId,
                    s.name AS student,
                    role.id AS roleId,
                    role.title AS role,
                    collect(DISTINCT skill.name) AS skills,
                    collect(DISTINCT course.title) AS courses,
                    count(DISTINCT skill) AS matchingSkills

                ORDER BY matchingSkills DESC, role.title
                """,
                Map.of("studentId", studentId)
            ).list(record -> record.asMap());
        }
    }


    // =========================================================
    // STUDENT SKILLS
    // =========================================================

    public List<Map<String, Object>> getStudentSkills(
            String studentId) {

        try (Session session = driver.session()) {

            return session.run(
                """
                MATCH (s:Student {id: $studentId})
                      -[:HAS_SKILL]->(skill:Skill)

                RETURN
                    skill.id AS id,
                    skill.name AS name,
                    skill.level AS level

                ORDER BY skill.name
                """,
                Map.of("studentId", studentId)
            ).list(record -> record.asMap());
        }
    }


    // =========================================================
    // ALL JOB ROLES
    // =========================================================

    public List<Map<String, Object>> getJobRoles() {

        try (Session session = driver.session()) {

            return session.run(
                """
                MATCH (role:JobRole)

                RETURN
                    role.id AS id,
                    role.title AS title

                ORDER BY role.title
                """
            ).list(record -> record.asMap());
        }
    }


    // =========================================================
    // ALL SKILLS
    // =========================================================

    public List<Map<String, Object>> getSkills() {

        try (Session session = driver.session()) {

            return session.run(
                """
                MATCH (skill:Skill)

                RETURN
                    skill.id AS id,
                    skill.name AS name,
                    skill.level AS level

                ORDER BY skill.name
                """
            ).list(record -> record.asMap());
        }
    }


    // =========================================================
    // ALL COURSES
    // =========================================================

    public List<Map<String, Object>> getCourses() {

        try (Session session = driver.session()) {

            return session.run(
                """
                MATCH (course:Course)

                RETURN
                    course.id AS id,
                    course.title AS title

                ORDER BY course.title
                """
            ).list(record -> record.asMap());
        }
    }
}