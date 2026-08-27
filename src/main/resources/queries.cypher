// ============================================================
// SkillPath - Cypher Queries
// ============================================================


// 1. Get all students
// Used by: GET /api/students

MATCH (s:Student)
RETURN
    s.id AS id,
    s.name AS name,
    s.email AS email
ORDER BY s.name;


// ============================================================


// 2. Get skills for a student
// Used by: GET /api/students/{studentId}/skills

MATCH (s:Student {id: $studentId})
      -[:HAS_SKILL]->(skill:Skill)

RETURN
    skill.id AS id,
    skill.name AS name,
    skill.level AS level
ORDER BY skill.name;


// ============================================================


// 3. Multi-hop career recommendation
//
// Student
//   -> Skill
//   -> JobRole
//   -> Course
//
// Used by: GET /api/recommendations/{studentId}

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

ORDER BY matchingSkills DESC, role.title;


// ============================================================


// 4. Explore the complete graph around a student
//
// This demonstrates graph traversal across
// multiple relationship types.

MATCH path =
    (s:Student {id: $studentId})
    -[:HAS_SKILL]->
    (skill:Skill)
    -[:REQUIRED_FOR]->
    (role:JobRole)
    -[:RECOMMENDS]->
    (course:Course)

RETURN path;


// ============================================================


// 5. Find career roles connected to a student's skills
//
// This is useful for understanding which roles
// are connected to the student's current skills.

MATCH (s:Student {id: $studentId})
      -[:HAS_SKILL]->(skill:Skill)
      -[:REQUIRED_FOR]->(role:JobRole)

RETURN
    role.id AS roleId,
    role.title AS role,
    collect(DISTINCT skill.name) AS matchingSkills,
    count(DISTINCT skill) AS skillCount

ORDER BY skillCount DESC, role.title;