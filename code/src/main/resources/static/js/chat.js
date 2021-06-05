const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var logoutForm = document.querySelector('#logoutForm');
var username = null;
var nombre = null;


const colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect() {
    username = document.getElementById("user").innerText.trim();
    nombre = document.getElementById("nombre").innerText.trim();

    if(username) {

        var socket = new SockJS('/chatsocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({sender: username, name: nombre, type: 'JOIN'})
    )

    //connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

//ignorar
function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        console.log("entróoó");
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}
//ignorar
function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        console.log(users);
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i].username + '\')"><li class="clearfix">\n' +
                '                <img th:src="@{/img/escudo.jpg}" width="55px" height="55px" alt="avatar" />\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i].username + '" class="name">' + users[i].username + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
            //connectToChat(users[i].username);
        }
        $('#usersList').html(usersTemplateHTML);
    });
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            name: nombre,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(cur_message) {
    console.log("onmessagerece");
    console.log(cur_message);
    var message = JSON.parse(cur_message.body);

    //adiciona el mensaje enviado
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.name + ' está conectado!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.name + ' se ha desconectado.';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.name[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        //appendChild: agrega algo a mi div actual
        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.name);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function leave() {
console.log("sefue");
    if(stompClient) {
        var chatMessage = {
            sender: username,
            name: nombre,
            type: 'LEAVE'
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();

    return true;
}

messageForm.addEventListener('submit', send, true);
//logoutForm.addEventListener('submit', leave, true);

document.addEventListener('DOMContentLoaded', function() {
    connect();
    onConnected();
},  false);

