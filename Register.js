$("#register-submit").click(function(){
    console.log("Test");

    var teacherInfo = {
        firstName: $("#first-name").val(),
        lastName: $("#last-name").val(),
        login: $("#email").val(),
        password: $("#password").val(),
    }
    console.log(teacherInfo)

    $.post({
        url:"http://localhost:8080/teacher",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(teacherInfo)    
    }).done(function(data) {
        console.log(data);
        localStorage.clear
        localStorage.setItem("teacherInfo", JSON.stringify(data))  
        window.location.href="Teacher-profile.html"  
    }); 
  });