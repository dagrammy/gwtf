casper.then(function(){
	casper.waitForSelector($selector,
	   function success() {
		   test.assertExists($selector);
	       this.$clickType($selector);
	   },
	   function fail() {
	       test.assertExists($selector);
	});
});
