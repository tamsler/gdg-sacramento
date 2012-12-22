
var restify = require('restify');
var api = require('./lib/api');
var www = require('./lib/www');
var mongo = require('./lib/mongo');


var server = restify.createServer({
    name : "Raffle App Server",
    version : "0.0.1-a"
});

server.use(restify.acceptParser(server.acceptable));
server.use(restify.authorizationParser());
server.use(restify.queryParser({ mapParams: false }));
server.use(restify.jsonBodyParser({ mapParams: false }));

/*
 * Create a new raffle
 * POST Body: { "raffle_name" : "<raffle name>" }
 *
 * TEST: curl -H "Content-Type: application/json" -i -X POST -d '{ "raffle_name" : "GDG DevFest Rafflle" }' localhost:8080/api/v1/raffle
 */
server.post('/api/v1/raffle', api.postRaffleV1);

/*
 * Create a new raffle entry
 * POST Body: { "raffle_id" : "<raffle id>", "user_name" : "<user name>" }
 *
 * TEST: curl -H "Content-Type: application/json" -i -X POST -d '{"raffle_id":"505f78b7e6c235e253000001", "user_name":"thomas"}' localhost:8080/api/v1/ticket
 */
server.post('/api/v1/ticket', api.postTicketV1);

/*
 * Get a list of all participants. The order of particpants determins winner(s)
 * URL parameter:
 * :id = Raffle ID
 *
 * TEST: curl -i -X GET localhost:8080/api/v1/winner/505f78b7e6c235e253000001
 */
server.get('/api/v1/winner/:id', api.getWinnerV1);

/*
 * Get the list of raffles
 *
 * TEST: curl -i -X GET localhost:8080/api/v1/raffle
 */
server.get('/api/v1/raffle', api.getRaffleV1);


/*
 * Serve static content
 */ 
server.get('\/.*', www.serveV1);

mongo.init(function() {

    console.log("INFO: MongoDB is ready");

    /*
     * Starting the server
     */
    server.listen(process.env.VCAP_APP_PORT || 8080, "0.0.0.0", function () {
        console.log('%s listening at %s', server.name, server.url);
    });
});


