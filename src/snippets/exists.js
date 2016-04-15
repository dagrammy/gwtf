casper.then(function(){
	casper.waitForSelector($selector,
		   function success() {
				test.assertExists($selector);
		   },
		   function fail() {
		       test.assertExists($selector);
		});	
});
