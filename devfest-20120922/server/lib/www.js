/*
 * Author: Thomas Amsler : tamsler@gmail.com
 */

/*
 * Serving static web content HTML, JS, CSS, PNG, JPEG, JPG
 */

 var fs = require('fs');
 var path = require('path');

 function serveV1(request, response, next) {

 	var filePath = './public' + request.url;
 	
 	if (filePath === './public/') {

 		filePath = './public/index.html';
 	}

 	var extname = path.extname(filePath);
 	var contentType = 'text/html';

 	switch (extname) {
 		case '.js':
 		contentType = 'text/javascript';
 		break;
 		case '.css':
 		contentType = 'text/css';
 		break;
 		case '.png':
 		contentType = 'image/png';
 		break;
 		case '.jpeg':
 		contentType = 'image/jpeg';
 		break;
 		case '.jpg':
 		contentType = 'image/jpeg';
 		break;
 	}

 	fs.exists(filePath, function(exists) {

 		if (exists) {

 			fs.readFile(filePath, function(error, content) {

 				if (error) {

 					response.writeHead(500);
 					response.end();
 				}
 				else {

 					response.writeHead(200, { 'Content-Type': contentType });
 					response.end(content, 'utf-8');
 				}
 			});
 		}
 		else {

 			fs.readFile('./public/index.html', function(error, content) {

 				if (error) {

 					response.writeHead(500);
 					response.end();
 				}
 				else {

 					response.writeHead(200, { 'Content-Type': contentType });
 					response.end(content, 'utf-8');
 				}
 			});
 		}
 	});
 }

 exports.serveV1 = serveV1;