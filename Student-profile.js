window.onload = function () {
    var student = JSON.parse(localStorage.getItem("studentInfo"));

    $.get({
        url: "http://localhost:8080/student/" + student.id,
        headers: {
            Authorization: 'Bearer ' + student.token
        }
    }).done(function (data) {
        $("#first-name").val(data.firstName)
        $("#last-name").val(data.lastName)
        $("#email").val(data.login)
        $("#profileImg").attr("src", data.avatar)
    });


    $.get({
        url: "http://localhost:8080/lesson/find/" + student.id,
        headers: {
            Authorization: 'Bearer ' + student.token
        }
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            var date = new Date(element.lessonTime);
            $("#lesson-table").find('tbody')
                .append($('<tr>')
                    .on("click", function () {
                        redirectOnZoom(element);
                    })
                    .append($('<th>')
                        .append($('<p>')
                            .text(date.toLocaleTimeString() + " " + date.toLocaleDateString())
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.topic)
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.homework)
                        )
                    )

                    .append($('<th>')
                        .append($('<p>')
                            .text(element.mark)
                        )
                    )
                );
            $("#lesson").append(`<option value=` + element.id + `> ` + element.topic + ` </option>`);
        });


    });

}

$("#update-submit").click(function () {

    var updateInfo = {
        login: $("#email").val(),
        firstName: $("#first-name").val(),
        lastName: $("#last-name").val()
    }

    var student = JSON.parse(localStorage.getItem("studentInfo"));
    console.log(student.id, student.token);

    $.ajax({
        url: "http://localhost:8080/student/edit/" + student.id,
        type: 'PUT',
        headers: {
            Authorization: 'Bearer ' + student.token
        },
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(updateInfo)
    }).done(function () {
        window.location.reload();
    });
});


function redirectOnZoom(element){
    window.open(element.lessonLink);
}

$("#view-lesson").on("click", function () {
    var selectedLessonId = $("#lesson").val();
    var student = JSON.parse(localStorage.getItem("studentInfo"));

    // Make an AJAX request to get lesson details
    $.ajax({
        url: "http://localhost:8080/lesson/" + selectedLessonId,
        headers: {
            Authorization: 'Bearer ' + student.token
        },
        method: "GET",
        success: function (lessonDetails) {
            // Update the HTML with the lesson details
            $("#lesson-topic").text("Тема уроку: " + lessonDetails.topic);
            $("#lesson-description").text("Деталі уроку: " + lessonDetails.description);
            $("#lesson-homework").text("Домашнє завдання: " + lessonDetails.homework);
            $("#files-topic").text("Файли уроку: ");

            // Clear and populate the list of files
            var filesList = $("#lesson-files");
            filesList.empty();
            lessonDetails.files.forEach(function (file) {
                filesList.append("<li><a href='" + file.url + "' target='_blank'>" + file.name + "</a></li>");
            });

            // Display the lesson info div with a fade-in effect
            $("#lesson-info").fadeIn();
        },
        error: function (error) {
            console.error("Error getting lesson details:", error);
        }
    });
});

$("#exit").click(function () {
    localStorage.removeItem("studentInfo"),
        window.location.href = "Головна.html";
});