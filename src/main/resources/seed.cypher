// ============================================================
// SkillPath - Seed Data
// ============================================================
// Creates the complete SkillPath graph:
//
// Student -> Skill -> JobRole -> Course
//
// This script is safe to run multiple times because MERGE
// prevents duplicate nodes and relationships.
// ============================================================


// ============================================================
// STUDENTS
// ============================================================

MERGE (s:Student {id: 'S001'})
SET
    s.name = 'Rahul',
    s.email = 'rahul@example.com';

MERGE (s:Student {id: 'S002'})
SET
    s.name = 'Priya',
    s.email = 'priya@example.com';

MERGE (s:Student {id: 'S003'})
SET
    s.name = 'Arjun',
    s.email = 'arjun@example.com';


// ============================================================
// SKILLS
// ============================================================

MERGE (s:Skill {id: 'SK001'})
SET
    s.name = 'Java',
    s.level = 'Intermediate';

MERGE (s:Skill {id: 'SK002'})
SET
    s.name = 'Spring Boot',
    s.level = 'Intermediate';

MERGE (s:Skill {id: 'SK003'})
SET
    s.name = 'SQL',
    s.level = 'Intermediate';

MERGE (s:Skill {id: 'SK004'})
SET
    s.name = 'React',
    s.level = 'Beginner';

MERGE (s:Skill {id: 'SK005'})
SET
    s.name = 'Docker',
    s.level = 'Beginner';


// ============================================================
// JOB ROLES
// ============================================================

MERGE (r:JobRole {id: 'R001'})
SET
    r.title = 'Java Backend Developer';

MERGE (r:JobRole {id: 'R002'})
SET
    r.title = 'Full Stack Developer';

MERGE (r:JobRole {id: 'R003'})
SET
    r.title = 'DevOps Engineer';


// ============================================================
// COURSES
// ============================================================

MERGE (c:Course {id: 'C001'})
SET
    c.title = 'Spring Boot Masterclass';

MERGE (c:Course {id: 'C002'})
SET
    c.title = 'React Fundamentals';

MERGE (c:Course {id: 'C003'})
SET
    c.title = 'Docker for Developers';


// ============================================================
// STUDENT -> SKILL
// ============================================================

// Rahul
MATCH (s:Student {id: 'S001'})
MATCH (skill:Skill {id: 'SK001'})
MERGE (s)-[:HAS_SKILL]->(skill);

MATCH (s:Student {id: 'S001'})
MATCH (skill:Skill {id: 'SK002'})
MERGE (s)-[:HAS_SKILL]->(skill);

MATCH (s:Student {id: 'S001'})
MATCH (skill:Skill {id: 'SK003'})
MERGE (s)-[:HAS_SKILL]->(skill);


// Priya
MATCH (s:Student {id: 'S002'})
MATCH (skill:Skill {id: 'SK001'})
MERGE (s)-[:HAS_SKILL]->(skill);

MATCH (s:Student {id: 'S002'})
MATCH (skill:Skill {id: 'SK004'})
MERGE (s)-[:HAS_SKILL]->(skill);


// Arjun
MATCH (s:Student {id: 'S003'})
MATCH (skill:Skill {id: 'SK001'})
MERGE (s)-[:HAS_SKILL]->(skill);

MATCH (s:Student {id: 'S003'})
MATCH (skill:Skill {id: 'SK005'})
MERGE (s)-[:HAS_SKILL]->(skill);


// ============================================================
// SKILL -> JOB ROLE
// ============================================================

// Java Backend Developer
MATCH (skill:Skill {id: 'SK001'})
MATCH (role:JobRole {id: 'R001'})
MERGE (skill)-[:REQUIRED_FOR]->(role);

MATCH (skill:Skill {id: 'SK002'})
MATCH (role:JobRole {id: 'R001'})
MERGE (skill)-[:REQUIRED_FOR]->(role);

MATCH (skill:Skill {id: 'SK003'})
MATCH (role:JobRole {id: 'R001'})
MERGE (skill)-[:REQUIRED_FOR]->(role);


// Full Stack Developer
MATCH (skill:Skill {id: 'SK001'})
MATCH (role:JobRole {id: 'R002'})
MERGE (skill)-[:REQUIRED_FOR]->(role);

MATCH (skill:Skill {id: 'SK004'})
MATCH (role:JobRole {id: 'R002'})
MERGE (skill)-[:REQUIRED_FOR]->(role);


// DevOps Engineer
MATCH (skill:Skill {id: 'SK005'})
MATCH (role:JobRole {id: 'R003'})
MERGE (skill)-[:REQUIRED_FOR]->(role);


// ============================================================
// JOB ROLE -> COURSE 
// ============================================================

MATCH (role:JobRole {id: 'R001'})
MATCH (course:Course {id: 'C001'})
MERGE (role)-[:RECOMMENDS]->(course);

MATCH (role:JobRole {id: 'R002'})
MATCH (course:Course {id: 'C002'})
MERGE (role)-[:RECOMMENDS]->(course);

MATCH (role:JobRole {id: 'R003'})
MATCH (course:Course {id: 'C003'})
MERGE (role)-[:RECOMMENDS]->(course);


// ============================================================
// END OF SEED DATA 
// ============================================================