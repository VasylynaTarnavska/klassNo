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

$("#create-quizz-submit").click(function () {
    var quizzInfo = {
        name: $("#quizName").val(),
        lessonId: $("#lesson option:selected").val()
    }
    console.log(quizzInfo);
    $.ajax({
        url: "http://localhost:8080/quizz",
        type: 'POST',
        headers: headers,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(quizzInfo),

        success: function (data) {
            let quiz = document.querySelector("#new-quiz");
            quiz.dataset.quizId = data;
            console.log("New quiz id = " + quiz.dataset.quizId);
            displayButton();

        },
        error: function (error) {
            console.error('Error creating quiz:', error);
        }
    });

});


function displayButton() {
    $("#button-container").empty();

    var editButton = $("<button>Редагувати</button>").click(function () {
        // Handle edit button click
        console.log("Edit button clicked");
    });
    editButton.addClass("u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1")

    var deleteButton = $("<button>Видалити</button>").click(function () {
        deleteQuiz();
        console.log("Delete button clicked");
    });
    deleteButton.addClass("u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1")

    var addQuestionButton = $("<button>+ питання</button>").click(function () {
        var questionForm = createQuestionForm();
        $("#question-container").empty();
        $("#question-container").append(questionForm);
        var finalButton = createFinalButton();
        $("#final-container").append(finalButton);
        console.log("Add Question button clicked");
    });
    addQuestionButton.addClass("u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1")

    $("#button-container").append(editButton, deleteButton, addQuestionButton);
}

function deleteQuiz() {
    let quiz = document.querySelector("#new-quiz");
    var quizId = quiz.dataset.quizId;

    $.ajax({
        url: "http://localhost:8080/quizz/" + quizId,
        type: 'DELETE',
        headers: headers,
        success: function () {
            console.log("Delete quizz");
            window.location.reload();
        },
        error: function (error) {
            console.error('Error deleting quizz:', error);
        }
    });
}

function createQuestionForm() {
    var questionForm = $("<div class='question-form'>");
    questionForm.append("<br> <textarea rows='1' cols='64' name='questionText' placeholder='Введіть запитання'></textarea><br>")
    questionForm.append("<span> Вкажіть варіанти відповідей і позначте правильний:  </span></div>")

    for (var i = 0; i < 4; i++) {
        var optionForm = $("<div class='option-form option-item'>");
        var label = $("<label class='radio-label'" +
            " style=' display: flex;\n" +
            "    align-items: center;\n" +
            "    margin-bottom: 10px;\n" +
            "    box-shadow: none;\n" +
            "    border-bottom: none;\n" +
            "    transition: none;" +
            "   font-size: 16px'>");
        label.append("<input type='radio' name='correctOption' class='styled-radio'" +
            " style=' appearance: none;\n" +
            "    -webkit-appearance: none;\n" +
            "    -moz-appearance: none;\n" +
            "    width: 16px;\n" +
            "    height: 16px;\n" +
            "    border: 2px solid #3498db;\n" +
            "    border-radius: 50%;\n" +
            "    outline: none;\n" +
            "    transition: 0.2s;\n" +
            "    cursor: pointer;\n" +
            "    display: flex;" +
            "    margin-right: 10px;" +
            "    margin-left: 10px;'>");
        label.append("<textarea rows='1' cols='30' name='answerOption' class='answer-option' placeholder='Варіант відповіді'></textarea>");
        optionForm.append(label);
        questionForm.append(optionForm);
    }

    return questionForm;
}

function createFinalButton() {
    var finalButtons = $("<div id='button'><button class='save-question form-buttons u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1'>" +
        "Зберегти</button>");
    finalButtons.append(" <button class='add-question u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1'>" +
        "+ питання</button>")

    var final = $("<button>Завершити</button>").click(function () {
        window.location.reload();
    });
    final.addClass("u-border-none u-btn u-btn-round u-button-style u-hover-palette-3-light-1 u-palette-3-base u-radius-10 u-btn-1")
    finalButtons.append(final);

    return finalButtons;
}

$("#final-container").on("click", ".save-question", function () {
    let quiz = document.querySelector("#new-quiz");
    var quizId = quiz.dataset.quizId;
    var questionText = $("textarea[name='questionText']").val();

    // Collect answers
    var answers = [];

    $(".question-form").find(".option-form").each(function () {
        var answerOption = $("textarea[name='answerOption']").val();
        var isCorrect = $("input[name='correctOption']").prop("checked").val();
        answers.push({ answerOption: answerOption, isCorrect: isCorrect });
    });

    console.log('Question Text:', questionText);
    console.log('Answers:', answers);

    var questionData = {
        quizzId: quizId,
        questionText: questionText,
        answers: answers
    };

    console.log(questionData)

    // Send data to the server
    $.ajax({
        url: "http://localhost:8080/quizz/add-question",
        method: 'POST',
        headers: headers,
        contentType: 'application/json',
        data: JSON.stringify(questionData),
        success: function (response) {
            console.log('Data sent successfully:', response);
        },
        error: function (error) {
            console.error('Error sending data:', error);
        }
    });
});

$("#final-container").on("click", ".add-question", function () {
    var questionForm = createQuestionForm();
    $(".question-form").after(questionForm);
});


$("#exit").click(function () {
    localStorage.removeItem("teacherInfo"),
        window.location.href = "Головна.html";
});