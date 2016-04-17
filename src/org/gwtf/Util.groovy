package org.gwtf

class Util {

	public static def String getSnippet(String templateName){
	//	return getTextFromFile('snippets/'+templateName)
		return this.getClass().getResource('/snippets/'+templateName ).getText('UTF-8')
	}
	
	public static def String getTemplate(String templateName){
		def t = this.getClass().getResource('/templates/'+templateName)
		if (t == null) return null
		return t.getText('UTF-8')
	}
	
	public static def String getCustomTemplate(String templateName){
		return getTextFromFile(templateName)
	}
	
	def static String getTextFromFile(String p){
		return new File(p).getText('UTF-8')
	}
	
}
