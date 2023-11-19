$("#sing-in-submit").click(function(){

    var singInInfo = {
        email: $("#email").val(),
        password: $("#password").val(),
    }

    $.post({
        url:"http://localhost:8080/user/sign-in",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(singInInfo)    
    }).done(function(data) {
        localStorage.clear()
        if (data.role == "TEACHER"){
            localStorage.setItem("teacherInfo", JSON.stringify(data))
            window.location.href="Teacher-profile.html";  
        } else {
            localStorage.setItem("studentInfo", JSON.stringify(data))
            window.location.href="Student-profile.html";
        }          
        
    });  
  });