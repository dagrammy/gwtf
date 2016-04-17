var x = require('casper').selectXPath;
var utils = require('utils');

var curDir = casper.cli.get("curDir");

// öäü

casper.test.setUp(function() {
    $setUp
});

casper.test.tearDown(function() {
    $tearDown
});	

function exit(code) {
	setTimeout(function(){ phantom.exit(code); }, 0);
	phantom.onError = function(){};
}

casper.test.begin('$testname', function(test) {

	casper.on('remote.message', function(msg) {
		this.echo('remote message caught: ' + msg);
	});
	
	casper.on('page.error', function(msg,backtrace) {
		this.echo('remote JS Error: ' + msg);
		this.log(msg, "ERROR");
	});	
		
	
	casper.test.on('fail', function(failure) {
		if (curDir != null)	casper.captureSelector(curDir+"/screenshot_error_"+failure.line+".png", "html");
	});
	
	casper.options.verbose = $verbose;
	casper.options.logLevel = '$logLevel';

   casper.start('$testurl').then(function() {
	<% if (viewPort){ %>
	casper.viewport($viewPort);
	<% } %>
   
   $body	
   
<% if (dumpLogs){ %>
   casper.then(function() {
	   utils.dump(this.result.log);
	});
<% } %>
   

   }).run(function() {test.done(); exit(0);});
});