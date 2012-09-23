

var mongo = require('./mongo');

var COLLECTION_NAME = "raffle";

/*
 * Create a new raffle
 */
function postRaffleV1(req, res, next) {

    if(!req.body || !req.body.raffle_name) {

        console.log("ERROR: Missing POST body data");
        res.send(404);
        return next();
    }

    mongo.collection(COLLECTION_NAME, function(collection) {

        var raffle = {};
        raffle.name = req.body.raffle_name;
        raffle.tickets = [];

        collection.insert(raffle, function(err, doc) {

            if(err) {

                res.send(500);
            }
            else {

                res.json(200, doc);
            }

            return next();
        });

    });
}

function postTicketV1(req, res, next) {

    if(!req.body || !req.body.raffle_id || !req.body.user_name) {

        console.log("ERROR: Missing POST body data");
        res.send(404);
        return next();
    }

    var objectId = mongo.objectID(req.body.raffle_id);

    mongo.collection(COLLECTION_NAME, function(collection) {

        collection.update({_id : objectId}, { $push : { tickets : req.body.user_name } }, function(err, result) {

            if(err) {

                res.send(500);
            }
            else {

                res.send(200);
            }

            return next();
        });
    });


}

function getWinnerV1(req, res, next) {

    if(!req.params.id) {

        console.log("ERROR: Missing url parameters");
        res.send(404);
        return next();
    }

    var objectId = mongo.objectID(req.params.id);

    mongo.collection(COLLECTION_NAME, function(collection) {

        var cursor = collection.find({_id : objectId},{tickets : 1, _id : 0});

        cursor.toArray(function(err, result) {

            if(err) {

                res.send(500);
            }
            else {

                var tickets = result[0].tickets;

                // Randomize tickets
                fisherYates(tickets);

                res.send(200, tickets);
            }

            return next();
        });
    });
}

/*
 * http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
 */
function fisherYates ( myArray ) {

    var i = myArray.length;

    if ( i == 0 ) return false;

    while ( --i ) {

        var j = Math.floor( Math.random() * ( i + 1 ) );

        var tempi = myArray[i];

        var tempj = myArray[j];

        myArray[i] = tempj;

        myArray[j] = tempi;
    }
}

/*
 * Export functions
 */
exports.postRaffleV1 = postRaffleV1;
exports.postTicketV1 = postTicketV1;
exports.getWinnerV1 = getWinnerV1;
