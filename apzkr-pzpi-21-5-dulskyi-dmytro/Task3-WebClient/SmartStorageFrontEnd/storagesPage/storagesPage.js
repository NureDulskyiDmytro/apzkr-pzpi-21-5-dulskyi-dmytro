const getStoragesURL = 'http://localhost:25016/api/getStorage';
const deleteStorageURL = 'http://localhost:25016/api/deleteStorage';
const addStorageURL = 'http://localhost:25016/api/addStorage';

let token = sessionStorage.getItem('id_token');
let deleteInput;
let deleteButton;
let modalAdd;
let modal = document.querySelector('.modal');

function getStorages() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', getStoragesURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState === 4) {
            let storages = JSON.parse(xhr.responseText);
            renderStorages(storages);
        }
    };
}

function renderStorages(arr) {
    console.log(arr);
    let parent = document.querySelector('.orders');
    parent.innerHTML = `<h1 class="orders__heading lang" key="storages-title">Склад</h1>
                        <div class="delete">
                            <label for="delete-input"></label>
                            <input type="text" id="delete-input">
                            <button class="lang delete-button" key="delete">Видалити</button>
                        </div>`;

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.classList.add('current__ordering')
        parent.appendChild(mainDiv);

        let addressStorageId = document.createElement('h3');
        addressStorageId.innerHTML = `Address Storage Id: <br><span class="sub-text">${element.addressStorageId}</span>`;
        mainDiv.appendChild(addressStorageId);

        let storageId = document.createElement('h3');
        storageId.innerHTML = `StorageId: <br><span class="sub-text">${element.storageId}</span>`;
        mainDiv.appendChild(storageId);

        let size = document.createElement('h3');
        size.innerHTML = `SIZE: <br>${element.size}</span>`;
        mainDiv.appendChild(size);

        let price = document.createElement('h3');
        price.innerHTML = `Price: <br><span class="sub-text">${element.price}</span>`;
        mainDiv.appendChild(price);

        let status = document.createElement('h3');
        status.innerHTML = `Status: <br><span class="sub-text">${element.status}</span>`;
        mainDiv.appendChild(status);

        let productId = document.createElement('h3');
        productId.innerHTML = `ProductId: <br><span class="sub-text">${element.productId}</span>`;
        mainDiv.appendChild(productId);

    });

    modalAdd = document.querySelector('.orders__heading');
    deleteInput = document.querySelector('#delete-input');
    deleteButton = document.querySelector('.delete-button');

    deleteButton.addEventListener('click', () => {
        deleteStorage();
    });
    modalAdd.addEventListener('click', () => {
        showModal();
    });
}

function showModal() {
    modal.classList.remove('invisible');
    modal.style.display = 'flex';


    let cancel = document.querySelector('#cancel');
    let order = document.querySelector('#order');

    cancel.addEventListener('click', () => {
        modal.classList.add('invisible');
        modal.style.display = 'none';
    });

    order.addEventListener('click', () => {
        addStorage();
    });
}

function addStorage() {

    // Debugging logs
    
    let sizeInput = document.querySelector('#size');
    let storageIdInput = document.querySelector('#storageId');
    console.log('sizeInput:', sizeInput.value);
    console.log('storageIdInput:', storageIdInput.value);
    let data = {
        price: null,// Placeholder value, modify as needed
    productId: null, // Placeholder value, modify as needed
    size: sizeInput.value,
    status: false,
    storageId: storageIdInput.value
    };
    
    console.log(data);

    let dataToJSON = JSON.stringify(data);

    let xhr = new XMLHttpRequest();
    xhr.open('POST', addStorageURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(dataToJSON);
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            modal.classList.add('invisible');
            modal.style.display = 'none';
            getStorages();
        }
    };
}

function deleteStorage() {
    let xhr = new XMLHttpRequest();
    xhr.open('DELETE', `${deleteStorageURL}/${deleteInput.value}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            getStorages();
        }
    };
}

getStorages();

document.addEventListener('DOMContentLoaded', getLocalLang);

let arrLang = {
    'en': {
        'price': 'Price',
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
        'storages-title': 'Storages',
        'delete': 'Delete'
    },
    'ua': {
        'price': 'Ціна',
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
        'storages-title': 'Склад',
        'delete': 'Видалити'
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
