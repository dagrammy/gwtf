casper.then(function(){
	<% if (form && field) { %>
		test.pass("$msg: " + this.getFormValues($form).$field);
	<% } else { %>
		test.pass("$msg");
	<% } %>	
});
