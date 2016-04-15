   casper.wait(500);
   casper.then(function() {
	   if (curDir != null) this.captureSelector(curDir+"/screenshot_${no}.png", "html");
   });