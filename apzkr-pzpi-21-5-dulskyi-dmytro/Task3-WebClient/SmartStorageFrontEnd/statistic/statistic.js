const statistic1URL = 'http://localhost:25016/api/getAnalise1ByArrayList';
const statistic2URL = 'http://localhost:25016/api/getAnalise2ByArrayList';
const statistic3URL = 'http://localhost:25016/api/getAnalise3ByArrayList';
let token = sessionStorage.getItem('id_token');
let parent = document.querySelector('.orders');

function getStatistic1() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', statistic1URL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let statistic = JSON.parse(xhr.responseText);
            renderStatistic1(statistic);
        }
    };
}

function getStatistic2() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', statistic2URL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let statistic = JSON.parse(xhr.responseText);
            renderStatistic2(statistic);
        }
    };
}

function getStatistic3() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', statistic3URL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let statistic = JSON.parse(xhr.responseText);
            renderStatistic3(statistic);
        }
    };
}

function renderStatistic1(arr) {
    let container = document.createElement('div');
    container.classList.add('container');
    parent.appendChild(container);

    let headind = document.createElement('h2');
    headind.innerHTML = 'Загальна статистика';
    headind.classList.add('heading');
    container.appendChild(headind);

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.classList.add('current__ordering');
        container.appendChild(mainDiv);

        let totalSum = document.createElement('h5');
        totalSum.innerHTML = `Загальна сума: <br><span class="sub-text">${element[0]}</span>`;
        mainDiv.appendChild(totalSum);

        let size = document.createElement('h5');
        size.innerHTML = `Розмір: <br><span class="sub-text">${element[1]}</span>`;
        mainDiv.appendChild(size);

        let category = document.createElement('h5');
        category.innerHTML = `Категорія: <br><span class="sub-text">${element[2]}</span>`;
        mainDiv.appendChild(category);

        let date = document.createElement('h5');
        date.innerHTML = `Дата: <br><span class="sub-text">${element[3]}</span>`;
        mainDiv.appendChild(date);

        let amount = document.createElement('h5');
        amount.innerHTML = `Кількість: <br><span class="sub-text">${element[4]}</span>`;
        mainDiv.appendChild(amount);

        let productId = document.createElement('h5');
        productId.innerHTML = `ID продукта: <br><span class="sub-text">${element[5]}</span>`;
        mainDiv.appendChild(productId);
    });
}

function renderStatistic2(arr) {
    let container = document.createElement('div');
    container.classList.add('container');
    parent.appendChild(container);

    let headind = document.createElement('h2');
    headind.innerHTML = 'Статистика популярності розмірів сховище';
    headind.classList.add('heading');
    container.appendChild(headind);

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.classList.add('current__ordering');
        container.appendChild(mainDiv);

        let totalSum = document.createElement('h5');
        totalSum.innerHTML = `Загальна сума: <br><span class="sub-text">${element[0]}</span>`;
        mainDiv.appendChild(totalSum);

        let size = document.createElement('h5');
        size.innerHTML = `Розмір: <br><span class="sub-text">${element[1]}</span>`;
        mainDiv.appendChild(size);

        let amount = document.createElement('h5');
        amount.innerHTML = `Кількість: <br><span class="sub-text">${element[2]}</span>`;
        mainDiv.appendChild(amount);
    });
}

function renderStatistic3(arr) {
    console.log(arr);
    let container = document.createElement('div');
    container.classList.add('container');
    parent.appendChild(container);

    let headind = document.createElement('h2');
    headind.innerHTML = 'Статистика для рекламодавця';
    headind.classList.add('heading');
    container.appendChild(headind);

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.classList.add('current__ordering');
        container.appendChild(mainDiv);

        let totalSum = document.createElement('h5');
        totalSum.innerHTML = `Загальна сума: <br><span class="sub-text">${element[0]}</span>`;
        mainDiv.appendChild(totalSum);

        let category = document.createElement('h5');
        category.innerHTML = `Категорія: <br><span class="sub-text">${element[1]}</span>`;
        mainDiv.appendChild(category);

        let amount = document.createElement('h5');
        amount.innerHTML = `Кількість: <br><span class="sub-text">${element[2]}</span>`;
        mainDiv.appendChild(amount);

    });
}

getStatistic1();
getStatistic2();
getStatistic3();


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
        'myOrders-title': 'My Orders',
        'copy': 'Backup DB',
        'restore': 'Restore DB',
        'users-title': 'Users',
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
        'myOrders-title': 'Мої замовлення',
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
