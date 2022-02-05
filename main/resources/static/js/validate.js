$(document).ready(function () {
    $("#submit").click(function () {
        const  fName=$("#fname").val();
        if(fName.length===0||!(isNaN(fName[0]))){
            alert("Please Enter the name");
            return false;
        }
        const lName=$("#lname").val();
        if(lName.length===0||!(isNaN(lName[0]))){
            alert("Please Enter the last name");
            return false;
        }
        const phone=$("#phone").val();
        if(isNaN(phone)||phone.length!==10){
            alert("Please Enter Valid phone number")
            return false;
        }
        const gender=$("#gender").val();
        if(gender==="GENDER"){
            alert("Please Select Gender");
            return false;
        }
        const user=$("#user").val();
        if(user.length<=5){
            alert("Please enter valid user name at least 5 character above");
            return false;
        }
        const ex=$("#exp").val();
        if(ex.length===0||isNaN(ex)||ex<0){
            alert("please give valid experience");
            return false;
        }
        const pass1=$("#pass1").val();
        const pass2=$("#pass2").val();
        if(pass1.length===0||pass1!==pass2) {
            alert("Password Mismatch");
            return false;
        }

    });
});
