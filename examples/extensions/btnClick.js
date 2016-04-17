  casper.waitForSelector("$selector",
       function success() {
           test.assertExists("$selector");
           this.click("$selector");
       },
       function fail() {
           test.assertExists("$selector");
   });