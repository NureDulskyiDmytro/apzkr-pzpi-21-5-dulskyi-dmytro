const addProductURL = 'http://localhost:25016/api/addProduct';
const getProductIdURL = 'http://localhost:25016/api/getProductId';
const addOrderingURL = 'http://localhost:25016/api/addOrdering';
const backupDB = 'http://localhost:25016/api/backupDB';
const restoreDB = 'http://localhost:25016/api/restoreDB';

let token = sessionStorage.getItem('id_token');
let user = JSON.parse(sessionStorage.getItem('user'));
console.log(user);
let productId;
let sum;

let copyButton = document.querySelector('#copy');
let restoreButton = document.querySelector('#restore');

copyButton.addEventListener('click', copyDataBase);

function copyDataBase() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', backupDB, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            alert('База даних успішно скопійована!');
        }
    };
}

restoreButton.addEventListener('click', restoreDataBase);

function restoreDataBase() {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', restoreDB, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            alert('База даних успішно відновлена!');
            document.location.href = '../main.html';
        }
    };
}

//Modal
let orderModalButton = document.querySelector('#order-modal');
let modalWindow = document.querySelector('.modal');
let cancelButton = document.getElementById('cancel');
let orderButton = document.getElementById('order');
orderModalButton.addEventListener('click', () => {
    modalWindow.classList.remove('invisible');
    modalWindow.style.display = 'flex';
});
cancelButton.addEventListener('click', () => {
    modalWindow.classList.add('invisible');
    modalWindow.style.display = 'none';
});

//Inputs
let weight = document.getElementById('weight');
let size = document.getElementById('size');
let typeOfProduct = document.getElementById('typeOfProduct');
let quantity = document.getElementById('quantity');
let storageLife = document.getElementById('storageLife');
let temperatureRange = document.getElementById('temperatureRange');
let humidityRange = document.getElementById('humidityRange');

let total = document.querySelector('.total__sum_money');

orderButton.addEventListener('click', sendOrder);
quantity.addEventListener('change', () => {
    let today = new Date();
    let deadLine = new Date(storageLife.value);
    let difference = getDays(today, deadLine);
    sum = countTotalSum(difference, size.value, quantity.value, weight.value);
    total.innerHTML = sum;
    console.log(difference);
    console.log(size.value);
    console.log(quantity.value);
    console.log(sum);
});
weight.addEventListener('change', () => {
    let today = new Date();
    let deadLine = new Date(storageLife.value);
    let difference = getDays(today, deadLine);
    sum = countTotalSum(difference, size.value, quantity.value, weight.value);
    total.innerHTML = sum;
    console.log(difference);
    console.log(size.value);
    console.log(quantity.value);
    console.log(sum);
});
size.addEventListener('change', () => {
    if(size.value === "m" || size.value === "М" || size.value === "м") {
        size.value = "M";
    } else if(size.value === "l" || size.value === "Л" || size.value === "л") {
        size.value = "L";
    } else if(size.value === "xl" || size.value === "ХЛ" || size.value === "хл") {
        size.value = "XL";
    }
    let today = new Date();
    let deadLine = new Date(storageLife.value);
    let difference = getDays(today, deadLine);
    sum = countTotalSum(difference, size.value, quantity.value, weight.value);
    total.innerHTML = sum;
    console.log(difference);
    console.log(size.value);
    console.log(quantity.value);
    console.log(sum);
});
storageLife.addEventListener('change', () => {
        let today = new Date();
        let deadLine = new Date(storageLife.value);
        let difference = getDays(today, deadLine);
        sum = countTotalSum(difference, size.value, quantity.value, weight.value);
        total.innerHTML = sum;
        console.log(difference);
        console.log(size.value);
        console.log(quantity.value);
        console.log(sum);
});

function sendOrder() {
    let data = {};
    if(new Date(storageLife.value) < new Date()) {
        alert('Дата не може бути меншою за сьогоднішню! Поставлена сьогоднішня!');
        sum = countTotalSum(difference, size.value, quantity.value, weight.value);
        total.innerHTML = sum;
    }
    
    data.weight = `${weight.value} кг`;
    data.size = size.value;
    data.typeOfProduct = typeOfProduct.value;
    data.quantity = quantity.value;
    data.temperatureRange = `${temperatureRange.value} C`;
    data.humidityRange = `${humidityRange.value}%`;
    data.summa = total.innerHTML;
    data.customerId = user.id;

    let dataToJSON = JSON.stringify(data);
    console.log(dataToJSON);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', addProductURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    console.log('Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        if (xhr.readyState == 4) {
            if (xhr.status == 201) {
                let answer = JSON.parse(xhr.responseText);
                productId = answer.productId;
                console.log('productId: ' + answer.productId);
                addOrdering();
                console.log('success IN ADDING PRODUCT');
            } else {
                console.log('Error in adding product: ' + xhr.responseText);
            }
        }
    };
}


function addOrdering() {
    let today = new Date();
    let data = {};
    data.date = `${transformDate(today)}`;
    data.productId = productId;
    data.status = false;
    data.sum = `${sum}`;
    data.size = size.value;

    let dataToJSON = JSON.stringify(data);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', addOrderingURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer);
            document.location.href = './welcomePage.html';
        }
    };
}

function getDays(today, deadLine) {
    let t1 = deadLine.getTime();
    let t2 = today.getTime();
    return parseInt((t1-t2)/(24*3600*1000))+1;
}

function countTotalSum(difference, size, quantity, weight) {
    switch(size) {
        case "M":
            return Math.round(difference * (20 + weight * 0.1) * quantity)
        case "L":
            return Math.round(difference * (25 + weight * 0.08) * quantity)
        case "XL":
            return Math.round(difference * (30 + weight * 0.05) * quantity)
        default:
            return;
    }
}

function transformDate(today) {
    let day = today.getDate();
    let month = today.getMonth()+1;
    let year = today.getFullYear();
    if(day < 10) day = `0${day}`
    if(month < 10) month = `0${month}`
    return `${day}.${month}.${year}`;
}

document.addEventListener('DOMContentLoaded', getLocalLang);

let arrLang = {
    'en': {
        'cancel': 'Cancel',
        'order': 'Order',
        'total__sum': 'Total sum',
        'storageLife': 'Storage life',
        'temperatureRange': 'Temperature range',
        'humidityRange': 'Humidity range',
        'size': 'Size',
        'typeOfProduct': 'Type of product',
        'quantity': 'Quantity',
        'weight': 'Weight',
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
        'users': 'Users',
        'copy': 'Backup DB',
        'restore': 'Restore DB',
        'products-title': 'Products'
    },
    'ua': {
        'cancel': 'Скасувати',
        'order': 'Замовити',
        'total__sum': 'Загальна сума',
        'storageLife': 'Термін зберігання',
        'temperatureRange': 'Діапазон температур',
        'humidityRange': 'Діапазон вологості',
        'size': 'Розмір',
        'typeOfProduct': 'Тип товару',
        'quantity': 'Кількість',
        'weight': 'Вага',
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
        'users': 'Користувачі',
        'copy': 'Копіювання БД',
        'restore': 'Відновлення БД',
        'products-title': 'Товар'
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
