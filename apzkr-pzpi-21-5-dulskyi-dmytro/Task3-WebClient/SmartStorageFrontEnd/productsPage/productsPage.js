const getProductsURL = 'http://localhost:25016/api/getProduct';
const updateProductURL = 'http://localhost:25016/api/updateIndicator';
const deleteProductURL = 'http://localhost:25016/api/deleteProduct';
const updateAllIndicatorsURL = 'http://localhost:25016/api/updateIndicators';
let token = sessionStorage.getItem('id_token');
let user = JSON.parse(sessionStorage.getItem('user'));
let modal = document.querySelector('.modal');

let deleteInput;
let deleteButton;

function getProducts() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', getProductsURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let products = JSON.parse(xhr.responseText);
            renderOrders(products);
        }
    };
}

function renderOrders(arr) {
    console.log(arr);
    let parent = document.querySelector('.orders');
    parent.innerHTML = `<h1 class="orders__heading lang" key="products-title">Товар</h1>
    <div class="delete">
        <label for="delete-input"></label>
        <input type="text" id="delete-input">
        <button class="lang delete-button" key="delete">Видалити</button>
    </div>`;

    arr.forEach(element => {
        let mainDiv = document.createElement('div');
        mainDiv.dataset.productId = element.productId;
        mainDiv.dataset.temperatureRange = element.temperatureRange;
        mainDiv.dataset.humidityRange = element.humidityRange;
        mainDiv.dataset.temperatureNow = element.temperatureNow;
        mainDiv.dataset.humidityNow = element.humidityNow;
        mainDiv.classList.add('current__ordering');
        parent.appendChild(mainDiv);

        mainDiv.addEventListener('click', () => {
            showEditModal(
                mainDiv.dataset.productId,
                mainDiv.dataset.temperatureRange,
                mainDiv.dataset.humidityRange,
                mainDiv.dataset.temperatureNow,
                mainDiv.dataset.humidityNow
            );
        });

        let productId = document.createElement('h5');
        productId.id = element.productId;
        productId.innerHTML = `ProductId: <br><span class="sub-text">${element.productId}</span>`;
        mainDiv.appendChild(productId);

        let weight = document.createElement('h5');
        weight.innerHTML = `Weight: <br><span class="sub-text">${element.weight}</span>`;
        mainDiv.appendChild(weight);

        let size = document.createElement('h5');
        size.innerHTML = `SIZE: <br><span class="sub-text">${element.size}</span>`;
        mainDiv.appendChild(size);

        let typeOfProduct = document.createElement('h5');
        typeOfProduct.innerHTML = `Type Of Product: <br><span class="sub-text">${element.typeOfProduct}</span>`;
        mainDiv.appendChild(typeOfProduct);

        let quantity = document.createElement('h5');
        quantity.innerHTML = `Quantity: <br><span class="sub-text">${element.quantity}</span>`;
        mainDiv.appendChild(quantity);

        let storageLife = document.createElement('h5');
        storageLife.innerHTML = `Storage Life: <br><span class="sub-text">${element.storageLife}</span>`;
        mainDiv.appendChild(storageLife);

        let storageAddress = document.createElement('h5');
        storageAddress.innerHTML = `Storage Address: <br><span class="sub-text">${element.storageAddress}</span>`;
        mainDiv.appendChild(storageAddress);

        let temperatureRange = document.createElement('h5');
        temperatureRange.id = element.temperatureRange;
        temperatureRange.innerHTML = `Temperature Range: <br><span class="sub-text">${element.temperatureRange}</span>`;
        mainDiv.appendChild(temperatureRange);

        let humidityRange = document.createElement('h5');
        humidityRange.innerHTML = `Humidity Range: <br><span class="sub-text">${element.humidityRange}</span>`;
        mainDiv.appendChild(humidityRange);

        let temperatureNow = document.createElement('h5');
        temperatureNow.innerHTML = `<span style="color: white;"> Temperature Now: <br><span class="sub-text" style="color: white;">${element.temperatureNow}</span></span>`;
        setIndicatorColor(temperatureNow, element.temperatureRange, element.temperatureNow, element.storageAddress);
        mainDiv.appendChild(temperatureNow);

        let humidityNow = document.createElement('h5');
        humidityNow.innerHTML = `<span style="color: white;">Humidity Now: <br><span class="sub-text" style="color: white;">${element.humidityNow}</span></span>`;
        setIndicatorColor(humidityNow, element.humidityRange, element.humidityNow, element.storageAddress);
        mainDiv.appendChild(humidityNow);

        let customerId = document.createElement('h5');
        customerId.innerHTML = `CustomerId: <br><span class="sub-text">${element.customerId}</span>`;
        mainDiv.appendChild(customerId);

        let workerId = document.createElement('h5');
        workerId.innerHTML = `WorkerId: <br><span class="sub-text">${element.workerId}</span>`;
        mainDiv.appendChild(workerId);

        let summa = document.createElement('h5');
        summa.innerHTML = `Summa: <br><span class="sub-text">${element.summa}</span>`;
        mainDiv.appendChild(summa);
    });

    deleteInput = document.querySelector('#delete-input');
    deleteButton = document.querySelector('.delete-button');

    deleteButton.addEventListener('click', () => {
        deleteProduct();
    });
}

function deleteProduct() {
    let xhr = new XMLHttpRequest();
    xhr.open('DELETE', `${deleteProductURL}/${deleteInput.value}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            getProducts();
        }
    };
}

function setIndicatorColor(element, range, currentValue, isplaced) {
    if(isplaced != null)
        {
            let [min, max] = range.replace(/\s+/g, '').split(/(?<!^)-/).map(Number);

            currentValue = Number(currentValue);

            let color = 'darkgreen';
            if (currentValue <= min - 10 || currentValue >= max + 10) {
                color = 'black';
            } else if (currentValue <= min - 5 || currentValue >= max + 5) {
                color = 'darkred';
            } else if (currentValue < min || currentValue > max) {
                color = 'darkorange';
            }

            element.style.backgroundColor = color;
        }
    
}

function showEditModal(productId, temperatureRange, humidityRange, temperatureNow, humidityNow) {
    modal.classList.remove('invisible');
    modal.style.display = 'flex';

    let productIdText = document.querySelector('.modal_id_span');
    productIdText.innerHTML = productId;

    let tempText = document.querySelector('.modal_temp_span');
    tempText.innerHTML = temperatureRange;

    let humText = document.querySelector('.modal_hum_span');
    humText.innerHTML = humidityRange;

    let tempInput = document.querySelector('#temperatureRange');
    tempInput.value = temperatureNow;

    let humInput = document.querySelector('#humidityRange');
    humInput.value = humidityNow;

    let cancel = document.querySelector('#cancel');
    let order = document.querySelector('#order');

    // Remove previous event listeners to avoid multiple bindings
    cancel.onclick = closeModal;
    order.onclick = () => {
        handleOrderClick(productId, tempInput.value, humInput.value, temperatureRange, humidityRange);
    };

    function closeModal() {
        modal.classList.add('invisible');
        modal.style.display = 'none';
    }

    function handleOrderClick(productId, tempInputValue, humInputValue, temperatureRange, humidityRange) {
        updateProduct(productId, tempInputValue, humInputValue, user.id, () => {
            setIndicatorColorById(productId, tempInputValue, temperatureRange, 'Temperature Now');
            setIndicatorColorById(productId, humInputValue, humidityRange, 'Humidity Now');
            closeModal();
        });
    }
}


document.getElementById('getIOTData').addEventListener('click', () => {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', updateAllIndicatorsURL, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
            getProducts();
        }
    };
});

function updateProduct(productId, tempInput, hum, userId) {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `${updateProductURL}/${productId}/${tempInput}/${hum}/${userId}`, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send();
    xhr.onreadystatechange = () => {
        console.log('success');
        if(xhr.readyState == 4) {
            let answer = JSON.parse(xhr.responseText);
            console.log(answer);
            modal.classList.add('invisible');
            modal.style.display = 'none';
            getProducts();
        }
    };
}

getProducts();

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
        'users': 'Users',
        'products-title': 'Products',
        'delete': 'Delete'
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
        'users': 'Користувачі',
        'products-title': 'Товар',
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
