//URLs
let loginURL = 'http://localhost:25016/api/authenticate';
let signupURL = 'http://localhost:25016/api/register';
let usersURL = 'http://localhost:25016/api/user';

let signUpLink = document.getElementById('sign-up-link');
let logInLink = document.getElementById('log-in-link');

let logInContainer = document.querySelector('.auth__box');
let signUpContainer = document.querySelector('.auth__box_2');
console.log(sessionStorage.getItem('id_token'));
signUpLink.addEventListener('click', () => {
    logInContainer.classList.add('invisible');
    signUpContainer.classList.remove('invisible');
});
logInLink.addEventListener('click', () => {
    logInContainer.classList.remove('invisible');
    signUpContainer.classList.add('invisible');
}); 

function findUser() {
    let token = sessionStorage.getItem('id_token');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `${usersURL}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let user = JSON.parse(xhr.responseText);
            console.log(JSON.parse(xhr.responseText));
            debugger;
            if(!user.activated) {
                alert('Ви заблоковані!');
                return;
            }
            sessionStorage.setItem('user', JSON.stringify(JSON.parse(xhr.responseText)));
            document.location.href = './welcomePage/welcomePage.html';
        }
    };
}
//Авторизація


let login_username = document.querySelector('#login-username');
let login_password = document.querySelector('#login-password');
let loginButton = document.querySelector('#login-button');

loginButton.addEventListener('click', loginUser);

function loginUser() {
    let data = {};
    if(!login_username.value || !login_password.value) {
        alert("Введіть логін та пароль!");
        return;
    }
    data.username = login_username.value;
    data.password = login_password.value;

    let dataToJSON = JSON.stringify(data);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', loginURL, true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer);
            if(answer.message === 'Неверные учетные данные пользователя') {
                alert("Неправильний логін чи пароль!");
                return;
            } else if(answer.message === `User ${login_username.value} was not activated`) {
                alert("Ви заблоковані!");
                return;
            }
            sessionStorage.setItem('id_token', answer.id_token);
            findUser();
        }
    };
}


//Реєстрація 

let signup_username = document.querySelector('.auth__username');
let signup_email = document.querySelector('.auth__email_2');
let signup_password = document.querySelector('.auth__password_2');
let signup_name = document.querySelector('.auth__lastname');
let signup_surname = document.querySelector('.auth__firstname');
let signup_birthdate = document.querySelector('.auth__birthdate');
let signup_phoneNumber = document.querySelector('#tel');

let signupButton = document.querySelector('#sign-up');

signupButton.addEventListener('click', registerUser);

function registerUser() {
    let data = {};
    data.username = signup_username.value;
    data.email = signup_email.value;
    data.password = signup_password.value;
    data.fName = signup_name.value;
    data.lName = signup_surname.value;
    data.age = `${setAge(signup_birthdate.value)}`;
    data.phone = signup_phoneNumber.value;

    let dataToJSON = JSON.stringify(data);
    console.log(data);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', signupURL, true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            alert('Success registration!');
            document.location.href = './main.html';
        }
    }; 
}

function setAge(date) {
    var now = new Date(); 
    var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    var dob = new Date(date);
    var dobnow = new Date(today.getFullYear(), dob.getMonth(), dob.getDate()); //ДР в текущем году
    var age; 

    age = today.getFullYear() - dob.getFullYear();
    if (today < dobnow) {
        age = age-1;
    }
    
    return age;
}