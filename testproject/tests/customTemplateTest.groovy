test = {

  url = "https://rawgit.com/dagrammy/gwtf/master/web/index.html"
  template = "customTemplate.js"
  viewPort = "1280, 1024"
  
 steps = [
	screenshot(),
	exists("#exampleInputEmail"),
	exists("#exampleInputEmailConfirm")

 ]

}
