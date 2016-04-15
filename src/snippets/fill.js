casper.then(function(){
	 this.$fillType($selector, {
		 ${map.collect { k,v -> "'$k' : $v" }.join(', ')}
	    }, $submit);
});
