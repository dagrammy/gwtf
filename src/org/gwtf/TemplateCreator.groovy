package org.gwtf

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import groovy.lang.Closure;
import groovy.lang.Script;

class TemplateCreator{
	
	private static final Logger logger = LogManager.getLogger(TemplateCreator.class)
	def currentTestName
	
	
	def File createCasperScript(String testFile, String testUrl, String jsDir){
		def now = new Date()
		def dt = now.format("yyyyMMdd_HHmmss")

		def cconf = new	org.codehaus.groovy.control.CompilerConfiguration(sourceEncoding: 'UTF-8')
		
		BindingCreator b = new BindingCreator()
		def binding = b.createBinding()
		
		def shell = new GroovyShell(binding, cconf)
		
		def groovyScriptFile = new File(testFile)
		def script = groovyScriptFile.getText('UTF-8')
		def evaluatedScript = shell.evaluate(script)
		
		def test = new ConfigSlurper().parse(new ClosureScript(closure: evaluatedScript))
		
		def template = test.template
		if (!template) template = "defaultTemplate.js"
		
		def bodyTemplateTmpl = Util.getTemplate(template)
		
		def complete = ''
		test.steps.each {it ->
			complete += System.getProperty("line.separator") + it
		}
		
		def params = [:]
		def testname = null
		def url = null
		def setUp = ""
		def tearDown = ""
		def logLevel = "error"
		def verbose = false
		def dumpLogs = false
		def viewPort = null
		
		if (test.params) params = test.params
		if (test.url) url =  test.url
		if (test.name) testname =  test.name
		if (test.setUp) setUp =  test.setUp
		if (test.tearDown) tearDown =  test.tearDown
		if (test.logLevel) logLevel = test.logLevel
		if (test.verbose) verbose = test.verbose
		if (test.dumpLogs) dumpLogs = test.dumpLogs
		if (test.viewPort) viewPort = test.viewPort
		
		if (testUrl) url = testUrl

				
		if (url == null) throw Exception("URL not specified")
		if (!testname) testname =  groovyScriptFile.name.substring(0, groovyScriptFile.name.lastIndexOf('.'))
		currentTestName = testname
				
		def completeBinding = ["params": params, "testname": testname, "testurl": url, "body": complete, "setUp" : setUp, "tearDown" : tearDown, "verbose" : verbose, "logLevel" : logLevel, "dumpLogs" : dumpLogs, "viewPort" : viewPort]
		
		def completeEngine = new groovy.text.SimpleTemplateEngine()
		def completeTemplate = completeEngine.createTemplate(bodyTemplateTmpl).make(completeBinding)
		def end = completeTemplate.toString()
		
		def jsFileName = groovyScriptFile.name.substring(0, groovyScriptFile.name.lastIndexOf('.'))  + ".js"
		def jsd = new File("." + File.separator +"scripts_js")
		if (jsDir != null){
			 jsd = new File(jsDir)
		}
		
		if (!jsd.exists()) {
			logger.info("create dir " + jsd.absolutePath)
			jsd.mkdir()
		}
		jsFileName = jsd.absolutePath + File.separator + jsFileName
		
		def js = new File(jsFileName)
		js.withWriter("UTF-8") { out ->
			  out.println end
		}
		return js
	}
	
}
