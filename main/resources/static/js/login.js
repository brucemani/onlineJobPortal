$(document).ready(function(){
    $("#submit").click(function(){
        if($("#user").val().length===0){
            alert("Please enter the user value");
            return false;
        }
        if($("#password").val().length===0) {
            alert("Please enter the Password");
            return false;
        }
    })
})