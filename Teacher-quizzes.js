window.onload = function () {
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
    const headers = {
        Authorization: 'Bearer ' + teacher.token
    };

    $.get({
        url: "http://localhost:8080/quizz/list/for-teacher",
        headers: headers
    }).done(function (quizzes) {
        console.log(quizzes);
        buildQuizTable(quizzes)
    });

    $.get({
        url: "http://localhost:8080/lesson/without-quiz?teacherId=" + teacher.id,
        headers: headers
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            $("#lesson").append(`<option value=` + element.id + ` > ` + element.topic + ` </option>`);
        });
    })

}


var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
const headers = {
    Authorization: 'Bearer ' + teacher.token
};

function buildQuizTable(quizzes) {
    quizzes.forEach(element => {
        $("#quizzes-table").find('tbody')
            .append($('<tr>')
                .on("click", function () {
                    updateQuizInfo(element);
                    getQuestions(element);
                    getResults(element);
                })
                .append($('<th>')
                    .append($('<p>')
                        .text(element.name)
                    )
                )

                .append($('<th>')
                    .append($('<p>')
                        .text(element.lesson.topic)
                    )
                )

                .append($('<th>')
                    .append($('<p>')
                        .text(element.numberOfQuestions)
                    )
                )

                .append($('<th>')
                    .append($('<p>')
                        .text(element.numberOfResults + "/" + element.numberOfStudentInClass)
                    )
                )

                .append($('<th>')
                    .append($('<p>')
                        .text(element.result + "%")
                    )
                )
            );
    });
}


function getQuestions(element) {
    $.get({
        url: "http://localhost:8080/quizz/questions/" + element.id,
        headers: headers,
        success: function (questions) {
            console.log(questions);
            // Display the questions on the page
            displayQuestions(questions);
        },
        error: function (error) {
            console.error('Error fetching questions:', error);
        }
    });
}


function getResults(element) {
    $.get({
        url: "http://localhost:8080/quizz/result/" + element.id,
        headers: headers,
    }).done(function (data) {
        console.log(data);
        buildResultTable(data);
    });
}


function buildResultTable(data) {
    $("#results-table").find("tr:gt(0)").remove();
    data.forEach(result => {
        $("#results-table").find('tbody')
            .append($('<tr>')
                .on("click", function () {
                    getResultModal(result);
                })
                .append($('<th>')
                    .append($('<p>')
                        .text(result.student.lastName + " " + result.student.firstName)
                    )
                )

                .append($('<th>')
                    .append($('<p>')
                        .text(result.correctAnswersAmount + "/" + result.numberOfQuestions)
                    )
                )

                .append($('<th>')
                    .append($('<p>')
                        .text(result.result + "%")
                    )
                )
            );
    });
}

function updateQuizInfo(element) {
    const quizData = {
        name: element.name,
        numQuestions: element.numberOfQuestions,
        numStudentsAnswered: element.numberOfResults + "/" + element.numberOfStudentInClass,
        quizResult: element.result + "%",
    };
    $("#quiz-name").text(quizData.name);
    $("#num-questions-value").text(quizData.numQuestions);
    $("#num-students-value").text(quizData.numStudentsAnswered);
    $("#quiz-result-value").text(quizData.quizResult);

    $("#quiz-name-1").text(quizData.name);
    $("#num-questions-value-1").text(quizData.numQuestions);
    $("#num-students-value-1").text(quizData.numStudentsAnswered);
    $("#quiz-result-value-1").text(quizData.quizResult);
}

function displayQuestions(questions) {
    const questionsList = $("#questions-list");
    questionsList.empty();
    questions.forEach((question, index) => {
        const listItem = $("<li>");
        listItem.append("<p><strong>Запитання " + (index + 1) + ":</strong> " + question.questionText + "</p>");

        const optionsList = $("<ul class='options-list'>");
        question.answers.forEach((option, optionIndex) => {
            const optionItem = $("<li>");
            optionItem.text(option.answerOption);

            // Check if the option is correct and mark it
            if (option.isCorrect) {
                optionItem.addClass("correct-answer");
            }

            optionsList.append(optionItem);
        });

        listItem.append(optionsList);
        questionsList.append(listItem);
    });
}

function getResultModal(resultDetails) {
    $.get({
        url: "http://localhost:8080/quizz/full-result/" + resultDetails.id,
        headers: headers,
    }).done(function (data) {
        console.log(data);
        buildModalContent(data);
    });
}

function buildModalContent(result) {
    // Create modal structure dynamically

    updateQuizDetailsInfo(result);
    buildResultList(result);
    // Display the modal
    $('#resultModal').modal('show');
}

function updateQuizDetailsInfo(element) {
    $("#quiz-details-name").text(element.name);
    $("#num-details-questions-value").text(element.numberOfQuestions);
    $("#num-details-corrects-value").text(element.correctAnswersAmount);
    $("#quiz-details-result-value").text(element.result + "%");
}

function buildResultList(result) {
    const questionsList = $("#results-list");
    questionsList.empty();

    result.questions.forEach((question, questionIndex) => {
        const listItem = $("<li>");
        listItem.append("<p><strong>Запитання " + (questionIndex + 1) + ":</strong> " + question.questionText + "</p>");

        const optionsList = $("<ul class='options-list'>");
        question.answers.forEach((option, index) => {
            const optionItem = $("<li>");
            optionItem.text(option.answerOption);

            if (option.isCorrect) {
                optionItem.addClass("correct-answer");
            }

            if (!option.isCorrect && (question.questionResult.selectedAnswer.id === option.id)) {
                optionItem.addClass("wrong-answer");
            }

            optionsList.append(optionItem);
        });

        listItem.append(optionsList);
        questionsList.append(listItem);
    });
    $(".question-container").fadeIn();
}


$("#exit").click(function () {
    localStorage.removeItem("teacherInfo"),
        window.location.href = "Головна.html";
});