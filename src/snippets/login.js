casper.waitForSelector("$login_field", function success() {
	test.assertExists("$login_field");
	this.click("$login_field");
	this.sendKeys("$login_field", "$user");
}, function fail() {
	test.assertExists("$login_field");
});

casper.waitForSelector("$password_field", function success() {
	test.assertExists("$password_field");
	this.sendKeys("$password_field", "$password");
}, function fail() {
	test.assertExists("$password_field");
});

casper.waitForSelector("$login_button", function success() {
	test.assertExists("$login_button");
	this.click("$login_button");
}, function fail() {
	test.assertExists("$login_button");
});

casper.wait(12000, function() {
	this.echo("Wait after login.");
});