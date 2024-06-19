// validation.js

$(document).ready(function() {
    $("form").validate({
        rules: {
            name: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 6
            },
            confirmPassword: {
                required: true,
                equalTo: "#password"
            },
            age: {
                required: true,
                number: true,
                min: 18
            },
            phoneNumber:{
                required: true,
                minlength : 10,
                maxlength:13
            },
            about:{
                required: true,
            }

        },
        messages: {
            name: {
                required: "Name is required"
            },
            email: {
                required: "Email is required",
                email: "Please enter a valid email address"
            },
            password: {
                required: "Password is required",
                minlength: "Password must be at least 6 characters long"
            },
            confirmPassword: {
                required: "Please confirm your password",
                equalTo: "Passwords do not match"
            },
            age: {
                required: "Age is required",
                number: "Please enter a valid age",
                min: "You must be at least 18 years old"
            },
            phoneNumber: {
                required: "Mobile number is required",
                minLength: "Minimun lenght is 10 Digit",
                maxlength: "Max lenght is 13 Digit"
            },
            about: {
                required: "About is required",
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.parent().find(".error-container"));
        }
    });
});
