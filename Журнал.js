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
    console.log("Test");

    var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
    var classRoomId = $("#class-room option:selected").val();
    var subjectId = $("#subject option:selected").val()

    $("#scorecard-table").find("tr:gt(0)").remove();


    $.get({
        url: "http://localhost:8080/lesson/short/" + classRoomId + "/" + subjectId,
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);
        $("#scorecard-table").find('thead')
            .append($('<th>')
                .append($('<p>')
                    .text("Учні класу")
                )
            );
        data.forEach(element => {
            var date = new Date(element.lessonTime);
            $("#scorecard-table").find('thead')
                .append($('<th>')
                    .append($('<p>')
                        .text(date.toLocaleDateString())
                        .val(element.id)
                    )
                );
        });
    });

    $.get({
        url: "http://localhost:8080/student/scorecards/" + classRoomId + "/" + subjectId,
        headers: {
            Authorization: 'Bearer ' + teacher.token
        }
    }).done(function (data) {
        console.log(data);

        var table = $("#scorecard-table").find('tbody');



            $.each(data, function(key, val) {
                var studentName =   $.map(data, function () {
                    return $('<td contenteditable="true">').text(data.lastName + '' + data.firstName);
                });

                // $.each(student, function(key, val) {
                //     var tds =$.map(attendance, function() {
                //         return ' <td contenteditable="true">' + attendance.mark.value + '</td>';
                //     });
                // });

                table.append('<tr>' + studentName.join('')+
                    // tds.join('') +
                    '</tr>');
            });
        });
    //     data.content.forEach(element => {
    //         $("#scorecard-table").find('tbody')
    //             .append($('<tr>')
    //                 .append($('<th>')
    //                     .append($('<p>')
    //                         .text(element.lastName + '' + element.firstName)
    //                     )
    //                 ),
    //                 element.attendances.forEach(at => {
    //                 .append($('<th>')
    //                         .append($('<p>')
    //                             .text(at.mark.value)
    //                             .val(at.id)
    //                         )
    //                     )
    //                 })
    //             );
    //     });
    // });
});

//
//
// $("#new-class-submit").click(function () {
//     var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
//         var classRoomInfo = {
//         grade: $("#grade option:selected").val(),
//         className: $("#class-name").val()
//         }
//         console.log(classRoomInfo);
//
//         $.post({
//             url: "http://localhost:8080/class-room",
//             headers: {
//                 Authorization: 'Bearer ' + teacher.token
//             },
//             contentType: 'application/json',
//             dataType: 'json',
//             data: JSON.stringify(classRoomInfo)
//         }).done(function () {
//             console.log("test");
//             window.location.reload();
//         });
//     });
//
//
//
//     $("#student-list-submit").click(function () {
//         // console.log("test")
//         var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
//         var classRoomId = $("#class-selector option:selected").val();
//         $.get({
//             url: "http://localhost:8080/student/by-class/" + classRoomId,
//             headers: {
//                 Authorization: 'Bearer ' + teacher.token
//             }
//         }).done(function (data) {
//             console.log(data);
//             data.content.forEach(element => {
//                 $("#student-table").find('tbody')
//                     .append($('<tr>')
//                         .append($('<th>')
//                             .append($('<p>')
//                                 .text(element.lastName)
//                             )
//                         )
//                         .append($('<th>')
//                             .append($('<p>')
//                                 .text(element.firstName)
//                             )
//                         )
//                         .append($('<th>')
//                             .append($('<p>')
//                                 .text(element.login)
//                             )
//                         )
//                     );
//             });
//
//         });
//     });
//
// $("#create-student-submit").click(function () {
//     console.log("test")
//     var teacher = JSON.parse(localStorage.getItem("teacherInfo"));
//         var studentInfo = {
//         classRoomId: $("#classRoom option:selected").val(),
//         lastName: $("#lastName").val(),
//         firstName: $("#firstName").val(),
//         password: $("#password").val()
//         }
//
//         $.post({
//             url: "http://localhost:8080/student",
//             headers: {
//                 Authorization: 'Bearer ' + teacher.token
//             },
//             contentType: 'application/json',
//             dataType: 'json',
//             data: JSON.stringify(studentInfo)
//         }).done(function () {
//             console.log("test1");
//             window.location.reload();
//         });
//     });

$("#exit").click(function () {
    localStorage.removeItem("teacherInfo"),
        window.location.href = "Головна.html";
});