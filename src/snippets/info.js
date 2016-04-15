casper.then(function(){
	<% if (form && field) { %>
		test.info("$msg: " + this.getFormValues($form).$field);
	<% } else { %>
		test.info("$msg");
	<% } %>
});
