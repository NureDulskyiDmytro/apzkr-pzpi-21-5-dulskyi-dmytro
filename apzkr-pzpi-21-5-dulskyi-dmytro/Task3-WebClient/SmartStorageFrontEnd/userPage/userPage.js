let userUpdateURL = 'http://localhost:25016/api/userUpdate';
let usersURL = 'http://localhost:25016/api/user';
let user = JSON.parse(sessionStorage.getItem('user'));
let token = sessionStorage.getItem('id_token');

let nameField = document.querySelector('.account__current__name');
nameField.innerHTML = user.fName;
let nameInput = document.querySelector('#name');
nameInput.value = user.fName;
let changeButton = document.querySelector('#change-name');
changeButton.addEventListener('click', () => {
    changeData(changeButton, nameField, nameInput);
});

let surnameField = document.querySelector('.account__current__surname');
surnameField.innerHTML = user.lName;
let surnameInput = document.querySelector('#surname');
surnameInput.value = user.lName;
let changeButton_2 = document.querySelector('#change-surname');
changeButton_2.addEventListener('click', () => {
    changeData(changeButton_2, surnameField, surnameInput);
});


let birthDateField = document.querySelector('.account__current__birthdate');
birthDateField.classList.add('lang');
birthDateField.setAttribute('key', 'change-date')
birthDateField.innerHTML = 'Змінити дату народження';
let birthDateInput = document.querySelector('#birthdate');
birthDateInput.value = user.birthDate;
let changeButton_4 = document.querySelector('#change-birthdate');
changeButton_4.addEventListener('click', () => {
    changeData(changeButton_4, birthDateField, birthDateInput);
});


let emailField = document.querySelector('.account__current__email');
emailField.innerHTML = user.email;
let emailInput = document.querySelector('#email');
emailInput.value = user.email;
let changeButton_7 = document.querySelector('#change-email');
changeButton_7.addEventListener('click', () => {
    changeData(changeButton_7, emailField, emailInput);
});


let phoneField = document.querySelector('.account__current__phone');
phoneField.innerHTML = user.phone;
let phoneInput = document.querySelector('#phone');
phoneInput.value = user.phone;
let changeButton_9 = document.querySelector('#change-phone');
changeButton_9.addEventListener('click', () => {
    changeData(changeButton_9, phoneField, phoneInput);
});


function changeData(button, text, input) {
    if(button.innerHTML === 'Змінити' || button.innerHTML === 'Change') {
        text.classList.add('invisible');
        input.classList.remove('invisible');
        button.innerHTML = 'Зберегти';
    } else {
        text.classList.remove('invisible');
        input.classList.add('invisible');
        text.innerHTML = input.value;
        button.innerHTML = 'Змінити';
    }
}


let saveButton = document.querySelector('.account__savebutton');

saveButton.addEventListener('click', saveChanges);

function saveChanges() {
    let data = {};
    data.email = emailInput.value;
    data.fName = nameInput.value;
    data.lName = surnameInput.value;
    data.phone = phoneInput.value;
    data.age = setAge(birthDateInput.value) || user.age;
    data.id = user.id;

    let dataToJSON = JSON.stringify(data);
    console.log(data);

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', userUpdateURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            findUser();
            alert('success');
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

    if(isNaN(age)) return false;

    return age;
}

function findUser() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `${usersURL}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            sessionStorage.setItem('user', JSON.stringify(JSON.parse(xhr.responseText)));
            document.location.href = './userPage.html';
        }
    };
}

document.addEventListener('DOMContentLoaded', getLocalLang);

let arrLang = {
    'en': {
        'exit': 'Exit',
        'order': 'Order',
        'myOrders': 'My Orders',
        'myAccount': 'My Account',
        'products': 'Products',
        'statistic': 'Statistic',
        'orderings': 'Orders',
        'storages': 'Storages',
        'storages-title': 'Storages',
        'name': 'Name',
        'surname': 'Surname',
        'birthdate': 'Birth Date',
        'email': 'E-mail',
        'phone': 'Phone number',
        'change-date': 'Change Birth Date',
        'button': 'Change',
        'personalData': 'Personal Data',
        'saveChanges': 'Save',
        'copy': 'Backup DB',
        'restore': 'Restore DB',
        'users-title': 'Users',
        'products-title': 'Products',
        'users': 'Users',

    },
    'ua': {
        'exit': 'Вийти',
        'order': 'Замовити',
        'myOrders': 'Мої замовлення',
        'myAccount': 'Мій акаунт',
        'products': 'Товар',
        'statistic': 'Статистика',
        'orderings': 'Замовлення',
        'storages': 'Склад',
        'storages-title': 'Склад',
        'name': 'Ім`я',
        'surname': 'Прізвище',
        'birthdate': 'Дата народження',
        'email': 'Електронна пошта',
        'phone': 'Телефон',
        'change-date': 'Змінити дату народження',
        'button': 'Змінити',
        'personalData': 'Персональні дані',
        'saveChanges': 'Зберегти зміни',
        'copy': 'Копіювання БД',
        'restore': 'Відновлення БД',
        'users-title': 'Користувачі',
        'products-title': 'Товар',
        'users': 'Користувачі',

    }
  };
  $(function() {
    $('.translate').click(function() {
        var lang = $(this).attr('id');
        saveLocalLang(lang);

        $('.lang').each(function(index, item) {
            $(this).text(arrLang[lang][$(this).attr('key')]);
        });
    });
  });


function saveLocalLang(language) {
    let langs;
    if(localStorage.getItem('langs') === null) {
        langs = [];
    } else {
        langs = JSON.parse(localStorage.getItem('langs'));
    }
    langs.push(language);
    localStorage.setItem('langs', JSON.stringify(langs));
}

function getLocalLang(language) {
    let langs;
    if(localStorage.getItem('langs') === null) {
        langs = [];
    } else {
        langs = JSON.parse(localStorage.getItem('langs'));
    }
    langs.forEach(function (language) {
        let lang = langs[langs.length - 1];
        $('.lang').each(function(index, item) {
            $(this).text(arrLang[lang][$(this).attr('key')]);
        });
    });
}
