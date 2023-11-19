window.onload = function () {
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));

    $.get({
        url: "http://localhost:8080/teacher/" + teacher.id,
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        $("#first-name").val(data.firstName)
        $("#last-name").val(data.lastName)
        $("#email").val(data.login)
        $("#lesson-link").val(data.lessonLink)
        $("#profileImg").attr("src", data.avatar)
    });

    
    $.get({
        url: "http://localhost:8080/lesson/find?teacherId=" + teacher.id,
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.content.forEach(element => {
            var date = new Date(element.lessonTime);
            $("#lesson-table").find('tbody')
                .append($('<tr>')
                    .append($('<th>')
                        .append($('<p>')
                            .text( date.toLocaleTimeString() + " " + date.toLocaleDateString())
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.classGrade)
                        )
                    )

                    .append($('<th>')
                        .append($('<p>')
                            .text(element.subjectName)
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.topic)
                        )
                    )
                );
        });

    });

    $.get({
        url: "http://localhost:8080/class-room/all",
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            $("#classRoom").append(`<option value=`+ element.id +`> `+ element.className +` </option>`);
        });
    })

    $.get({
        url: "http://localhost:8080/subject/all",
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            $("#subject").append(`<option value=`+ element.id +`> `+ element.subjectName +` </option>`);
        });
    })
}

$("#update-submit").click(function () {
    console.log("Test");

    var updateInfo = {
        login: $("#email").val(),
        firstName: $("#first-name").val(),
        lastName: $("#last-name").val(),
        lessonLink: $("#lesson-link").val()
    }

    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
    console.log(teacher.id, teacher.token);

    $.ajax({
        url: "http://localhost:8080/teacher/edit/" + teacher.id,
        type: 'PUT',
        headers: {
            Authorization: 'Bearer ' + teacher.token
        },
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(updateInfo)
    }).done(function () {
        window.location.reload();
    });    
});

$("#create-lesson-submit").click(function () {
        var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
        console.log($("#file").get(0).files[0]);
        var formData = new FormData();
        console.log($("#lessonTime").val());
        formData.append("classRoomId", $("#classRoom option:selected").val());
        formData.append("subjectId",  $("#subject option:selected").val());
        formData.append("lessonTime",  $("#lessonTime").val());
        formData.append("topic", $("#topic").val());
        formData.append("description", $("#description").val());
        formData.append("homework", $("#homework").val());
        formData.append("teacherId", teacher.id);
        formData.append("file", $("#file").get(0).files[0]);
        console.log(formData)
        $.post({
            url: "http://localhost:8080/lesson",
            headers: {
                Authorization: 'Bearer ' + teacher.token
            },
            data: formData,
            cache: false,
            enctype: 'multipart/form-data',
            contentType:false,
            processData: false,
            method: 'POST',
        }).done(function () {
            console.log("test");
            window.location.reload();
        });
    });

    $("#exit").click(function (){
        localStorage.removeItem("teacherInfo"),
        window.location.href = "Головна.html";
        });
