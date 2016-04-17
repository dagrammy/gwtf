test = {

  url = "https://rawgit.com/dagrammy/gwtf/master/web/index.html"
	
  setUp = "casper.echo('setting'); casper.echo('it up');"
  tearDown = "casper.echo('See ya');"
  
  viewPort = "1280, 1024"
  
 steps = [
	screenshot(),
	exists("#exampleInputEmail")

 ]

}
