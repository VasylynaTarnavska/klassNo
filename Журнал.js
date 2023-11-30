window.onload = function () {
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));

    $.get({
        url: "http://localhost:8080/class-room/all",
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        data.forEach(element => {
            $("#class-room").append(`<option value=` + element.id + ` > ` + element.className + ` </option>`);
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
            $("#subject").append(`<option value=` + element.id + `> ` + element.subjectName + ` </option>`);
        });
    })

}
$("#show-marks").click(function () {
    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
    var classRoomId = $("#class-room option:selected").val();
    var subjectId = $("#subject option:selected").val()

    $("#scorecard-table thead").empty();
    $("#scorecard-table tbody").empty();

    $.get({
        url: "http://localhost:8080/lesson/short/" + classRoomId + "/" + subjectId,
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (lessonData) {
        console.log(lessonData);
        var tableHeader = $("#scorecard-table thead");

        tableHeader.append('<th><p>Учні класу</p></th>');

        lessonData.forEach(function (lesson) {
            var date = new Date(lesson.lessonTime);
            tableHeader.append('<th><p>' + date.toLocaleDateString() + '</p></th>');
        });

        $.get({
            url: "http://localhost:8080/student/scorecards/" + classRoomId + "/" + subjectId,
            headers: {
                Authorization: 'Bearer ' + teacher.token
            }
        }).done(function (studentData) {
            console.log(studentData);
            var tableBody = $("#scorecard-table tbody");

            studentData.forEach(function (student) {
                var studentName = '<td>' + student.lastName + ' ' + student.firstName + '</td>';
                var tds = "";

                lessonData.forEach(function (lesson) {
                    // Adjust this part based on the structure of your 'attendance' object
                    var attendance = student.attendances.find(att => att.lessonId === lesson.id);
                    var markValue = attendance && attendance.mark ? attendance.mark.value : '';
                    tds += '<td contenteditable="true">' + markValue + '</td>';
                });

                tableBody.append('<tr>' + studentName + tds + '</tr>');
            });
        });
    });
});


$("#exit").click(function () {
    localStorage.removeItem("teacherInfo"),
        window.location.href = "Головна.html";
});