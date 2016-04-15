<% if (selectorType == "css"){ %>
casper.then(function(){
	test.assertFieldCSS($selector, $value, 'assert value: ' + $value);
}); 
<% } else if (selectorType == "xpath") { %>
casper.then(function(){
	//test.assertFieldXPath($selector, '$value', 'test value: $value');
	test.assertField({type: 'xpath', path: $selector}, $value, 'assert value: ' + $value);
}); 
<% } else if (selectorType == "name") { %>
casper.then(function(){
	test.assertField($selector, $value, 'assert value: ' + $value);
}); 
<% } %>
