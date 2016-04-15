 casper.then(function(){
		casper.waitForSelector($selector,
			   function success() {
					test.assertNotVisible($selector);
			   },
			   function fail() {
				   test.assertNotVisible($selector);
			});	
		});
