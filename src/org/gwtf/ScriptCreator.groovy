package org.gwtf


def writeFile(String content, String filename){
	new File(filename).withWriter("UTF-8") { out ->
		out.println content
  }
}




