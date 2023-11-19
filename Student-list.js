window.onload = function () {
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));

    $.get({
        url: "http://localhost:8080/class-room/grades",
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            $("#grade").append(`<option value=`+ element +` > `+ element +` </option>`);
        });
    })

        $.get({
        url: "http://localhost:8080/class-room/all",
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            $("#class-table").find('tbody')
                .append($('<tr>').addClass("classRoom")
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.grade)
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.className)
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
            $("#class-selector").append(`<option value=`+ element.id+` > `+ element.className +` </option>`);
        });
        data.forEach(element => {
            $("#classRoom").append(`<option value=`+ element.id+` > `+ element.className +` </option>`);
        });
    })
    

    
}

$("#new-class-submit").click(function () {    
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
        var classRoomInfo = {
        grade: $("#grade option:selected").val(),
        className: $("#class-name").val()      
        }
        console.log(classRoomInfo);
        
        $.post({
            url: "http://localhost:8080/class-room",
            headers: {
                Authorization: 'Bearer ' + teacher.token
            },
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(classRoomInfo)
        }).done(function () {
            console.log("test");
            window.location.reload();
        });
    });

   

$("#student-list-submit").click(function () {
    $("#student-table").find("tr:gt(0)").remove();
        var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
        var classRoomId = $("#class-selector option:selected").val();         
    $.get({
        url: "http://localhost:8080/student/by-class/" + classRoomId,
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.content.forEach(element => {
            $("#student-table").find('tbody')
                .append($('<tr>')
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.lastName)
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.firstName)
                        )
                    )
                    .append($('<th>')
                        .append($('<p>')
                            .text(element.login)
                        )
                    )
            
                );
        });

    });
});

$("#create-student-submit").click(function () {
    console.log("test")
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
        var studentInfo = {
        classRoomId: $("#classRoom option:selected").val(),
        lastName: $("#lastName").val(),
        firstName: $("#firstName").val(),
        password: $("#password").val()
        }
        
        $.post({
            url: "http://localhost:8080/student",
            headers: {
                Authorization: 'Bearer ' + teacher.token
            },
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(studentInfo)
        }).done(function () {
            console.log("test1");
            window.location.reload();
        });
    });

$("#exit").click(function (){
localStorage.removeItem("teacherInfo"),
window.location.href = "Головна.html";
});