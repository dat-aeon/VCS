"use strict";

process.title = 'node-chat';

// Port where we'll run the websocket server
var webSocketsServerPort = 8086;

// websocket and http servers
var webSocketServer = require('websocket').server;
var http = require('http');
const JSON1 = require('circular-json');


// list of currently connected clients (users)
var clients = new Array();

var listroom = new Array();

var hasUser = false;

const pg  = require('pg')


var oldRoom;

var db_host="172.17.101.60";
var db_port="45326";
var db_name="vcs";
var db_user="vcs";
var db_password="vcs";

var image_path = "/mnt/jboss-eap-6.4/standalone/deployments/VCS.war/PhotoImage/FreeMessageImage/";

var image_link = "https://amf.aeoncredit.com.mm/vcsm2/free-message-image-files/";

const opts = {
		logDirectory:'/home/free-chat-server/log', // NOTE: folder must exist and be writable...
        fileNamePattern:'vcs-free-chat-server-<DATE>.log',
        dateFormat:'YYYY.MM.DD'
};
const log = require('simple-node-logger').createRollingFileLogger( opts );
/**
 * HTTP server
 */
var server = http.createServer(function(request, response) {
	// Not important for us. We're writing WebSocket server, not HTTP server
});
server.listen(webSocketsServerPort, function() {
	log.info("Server Start : On Port - " + webSocketsServerPort + ".");
});

/**
 * WebSocket server
 */

var wsServer = new webSocketServer({
	// WebSocket server is tied to a HTTP server.
	httpServer : server,
	maxReceivedFrameSize: 2493644,
    	maxReceivedMessageSize: 10 * 1024 * 1024,
    	autoAcceptConnections: false
});

function forEach(array, action) {
	try {
		for ( var i = 0; i < array.length; i++)
			action(array[i], i);
	} catch (exception) {
		if (exception != Break) {
			throw exception;
		}
	}
}
// move connection to assigned room, new roomIndex is also be assigned
function moveToRoom(room, newroom, user) {

	var tmp = [ newroom, [ user ] ];
	var ri = -1, ui = -1;
	
	//console.log(JSON1.stringify(clients[user.ri][1]));
	//console.log("----------------------------");
	
	
	clients[user.ri][1].splice(user.ui, 1);
	clients[user.ri][1].ui -= 1;
	
	for ( var i = user.ui; i < clients[user.ri][1].length; i++) {
		clients[user.ri][1][i].ui -= 1;
		//console.log(JSON1.stringify(clients[user.ri][1]));
		//console.log("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
	//	console.log(JSON1.stringify(clients[user.ri][1][i]));
	//	console.log("$$$$$$$$$$$$$$$$$$$$$$$");
	}
	for ( var i = 0; i < clients.length; i++) {
		if (clients[i][0] == newroom) {
			//console.log("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			ui = clients[i][1].push(user) - 1;
			ri = i;
			//console.log("MMMMMMMMMMM Room "+room+" New Room: "+newroom+" user: "+JSON1.stringify(user));
			//console.log("IIIIII "+ri+" UI: "+ui);
			return [ ri, ui ];
		}

	}
	
	ri = clients.push(tmp) - 1;
	ui = 0;
	//console.log("OOOOOO "+ri+" UI: "+ui);
	return [ ri, ui ];
}

//function to encode file data to base64 encoded string
function base64_encode(file) {
    // read binary data
	var fs = require('fs');
    var bitmap = fs.readFileSync(file);
    // convert binary data to base64 encoded string
    return new Buffer(bitmap).toString('base64');
}

// This callback function is called every time someone tries to connect to
// the WebSocket server
wsServer.on('request', function(request) {
	
	var userName = false;
	var userId = false;
	var conn = getConnection();

	function getConnection() {
		// accept connection
		//console.log("REquest.Origin >>> " + request.origin);
		var connection = request.accept(null, request.origin);
		var roomName = false;
		var roomIndex = 0;
		
		log.info("New Connection is accepted.");
		
		listroom=[];
		userName = true;
		connection.on('message', function(message) {
			//console.log("Connection +++++ " + connection.origin);
			if (message.type === 'utf8') { 
				var t="message="+message.utf8Data+"";
				
			   if (message.utf8Data.indexOf("userName:")==0){ // first message sent by user is
				   //console.log("$$$$$$$$ "+message.utf8Data);
				   userName = message.utf8Data.substring(9,message.utf8Data.indexOf("userId:"));
					userId = message.utf8Data.substring( message.utf8Data.indexOf("userId:")+7);
					this.name = userName;
					log.info("User Connect : '" + userName +"' is successfully connected.");
				} 
			   //cr - current room , or = old room
				else if(message.utf8Data.indexOf("cr:")==0){
					//console.log("@@@@@@@ "+message.utf8Data);
					//console.log("CCCCCCC "+JSON1.stringify(connection));
					log.info("Change Room : Room changing was requested.");
					
					roomName = message.utf8Data.substring( 3,message.utf8Data.indexOf("or:"));
					oldRoom = message.utf8Data.substring( message.utf8Data.indexOf("or:")+3,message.utf8Data.indexOf("userWithAgency:"));
					
					var userWithAgency = message.utf8Data.substring( message.utf8Data.indexOf("userWithAgency:")+15);
					
					hasUser = true;
					
					if(oldRoom == "" ){
						//console.log(roomName);
						//console.log(userName);
						var isExistRoom = false;
						for ( var i = 0; i < clients.length; i++) {
							if (clients[i][0] == roomName) {
								//console.log("Connection  >>>> " + connection);
								isExistRoom = true;
								this.ri = i;
								this.name = userName;
								this.ui = clients[i][1].push(connection) - 1;
								//console.log("!!!!!!!!!!! "+JSON1.stringify(clients[i][0]));
								//console.log("!!!!!!!!!!! "+JSON1.stringify(clients[i][1]));
								break;
							}
						}
						if(isExistRoom == false)
						{
							clients.push([ roomName, [] ]);
							this.ri = clients.length-1;
							this.name = userName;
							//console.log("QQQQQQQQQQQQQQ client length "+clients.length);
							this.ui = clients[clients.length-1][1].push(connection) -1;
							//console.log("XXXXXXXXXXX "+JSON1.stringify(clients[clients.length-1][0]));
							//console.log("XXXXXXXXXXX "+JSON1.stringify(clients[clients.length-1][1]));
						}
						//console.log("*************** "+JSON1.stringify(clients[0]));
						/*console.log("*************** "+JSON1.stringify(connection.ri));
			        	console.log("*************** "+this.name);
			        	console.log("*************** "+JSON1.parse(this.ui));
			        	console.log("++++++++++++++++++++++");*/
					}else{
						var inds = moveToRoom(oldRoom, roomName, connection);
						this.ri = inds[0];
						this.ui = inds[1];
						
						/*console.log("*************** "+JSON1.stringify(connection.ri));
			        	console.log("*************** "+this.name);
			        	console.log("*************** "+JSON1.parse(this.ui));*/
					}

					connection.sendUTF(JSON.stringify({
						type : 'room',
						data : userWithAgency
					}));
					this.room = roomName;
					
					log.info("Change Room : Room changing was successfull.");
				}
				 else if(message.utf8Data.indexOf("getRoomList:")==0){
					 
					 const pool = new pg.Pool({
							user: db_user,
							host: db_host,
							database: db_name,
							password: db_password,
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "getRoomList". Error : ' + err);
							  done(err);
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "getRoomList" was successful.');
							  
							  client.query('SELECT sum(count) count,free_customer_info_id,phone_no,finish_flag, free_cust_room_info_id, user_id '+
							    		'FROM  (SELECT CASE  WHEN (read_flag= 0 and  op_send_flag=0 )THEN count(m.free_message_info_id) ELSE 0 END count, '+
							    		'phone_no,c.free_customer_info_id,cr.last_send_time,finish_flag, cr.free_cust_room_info_id , ac.user_id '+
							    		'FROM free_cust_room_info cr '+
									  	'LEFT JOIN free_customer_info c ON cr.free_customer_info_id=c.free_customer_info_id '+
							    		'LEFT JOIN free_admin_cust_room ac ON cr.free_cust_room_info_id=ac.free_cust_room_info_id '+
							    		'LEFT JOIN free_message_room mr ON cr.free_cust_room_info_id=mr.free_cust_room_info_id '+
							    		'LEFT JOIN free_message_info m ON m.free_message_info_id=mr.free_message_info_id WHERE ac.inactive_room_flag = 0 and ac.user_id = $1'+
							    		'GROUP BY phone_no,c.free_customer_info_id,read_flag,cr.last_send_time,finish_flag,op_send_flag,cr.free_cust_room_info_id, user_id '+
							    		') tbl '+
							    		'GROUP BY free_customer_info_id,phone_no,last_send_time,finish_flag,free_cust_room_info_id, user_id '+
							    		'ORDER BY last_send_time ASC', [userId], function(err, result) {						      
							  if(err) {
						    	  log.error('Query Failed : Select query for "getRoomList". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var roomList = new Array();
				    				  for(var i =0 ;i<result.rows.length;i++) {	
				    					  var finishFlag = 0;
				    					  var adminId = 0;
				    					  var custRoomId = result.rows[i].cust_room_id;
				    					  if(result.rows[i].finish_flag == null){
				    						  client.query('INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ($1,$2,$3)', [adminId,custRoomId,0], function(err, result) {
								    			  if(err) {
								    				  log.error('Query Failed : INSERT query "INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag)..." for "getRoomList". Error : ' + err);
								    			  }
								    			  else{
								    				  log.info('Query Success : INSERT query for "getRoomList": INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ('+adminId+','+custRoomId+','+finishFlag+')');
									    				 
								    				  client.query('COMMIT', function(err) {
								    					  if(err) {
								    						  log.error('Query Failed : COMMIT of query "INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag)..." for "getRoomList". Error : ' + err);
								    					  }
												          
												      });
								    				  
								    				  finishFlag = 0;
								    				  adminId = userId;
								    			  }
								    		  });
				    					  } else {
				    						  finishFlag = result.rows[i].finish_flag;
				    						  adminId = result.rows[i].user_id;
				    					  }
				    					
					    					  var obj = {
					    							  count :(result.rows[i].count>0)?result.rows[i].count:'',
					    							  phone_no : result.rows[i].phone_no,
					    							  customer_id : result.rows[i].free_customer_info_id,
					    							  finish_flag : finishFlag
						    						};
					    					  roomList.push(obj);
				    					 
				    				  }
				    				  
				    				  
				    				  connection.sendUTF(JSON.stringify({
				  						type : 'roomList',
				  						data : roomList
				  					}));
						    	  }
						    	  else{
						    		
						    		  connection.sendUTF(JSON.stringify({
					  						type : 'roomList',
					  						data : ''
					  					}));
						    	  }
						    	
						      }
						    });
							  done();
						  }
				});
					 	
					}
					else if(message.utf8Data.indexOf("endconversation:")==0){// End conversation
					 
					 log.info("end conversation ");
					 
					 var phone_no = message.utf8Data.substring( message.utf8Data.indexOf("phonenno:")+9);
					 		
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "endconversation". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "endconversation" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "endconversation". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
						    client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0', [phone_no], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0..." for "endconversation". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var customer_id = result.rows[0].free_customer_info_id;
										client.query('SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id = $1', [customer_id], function(err, result) {
											if(err) {
												log.error('Query Failed : Select query "SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id = $1" for "endconversation". Error : ' + err);
											}else{
												if(result!=undefined){
													var cust_room_id = result.rows[0].free_cust_room_info_id;
													client.query("UPDATE free_admin_cust_room acr SET finish_flag=1,inactive_room_flag=1 WHERE acr.free_cust_room_info_id = $1", [cust_room_id], function(err, result) {
														if(err) {
															log.error('Query Failed : Update query "UPDATE free_admin_cust_room acr SET finish_flag=1 WHERE acr.free_cust_room_info_id = $1" for "endconversation". Error : ' + err);		  
																return client.query('ROLLBACK', function(err) {
																	if(err) {
																		log.error('Query Failed : ROLLBACK query of "UPDATE free_admin_cust_room acr SET finish_flag=1 WHERE acr.free_cust_room_info_id = $1" for "endconversation". Error : ' + err);
																	}
																});
														}else{
															client.query("UPDATE free_cust_room_info cri SET conver_lock_flag=0 WHERE cri.free_customer_info_id = $1", [customer_id], function(err, result) {
																if(err) {
																	log.error('Query Failed : Update query "UPDATE free_cust_room_info cri SET conver_lock_flag=0 WHERE cri.free_customer_info_id = $1..." for "endconversation". Error : ' + err);		  
																		return client.query('ROLLBACK', function(err) {
																			if(err) {
																				log.error('Query Failed : ROLLBACK query of "UPDATE free_cust_room_info cri SET conver_lock_flag=0 WHERE cri.free_customer_info_id = $1..." for "endconversation". Error : ' + err);
																			}
																		});
																}else{
																	client.query('COMMIT', function(err) {
																		if(err) {
																			log.error('Query Failed : COMMIT query of "UPDATE free_cust_room_info cri SET conver_lock_flag=0 WHERE cri.free_customer_info_id = $1..." for "endconversation". Error : ' + err);
																		}else{
																			connection.sendUTF(JSON.stringify({
																					type : 'endconversationObject'
																			}));			
																		}
																	});
																}
															});
														}
													});
												}
											}
										});   
						    	  }
						      }
							  
						    });
						  });
						  done();
						  }
					});
						

				}
					else if(message.utf8Data.indexOf("checkendconverflag:")==0){// Check end conversation flag
					 
					 log.info("check end conversation flag");
					 
					 var phone_no = message.utf8Data.substring( message.utf8Data.indexOf("phonenno:")+9, message.utf8Data.indexOf("userid:"));
					 var user_id = message.utf8Data.substring( message.utf8Data.indexOf("userid:")+7);
					 		
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "checkendconverflag". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "checkendconverflag" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "checkendconverflag". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
						    client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0', [phone_no], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0..." for "checkendconverflag". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var customer_id = result.rows[0].free_customer_info_id;
						    		   client.query('select count(RES.sender) as end_conver_flag from (select mi.sender from free_cust_room_info cri left join free_message_room mr on cri.free_cust_room_info_id = mr.free_cust_room_info_id left join free_message_info mi on mr.free_message_info_id = mi.free_message_info_id where mi.op_send_flag = 1 and cri.free_customer_info_id = $1 and cri.conver_lock_flag = 1 order by mi.send_time desc limit 1) RES where CAST(RES.sender as Integer) = $2', [customer_id,user_id], function(err, result) {
											if(err) {
												log.error('select count(RES.sender) as end_conver_flag from (select mi.sender from free_cust_room_info cri left join free_message_room mr on cri.free_cust_room_info_id = mr.free_cust_room_info_id left join free_message_info mi on mr.free_message_info_id = mi.free_message_info_id where mi.op_send_flag = 1 and cri.free_customer_info_id = $1 and cri.conver_lock_flag = 1 order by mi.send_time desc limit 1) RES where CAST(RES.sender as Integer) = $2..." for "checkMsgLock". Error : ' + err);
											}
											else{
													var endConverObject = {
														end_conver_flag : result.rows[0].end_conver_flag
													};
													connection.sendUTF(JSON.stringify({
														type : 'endConverObject',
														data : endConverObject
													}));
													
												
											}
						    			});
						    	  }
						      }
							  
						    });
						  });
						  done();
						  }
					});
						

				}
					else if(message.utf8Data.indexOf("checkmsglock:")==0){// Check msg lock
					 
					 log.info("check msg lock");
					 
					 var phone_no = message.utf8Data.substring( message.utf8Data.indexOf("phonenno:")+9, message.utf8Data.indexOf("userid:"));
					 var user_id = message.utf8Data.substring( message.utf8Data.indexOf("userid:")+7);
					 		
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "checkMsgLock". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "checkMsgLock" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "checkMsgLock". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
						    client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0', [phone_no], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1..." for "checkMsgLock". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var customer_id = result.rows[0].free_customer_info_id;
						    		   client.query('select count(RES.sender) as lock_count from (select mi.sender from free_cust_room_info cri left join free_message_room mr on cri.free_cust_room_info_id = mr.free_cust_room_info_id left join free_message_info mi on mr.free_message_info_id = mi.free_message_info_id where mi.op_send_flag = 1 and cri.free_customer_info_id = $1 and cri.conver_lock_flag = 1 order by mi.send_time desc limit 1) RES where CAST(RES.sender as Integer) != $2', [customer_id,user_id], function(err, result) {
											if(err) {
												log.error('select count(RES.sender) as lock_count from (select mi.sender from free_cust_room_info cri left join free_message_room mr on cri.free_cust_room_info_id = mr.free_cust_room_info_id left join free_message_info mi on mr.free_message_info_id = mi.free_message_info_id where mi.op_send_flag = 1 and cri.free_customer_info_id = $1 and cri.conver_lock_flag = 1 order by mi.send_time desc limit 1) RES where CAST(RES.sender as Integer) != $2..." for "checkMsgLock". Error : ' + err);
											}
											else{
													var lockObject = {
														lock_count : result.rows[0].lock_count
													};
													connection.sendUTF(JSON.stringify({
														type : 'lockObject',
														data : lockObject
													}));
													
												
											}
						    			});
						    	  }
						      }
							  
						    });
						  });
						  done();
						  }
					});
						

				}
				else if(message.utf8Data.indexOf("setfinishflagfalse:")==0){// Check msg lock
					 
					 log.info("set finish flag");
					 
					 var phone_no = message.utf8Data.substring( message.utf8Data.indexOf("phonenno:")+9, message.utf8Data.indexOf("userid:"));
					 var user_id = message.utf8Data.substring( message.utf8Data.indexOf("userid:")+7);
					 		
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						if(err) {
							log.error('Could not connect to PostgreSQL server for "setfinishflagfalse". Error : ' + err);
							done();
						}else{
							log.info('Connecting to PostgreSQL server for "setfinishflagfalse" was successful.');
							client.query('BEGIN', function(err) {
								if(err) {
									log.error('Query Failed : BEGIN query for "setfinishflagfalse". Error : ' + err);
									return done(true); //pass non-falsy value to done to kill client & remove from pool
								}
								client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0', [phone_no], function(err, result) {
									if(err) {
										log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1..." for "setfinishflagfalse". Error : ' + err);
									}else{
										if(result!=undefined){
											var customer_id = result.rows[0].free_customer_info_id;
											client.query('select free_cust_room_info_id from free_cust_room_info where free_customer_info_id = $1', [customer_id], function(err, result) {
												if(err) {
													log.error('select free_cust_room_info_id from free_cust_room_info where free_customer_info_id = $1" for "setfinishflagfalse". Error : ' + err);
												}else{
													if(result!=undefined){
														var cust_room_id = result.rows[0].free_cust_room_info_id;
														client.query("UPDATE free_admin_cust_room acr SET inactive_room_flag=0 WHERE acr.free_cust_room_info_id = $1 AND acr.user_id = $2", [cust_room_id,user_id], function(err, result) {
															if(err) {
																log.error('Query Failed : Update query "UPDATE free_admin_cust_room acr SET inactive_room_flag=0 WHERE acr.free_cust_room_info_id = $1 AND acr.user_id = $2" for "setfinishflagfalse". Error : ' + err);				    	  
																return client.query('ROLLBACK', function(err) {
																	if(err) {
																		log.error('Query Failed : ROLLBACK query of "UPDATE free_admin_cust_room acr SET inactive_room_flag=0 WHERE acr.free_cust_room_info_id = $1 AND acr.user_id = $2" for "setfinishflagfalse". Error : ' + err);
																	}
																});
															}else{
																client.query('COMMIT', function(err) {
																	if(err) {
																		log.error('Query Failed : COMMIT query of "UPDATE free_admin_cust_room acr SET inactive_room_flag=0 WHERE acr.free_cust_room_info_id = $1 AND acr.user_id = $2" for "setfinishflagfalse". Error : ' + err);
																	}
																});
															}
														});
													}
												}
											});
										}
									}
							  
								});
								
							});
							done();
						}
					});
						

				}
				 else if(message.utf8Data.indexOf("getAllRoomList:")==0){
					
					const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "getAllRoomList". Error : ' + err);
							done();
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "getAllRoomList" was successful.');
							  
						    client.query('select cri.free_cust_room_info_id,ci.free_customer_info_id, ci.phone_no FROM free_cust_room_info cri left join free_customer_info ci on cri.free_customer_info_id = ci.free_customer_info_id left join free_admin_cust_room acr on cri.free_cust_room_info_id =  acr.free_cust_room_info_id WHERE ci.del_flag = 0 and acr.user_id = $1  and cri.free_cust_room_info_id NOT IN (select free_cust_room_info_id from free_admin_cust_room where user_id != $1 and finish_flag = 0 group by free_cust_room_info_id)', [userId], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query for "getAllRoomList". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var roomList = new Array();
				    				  for(var i =0 ;i<result.rows.length;i++) {
				    					  var obj = {
				    							  customer_id : result.rows[i].free_customer_info_id,
				    							  phone_no : result.rows[i].phone_no
					    						};
				    					  roomList.push(obj);
				    				  }
				    				  connection.sendUTF(JSON.stringify({
				  						type : 'allRoomList',
				  						data : roomList
				  					}));
				    				 
						    	  }
						    	  
						      }
						    });
						    done();
						  }
					 });
						
				} else if(message.utf8Data.indexOf("msgid:")==0){ // Search message
					 log.info('Search message.');
					
					var msgid = message.utf8Data.substring( 6,message.utf8Data.indexOf("room:"));
					roomName = message.utf8Data.substring( message.utf8Data.indexOf("room:")+5);
					
					log.info('msgid => ' + msgid);
					
					log.info('roomName => ' + roomName);
					
					log.info('Search message.');
					var strSearch = "";
					if(msgid){
							
						strSearch = " AND mi.free_message_info_id < "+msgid;
					}
					log.info('strSearch => ' + strSearch);
					const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					pool.connect(function(err, client, done) {
						if(err) {
							log.error('Could not connect to PostgreSQL server for "msgid". Error : ' + err);
							done();
						}else{
							log.info('Connecting to PostgreSQL server for "msgid" was successful.');
								  
							client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1', [roomName], function(err, result) {
							if(err) {
							    log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1" for "msgid". Error : ' + err);
							}
							else{
							    	 
							    if(result.rows[0] != undefined){
							    	var customer_id = result.rows[0].free_customer_info_id;
									log.info('customer_id => ' + customer_id);
							    	client.query("SELECT mi.free_message_info_id as msgid,mi.message_type,message_content,to_char(send_time, 'DD MON YY HH:MI am') send_time,c.phone_no as login_id,op_send_flag,mi.free_message_info_id "+
							    				"FROM free_message_info mi INNER JOIN free_message_room mr ON mi.free_message_info_id=mr.free_message_info_id "+
							    				"INNER JOIN free_cust_room_info cr ON cr.free_cust_room_info_id=mr.free_cust_room_info_id "+
							    				"INNER JOIN free_customer_info c ON c.free_customer_info_id::TEXT=mi.sender "+
							    				"WHERE c.free_customer_info_id=$1 AND op_send_flag=0 "+strSearch+
							    				"UNION "+
							    				"SELECT mi.free_message_info_id as msgid,mi.message_type,message_content,to_char(send_time, 'DD MON YY HH:MI am') send_time,u.login_id,op_send_flag,mi.free_message_info_id "+
							    				"FROM free_message_info mi INNER JOIN free_message_room mr ON mi.free_message_info_id=mr.free_message_info_id "+
							    				"INNER JOIN free_cust_room_info cr ON cr.free_cust_room_info_id=mr.free_cust_room_info_id "+
							    				"INNER JOIN user_info u ON u.user_id::text=mi.sender "+
							    				"WHERE cr.free_customer_info_id=$1 AND op_send_flag=1 "+strSearch+
							    				"ORDER BY msgid DESC LIMIT 10 ", [customer_id], function(err, result) {
							    	if(err) {
										log.error('Query Failed : Messages select query "SELECT mi.free_message_info_id as msgid,mi.message_type,message_content,to_char(send_time,..." for "msgid". Error : ' + err); 
							    	}
							    	else{
							    		var oldMessage = new Array();
							    		var msgids = "";
							    		for(var i =0 ;i<result.rows.length;i++) {
							    			msgids += result.rows[i].msgid +",";
											
											if(result.rows[i].message_type == '1'){
												var obj = {
													time : result.rows[i].send_time,
													text : image_link + result.rows[i].message_content,
													author : result.rows[i].login_id,
													op_send_flag : result.rows[i].op_send_flag,
													message_id : result.rows[i].msgid,
													message_type: result.rows[i].message_type
													};
											} else {
												var obj = {
												time : result.rows[i].send_time,
								    			text : result.rows[i].message_content,
												author : result.rows[i].login_id,
								    			op_send_flag : result.rows[i].op_send_flag,
								    			message_id : result.rows[i].msgid,
												message_type: result.rows[i].message_type
												};
											}
												
							    			oldMessage.push(obj);
							    		}
							    		if(msgids) {
							    			log.info('message info 704');		
							    			msgids = msgids.substr(0, msgids.length-1);
							    			client.query("UPDATE free_message_info m SET read_flag=1 WHERE m.free_message_info_id IN ("+ msgids +") AND m.op_send_flag=0", [], function(err, result) {
											if(err) {
												log.error('Query Failed : Update query "UPDATE free_message_info m SET read_flag=1 WHERE m.free_message_info_id IN..." for "msgid". Error : ' + err);
													    	  
													return client.query('ROLLBACK', function(err) {
														if(err) {
																 	log.error('Query Failed : ROLLBACK query of "UPDATE free_message_info m SET read_flag=1 WHERE m.free_message_info_id IN..." for "msgid". Error : ' + err);
															    }
													});
										    }else{
										    	client.query('COMMIT', function(err) {
														          
												if(err) {
													log.error('Query Failed : COMMIT query of "UPDATE free_message_info m SET read_flag=1 WHERE m.free_message_info_id IN..." for "msgid". Error : ' + err);
														           
												}else{
														        	
												}
										   });
										connection.sendUTF(JSON.stringify({
											type : 'oldMessage',
											data : oldMessage
											}));
									}
								});
							    				  }
							    				  else{
							    					  connection.sendUTF(JSON.stringify({
									  						type : 'oldMessage',
									  						data : oldMessage
									  					}));
							    				  }
							    			  }
					    				  }); 
							    	  }
							      }
							    });
							    done();
							  }
					});
				 }
				 else if(message.utf8Data.indexOf("msg:")==0){// Save and broadcast the message
					 
					 log.info("msg: message is sending. " + message.utf8Data);
					 
					 var msg = message.utf8Data.substring( 4,message.utf8Data.indexOf("op_send_flag:"));
					 var op_send_flag = message.utf8Data.substring( message.utf8Data.indexOf("op_send_flag:")+13, message.utf8Data.indexOf("message_type:"));
					 var message_type = message.utf8Data.substring( message.utf8Data.indexOf("message_type:")+13);
					 
					 
					 if (message_type == '1'){
						 var date_time = new Date().toISOString().replace(/\:/, '').replace(/T/, '').replace(/\..+/, '');
						 // save image on file server
						 var fs = require('fs');
						 var img = "data:image/png;base64," + msg;
						 var data = img.replace(/^data:image\/\w+;base64,/, "");
						 var buf = new Buffer(data, 'base64');
						 msg = userId + '_' + date_time + '.png';
						 log.info("msg ready to write.");

						 fs.writeFile(image_path + msg, buf, function(err){
							 if (err) {
								 log.error('Could not write image for "msg". Error : ' + err);
								 done();
							 } else {
								 log.info("msg photo wirte file.");
							 }
						 });
					 }
					
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "msg". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "msg" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "msg". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
							log.info("select free_customer_info_id");
							log.info("roomName => " + roomName);
						    client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0', [roomName], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1 and del_flag=0..." for "msg". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var customer_id = result.rows[0].free_customer_info_id;
						    		  var cust_room_id = 0;
									  log.info("select free_cust_room_info_id");
						    		   client.query('SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id = $1', [customer_id], function(err, result) {
						    			  if(err) {
									    	  log.error('Query Failed : SELECT query "SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id = $1" for "msg". Error : ' + err);
						    			  }
						    			  else{
						    				 //if group id is not exist, will insert.
						    				  if(result.rows[0]==undefined){
						    					  client.query('INSERT INTO free_cust_room_info(free_customer_info_id) VALUES ($1) RETURNING free_cust_room_info_id', [customer_id], function(err, result) {
									    			  if(err) {
												    	  log.error('Query Failed : INSERT query "INSERT INTO free_cust_room_info(free_customer_info_id) VALUES ($1) RETURNING free_cust_room_info_id" for "msg". Error : ' + err);
												    	  return client.query('ROLLBACK', function(err) {
												    		  if(err) {
														    	  log.error('Query Failed : ROLLBACK query of "INSERT INTO free_cust_room_info(free_customer_info_id) VALUES ($1) RETURNING free_cust_room_info_id" for "msg". Error : ' + err);
												    		  }
												    	  });
									    			  }
									    			  else{
									    				  cust_room_id = result.rows[0].free_cust_room_info_id;
									    				  if(cust_room_id > 0){
										    				  var message_id ;
										    				  
										    				  client.query('INSERT INTO free_message_info(message_content,message_type,sender,op_send_flag,read_flag) VALUES ($1,$2,$3,$4,$5) RETURNING free_message_info_id', [msg,message_type,userId,op_send_flag,0], function(err, result) {
												    			  if(err) {
															    	  log.error('Query Failed : INSERT query "INSERT INTO free_message_info(message_content,message_type,sender,op_send_flag,read_flag) VALUES ($1,$2,$3,$4,$5) RETURNING free_message_info_id...." for "msg". Error : ' + err);
															    	  
															    	  return client.query('ROLLBACK', function(err) {
															    		  if(err) {
																	    	  log.error('Query Failed : ROLLBACK query of "INSERT INTO free_message_info(message_content,message_type,sender,op_send_flag,read_flag) VALUES ($1,$2,$3,$4,$5) RETURNING free_message_info_id...." for "msg". Error : ' + err);
															    		  }
															    	  });
												    			  }
												    			  else{
												    				  message_id = result.rows[0].free_message_info_id;
												    				  client.query('INSERT INTO free_message_room(free_cust_room_info_id,message_id) VALUES ($1,$2)', [cust_room_id,message_id], function(err, result) {
														    			  if(err) {
																	    	  log.error('Query Failed : INSERT query "INSERT INTO free_message_room(free_cust_room_info_id,message_id) VALUES ($1,$2)" for "msg". Error : ' + err);
																	    	  
																	    	  return client.query('ROLLBACK', function(err) {
																			          if(err) {
																				    	  log.error('Query Failed : ROLLBACK query of "INSERT INTO free_message_room(free_cust_room_info_id,message_id) VALUES ($1,$2)" for "msg". Error : ' + err);
																			          }
																		      });
														    			  }
														    		  });
												    				  
												    			  }
												    		  });
										    				 
										    				 
										    				  client.query('COMMIT', function(err) {
														         
														          if(err) {
														        	  log.error('Query Failed : COMMIT query of new message and group info for "msg". Error : ' + err);
														          }else{
														        	
														        	  var obj;
																	  if(op_send_flag == '1'){
																		  if(message_type == '1'){
																			  obj = {
																						time : "",
																						text : image_link + msg,
																						room : roomName,
																						op_send_flag: op_send_flag,
																						author: userName,
																						message_id: message_id,
																						message_type: message_type
																					};
																		  } else {
																			  obj = {
																						time : "",
																						text : msg,
																						room : roomName,
																						op_send_flag: op_send_flag,
																						author: userName,
																						message_id: message_id,
																						message_type: message_type
																					};
																		  }
																	  }else{
																		if(message_type == '1'){
																			  obj = {
																						time : "",
																						text : image_link + msg,
																						room : roomName,
																						op_send_flag: op_send_flag,
																						author: roomName,
																						message_id: message_id,
																						message_type: message_type
																					};
																			
																		  } else {
																			  obj = {
																						time : "",
																						text : msg,
																						room : roomName,
																						op_send_flag: op_send_flag,
																						author: roomName,
																						message_id: message_id,
																						message_type: message_type
																					};
																			
																		  }
																	  }
																	  
																	  

														        	  var json = JSON.stringify({
																			type : 'message',
																			data : obj
														        	  });
																			
														        	  for (i = 0; i < clients.length; i++) {
																				
														        		  if(clients[i][0]==roomName){
																					
														        			  this.ri = i;
														        			  break;
														        		  }  
														        	  }
																	for ( var i = 0; i < clients[this.ri][1].length; i++) {
																				
																		clients[this.ri][1][i].sendUTF(json);
																		var output = '';
																		for (var property in clients[this.ri][1]) {
																			output += property + ': ' + clients[this.ri][1][property]+'; ';
																		}
																	}
														          }
														         
														      });
											    		  }
									    			  }
									    		  });
						    				  }
						    				  else{
						    					  cust_room_id = result.rows[0].free_cust_room_info_id;
							    				
							    				if(cust_room_id > 0){
								    				var message_id ;
													if(op_send_flag == 1){
														console.log("UPDATE free_cust_room_info SET conver_lock_flag=1 WHERE free_customer_info_id=$1");
														client.query('UPDATE free_cust_room_info SET conver_lock_flag=1 WHERE free_customer_info_id=$1', [customer_id], function(err, result) {
															if(err) {
																log.error('Query Failed : UPDATE query "UPDATE free_cust_room_info SET conver_lock_flag=1 WHERE free_customer_info_id=$1" for "msg". Error : ' + err);
																return client.query('ROLLBACK', function(err) {
																	if(err) {
																		log.error('Query Failed : ROLLBACK query of "UPDATE free_cust_room_info SET conver_lock_flag=1 WHERE free_customer_info_id=$1" for "msg". Error : ' + err);
																	}
																});
															}
														}); 
														console.log("UPDATE free_admin_cust_room SET finish_flag=1, INACTIVE_ROOM_FLAG=1 WHERE free_cust_room_info_id = $1 AND user_id != $2");
														client.query('UPDATE free_admin_cust_room SET finish_flag=1, INACTIVE_ROOM_FLAG=1 WHERE free_cust_room_info_id = $1 AND user_id != $2', [cust_room_id, userId], function(err, result) {
															if(err) {
																log.error('Query Failed : UPDATE query "UPDATE free_admin_cust_room SET finish_flag=1, INACTIVE_ROOM_FLAG=1 WHERE free_cust_room_info_id = $1 AND user_id != $2" for "msg". Error : ' + err);
																return client.query('ROLLBACK', function(err) {
																	if(err) {
																		log.error('Query Failed : ROLLBACK query of "UPDATE free_admin_cust_room SET finish_flag=1, INACTIVE_ROOM_FLAG=1 WHERE free_cust_room_info_id = $1 AND user_id != $2" for "msg". Error : ' + err);
																	}
																});
															}
														});
														client.query('UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1 AND user_id = $2', [cust_room_id, userId], function(err, result) {
															if(err) {
																log.error('Query Failed : UPDATE query "UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1 AND user_id = $2" for "msg". Error : ' + err);
																return client.query('ROLLBACK', function(err) {
																	if(err) {
																		log.error('Query Failed : ROLLBACK query of "UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1 AND user_id = $2" for "msg". Error : ' + err);
																	}

																});
															}
														});
													}else{
														client.query('SELECT free_admin_cust_room_id FROM free_admin_cust_room WHERE free_cust_room_info_id = $1 AND finish_flag = 0', [cust_room_id], function(err, result) {
																if(err) {
																	log.error('Query Failed : Select query "SELECT free_admin_cust_room_id FROM free_admin_cust_room WHERE free_cust_room_info_id = $1 AND finish_flag = 0" for "msg". Error : ' + err);
																}else{
																	if(result!=undefined){
																		if(result.rows.length == 1){
																			var free_admin_cust_room_id = result.rows[0].free_admin_cust_room_id;
																			console.log("UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_admin_cust_room_id = $1");
																			client.query('UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_admin_cust_room_id = $1', [free_admin_cust_room_id], function(err, result) {
																				if(err) {
																					log.error('Query Failed : UPDATE query "UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_admin_cust_room_id = $1" for "msg". Error : ' + err);
																					return client.query('ROLLBACK', function(err) {
																						if(err) {
																							log.error('Query Failed : ROLLBACK query "UPDATE free_admin_cust_room SET finish_flag=0, INACTIVE_ROOM_FLAG=0 WHERE free_admin_cust_room_id = $1" for "msg". Error : ' + err);
																						}
																					});
																				}
																			});
																		}else{
																			console.log("UPDATE free_admin_cust_room SET INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1");
																			client.query('UPDATE free_admin_cust_room SET INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1', [cust_room_id], function(err, result) {
																				if(err) {
																					log.error('Query Failed : UPDATE query "UPDATE free_admin_cust_room SET INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1" for "msg". Error : ' + err);
																					return client.query('ROLLBACK', function(err) {
																						if(err) {
																							log.error('Query Failed : ROLLBACK query "UPDATE free_admin_cust_room SET INACTIVE_ROOM_FLAG=0 WHERE free_cust_room_info_id = $1" for "msg". Error : ' + err);
																						}
																					});
																				}
																			});
																		}
																	}
																}
															}); 
													}

								    				  client.query('UPDATE free_cust_room_info SET last_send_time=now() WHERE free_customer_info_id=$1', [customer_id], function(err, result) {
										    			  if(err) {
										    				  log.error('Query Failed : UPDATE query "UPDATE free_cust_room_info SET last_send_time=now() WHERE free_customer_info_id=$1" for "msg". Error : ' + err);
										    				  return client.query('ROLLBACK', function(err) {
										    					  if(err) {
										    						  log.error('Query Failed : ROLLBACK query of "UPDATE free_cust_room_info SET last_send_time=now() WHERE free_customer_info_id=$1" for "msg". Error : ' + err);
										    					  }
										    				  });
										    			  }
								    				  }); 
								    				  client.query('INSERT INTO free_message_info(message_content,message_type,sender,op_send_flag,read_flag) VALUES ($1,$2,$3,$4,$5) RETURNING free_message_info_id', [msg,message_type,userId,op_send_flag,0], function(err, result) {
										    			  if(err) {
										    				  log.error('Query Failed : INSERT query "INSERT INTO free_message_info(message_content,message_type,sender,op_send_flag,read_flag) VALUES ($1,$2,$3,$4,$5) RETURNING free_message_info_id" for "msg". Error : ' + err);
																 return client.query('ROLLBACK', function(err) {
															          if(err) {
															        	  log.error('Query Failed : ROLLBACK query of "INSERT INTO free_message_info(message_content,message_type,sender,op_send_flag,read_flag) VALUES ($1,$2,$3,$4,$5) RETURNING free_message_info_id" for "msg". Error : ' + err);
															          }
																 });
										    			  }
										    			  else{
										    				  message_id = result.rows[0].free_message_info_id;
										    				  client.query('INSERT INTO free_message_room(free_cust_room_info_id,free_message_info_id) VALUES ($1,$2)', [cust_room_id,message_id], function(err, result) {
												    			  if(err) {
												    				  log.error('Query Failed : INSERT query "INSERT INTO free_message_room(free_cust_room_info_id,free_message_info_id) VALUES ($1,$2)" for "msg". Error : ' + err);
												    				  return client.query('ROLLBACK', function(err) {
												    					  if(err) {
												    						  log.error('Query Failed : ROLLBACK query "INSERT INTO free_message_room(free_cust_room_info_id,free_message_info_id) VALUES ($1,$2)" for "msg". Error : ' + err);
												    					  }
																      });
												    			  }
												    		  });										    				
										    			  }
										    		  });
								    				  client.query('COMMIT', function(err) {
												         
												          if(err) {
												        	  log.error('Query Failed : COMMIT query for "msg". Error : ' + err);
												          }else{
												        	  var obj;
															  if(op_send_flag == '1'){
																  if(message_type == '1'){
												        		  obj = {
																			time : "",
																			text : image_link + msg,
																			room : roomName,
																			op_send_flag: op_send_flag,
																			author: userName,
																			message_id: message_id,
																			message_type: message_type
																		};
																} else {
												        		  obj = {
																			time : "",
																			text : msg,
																			room : roomName,
																			op_send_flag: op_send_flag,
																			author: userName,
																			message_id: message_id,
																			message_type: message_type
																		};
																}
															  }else{
																if(message_type == '1'){
												        		  obj = {
																			time : "",
																			text : image_link + msg,
																			room : roomName,
																			author: roomName,
																			op_send_flag: op_send_flag,
																			message_id: message_id,
																			message_type: message_type
																		};
																} else {
												        		  obj = {
																			time : "",
																			text : msg,
																			room : roomName,
																			author: roomName,
																			op_send_flag: op_send_flag,
																			message_id: message_id,
																			message_type: message_type
																		};
																}  
															  }
												        	  
												        	  
																var json = JSON.stringify({
																	type : 'message',
																	data : obj
																});
																	
																	
																for (i = 0; i < clients.length; i++) {
																		
																	if(clients[i][0]==roomName)
																	{
																		log.info("roomName => " + roomName);
																		log.info("clients.length => " + clients.length);
																		this.ri = i;
																		break;
																	}
																}
																//console.log("################# "+JSON1.stringify(clients[this.ri]));
																for ( var i = 0; i < clients[this.ri][1].length; i++) {
																	clients[this.ri][1][i].sendUTF(json);
																	//console.log("!!!!!!!!!!!!clients[this.ri] "+clients[this.ri]+"*************** "+JSON1.stringify(clients[this.ri][1]));
																	//console.log("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
															        // console.log("################# "+JSON1.stringify(connection.name));
															        // console.log("################# "+JSON1.stringify(connection.ui));
																}
												          }
												          
												      });
									    		  }
							    			  }
						    			  }
						    		  });
						    	  }
						      }
							  
						    });
						  });
						  done();
						  }
					});
						

				}
				 else if(message.utf8Data.indexOf("unReadMesgCount:")==0){// Get unread Message for mobile
					 
					// console.log("message.utf8Data.indexOf(\"unReadMesgCount:\")==0");
					 const pool = new pg.Pool({
							user: db_user,
							host: db_host,
							database: db_name,
							password: db_password,
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
						  	log.error('Could not connect to PostgreSQL server for "unReadMesgCount". Error : ' + err);
							done(err);
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "unReadMesgCount" was successful.');
							  
							client.query('SELECT count(mi.free_message_info_id) FROM free_message_room mr '
								+' LEFT JOIN free_cust_room_info ci ON ci.free_cust_room_info_id = mr.free_cust_room_info_id '
								+' LEFT JOIN free_message_info mi ON mi.free_message_info_id = mr.free_message_info_id '
								+' WHERE ci.free_customer_info_id =$1'
								+' AND mi.read_flag=0'
								+' AND mi.op_send_flag=1', [userId], function(err, result) {
								  if(err) {
								   log.error('Query Failed : SELECT query "SELECT count(mi.free_message_info_id) FROM free_message_room mr..." for "unReadMesgCount". Error : ' + err);
								  }
								  else{
									
									  if(result!=undefined){
									
										  var unReadCount = { count: result.rows[0].count};										 
										  connection.sendUTF(JSON.stringify({
											type : 'unReadMesgCountForMobile',
											data : unReadCount
										}));
										 										 
									  }
									  else{
										
										  connection.sendUTF(JSON.stringify({
												type : 'unReadMesgCountForMobile',
												data : ''
											}));
											  
									  }
								  }
						    });
							done();
						}
					});
				}
				else if(message.utf8Data.indexOf("messageList:")==0){// Get All Message List for mobile
					 
					 //console.log("message.utf8Data.indexOf(\"messageList:\")==0");
					 const pool = new pg.Pool({
							user: db_user,
							host: db_host,
							database: db_name,
							password: db_password,
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
							log.error('Could not connect to PostgreSQL server for "messageList". Error : ' + err);
							done(err);
						  }
						  else{
							log.info('Connecting to PostgreSQL server for "messageList" was successful.');
							
							client.query('SELECT mr.free_message_info_id, mi.message_content, mi.message_type, mi.send_time:: timestamp without time zone, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time'
								+' FROM free_message_room mr'
								+' LEFT JOIN free_cust_room_info ci' 
								+' ON ci.free_cust_room_info_id = mr.free_cust_room_info_id'
								+' LEFT JOIN free_message_info mi '
								+' ON mi.free_message_info_id = mr.free_message_info_id'
								+' WHERE ci.free_customer_info_id =$1'
								+' ORDER BY mi.send_time DESC LIMIT 25', [userId], function(err, result) {
								  if(err) {
								   
									log.error('Query Failed : SELECT query "SELECT mg.free_message_info_id, mi.message_content, mi.message_type, mi.send_time,..." for "messageList". Error : ' + err);
								  
								  }
								  else{
									
									  if(result!=undefined){
									
										var messageList = new Array();
							    				
							    		var msgids = "";
							    		for(var i =0; i < result.rows.length; i++) {
							    			msgids += result.rows[i].free_message_info_id +",";
							    			var message;
							    			if (result.rows[i].message_type == '1'){
							    				message = image_link + result.rows[i].message_content;
							    			}else {
							    				message = result.rows[i].message_content;
							    			}
							    			var obj = {
							    				messageId : result.rows[i].free_message_info_id,
								    			messageContent : message,
								    			messageType : result.rows[i].message_type,
												sendTime : result.rows[i].send_time,
												sender : result.rows[i].sender,
								    			opSendFlag : result.rows[i].op_send_flag,
												readFlag : result.rows[i].read_flag,
												readTime : result.rows[i].read_time
								    		};
							    			messageList.push(obj);
							    		}
										  
										  connection.sendUTF(JSON.stringify({
											type : 'messageListData',
											data : messageList
										}));
										 
									  }
									  else{
										
										  connection.sendUTF(JSON.stringify({
												type : 'messageListData',
												data : ''
											}));
											  
									  }
								  }
						    });
							done();
						}
					});
				}
				else if(message.utf8Data.indexOf("unReadMessageList:")==0){// Get Unread Message List for mobile
					 
					 const pool = new pg.Pool({
							user: db_user,
							host: db_host,
							database: db_name,
							password: db_password,
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
						  
							log.error('Could not connect to PostgreSQL server for "unReadMessageList". Error : ' + err);
							done(err);
						  }
						  else{
							  
							client.query('SELECT mr.free_message_info_id, mi.message_content, mi.message_type, mi.send_time:: timestamp without time zone, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time'
								+' FROM free_message_room mr'
								+' LEFT JOIN free_cust_room_info ci' 
								+' ON ci.free_cust_room_info_id = mr.free_cust_room_info_id'
								+' LEFT JOIN free_message_info mi '
								+' ON mi.free_message_info_id = mr.free_message_info_id'
								+' WHERE ci.free_customer_info_id =$1'
								+' AND mi.read_flag = 0'
								+' AND op_send_flag = 1'
								+' ORDER BY mi.send_time DESC', [userId], function(err, result) {
								  if(err) {
								   
									log.error('SELECT mr.free_message_info_id, mi.message_content, mi.message_type, mi.send_time:: timestamp without time zone, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time'
										+' FROM free_message_room mr'
										+' LEFT JOIN free_cust_room_info ci' 
										+' ON ci.free_cust_room_info_id = mr.free_cust_room_info_id'
										+' LEFT JOIN free_message_info mi '
										+' ON mi.free_message_info_id = mr.free_message_info_id'
										+' WHERE ci.free_customer_info_id =$1'
										+' AND mi.read_flag = 0'
										+' AND op_send_flag = 1'
										+' ORDER BY mi.send_time DESC'+' Error : ' + err);
								  
								  }
								  else{
									
									  if(result!=undefined){
									
										//console.log("unread messge list for mobile "+result.rows.length);
										var messageList = new Array();
							    				
							    		var msgids = "";
							    		for(var i =0; i < result.rows.length; i++) {
							    			msgids += result.rows[i].free_message_info_id +",";
							    			var message;
							    			if (result.rows[i].message_type == '1'){
							    				message = image_link + result.rows[i].message_content;
							    			} else {
							    				message = result.rows[i].message_content;
							    			}
							    			var obj = {
							    				messageId : result.rows[i].free_message_info_id,
								    			messageContent : message,
								    			messageType : result.rows[i].message_type,
												sendTime : result.rows[i].send_time,
												sender : result.rows[i].sender,
								    			opSendFlag : result.rows[i].op_send_flag,
												readFlag : result.rows[i].read_flag,
												readTime : result.rows[i].read_time
								    		};
							    			messageList.push(obj);
							    		}
										  
										  connection.sendUTF(JSON.stringify({
											type : 'unReadMessageListData',
											data : messageList
										}));
										 
									  }
									  else{
										
										  connection.sendUTF(JSON.stringify({
												type : 'unReadMessageListData',
												data : ''
											}));
											  
									  }
								  }
						    });
							done();
						}
					});
				}
				 else if(message.utf8Data.indexOf("ChangeFinishFlag:")==0){
					 
					 var finishFlag = message.utf8Data.substring( 17,message.utf8Data.indexOf("cr:"));
					 roomName = message.utf8Data.substring( message.utf8Data.indexOf("cr:")+3);
					 
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "ChangeFinishFlag". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "ChangeFinishFlag" was successful.');
							  
							  client.query('BEGIN', function(err) {
								  if(err) {
									  log.error('Query Failed : BEGIN query for "ChangeFinishFlag". Error : ' + err);
									  return done(true); //pass non-falsy value to done to kill client & remove from pool
								  }
								  
								  client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1', [roomName], function(err, result) {
									  if(err) {
										  log.error('Query Failed : SELECT query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1" for "ChangeFinishFlag". Error : ' + err);
									  }
						      else{
						    	  if(result!=undefined){
						    		  var customer_id = result.rows[0].free_customer_info_id;
						    		  var cust_room_id = 0;
						    		  
						    		  client.query('SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id=$1', [customer_id], function(err, result) {
						    			  if(err) {
						    				  log.error('Query Failed : SELECT query "SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id=$1" for "ChangeFinishFlag". Error : ' + err);
						    			  }
						    			  else{
						    				  if(result.rows[0]!=undefined){
						    					  cust_room_id = result.rows[0].free_cust_room_info_id;
						    					  client.query('SELECT free_admin_cust_room_id FROM free_admin_cust_room WHERE user_id = $1 and free_cust_room_info_id =$2', [userId,cust_room_id], function(err, result) {
									    			  if(err) {
									    				  log.error('Query Failed : SELECT query "SELECT free_admin_cust_room_id FROM free_admin_cust_room WHERE user_id = $1 and free_cust_room_info_id =$2" for "ChangeFinishFlag". Error : ' + err);
									    			  }
									    			  else{
									    				 //if message_group id is not exist, will insert.
									    				  if(result.rows[0]==undefined){
									    					
										    				  client.query('INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ($1,$2,$3)', [userId,cust_room_id,finishFlag], function(err, result) {
												    			  if(err) {
												    				  log.error('Query Failed : INSERT query "INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ($1,$2,$3)" for "ChangeFinishFlag". Error : ' + err);
												    			  }
												    			  else{
												    				  client.query('COMMIT', function(err) {
												    					  if(err) {
												    						  log.error('Query Failed : COMMIT of query "INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) ..." for "ChangeFinishFlag". Error : ' + err);
												    					  }
																          else{
																        	  clients[connection.ri][1].splice(connection.ui, 1);
																        	  clients[connection.ri][1].ui -= 1;
																          }
																         
																      });
												    			  }
												    		  });
											    			  	
									    					 }
									    				  else{
									    					  client.query('UPDATE free_admin_cust_room SET finish_flag=$1 WHERE user_id = $2 and free_cust_room_info_id = $3', [finishFlag,userId,cust_room_id], function(err, result) {
												    			  if(err) {
												    				  log.error('Query Failed : UPDATE query "UPDATE free_admin_cust_room SET finish_flag=$1 WHERE user_id = $2 and free_cust_room_info_id = $3" for "ChangeFinishFlag". Error : ' + err);
												    			  }
												    			  else{
												    				  client.query('COMMIT', function(err) {
												    					  if(err) {
												    						  log.error('Query Failed : COMMIT of query "UPDATE free_admin_cust_room SET finish_flag=$1 WHERE user_id = $2 and free_cust_room_info_id = $3" for "ChangeFinishFlag". Error : ' + err);
												    					  }
																          else{
																        	 /* console.log("################# "+JSON1.stringify(connection.ri));
																        	  console.log("################# "+JSON1.stringify(connection.name));
																        	  console.log("################# "+JSON1.stringify(connection.ui));*/
																        	  connection.sendUTF(JSON.stringify({
																					type : 'hideFinishButton',
																					data : 1
																				}));
																        	  
																        	
																        	  clients[connection.ri][1].splice(connection.ui, 1);
																        	  clients[connection.ri][1].ui -= 1;
																          }
																         
																      });
												    			  }
												    		  });
									    				  }
									    			  }
									    		  });
						    				  }
						    				  else{
						    					  client.query('INSERT INTO free_cust_room_info(free_customer_info_id) VALUES ($1) RETURNING free_cust_room_info_id', [customer_id], function(err, result) {
									    			  if(err) {
									    				  log.error('Query Failed : INSERT query "INSERT INTO free_cust_room_info(free_customer_info_id) VALUES ($1) RETURNING free_cust_room_info_id" for "ChangeFinishFlag". Error : ' + err);
									    			  }
									    			  else{
									    				  cust_room_id = result.rows[0].free_cust_room_info_id;
									    				  client.query('INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ($1,$2,$3)', [userId,cust_room_id,finishFlag], function(err, result) {
											    			  if(err) {
											    				  log.error('Query Failed : INSERT query "INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ($1,$2,$3)" for "ChangeFinishFlag". Error : ' + err);
											    			  }
											    			  else{
											    				  client.query('COMMIT', function(err) {
																         
															          if(err) {
															        	  log.error('Query Failed : COMMIT of query "INSERT INTO free_admin_cust_room(user_id,free_cust_room_info_id,finish_flag) VALUES ($1,$2,$3)" for "ChangeFinishFlag". Error : ' + err);
															          }
															          else{
															        	 
																			connection.sendUTF(JSON.stringify({
																			type : 'hideFinishButton',
																			data : 1
																		}));
																			clients[connection.ri][1].splice(connection.ui, 1);
																        	clients[connection.ri][1].ui -= 1;
															          }
															         
															      });
											    			  }
											    		  });
									    			  }
									    		  });
						    				  }
						    			  }
						    		  });
						    		  
						    	  }
						    	
						      }
							  
							  
						     
						    });
						   
						  });
						  done();
						  }
					});
				}else if(message.utf8Data.indexOf("ChangeFinishFlagByMobile:")==0){
					 
					 //var finishFlag = message.utf8Data.substring( 17,message.utf8Data.indexOf("cr:"));
					 roomName = message.utf8Data.substring(25);
					 
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "ChangeFinishFlagByMobile". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "ChangeFinishFlagByMobile" was successful.');
							  
							  client.query('BEGIN', function(err) {
								  if(err) {
									  log.error('Query Failed: BEGIN query for "ChangeFinishFlagByMobile". Error : ' + err);
									  return done(true); //pass non-false value to done to kill client & remove from pool
								  }
								  
								  client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1', [roomName], function(err, result) {
									  if(err) {
										  log.error('Query Failed: SELECT query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1" for "ChangeFinishFlagByMobile". Error : ' + err);
									  }
								      else{
								    	  if(result!=undefined){
								    		  
								    		  log.info('Query : Select query for "ChangeFinishFlagByMobile": SELECT customer_id FROM customer_info WHERE phone_no ='+roomName);
												 
								    		  var customer_id = result.rows[0].free_customer_info_id;
								    		  client.query('SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id=$1', [customer_id], function(err, result) {
								    			  if(err) {
								    				  log.error('Query Failed: SELECT query "SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id=$1". Error : ' + err);
								    			  }
								    			  else{
								    				  if(result.rows[0]!=undefined){
								    					  
								    					  log.info('Query : Select query for "ChangeFinishFlagByMobile": SELECT free_cust_room_info_id FROM free_cust_room_info WHERE free_customer_info_id='+customer_id);
											    		  
								    					  client.query('UPDATE free_admin_cust_room SET finish_flag=$1 WHERE free_cust_room_info_id = $2', [0,customer_id], function(err, result) {
											    			  if(err) {
											    				  log.error('Query Failed: UPDATE query "UPDATE free_admin_cust_room SET finish_flag=$1 WHERE free_cust_room_info_id = '+customer_id+'" for "ChangeFinishFlagByMobile". Error : ' + err);
											    			  }
											    			  else{
											    				  log.info('Query : Update query for "ChangeFinishFlagByMobile": UPDATE free_admin_cust_room SET finish_flag=0 WHERE free_cust_room_info_id =' +customer_id);
												    				
											    				  client.query('COMMIT', function(err) {
											    					  if(err) {
											    						  log.error('Query Failed : COMMIT of query "UPDATE free_admin_cust_room SET finish_flag=$1 WHERE free_cust_room_info_id = $2" for "ChangeFinishFlagByMobile". Error : ' + err);
											    					  }
															          
															      });
											    			  }
											    		  });
								    				  }
								    	
								    			  }
								    		  });
								    		  
								    	  }
						    	
								      }
							  
								  });
						   
							  });
						  done();
						  }
					});
						
				}
				 else if(message.utf8Data.indexOf("ChangeReadFlagWithMsgId:")==0){// Save and broadcast the message
					 
					  var msg_id = message.utf8Data.substring(24);
					
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "ChangeReadFlagWithMsgId". Error : ' + err);
							  done();
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "ChangeReadFlagWithMsgId" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "ChangeFinishFlag". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
							log.info('message info 1600');
						    client.query('UPDATE free_message_info SET read_flag=1 WHERE free_message_info_id = $1', [msg_id], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : UPDATE query "UPDATE free_message_info SET read_flag=1 WHERE free_message_info_id = $1" for "ChangeReadFlagWithMsgId". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  client.query('COMMIT', function(err) {
									         
								          if(err) {
								        	  log.error('Query Failed : COMMIT of query "UPDATE free_message_info SET read_flag=1 WHERE free_message_info_id = $1" for "ChangeReadFlagWithMsgId". Error : ' + err);
								          }
								          else{
								        	 
												connection.sendUTF(JSON.stringify({
												type : 'showMessage',
												data : msg_id
											}));
								          }
								         
								      });
						    		  
						    	  }
						    	
						      }
							  
						    });
						   
						  });
						  done();
						  }
					});
						

				}
				 else if(message.utf8Data.indexOf("msgphoto:")==0){// Save and broadcast the photo message
					 
					 log.info("msgPhoto start.");
					 var msg = message.utf8Data.substring( 4,message.utf8Data.indexOf("op_send_flag:"));
					 var op_send_flag = message.utf8Data.substring( message.utf8Data.indexOf("op_send_flag:")+13);
					 
					 // read image file
					 var base64str = base64_encode('/home/chat-server/vcs/IMG-2605.PNG');
					 // save image on file server
					 var fs = require('fs');
					 var img = "data:image/png;base64,"+base64str;
					 var data = img.replace(/^data:image\/\w+;base64,/, "");
					 var buf = new Buffer(data, 'base64');
					 fs.writeFile('/home/chat-server/vcs/image.png', buf);
					 
					 log.info("msgPhoto wirte file.");
					 
					 const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					 
					 connection.sendUTF(JSON.stringify({
							type : 'imagePhoto',
							data : 1
					}));
					
				}else if(message.utf8Data.indexOf("mobile_old_msgid:")==0){ // Search message
					
					var msgid = message.utf8Data.substring( 17,message.utf8Data.indexOf("room:"));
					roomName = message.utf8Data.substring( message.utf8Data.indexOf("room:")+5);
						
					var strSearch = "";
					if(msgid){
							
						strSearch = " AND mi.free_message_info_id < "+msgid;
					}
					const pool = new pg.Pool({
						user: db_user,
						host: db_host,
						database: db_name,
						password: db_password,
						port: db_port});
					pool.connect(function(err, client, done) {
						if(err) {
							log.error('Could not connect to PostgreSQL server for "msgid". Error : ' + err);
							done();
						}else{
							log.info('Connecting to PostgreSQL server for "mobile_old_msgid" was successful.');
								  
							client.query('SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1', [roomName], function(err, result) {
							if(err) {
							    log.error('Query Failed : Select query "SELECT free_customer_info_id FROM free_customer_info WHERE phone_no = $1" for "mobile_old_msgid". Error : ' + err);
							}
							else{
							    	 
							    if(result.rows[0] != undefined){
							    	var customer_id = result.rows[0].free_customer_info_id;
							    	client.query("SELECT mi.free_message_info_id as msgid,mi.message_type,message_content,to_char(send_time, 'DD MON YY HH:MI am') send_time,c.phone_no as login_id,op_send_flag,mi.free_message_info_id "+
							    				"FROM free_message_info mi INNER JOIN free_message_room mr ON mi.free_message_info_id=mr.free_message_info_id "+
							    				"INNER JOIN free_cust_room_info cr ON cr.free_cust_room_info_id=mr.free_cust_room_info_id "+
							    				"INNER JOIN free_customer_info c ON c.free_customer_info_id::TEXT=mi.sender "+
							    				"WHERE c.free_customer_info_id=$1 AND op_send_flag=0 "+strSearch+
							    				"UNION "+
							    				"SELECT mi.free_message_info_id as msgid,mi.message_type,message_content,to_char(send_time, 'DD MON YY HH:MI am') send_time,u.login_i,dop_send_flag,mi.free_message_info_id "+
							    				"FROM free_message_info mi INNER JOIN  free_message_room mr ON mi.free_message_info_id=mr.free_message_info_id "+
							    				"INNER JOIN free_cust_room_info cr ON cr.free_cust_room_info_id=mr.free_cust_room_info_id "+
							    				"INNER JOIN user_info u ON u.user_id::text=mi.sender "+
							    				"WHERE cr.free_customer_info_id=$1 AND op_send_flag=1 "+strSearch+
							    				"ORDER BY msgid DESC LIMIT 25 ", [customer_id], function(err, result) {
							    	if(err) {
										log.error('Query Failed : Messages select query "SELECT mi.free_message_info_id as msgid,message_content,to_char(send_time,..." for "mobile_old_msgid". Error : ' + err); 
							    	}
							    	else{
							    		var oldMessage = new Array();
							    		var msgids = "";
							    		for(var i =0 ;i<result.rows.length;i++) {
							    			msgids += result.rows[i].msgid +",";
											
											if(result.rows[i].message_type == '1'){
												var obj = {
													time : result.rows[i].send_time,
													text : image_link + result.rows[i].message_content,
													author : result.rows[i].login_id,
													op_send_flag : result.rows[i].op_send_flag,
													message_id : result.rows[i].msgid,
													message_type: result.rows[i].message_type
													};
											} else {
												var obj = {
												time : result.rows[i].send_time,
								    			text : result.rows[i].message_content,
												author : result.rows[i].login_id,
								    			op_send_flag : result.rows[i].op_send_flag,
								    			message_id : result.rows[i].msgid,
												message_type: result.rows[i].message_type
												};
											}
												
							    			oldMessage.push(obj);
							    		}
							    		if(msgids) {
							    			msgids = msgids.substr(0, msgids.length-1);	
							    			client.query("UPDATE free_message_info m SET read_flag=1 WHERE m.free_message_info_id IN ("+ msgids +") AND m.op_send_flag=1", [], function(err, result) {
											if(err) {
												log.error('Query Failed : Update query "UPDATE free_message_info m SET read_flag=1..." for "mobile_old_msgid". Error : ' + err);
													    	  
													return client.query('ROLLBACK', function(err) {
														if(err) {
																 	log.error('Query Failed : ROLLBACK query of "UPDATE free_message_info m SET read_flag=1..." for "mobile_old_msgid". Error : ' + err);
															    }
													});
										    }else{
										    	client.query('COMMIT', function(err) {
														          
												if(err) {
													log.error('Query Failed : COMMIT query of "UPDATE free_message_info m SET read_flag=1..." for "mobile_old_msgid". Error : ' + err);
														           
												}else{
														        	
												}
										   });
										connection.sendUTF(JSON.stringify({
											type : 'mobileOldMessage',
											data : oldMessage
											}));
									}
								});
							    				  }
							    				  else{
							    					  connection.sendUTF(JSON.stringify({
									  						type : 'mobileOldMessage',
									  						data : oldMessage
									  					}));
							    				  }
							    			  }
					    				  }); 
							    	  }
							      }
							    });
							    done();
							  }
					});
				 }
			   
			} else{
				
				log.error("BAD REQUEST.");
			}
		});

		// user disconnected
		connection.on('close', function(connection) {
			if (hasUser !== false ) {
				log.info("User Disconnect : '" + userName +"' is disconnected.");
				if(clients[this.ri] != undefined){
					clients[this.ri][1].splice(this.ui, 1);
					for ( var i = this.ui; i < clients[this.ri][1].length; i++) {
						clients[this.ri][1].ui -= 1;
					}
				}
				hasUser=false;
			}
		});
		return connection;
	}
});
