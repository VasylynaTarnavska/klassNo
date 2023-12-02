window.onload = function () {
    var student = JSON.parse(localStorage.getItem("studentInfo"));
    var headers = {
        Authorization: 'Bearer ' + student.token
    }

    $.get({
        url: "http://localhost:8080/quizz/list/for-student/" + student.id + "/to-pass",
        headers: headers
    }).done(function (data) {
        data.forEach(element => {
            $("#quiz-to-pass").append(`<option value=` + element.id + `> ` + element.name + ` </option>`);
        });
    });

    $.get({
        url: "http://localhost:8080/quizz/list/for-student/" + student.id + "/passed",
        headers: headers
    }).done(function (data) {
        data.forEach(element => {
            $("#quiz-passed").append(`<option value=` + element.id + `> ` + element.name + ` </option>`);
        });
    });

}

jQuery(document).ready(function ($) {
    // Your code here

    var student = JSON.parse(localStorage.getItem("studentInfo"));
    var headers = {
        Authorization: 'Bearer ' + student.token
    }
    var selectedAnswers = [];

    $("#view-quiz").on("click", function () {
        var selectedQuizId = $("#quiz-to-pass").val();

        $.ajax({
            url: "http://localhost:8080/quizz/questions/" + selectedQuizId,
            method: "GET",
            headers: headers,
            success: function (questions) {
                buildQuestionList(questions);
                createSaveButton();
            },
            error: function (error) {
                console.error("Error getting quiz details:", error);
            }
        });
    });

    function createSaveButton() {
        $("#button-container").empty();
        var save = $("<div id='save-button'>Завершити і надіслати</div>");
        save.addClass("save-button u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1")
        $("#button-container").append(save);
    }

    $("#button-container").on("click", ".save-button", function () {
        console.log(selectedAnswers)
        var quizzId = $("#quiz-to-pass").val();
        var studentId = student.id;

        var requestData = {
            quizzId: quizzId,
            studentId: studentId,
            questionPassRequests: selectedAnswers
        };
        console.log(requestData)

        $.ajax({
            url: "http://localhost:8080/quizz/pass", // Replace with your actual API endpoint for saving answers
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            headers: headers,
            success: function (response) {
                console.log("Answers saved successfully:", response);
                window.location.reload();
            },
            error: function (error) {
                console.error("Error saving answers:", error);
            }
        });
    });

    function buildQuestionList(questions) {
        const questionsList = $("#questions-list");
        questionsList.empty();
        selectedAnswers = [];

        questions.forEach((question, questionIndex) => {
            const listItem = $("<li>");
            listItem.append("<p><strong>Запитання " + (questionIndex + 1) + ":</strong> " + question.questionText + "</p>");

            const optionsList = $("<ul class='options-list'>");
            question.answers.forEach((option, index) => {
                const optionItem = $("<li>");
                optionItem.text(option.answerOption);

                optionItem.on("click", function () {
                    selectedAnswers[questionIndex] = {questionId: question.id, selectedAnswerId: option.id};
                    console.log("index: " + questionIndex + " questionId: " + question.id + ", optionId: " + option.id)
                    optionItem.addClass("selected-answer")
                });

                optionsList.append(optionItem);
            });

            listItem.append(optionsList);
            questionsList.append(listItem);
        });
        $(".question-container").fadeIn();
    }


    $("#view-result").on("click", function () {
        var selectedQuizId = $("#quiz-passed").val();

        $.ajax({
            url: "http://localhost:8080/quizz/result/" + student.id + "/" + selectedQuizId,
            method: "GET",
            headers: headers,
            success: function (result) {
                updateQuizInfo(result);
                buildResultList(result);
            },
            error: function (error) {
                console.error("Error getting quiz details:", error);
            }
        });
    });

    function updateQuizInfo(element) {
        $("#quiz-name").text(element.name);
        $("#num-questions-value").text(element.numberOfQuestions);
        $("#num-corrects-value").text(element.correctAnswersAmount);
        $("#quiz-result-value").text(element.result + "%");
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
        localStorage.removeItem("studentInfo"),
            window.location.href = "Головна.html";
    });

});