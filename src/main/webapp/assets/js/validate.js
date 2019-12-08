document.addEventListener('DOMContentLoaded', function () {
    var pass1 = document.querySelector('#pass1'),
        pass2 = document.querySelector('#pass2'),
        submit = document.querySelector('#submit');
    pass1.addEventListener('input', function () {
        this.value != pass2.value ? pass2.setCustomValidity('Пароли не совпадают') : pass2.setCustomValidity('');
    });
    pass2.addEventListener('input', function (e) {
        this.value != pass1.value ? this.setCustomValidity('Пароли не совпадают') : this.setCustomValidity('');
        !pass2.checkValidity() && submit.click();
    });
    submit.addEventListener('click', function (e) {
        pass1.value == '' && e.preventDefault();
    });
});