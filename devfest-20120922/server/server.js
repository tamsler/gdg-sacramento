
var restify = require('restify'),
    api = require('./lib/api'),
    mongo = require('./lib/mongo');


mongo.init(function() {

    console.log("INFO: MongoDB is ready");
});


var server = restify.createServer({
    name : "Raffle App Server",
    version : "0.0.1-a"
});
server.use(restify.acceptParser(server.acceptable));
server.use(restify.authorizationParser());
server.use(restify.queryParser({ mapParams: false }));
server.use(restify.jsonBodyParser({ mapParams: false }));

server.post('/api/v1/user', api.postUserV1);

/*
 * Starting the server
 */
server.listen(process.env.VCAP_APP_PORT || 8080, "0.0.0.0", function () {
    console.log('%s listening at %s', server.name, server.url);
});
