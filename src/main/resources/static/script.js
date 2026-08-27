const studentSelect =
    document.getElementById("studentSelect");

const recommendButton =
    document.getElementById("recommendButton");

const retryButton =
    document.getElementById("retryButton");

const loadingState =
    document.getElementById("loadingState");

const errorState =
    document.getElementById("errorState");

const emptyState =
    document.getElementById("emptyState");

const resultsSection =
    document.getElementById("resultsSection");

const resultsContainer =
    document.getElementById("resultsContainer");

const studentName =
    document.getElementById("studentName");

const studentSkills =
    document.getElementById("studentSkills");

const errorMessage =
    document.getElementById("errorMessage");



/* =========================================================
   UI STATE
   ========================================================= */

function hideAllStates() {

    loadingState.classList.add("hidden");

    errorState.classList.add("hidden");

    emptyState.classList.add("hidden");

    resultsSection.classList.add("hidden");
}



function showError(message) {

    hideAllStates();

    errorMessage.textContent = message;

    errorState.classList.remove("hidden");
}



/* =========================================================
   LOAD STUDENTS FROM COGNODB
   ========================================================= */

async function loadStudents() {

    studentSelect.disabled = true;

    recommendButton.disabled = true;

    studentSelect.innerHTML = `
        <option value="">
            Loading students...
        </option>
    `;


    try {

        const response =
            await fetch("/api/students");


        if (!response.ok) {

            throw new Error(
                "Unable to load students."
            );
        }


        const students =
            await response.json();


        if (!students ||
            students.length === 0) {

            studentSelect.innerHTML = `
                <option value="">
                    No students available
                </option>
            `;

            return;
        }


        studentSelect.innerHTML = `
            <option value="">
                Select a student
            </option>
        `;


        students.forEach(student => {

            const option =
                document.createElement("option");

            option.value = student.id;

            option.textContent =
                student.name;

            studentSelect.appendChild(
                option
            );

        });


        studentSelect.disabled = false;


    } catch (error) {

        console.error(error);


        studentSelect.innerHTML = `
            <option value="">
                Unable to load students
            </option>
        `;


        showError(
            "We couldn't load the student profiles. " +
            "Please check that the backend and database are running."
        );
    }
}



/* =========================================================
   ENABLE BUTTON AFTER STUDENT SELECTION
   ========================================================= */

studentSelect.addEventListener(
    "change",
    function () {

        recommendButton.disabled =
            !studentSelect.value;

        hideAllStates();
    }
);



/* =========================================================
   LOAD STUDENT SKILLS
   ========================================================= */

async function loadStudentSkills(
    studentId
) {

    const response =
        await fetch(
            `/api/students/${studentId}/skills`
        );


    if (!response.ok) {

        throw new Error(
            "Unable to load student skills."
        );
    }


    return await response.json();
}



/* =========================================================
   LOAD RECOMMENDATIONS
   ========================================================= */

async function loadRecommendations() {

    const studentId =
        studentSelect.value;


    if (!studentId) {

        return;
    }


    hideAllStates();

    loadingState.classList.remove(
        "hidden"
    );


    recommendButton.disabled = true;


    try {

        /*
         * Load career recommendations
         */

        const recommendationResponse =
            await fetch(
                `/api/recommendations/${studentId}`
            );


        if (!recommendationResponse.ok) {

            throw new Error(
                "Unable to load recommendations."
            );
        }


        const recommendations =
            await recommendationResponse.json();


        /*
         * Load student's connected skills
         */

        let skills = [];


        try {

            skills =
                await loadStudentSkills(
                    studentId
                );

        } catch (skillError) {

            console.warn(
                "Could not load skills:",
                skillError
            );

        }


        loadingState.classList.add(
            "hidden"
        );


        /*
         * No recommendation
         */

        if (!recommendations ||
            recommendations.length === 0) {

            emptyState.classList.remove(
                "hidden"
            );

            return;
        }


        /*
         * Student name
         */

        studentName.textContent =
            recommendations[0].student;


        /*
         * Display skills
         */

        renderStudentSkills(skills);


        /*
         * Display recommendations
         */

        renderRecommendations(
            recommendations
        );


        resultsSection.classList.remove(
            "hidden"
        );


    } catch (error) {

        console.error(error);

        showError(
            "We couldn't load your career recommendations. " +
            "Please make sure the database is available and try again."
        );

    } finally {

        recommendButton.disabled = false;

    }
}



/* =========================================================
   RENDER STUDENT SKILLS
   ========================================================= */

function renderStudentSkills(
    skills
) {

    studentSkills.innerHTML = "";


    if (!skills ||
        skills.length === 0) {

        studentSkills.innerHTML = `
            <span class="skill">
                No skills recorded
            </span>
        `;

        return;
    }


    skills.forEach(skill => {

        const element =
            document.createElement("span");

        element.className = "skill";

        element.textContent =
            skill.name;

        studentSkills.appendChild(
            element
        );

    });
}



/* =========================================================
   RENDER RECOMMENDATIONS
   ========================================================= */

function renderRecommendations(
    recommendations
) {

    resultsContainer.innerHTML = "";


    recommendations.forEach(
        recommendation => {


        const card =
            document.createElement("div");

        card.className =
            "result-card";


        const skills =
            recommendation.skills || [];


        const courses =
            recommendation.courses || [];


        const matchingSkills =
            recommendation.matchingSkills ||
            skills.length;


        const skillHtml =
            skills
                .map(skill => `
                    <span class="skill">
                        ${escapeHtml(skill)}
                    </span>
                `)
                .join("");


        const courseHtml =
            courses
                .map(course => `
                    <div class="course">
                        ${escapeHtml(course)}
                    </div>
                `)
                .join("");


        card.innerHTML = `

            <div class="role-top">

                <h3 class="role-title">
                    ${escapeHtml(
                        recommendation.role
                    )}
                </h3>

                <span class="match-badge">
                    ${matchingSkills}
                    matching skill${matchingSkills === 1 ? "" : "s"}
                </span>

            </div>


            <div class="result-grid">

                <div class="result-column">

                    <h4>
                        MATCHING SKILLS
                    </h4>

                    <div class="skills">

                        ${skillHtml}

                    </div>

                </div>


                <div class="result-column">

                    <h4>
                        RECOMMENDED LEARNING
                    </h4>

                    ${courseHtml || `
                        <div class="course">
                            No course recommendation available.
                        </div>
                    `}

                </div>

            </div>

        `;


        resultsContainer.appendChild(
            card
        );

    });
}



/* =========================================================
   HTML SAFETY
   ========================================================= */

function escapeHtml(value) {

    const div =
        document.createElement("div");

    div.textContent =
        value ?? "";

    return div.innerHTML;
}



/* =========================================================
   BUTTON EVENTS
   ========================================================= */

recommendButton.addEventListener(
    "click",
    loadRecommendations
);


retryButton.addEventListener(
    "click",
    function () {

        hideAllStates();

        loadStudents();

    }
);



/* =========================================================
   INITIAL PAGE LOAD
   ========================================================= */

loadStudents();