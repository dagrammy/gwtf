   var $variableName;
   
   casper.then(function() {
	   $variableName = this.getFormValues('$form').$field;
   });   