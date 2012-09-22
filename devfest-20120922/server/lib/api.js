

var mongo = require('./mongo');


var COLLECTION_NAME = "raffle";

function postUserV1(req, res, next) {

    res.send(200, {"foo": "bar"});

}


function helloV1(req, res, next) {

    mongo.collection(COLLECTION_NAME, function(collection) {

        var cursor = collection.find({});
        cursor.toArray(function(err, result) {

            if(err) {

                console.log("ERROR: err = " + err);
                res.send(500);
            }
            else {

                res.json(200, result);
            }

            return next();
        });

    });
}

exports.postUserV1 = postUserV1;
exports.helloV1 = helloV1;
