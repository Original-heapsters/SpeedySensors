import openSocket from 'socket.io-client';
let app_socket = null

function clientJoin(update_cb){
  app_socket = openSocket(process.env.REACT_APP_SOCKET_SVC || "http://localhost:5000");
  app_socket.on('update', updates => update_cb(null, updates));
  app_socket.emit('connected', {'type':'observer', 'dateTime': getDateTime()});
}

function clientLeave(update_cb){
  app_socket.on('update', updates => update_cb(null, updates));
  app_socket.emit('leave', {'type':'observer', 'dateTime': getDateTime()});
  app_socket = null;
}

function getDateTime(){
  var today = new Date();
  var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
  var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
  var dateTime = date+' '+time;
  return dateTime;
}

export { clientJoin, clientLeave }
