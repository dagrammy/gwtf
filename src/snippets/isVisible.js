 casper.then(function(){
	casper.waitForSelector($selector,
		   function success() {
				test.assertVisible($selector);
		   },
		   function fail() {
			   test.assertVisible($selector);
		});	
	});
