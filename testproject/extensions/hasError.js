 casper.then(function(){
	 test.assertVisible("#errorContainer");
	 <% if (text){ %>
	 	test.assertSelectorHasText($selector, '$text');
	 <% } %>
});  