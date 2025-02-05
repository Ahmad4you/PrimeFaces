function validateForm() {
    var name = document.getElementById("myForm:name").value;
    var email = document.getElementById("myForm:email").value;
    var age = document.getElementById("myForm:age").value;

    if (name.trim() === "") {
        alert("Bitte geben Sie einen Namen ein.");
        return false;
    }

    if (email.trim() === "" || !validateEmail(email)) {
        alert("Bitte geben Sie eine gültige E-Mail-Adresse ein.");
        return false;
    }

    if (isNaN(age) || age < 18 || age > 120) {
        alert("Bitte geben Sie ein gültiges Alter zwischen 18 und 120 ein.");
        return false;
    }

    return true;
}

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}