	casper.then(function(){
	    this.evaluate(function() {
	    	\$('${selector}').data('dropdown').selectOption(\$('#cb option[value=\"${value}\"]'));
	    	\$('${selector}').data('dropdown').setValue();
	    });
	
	});