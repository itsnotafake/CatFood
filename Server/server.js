const http = require('http');
const querystring = require('querystring');

var breakfastEnabled = true;
var dinnerEnabled = true;
var loggedDay = 0;
var loggedMonth = 0;

var requestHandler = function (req, res) {
	//Obtain the request Method, URL, and Header
	console.log("New request: Method = " + req.method);
	var reqMethod = req.method;
	console.log("New request: url = " + req.url);
	var reqURL = req.url;

	//Determine which method is being called from the client (CatFood, or DateAuthentication)
	// /feedTheCat?user=Devin&meal=breakfast
	// /dateAuthentication
	var calledMethod = reqURL.split("?");
	calledMethod = calledMethod[0];
	calledMethod = calledMethod.substring(1,calledMethod.length);
	console.log("Called method: " + calledMethod);

	var reply = "";
	if(calledMethod == "feedTheCat"){
		reply = feedTheCat(reqURL);
	}else if(calledMethod == "mealCheck"){
		reply = mealCheck();
	}else{
		reply = "unknown_request";
	}
  	
  	res.writeHead(200, {'Content-Type': 'text/plain'});
  	res.end(reply + '\n');
}

var feedTheCat = function (requestURL){
	var myArgs = querystring.parse(require('url').parse(requestURL).query);
	console.log(myArgs);
	var user = myArgs.user;
	var meal = myArgs.meal;
	
	//unknown_user check
	if(typeof user == "undefined"){
		return "unkown_user";
	}

	//unknown_meal check
	if(typeof meal == "undefined"){
		return "unkown_meal";
	}
	if(meal != "breakfast" && meal != "dinner"){
		return "unkown_meal";
	}

	//already_fed check
	if(meal == "breakfast" && !breakfastEnabled){
		return "already_fed";
	}
	if(meal == "dinner" && !dinnerEnabled){
		return "already_fed";
	}

	//ok return
	if(meal == "breakfast"){
		console.log("Test");
		breakfastEnabled = false;
		return "ok";
	}
	if(meal == "dinner"){
		dinnerEnabled = false;
		return "ok";
	
	//broken_request if nothing else works
	}else{
		return "broken_request";
	}

}

var mealCheck = function(){
	var dayDate = new Date();
	var day = dayDate.getDate();
	var monthDate = new Date();
	var month = monthDate.getMonth();
	//check to see if it's a new day, and if it is then we update the date and set breakfastEnabled and dinnerEnabled to true
	if(day != loggedDay || month != loggedMonth || loggedDay === 0){
		loggedDay = day;
		loggedMonth = month;
		breakfastEnabled = true;
		dinnerEnabled = true;
	} 
	return breakfastEnabled + " " + dinnerEnabled; 
}

var server = http.createServer(requestHandler);
console.log("Server created");

server.listen(8124, "192.168.1.106");
console.log("Server is listening");

console.log('Server running at on my PC at 192.168.1.106');
