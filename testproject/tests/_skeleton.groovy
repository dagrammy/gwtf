test = {

	name = "My first test" // name of the test case. if not specfied, the name of the groovy file is used 
	template = 'defaultTemplate.js' // if not specfied, the default template is used (see templates/defaultTemplate.js)
	url = "http://localhost/myApp" // the URL to start the test. can be set/overridden by parameter

	setUp = "" // function(s) to be executed before test is started
	tearDown = "" // function to be executed after the test completed

	// verbose = false // default is false
	// logLevel = "error" // default is "error"
	// dumpLogs = false // default is false
	
	
	steps = [
		// .. add some test steps
	]
}
