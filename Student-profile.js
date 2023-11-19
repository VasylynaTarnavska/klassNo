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
    console.log("Test");

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


$("#exit").click(function () {
    localStorage.removeItem("studentInfo"),
        window.location.href = "Головна.html";
});