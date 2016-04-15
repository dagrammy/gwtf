package org.gwtf

class Util {

	public static def String getSnippet(String templateName){
		//return new File('snippets/'+templateName).getText('UTF-8')
		return getTextFromFile('snippets/'+templateName)
	//	return this.getClass().getResource('/snippets/'+templateName ).getText('UTF-8')
	}
	
	public static def String getTemplate(String templateName){
		//return new File('templates/'+templateName).getText('UTF-8')
		//new File('templates/' + settings.template).getText('UTF-8')
	//	return this.getClass().getResource('/templates/'+templateName ).getText('UTF-8')
		return getTextFromFile('templates/'+templateName)
	}
	
	def static String getTextFromFile(String p){
		return new File(p).getText('UTF-8')
	}
	
}
