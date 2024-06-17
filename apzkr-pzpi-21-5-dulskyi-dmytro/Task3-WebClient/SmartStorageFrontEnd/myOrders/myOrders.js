const getProductByCustomerIdURL = 'http://localhost:25016/api/getProductByCustomerId';
let user = JSON.parse(sessionStorage.getItem('user'));
let token = sessionStorage.getItem('id_token');
let productContainer = document.querySelector('.orders');

function getProductByCustomerId() {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `${getProductByCustomerIdURL}/${user.id}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer);
            renderUserProducts(answer);
            //document.location.href = './welcomePage.html';
        }
    };
}

function renderUserProducts(arr) {
    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.classList.add('current__ordering');
        productContainer.appendChild(mainDiv);

            let productId = document.createElement('h5');
            productId.innerHTML = `ProductId: <br><span class="sub-text">${element[0]}</span>`;
            mainDiv.appendChild(productId);

            let humidityRangeNow = document.createElement('h5');
            humidityRangeNow.innerHTML = `Humidity Range Now: <br><span class="sub-text">${element[10]}</span>`;
            mainDiv.appendChild(humidityRangeNow);

            let humidityRange = document.createElement('h5');
            humidityRange.innerHTML = `Humidity Range: <br><span class="sub-text">${element[8]}</span>`;
            mainDiv.appendChild(humidityRange);

            let quantity = document.createElement('h5');
            quantity.innerHTML = `Quantity: <br><span class="sub-text">${element[4]}</span>`;
            mainDiv.appendChild(quantity);

            let size = document.createElement('h5');
            size.innerHTML = `SIZE: <br><span class="sub-text">${element[2]}</span>`;
            mainDiv.appendChild(size);

            let storageAddress = document.createElement('h5');
            storageAddress.innerHTML = `Storage Address: <br><span class="sub-text">${element[6]}</span>`;
            mainDiv.appendChild(storageAddress);

            let storageLife = document.createElement('h5');
            storageLife.innerHTML = `Storage Life: <br><span class="sub-text">${element[5]}</span>`;
            mainDiv.appendChild(storageLife);

            let summa = document.createElement('h5');
            summa.innerHTML = `Summa: <br><span class="sub-text">${element[13]}$</span>`;
            mainDiv.appendChild(summa);

            let temperatureRangeNow = document.createElement('h5');
            temperatureRangeNow.innerHTML = `Temperature Range Now: <br><span class="sub-text">${element[9]}</span>`;
            mainDiv.appendChild(temperatureRangeNow);

            let temperatureRange = document.createElement('h5');
            temperatureRange.innerHTML = `Temperature Range: <br><span class="sub-text">${element[7]}</span>`;
            mainDiv.appendChild(temperatureRange);

            let typeOfProduct = document.createElement('h5');
            typeOfProduct.innerHTML = `Type Of Product: <br><span class="sub-text">${element[3]}</span>`;
            mainDiv.appendChild(typeOfProduct);

            let weight = document.createElement('h5');
            weight.innerHTML = `Weight: <br><span class="sub-text">${element[1]}</span>`;
            mainDiv.appendChild(weight);
    });
}

getProductByCustomerId();

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
