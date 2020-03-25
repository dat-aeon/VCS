//$(function(win) {
$(function() {
	// window.onload = function() {
	var room = $('#roomName');
	var roomStatus = $('#roomStatus');
	var roomlist = $('#roomlist');

	var inboxChatList = $('#inboxChatList');

	var userName = false;
	var id = false;
	var listroom = [];
	var currentRoom;
	var lockAccount;
	var endConverFlag;
	var oldMessageCompleted = true;
	var phoneNo;
	var messageId;
	var autocompleteNrcNoTemp;
	var roomExistFlag = false;
	var lastRoom;
	var newAddRoomFlag = false;

	var isExistMsg = true;
	// if user is running mozilla then use it's built-in WebSocket
	window.WebSocket = window.WebSocket || window.MozWebSocket;
	$("#msgContent").hide();
	var isRoomChange = false;
	// if browser doesn't support WebSocket, just show some notification and
	// exit
	if (!window.WebSocket) {

		$("#errMsg").text(
				"Sorry, but your browser doesn\'t support WebSockets.");
		$("#errMsgDiv").show();
		$("#loadBar").hide();
		$("#msg_container").removeClass("no_mouse_events");
		$("#msg_container").addClass("disabled");

		return;
	}

	$("#finish").hide();
	window.msg_history.scrollTop = $('#msg_history').height();

	$("#search").autocomplete(
			{
				select : function(event, ui) {
					var val = ui.item.value;
					var phoneNoVal = val.substr(0, val.indexOf("(") - 1);
					var nrcNoVal = val.substr(val.indexOf(") (") + 3,
							val.length - val.indexOf(") (") - 4);
					var change_val = val.substr(val.indexOf("(") + 1, val
							.indexOf(")")
							- val.indexOf("(") - 1);
					var msgHeaderVal = change_val + " " + "(" + phoneNoVal
							+ ")";
					autocompleteNrcNoTemp = nrcNoVal;
					clickRoom(phoneNoVal, msgHeaderVal);
					$("#search").val('');

				},
				selectafter : function() {

					$("#search").val('');
				}
			});

	console.log(window.location.href);
	var connection;
//	if (window.location.port === '8082') {
//		connection = new WebSocket('ws://' + window.location.hostname + ':8086')
//	} else {
//		connection = new WebSocket('wss://' + window.location.hostname
//				+ '/free-chat-server')
//	}
	var connection = new WebSocket('wss://ass.aeoncredit.com.mm/free-chat-server');
	//	var connection = new WebSocket('ws://10.1.9.69:8086');

	connection.onopen = function() {
		connection.send("userName:" + $("#userId").val() + "userId:"
				+ $("#id").val());
		connection.send("getRoomList:");
		hideLoading();
	};

	connection.onerror = function(error) {

		$("#loadBar").hide();
		$("#msg_container").removeClass("no_mouse_events");
		$("#msg_container").addClass("disabled");
		$("#errMsg").text(
				"Unable to communicate with the server. Try to refresh.");
		$("#errMsgDiv").show();
	};

	connection.onmessage = function(message) {

		try {
			var json = JSON.parse(message.data);
		} catch (e) {
			console.log('This doesn\'t look like a valid JSON: ', message.data);
			return;
		}

		if (json.type === 'listroom') {
			listroom = json.data;

		} else if (json.type === 'room') {
			if(typeof autocompleteNrcNoTemp !== 'undefined' && autocompleteNrcNoTemp !== null && autocompleteNrcNoTemp !== '') {
				window.roomStatus.innerHTML = 'Message Content Of ' + json.data
				+ " (" + autocompleteNrcNoTemp + ")";
			}else{
				window.roomStatus.innerHTML = 'Message Content Of ' + json.data;
			}
			autocompleteNrcNoTemp = null;
			
			window.msg_history.innerHTML = "";
			hideLoading();
		} else if (json.type === 'oldMessage') {

			oldMessage = json.data;

			if (oldMessage.length == 0) {
				$('#msg_history')
						.prepend(
								'<div class="endOfMessage"><span>No more message</span></div>');
				isExistMsg = false;

			} else {
				for (var i = 0; i < oldMessage.length; i++) {
					addMessage(oldMessage[i]["author"], oldMessage[i]["text"],
							oldMessage[i]["time"],
							oldMessage[i]["op_send_flag"], 1,
							oldMessage[i]["message_type"]);
					if (i == oldMessage.length - 1) {
						msgid = oldMessage[i]["message_id"];
					}
				}
			}
			$('#msg_history').scrollTop(5);
			oldMessage = '';

			oldMessageCompleted = true;
			// Hide loader on success
			hideLoading();

		} else if (json.type === 'roomList') {
			listroom = json.data;
			window.inboxChatList.innerHTML = "";
			/*
			 * if (json.data.length > 0) {
			 * $("#msgContent").find("*").prop('disabled', false); } else { }
			 */
			for (var i = 0; i < json.data.length; i++) {
				var val = json.data[i];
				// if (val.admin_id==userId && val.finish_flag == 0) {
				addRooms(val.customer_id, val.name, val.phone_no, val.count,
						val.nrc_no);
				if(lastRoom != undefined){
					if(lastRoom == val.phone_no){
						roomExistFlag = true;
					}
				}
				
				// }
			}
			if(lastRoom != undefined && roomExistFlag == false){
				$('#msg_history').html('');
				$('#roomStatus').html('Message Content');
				$("#search").val('');
				$("#message").text('');
				$("#msgContent").hide();
				currentRoom = null;
				lastRoom = undefined;
			}
			hideLoading();
			roomExistFlag = false;

		} else if (json.type === 'allRoomList') {
			var allRoom = new Array();
			for (var i = 0; i < json.data.length; i++) {
				allRoom.push(json.data[i].phone_no + " (" + json.data[i].name
						+ ")" + " (" + json.data[i].nrc_no + ")");
			}

			$("#search").autocomplete({
				source : allRoom
			});
			hideLoading();
		} else if (json.type === 'message') {
			var roomExistFlagMsg = false;
			for (var i = 0; i < listroom.length; i++) {
				var val = listroom[i];
				if(val.phone_no === json.data.room){
					roomExistFlagMsg = true;
				}
			}

			if(roomExistFlagMsg){
				if (json.data.op_send_flag == 0 && json.data.room === phoneNo) {
					connection.send("ChangeReadFlagWithMsgId:"
							+ json.data.message_id);
				}
				if (messageId !== json.data.message_id) {
					// alert(json.data.text);
					datetime = dateFormat();
					addMessage(json.data.author, json.data.text, datetime,
							json.data.op_send_flag, 0, json.data.message_type,
							json.data.room);
					var div_msg_history = $("#msg_history");
					div_msg_history.scrollTop(div_msg_history.prop('scrollHeight'));
				}
				if (json.data.op_send_flag == 1 && json.data.room === phoneNo) {
	
					id = $("#id").val();
					connection.send("checkendconverflag:" + "phonenno:" + phoneNo
							+ "userid:" + id);
				}
				messageId = json.data.message_id;
				hideLoading();
			}
			

		}else if (json.type === 'hideFinishButton') {

			$("#finish").hide();
			$('#msg_history').html('');
			$('#roomStatus').html('Message Content');
			$("#search").val('');
			$("#message").text('');
			$("#msgContent").hide();
			currentRoom = null;
			connection.send("getRoomList:");
			hideLoading();
		} else if (json.type === 'lockObject') {

			lockAccount = json.data.lock_count;
			if (lockAccount > 0) {
				document.getElementById("msg_footer").style.pointerEvents = "none";
				$("#message").text('');
				$("#message").attr("placeholder",
						"Conversation is lock by other....");
			} else {
				document.getElementById("msg_footer").style.pointerEvents = "unset";
				$("#message").attr("placeholder", "Type message....");
				sendMsg();
			}
		} else if (json.type === 'endConverObject') {

			endConverFlag = json.data.end_conver_flag;
			if (endConverFlag > 0) {
				document.getElementById("conversationEndBtn").style.display = "unset";
			} else {
				document.getElementById("conversationEndBtn").style.display = "none";
			}
		} else if (json.type === 'endconversationObject') {
			$('#msg_history').html('');
			$('#roomStatus').html('Message Content');
			$("#search").val('');
			$("#message").text('');
			$("#msgContent").hide();
			currentRoom = null;
			connection.send("getRoomList:");
			hideLoading();
		} 
		else {

			console.log('Hmm..., I\'ve never seen JSON like this: ', json);
		}
	};

	function dateFormat() {
		var date = new Date();
		return date.getDate()
				+ "/"
				+ (date.getMonth() + 1)
				+ "/"
				+ date.getFullYear()
				+ " "
				+ (date.getHours() >= 12 ? date.getHours() - 12 : date
						.getHours()) + ":" + date.getMinutes() + ":"
				+ date.getSeconds() + " "
				+ (date.getHours() >= 12 ? 'PM' : 'AM');
	}

	window.dateFormat = dateFormat;
	function msgScroll() {
		showLoadingImg();
		if ($('#msg_history').scrollTop() == 0 && oldMessageCompleted == true
				&& isExistMsg && !isRoomChange) {

			oldMessageCompleted = false;
			$('#msg_history').scrollTop(7);

			connection.send("msgid:" + msgid + "room:" + currentRoom);

			id = $("#id").val();
			connection.send("checkmsglock:" + "phonenno:" + phoneNo + "userid:"
					+ id);
			connection.send("checkendconverflag:" + "phonenno:" + phoneNo
					+ "userid:" + id);

		} else {
			hideLoading();
			isRoomChange = false;
		}
	}

	window.msgScroll = msgScroll;

	function hideLoading() {
		$("#loadBar").hide();
		$("#msg_container").removeClass("disabled");
		$("#msg_container").removeClass("no_mouse_events");
	}

	function showLoadingImg() {
		$("#loadBar").show();
		$("#msg_container").addClass("no_mouse_events");
		$("#msg_container").removeClass("disabled");
		$("#errMsgDiv").hide();
	}

	function endConversation() {
		
		showLoadingImg();
		id = $("#id").val();
		connection.send("endconversation:" + "phonenno:" + phoneNo);
		phoneNo = null;
	}
	window.endConversation = endConversation;

	/*
	 * function send(e, obj) { if (e.keyCode === 13) { var msg = $(obj).val(); //
	 * send the message as an ordinary text if (msg != "") {
	 * connection.send("msg:" + msg + "op_send_flag:" + 1); $(obj).val(''); } } }
	 * window.send = send;
	 */

	function onloadCheckMsgLock() {
		id = $("#id").val();
		connection.send("checkmsglock:" + "phonenno:" + phoneNo + "userid:"
				+ id);

	}
	window.onloadCheckMsgLock = onloadCheckMsgLock;

	function sendMsg() {

		var msg = $("#message").text();
		if (lockAccount == 0 & msg != "" & msg.length <= 950) {
			connection.send("msg:" + msg + "op_send_flag:" + 1
					+ "message_type:0");
			$("#message").text('');
		}

	}
	window.sendMsg = sendMsg;
	/**
	 * This method is optional. If the server wasn't able to respond to the in 5
	 * seconds then show some error message to notify the user that something is
	 * wrong.
	 */
	setInterval(function() {
		if (connection.readyState !== 1) {

			$("#errMsg").text(
					"Unable to communicate with the server. Try to refresh.");
			$("#errMsgDiv").show();
			$("#loadBar").hide();
			$("#msg_container").removeClass("no_mouse_events");
			$("#msg_container").addClass("disabled");
		} else {
			connection.send("getRoomList:");
		}
	}, 5000);

	/**
	 * Add message to the chat window
	 */
	function addMessage(author, message, datetime, op_send_flag, isOldMsg,
			message_type, room) {

		var strTemp = "incoming_msg";
		if (op_send_flag == 1) {
			if (author == $("#userId").val()) {
				strTemp = "outgoing_msg_own";
			} else {
				strTemp = "outgoing_msg_operator";
			}
		}

		if (message_type == 1) {
			var image_link;
			image_link = message;

			strTemp = '<div class="msg_block"><div class="' + strTemp + '">'
					+ '<div class="msg_author">' + author + '</div>'
					+ '<img style="max-width: 100%; max-height:100%;" src="'
					+ image_link + '" img>' + '<span class="msg_tiemstamp">'
					+ datetime + '</span>' + '</div> </div>';

		} else {

			strTemp = '<div class="msg_block"><div class="' + strTemp + '">'
					+ '<div class="msg_author">' + author + '</div>'
					+ '<span class="msg_text">' + message + '</span>'
					+ '<span class="msg_tiemstamp">' + datetime + '</span>'
					+ '</div> </div>';
		}

		if (isOldMsg == 1) {
			$('#msg_history').prepend(strTemp);
		} else {
			if (op_send_flag == 1 || room == currentRoom) {
				window.msg_history.innerHTML = window.msg_history.innerHTML
						+ strTemp;
			}

		}
	}
	/**
	 * Add room to the roomlist
	 */
	function addRoom(roomname, i) {
		window.roomlist.append('<li onClick="change_room(' + i
				+ ');" class="line">[ ' + roomname + ' ]</li>   ');
	}

	function change_room(room, userWithAgency) {
		phoneNo = room;
		window.msg_history.innerHTML = "";
		id = $("#id").val();
		connection.send("checkmsglock:" + "phonenno:" + room + "userid:" + id);
		connection.send("checkendconverflag:" + "phonenno:" + room + "userid:"
				+ id);
		
		if (userName === false) {

			userName = $("#userId").val();

			// connection.send("cr:" + room +
			// "or:"+"userWithAgency:"+$("[id='"+room+"']").find('.user_name').html());

			connection.send("cr:" + room + "or:" + "userWithAgency:"
					+ userWithAgency);
			connection.send("msgid:" + "room:" + room);
			currentRoom = room;
			// $('#msg_history').scrollTop(18);
		} else {

			connection.send("cr:" + room + "or:" + currentRoom
					+ "userWithAgency:" + userWithAgency);
			connection.send("msgid:" + "room:" + room);
			currentRoom = room;

			// $('#msg_history').scrollTop(18);
		}

	}

	window.change_room = change_room;

	function clickRoom(val1, val2) {
		lastRoom = val1;
		showLoadingImg();
		isRoomChange = true;
		$("#msgContent").show();
		$("#message").text('');
		if (val1 != currentRoom || currentRoom == undefined) {
			id = $("#id").val();
			connection.send("setfinishflagfalse:" + "phonenno:" + val1 + "userid:" + id);
			newAddRoomFlag = true;
			msgid = "";
			isExistMsg = true;
			var x = document.getElementById("msgContent");

			if (x.style.display === "none") {
				x.style.display = "block";

			}
			$("#finish").show();
			change_room(val1, val2);
			$("#search").val('');
		} else {
			hideLoading();
		}
	}
	window.clickRoom = clickRoom;

	function addRooms(customer_id, name, phone_no, count, nrc_no) {
		window.inboxChatList.innerHTML = window.inboxChatList.innerHTML
				+ '<div class="chat_room" id="' + phone_no
				+ '" onclick="clickRoom(\'' + phone_no + '\',\'' 
				+ phone_no  +  '\')">'
				+ '<div class="user_info">' + '<div class="user_name">' 
				+ phone_no + ' </div>' + '<div class="phone_no">'
				+  '</div>' + '</div>' + '<div class="count">' + count
				+ '</div>' + '</div>';

	}

	function handlePaste(e) {
		var clipboardData, pastedData;
		var t = e.target;
		// Stop data actually being pasted into div
		// e.stopPropagation();
		// e.preventDefault();

		// Get pasted data via clipboard API
		clipboardData = e.clipboardData || window.clipboardData;
		pastedData = clipboardData.getData('Text');
		if (pastedData.length >= 950) {
			clipboardData.clearData('Text');
			alert("Your text is too long.");
			e.stopPropagation();
			e.preventDefault();
		} else {
			return true;
		}
	}

	document.getElementById('message').addEventListener('paste', handlePaste);

	document.getElementById("message").onkeydown = function(e) {
		var e = window.event || e
		if (e.code == "Backspace") {
			return true;
		} else {
			var text = $("#message").text();
			if (text.length >= 950) {
				return false;
			}
			return true;
		}
	}

	function goBack() {
		connection.close();
		window.history.back();
	}
	window.goBack = goBack;

	function changeFinishFlag() {
		phoneNo = null;
		showLoadingImg();
		connection.send("ChangeFinishFlag:1cr:" + currentRoom);
	}
	window.changeFinishFlag = changeFinishFlag;
	// };
	// }(window));
});
