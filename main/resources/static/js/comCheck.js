$(document).ready(function(){
    $("#submit").click(function(){
       const cname=$("#cname").val();
       if(cname.length===0){
           alert("Please enter the Company name");
           return false;
       }
       const pass1=$("#pass1").val();
       const pass2=$("#pass2").val();
       if(pass1.length===0||pass1!==pass2){
           alert("Password Mismatch");
           return false;
       }
       if($("#location").val().length===0){
           alert("Please enter Location");
           return false;
       }
       const reg=/[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)?/gi
       if(!(reg.test($("#web").val()))){
           alert("Invalid URL");
           return false;
       }
    })
})