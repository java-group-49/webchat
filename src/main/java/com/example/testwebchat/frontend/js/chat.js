const url= 'http://localhost:8080';
let stompClient;
let selectedUser;
function connectToChat(userName) {
    console.log("connecting to chat...");
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response){
            let data = JSON.parse(response.body);
            console.log("message is(debug): " + data);
            render(data.message, data.fromLogin);
        });
    });
}

function sendMsg(from, text){
    console.log("message was sent");
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}

function registration() {
    console.log("registration was confirmed");
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/" + userName, function (response){
        connectToChat(userName);
    }).fail(function (error){
        if(error.status === 400){
            alert("login is already busy!")
        }
    })
}

function selectUser(userName) {
    console.log("selecting user:" + userName);
    selectedUser = userName;
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with: ' + userName);
}

function fetchAll() {
    console.log("fetchAll was called")
    $.get(url + "/fetchAll", function (response){
        let users = response;
        let usersTemplateHTML = "";
        for(let i=0; i< users.length; i++){
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\''+users[i]+'\')"><li class="clearfix">\n' +
                '                <div class="about">\n' +
                '                    <div class="name">'+users[i]+'</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
    });
}