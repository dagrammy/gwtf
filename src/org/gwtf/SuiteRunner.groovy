package org.gwtf

import groovy.lang.Closure;
import groovy.lang.Script;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

class SuiteRunner{
	
	private static final Logger logger = LogManager.getLogger(SuiteRunner.class)
	
	def runSuite(String suiteFile, String testUrl, String jsDir, String casperPath, String testName, String testDir, boolean onlyCreateJsFile, boolean createTestResultFiles){
		def binding = new Binding()
		def cconf = new	org.codehaus.groovy.control.CompilerConfiguration(sourceEncoding: 'UTF-8')
		def shell = new GroovyShell(binding, cconf)
		
		def groovyScriptFile = new File(suiteFile)
		def script = groovyScriptFile.getText('UTF-8')
		def suiteScript = shell.evaluate(script)
		
		def suite = new ConfigSlurper().parse(new ClosureScript(closure: suiteScript))
		
		if (testName == null){
			testName = suite.name + "_" + new Date().format("yyyyMMdd_HHmmss")
		}
		
		logger.info("Running TestSuite: " + suite.name)
		def d = new Date().format("yyyyMMdd_HHmmss")
		suite.tests.each {it ->
			logger.info("run " + it)
			def f = new File(groovyScriptFile.parentFile.parentFile.absolutePath + File.separator + "tests" + File.separator + "${it}.groovy")
			//TemplateCreator.main("${f.absolutePath}", "scripts_js", "${suite.name.replaceAll(" ", "_")}_${d}/${it}", casperPath, url, workPath);
			TemplateCreator c = new TemplateCreator()
			def jsFile = c.createCasperScript(f.absolutePath, testUrl, jsDir)
			if (! onlyCreateJsFile){
				ScriptRunner r = new ScriptRunner()
				r.runScript(casperPath, jsFile.absolutePath, testName + File.separator + c.currentTestName, testDir, createTestResultFiles)
			}
		}
	}
	
}



