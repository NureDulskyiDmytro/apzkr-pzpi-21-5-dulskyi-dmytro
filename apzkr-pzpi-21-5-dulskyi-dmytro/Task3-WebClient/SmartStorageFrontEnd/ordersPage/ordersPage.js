const getOrdersURL = 'http://localhost:25016/api/getOrdering';
const getStorageIdURL = 'http://localhost:25016/api/getStorageId';
const updateStorageAddressURL = 'http://localhost:25016/api/updateStorageAddress';
const updateStorageURL = 'http://localhost:25016/api/updateStorage';
const updateOrderingURL = 'http://localhost:25016/api/updateOrdering';

let token = sessionStorage.getItem('id_token');
//console.log(token);

function getOrders() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `${getOrdersURL}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let orderings = JSON.parse(xhr.responseText);
            renderOrders(orderings);
        }
    };
}

function renderOrders(arr) {
    let parent = document.querySelector('.orders');
    parent.innerHTML = '<h1 class="lang orders__heading" key="orders-title">Замовлення</h1>';

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.classList.add('current__ordering')
        parent.appendChild(mainDiv);

        let orderingId = document.createElement('h3');
        orderingId.innerHTML = `OrderingId: ${element.orderingId}`;
        mainDiv.appendChild(orderingId);

        let productId = document.createElement('h3');
        productId.innerHTML = `ProductId: ${element.productId}`;
        mainDiv.appendChild(productId);

        let size = document.createElement('h3');
        size.innerHTML = `SIZE: ${element.size}`;
        mainDiv.appendChild(size);

        let date = document.createElement('h3');
        date.innerHTML = `DATE: ${element.date}`;
        mainDiv.appendChild(date);

        let sum = document.createElement('h3');
        sum.innerHTML = `SUM: ${element.sum}`;
        mainDiv.appendChild(sum);

        let statusDiv = document.createElement('div');
        mainDiv.appendChild(statusDiv);
        let status = document.createElement('h3');
        status.innerHTML = `STATUS: ${element.status}`;
        statusDiv.appendChild(status);
        if(element.status === false) {
            let changeStatus = document.createElement('button');
            changeStatus.classList.add('change__ordering');
            changeStatus.innerHTML = 'Прийняти замовлення';
            changeStatus.dataset.orderingId = element.orderingId;
            changeStatus.dataset.productId = element.productId;
            changeStatus.dataset.size = element.size;
            changeStatus.dataset.sum = element.sum;
            statusDiv.appendChild(changeStatus);
            changeStatus.addEventListener('click', event => {
                sessionStorage.setItem('orderingId', event.target.dataset.orderingId);
                sessionStorage.setItem('productId', event.target.dataset.productId);
                sessionStorage.setItem('size', event.target.dataset.size);
                sessionStorage.setItem('sum', event.target.dataset.sum);
                changeStatusEvent(event.target.dataset.orderingId, event.target.dataset.productId, event.target.dataset.size, event.target.dataset.sum);
            });
        }

    });
}
function changeStatusEvent(orderingId, productId, size) {
    console.log(orderingId, productId, size);

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `${getStorageIdURL}/${size}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer[0]);
            updateStorageAddress(answer[0]);
        }
    };
}

function updateStorageAddress(arr) {
    let productId = sessionStorage.getItem('productId');
    sessionStorage.setItem('addressStorageId', arr[1]);
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `${updateStorageAddressURL}/${productId}/${arr[0]}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer);
            updateStorage();
        }
    };
}
function updateStorage() {
    let orderingId = sessionStorage.getItem('orderingId');
    let productId = sessionStorage.getItem('productId');
    let size = sessionStorage.getItem('size');
    let price = sessionStorage.getItem('sum'); // Assuming 'sum' is the price
    let addressStorageId = sessionStorage.getItem('addressStorageId');
    
    // Creating the storage object to be sent in the request body
    let storage = {
        storageId: addressStorageId, // or the correct field for storageId
        size: size,
        price: price,
        productId: productId,
        status: true // or another status value if needed
    };

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `http://localhost:25016/api/updateStorage/${addressStorageId}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    
    xhr.send(JSON.stringify(storage)); // Send the storage object as JSON
    
    xhr.onreadystatechange = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer);
            updateOrdering(orderingId); // Call another function if necessary
        }
    };
}


function updateOrdering(orderingId) {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `${updateOrderingURL}/${orderingId}/true`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            getOrders();
        }
    };
}
getOrders();

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
        'users': 'Users',
        'orders-title': 'Orders'
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
        'orders-title': 'Замовлення'
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


