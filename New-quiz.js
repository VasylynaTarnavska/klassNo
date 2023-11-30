window.onload = function () {
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
    const headers = {
        Authorization: 'Bearer ' + teacher.token
    };


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
    questionForm.append("<br> <textarea rows='1' cols='110' name='questionText'  class='text' placeholder='Введіть запитання'></textarea><br>")
    questionForm.append("<span class='text'> Вкажіть варіанти відповідей і позначте правильний:  </span></div>")

    for (var i = 0; i < 4; i += 2) {
        var optionsRow = $("<div class='options-row'>");

        // First Option
        var optionForm1 = $("<div class='option-form option-item'>");
        var label1 = $("<label class='radio-label'>");
        label1.append("<input type='radio' name='correctOption' class='styled-radio'>");
        label1.append("<textarea rows='1' cols='45' name='answerOption' class='answer-option' placeholder='Варіант відповіді'></textarea>");
        optionForm1.append(label1);
        optionsRow.append(optionForm1);

        // Second Option
        var optionForm2 = $("<div class='option-form option-item'>");
        var label2 = $("<label class='radio-label'>");
        label2.append("<input type='radio' name='correctOption' class='styled-radio'>");
        label2.append("<textarea rows='1' cols='45' name='answerOption' class='answer-option' placeholder='Варіант відповіді'></textarea>");
        optionForm2.append(label2);
        optionsRow.append(optionForm2);

        questionForm.append(optionsRow);
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
        var answerOption = $(this).find("textarea[name='answerOption']").val();
        var isCorrect = $(this).find("input[name='correctOption']").prop("checked");
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
            $(".question-form :input").prop("disabled", true)
            $(".question-form .styled-radio").prop("disabled", true);
            $(".question-form textarea[name='answerOption']").prop("disabled", true);
        },
        error: function (error) {
            console.error('Error sending data:', error);
        }
    });
});

$("#final-container").on("click", ".add-question", function () {
    var questionForm = createQuestionForm();
    $(".question-form:last").after(questionForm); // Append after the last occurrence of .question-form
});



$("#exit").click(function () {
    localStorage.removeItem("teacherInfo"),
        window.location.href = "Головна.html";
});