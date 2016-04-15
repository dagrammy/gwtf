package org.gwtf



def String getTemplate(String templateName){
	//return new File('snippets/'+templateName).getText('UTF-8')

	this.getClass().getResource('/snippets/'+templateName ).getText('UTF-8')
//	.withInputStream { ris ->
//		new File( destDir ).withOutputStream { fos ->
//		  fos << ris
//		}
//	  }
	
}

def btnClickTmpl = getTemplate('btnClick.js')
println btnClickTmpl



