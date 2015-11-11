var io = require('socket.io')();
io.on('connection', function(socket){
  socket.on('event', function(data){
    socket.emit('event created', '');
  });
});
console.log("Server is running on port 3000");
io.listen(3000);
