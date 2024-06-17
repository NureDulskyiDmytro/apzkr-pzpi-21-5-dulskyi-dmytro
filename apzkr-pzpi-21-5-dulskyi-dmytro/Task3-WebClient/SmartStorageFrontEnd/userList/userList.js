const getUsersURL = 'http://localhost:25016/api/getUser';
const updateUserURL = 'http://localhost:25016/api/userUpdate';
let token = sessionStorage.getItem('id_token');
let modal = document.querySelector('.modal');
let user = JSON.parse(sessionStorage.getItem('user'));
console.log(user);

function getUsers() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `${getUsersURL}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let users = JSON.parse(xhr.responseText);
            renderUsers(users);
        }
    };
}
// console.log(token);

function renderUsers(arr) {
    console.log(arr);
    let parent = document.querySelector('.user-list');
    parent.innerHTML = '<h1 class="lang orders__heading" key="users-title">Користувачі</h1>';

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.dataset.username = element.username;
        mainDiv.dataset.fName = element.fName;
        mainDiv.dataset.lName = element.lName;
        mainDiv.dataset.email = element.email;
        mainDiv.classList.add('current__ordering');
        parent.appendChild(mainDiv);
        mainDiv.addEventListener('click', (event) => {
            // showEditModal(event, mainDiv.dataset.fName, mainDiv.dataset.lName, mainDiv.dataset.email, mainDiv.dataset.username);
        });

        let email = document.createElement('h4');
        email.innerHTML = `E-mail: ${element.email}`;
        mainDiv.appendChild(email);

        let fName = document.createElement('h4');
        fName.innerHTML = `Name: ${element.fName}`;
        mainDiv.appendChild(fName);

        let lName = document.createElement('h4');
        lName.innerHTML = `Surname: ${element.lName}`;
        mainDiv.appendChild(lName);

        let age = document.createElement('h4');
        age.innerHTML = `Age: ${element.age}`;
        mainDiv.appendChild(age);

        let phone = document.createElement('h4');
        phone.innerHTML = `Phone: ${element.phone}`;
        mainDiv.appendChild(phone);

        let userName = document.createElement('h4');
        userName.innerHTML = `Username: ${element.username}`;
        mainDiv.appendChild(userName);

        if(element.authorities[0].name === 'ROLE_ADMIN') {
            let statusDiv = document.createElement('div');
            mainDiv.appendChild(statusDiv); 
                let changeStatus = document.createElement('button');
                changeStatus.classList.add('change__ordering');
                changeStatus.classList.add('lang');
                changeStatus.innerHTML = element.activated ? 'Блокувати' : 'Розблокувати';
                if(element.activated) {
                    changeStatus.setAttribute('key', 'ban');
                } else {
                    changeStatus.setAttribute('key', 'unban');
                }
                changeStatus.dataset.userId = element.id;
                statusDiv.appendChild(changeStatus);
                changeStatus.addEventListener('click', () => {
                   blockUnblockUser(element.id, changeStatus.innerHTML);
                });
        }
    });
}

function showEditModal(event, fName, lName, email, username) {
    console.log(email);
    if(event.target.getAttribute('key') === 'unban' || event.target.getAttribute('key') === 'ban') {
        return;
    }
    modal.classList.remove('invisible');
    modal.style.display = 'flex';

    let nameInput = document.querySelector('#name');
    nameInput.value = fName;
    let surnameInput = document.querySelector('#surname');
    surnameInput.value = lName;
    let emailInput = document.querySelector('#email');
    emailInput.value = email;
    let usernameInput = document.querySelector('#username');

    let cancel = document.querySelector('#cancel');
    let order = document.querySelector('#order');

    cancel.addEventListener('click', () => {
        modal.classList.add('invisible');
        modal.style.display = 'none';
    });

    order.addEventListener('click', () => {
        updateUser(nameInput.value, surnameInput.value, emailInput.value, username);
    });
}

function updateUser(name, lastname, email, username) {
    console.log(email);
    let data = {};
    data.username = username;
    data.fName = name;
    data.lName = lastname;
    data.email = email;

    let dataToJSON = JSON.stringify(data);
    let xhr = new 
    xhr.open('PUT', updateUserURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            getUsers();
            modal.classList.add('invisible');
            modal.style.display = 'none';
        }
    };
}

function blockUnblockUser(id, text) {
    let stringURL;
    if(text === 'Блокувати' || text === 'Ban') {
        stringURL = `http://localhost:25016/api/banUser/${id}/false`;
    } else {
        stringURL = `http://localhost:25016/api/banUser/${id}/true`;
    }
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', stringURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState === 4) {
            getUsers();
        }
    };
}

getUsers();

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
        'welcome': 'Welcome!',
        'backHeading': 'Welcome to',
        'users-title': 'Users',
        'ban': 'Ban',
        'unban': 'Unban',
        'copy': 'Backup DB',
        'restore': 'Restore DB',
        'products-title': 'Products',
        'users': 'Users'
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
        'welcome': 'Вітаю!',
        'backHeading': 'Вас вітає',
        'users-title': 'Користувачі',
        'ban': 'Блокувати',
        'unban': 'Розблокувати',
        'copy': 'Копіювання БД',
        'restore': 'Відновлення БД',
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
        setTimeout(() => {
            $('.lang').each(function(index, item) {
                $(this).text(arrLang[lang][$(this).attr('key')]);
            });
        }, 0);
    });
}
