package org.gwtf

//@Grab('log4j:log4j:1.2.17')

import groovy.util.CliBuilder

import org.apache.commons.cli.*
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import groovy.util.logging.*

def config = new ConfigSlurper().parse(this.class.getResource('/resources/log4jconfig.groovy'))
PropertyConfigurator.configure(config.toProperties())

def final Logger logger = LogManager.getLogger(this.class)

def cli = new CliBuilder()
// "java -classpath xwtt.jar;lib\\* xwtt.Xwtt"
cli.with
{
   h(longOpt: 'help', 'Help - Usage Information')
   f(longOpt: 'groovyfile', 'path to the groovy testfile', args: 1, type: String, required: true)
   c(longOpt: 'casperjspath', 'path to the binary directory of CasperJS', args: 1, type: String, required: false)
   j(longOpt: 'jsscriptdir', 'directory to store the CasperJS js files in', args: 1, type: String, required: false)
   u(longOpt: 'url', 'start URL', args: 1, type: String, required: false)
   t(longOpt: 'testresults', 'create xUnit test results files (default: false)', required: false)
   o(longOpt: 'scriptonly', 'do not run test, just create JS file', required: false)
   s(longOpt: 'suite', 'run a test suite script', required: false)
   tn(longOpt: 'testname', 'name of the test run', args: 1, type: String, required: false)
   td(longOpt: 'testdir', 'directory to store the xUnit test results files', args: 1, type: String, required: false)
}

//cli.usage()
def options = cli.parse(args)
if (!options) System.exit(0)

if (options.h){
	 cli.usage()
	 System.exit(0)
}


def testFile = new File(options.f)
if (!testFile.exists()) {
	println "could not fine test file '${options.f}'"
	 System.exit(0)
}

def currentDir = new File("").absolutePath
def url = null
def jsPath = testFile.parentFile.parent + File.separator + "scripts_js"
def casperPath = ""
def testName = null
def testDir = testFile.parentFile.parent

if (options.j) jsPath =  options.j
if (options.u) url =  options.u
if (options.c) casperPath = options.c
if (options.tn) testName = options.tn
if (options.td) testDir = options.td


if (options.s){
	// run test suite
	SuiteRunner r = new SuiteRunner()
	r.runSuite(options.f, url, jsPath, casperPath, testName, testDir, options.o, options.t)
} else {
	TemplateCreator c = new TemplateCreator()
	def jsFile = c.createCasperScript(options.f, url, jsPath)
	if (!options.o){
		ScriptRunner r = new ScriptRunner()
		r.runScript(casperPath, jsFile.absolutePath,testName, testDir, options.t)
	}
	
}



