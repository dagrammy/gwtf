package org.gwtf;

import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

// this.clickLabel('Anchor_Text','a')

class BindingCreator {

	private static final Logger logger = LogManager.getLogger(SuiteRunner.class)
	
	def createJsSnippet(String t, Map binding){
		def engine = new groovy.text.SimpleTemplateEngine()
		def template = engine.createTemplate(t).make(binding.withDefault{ '' })
		return template.toString()
	}
	
	def click(clickType, selector, snippets){
		createJsSnippet(snippets["click.js"], ["clickType" : clickType, "selector" : selector])
	}
	
	def fill(fillType, selector, submit, map, snippets){
		createJsSnippet(snippets["fill.js"], ["fillType" : fillType, "selector" : selector, "submit" : submit, "map" : map])
	}
	
	def toggle(fillType, formSelector, field, value, snippets){
		def map = [:]
		map[field] = value
		fill(fillType, "\"${formSelector}\"", false, map, snippets)
	}
	
	def select(fillType, formSelector, field, values, snippets){
		def map = [:]
		if (values instanceof List){
			map[field] = "[" + values.each{  "'${it}'" }.join(", ") + "]"
		} else {
			map[field] = "'${values}'"
		}
		fill(fillType, "\"${formSelector}\"", false, map, snippets)
	}
	
	public Binding createBinding(){
		def binding = new Binding()
		
		def snippets = [:]
		File snippetsDir = new File("snippets")
		
		snippetsDir.listFiles({d, f-> f.endsWith(".js")} as FilenameFilter).each {
			snippets[it.name] = Util.getSnippet(it.name)
		}

		// evals
		binding.eval = { x ->
			createJsSnippet(snippets["eval.js"], ["js" : x])
		}
		
		binding.evalEquals = { x, y ->
			createJsSnippet(snippets["evalEquals.js"], ["js" : x, "value" : y])
		}
		
		// exists
		binding.exists = { x ->
			createJsSnippet(snippets["exists.js"], ["selector" : "\"${x}\""])
		}
		
		binding.existsXpath = { x ->
			createJsSnippet(snippets["exists.js"], ["selector" : "x('${x}')"])
		}
		
		binding.doesntExist = { x ->
			createJsSnippet(snippets["doesntExiss.js"], ["selector" : "\"${x}\""])
		}
		
		binding.doesntExistXpath = { x ->
			createJsSnippet(snippets["doesntExist.js"], ["selector" : "x('${x}')"])
		}
		
		binding.resourceExists = { x ->
			createJsSnippet(snippets["resourceExists.js"], ["resource" : x])
		}
		
		// value checks
		binding.assertField = 
		binding.fieldHasValue = { x, y ->
			createJsSnippet(snippets["fieldHasValue.js"], ["selector" : "\"${x}\"", "value" : "\"${y}\"", "selectorType" : "name"])
		}
		
		binding.assertFieldCss =
		binding.fieldByCssHasValue = { x, y ->
			createJsSnippet(snippets["fieldHasValue.js"], ["selector" : "\"${x}\"", "value" : "\"${y}\"", "selectorType" : "css"])
		}
		
		binding.assertFieldXpath =
		binding.fieldByXpathHasValue = { x, y ->
			createJsSnippet(snippets["fieldHasValue.js"], ["selector" : "'${x}'", "value" : "\"${y}\"", "selectorType" : "xpath"])
		}
		
		binding.fieldHasValueOfVar = { x, y ->
			createJsSnippet(snippets["fieldHasValue.js"], ["selector" : "\"${x}\"", "value" : y, "selectorType" : "name"])
		}
		
		binding.fieldByCssHasValueOfVar = { x, y ->
			createJsSnippet(snippets["fieldHasValue.js"], ["selector" : "\"${x}\"", "value" : y, "selectorType" : "css"])
		}
		
		binding.fieldByXpathHasValueOfVar = { x, y ->
			createJsSnippet(snippets["fieldHasValue.js"], ["selector" : "'${x}'", "value" : y, "selectorType" : "xpath"])
		}
		
		// click
		binding.click = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "\"${x}\""])
			click("click",  "\"${x}\"", snippets)
		}
		
		binding.clickXpath = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "x('${x}')"])
			click("click",  "x('${x}')", snippets)
		}
		
		binding.clickHyperlinkWithText = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "x(\"//a[normalize-space(text())='" + x + "']\")"])
			click("click",  "x(\"//a[normalize-space(text())='" + x + "']\")", snippets)
		}
		
		binding.doubleClick = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "\"${x}\""])
			click("doubleclick",  "\"${x}\"", snippets)
		}
		
		binding.doubleClickXpath = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "x('${x}')"])
			click("doubleclick",  "x('${x}')", snippets)
		}
		
		binding.rightClick = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "\"${x}\""])
			click("rightclick",  "\"${x}\"", snippets)
		}
		
		binding.rightClickXpath = { x ->
			//createJsSnippet(snippets["click.js"], ["clickType" : "click", "selector" : "x('${x}')"])
			click("rightclick",  "x('${x}')", snippets)
		}
		
		// navigation
		binding.back = {
			createJsSnippet(snippets["back.js"], [:])
		}
		
		binding.forward = {
			createJsSnippet(snippets["forward.js"], [:])
		}
		
		// enabled/disabled & readonly stuff 
		binding.isEnabled = { x ->
			createJsSnippet(snippets["exists.js"], ["selector" : "\"${x}:not(:disabled)\""])
		}
		
		binding.isDisabled = { x ->
			createJsSnippet(snippets["exists.js"], ["selector" : "\"${x}:disabled\""])
		}
		
		binding.isReadOnly = { x ->
			createJsSnippet(snippets["exists.js"], ["selector" : "\"${x}:read-only\""])
		}
		
		binding.isNotReadOnly = { x ->
			createJsSnippet(snippets["exists.js"], ["selector" : "\"${x}:not(:read-only)\""])
		}
		
		// visibility
		binding.assertVisible = 
		binding.isVisible = { x ->
			createJsSnippet(snippets["isVisible.js"], ["selector" : "\"${x}\""])
		}
		
		binding.assertVisibleXpath =
		binding.isVisibleXpath = { x ->
			createJsSnippet(snippets["isVisible.js"], ["selector" : "x('${x}')"])
		}
		
		binding.assertNotVisible =
		binding.isNotVisible = { x ->
			createJsSnippet(snippets["isNotVisible.js"], ["selector" : "\"${x}\""])
		}
		
		binding.assertNotVisibleXpath =
		binding.isNotVisibleXpath = { x ->
			createJsSnippet(snippets["isNotVisible.js"], ["selector" : "x('${x}')"])
		}
		
		// counting
		binding.assertElementCount = 
		binding.elementCount = { x, y ->
			createJsSnippet(snippets["elementCount.js"], ["selector" : "\"${x}\"", "count" : y])
		}
		

		
		binding.elementCountXpath = { x, y ->
			createJsSnippet(snippets["elementCount.js"], ["selector" : "x('${x}')", "count" : y])
		}
		
		// plain text
		binding.textExists = { x ->
			createJsSnippet(snippets["textExists.js"], ["text" : x])
		}
		
		binding.hasText = { x, text ->
			createJsSnippet(snippets["hasText.js"], ["selector" : "\"${x}\"", "text" : text])
		}
		
		binding.hasTextXpath = { x, text ->
			createJsSnippet(snippets["hasText.js"], ["selector" : "x('${x}')", "text" : text])
		}
		
		binding.doesntHaveText = { x, text ->
			createJsSnippet(snippets["doesntHaveText.js"], ["selector" : "\"${x}\"", "text" : text])
		}
		
		binding.doesntHaveTextXpath = { x, text ->
			createJsSnippet(snippets["doesntHaveText.js"], ["selector" : "x('${x}')", "text" : text])
		}
		
		// pure JS evaluation
		binding.evaluateJs = { x ->
			createJsSnippet(snippets["evaluate.js"], ["js" : x])
		}
		
		// key and other events
		binding.sendKey = { x ->
			createJsSnippet(snippets["sendKeys.js"], ["selector" : "'body'", "value" : "casper.page.event.key.${x}"])
		}
		
		binding.sendKeys = { x,y ->
			createJsSnippet(snippets["sendKeys.js"], ["selector" : "\"${x}\"", "value" : "\"${x}\""])
		}
		
		binding.sendKeyToSelector = { x,y ->
			createJsSnippet(snippets["sendKeys.js"], ["selector" : "\"${x}\"", "value" : "casper.page.event.key.${y}"])
		}
		
		binding.sendKeyToSelectorXpath = { x,y ->
			createJsSnippet(snippets["sendKeys.js"], ["selector" :"x('${x}')", "value" : "casper.page.event.key.${y}"])
		}
		
		binding.sendKeysToSelector = { x,y ->
			createJsSnippet(snippets["sendKeys.js"], ["selector" : "\"${x}\"", "value" : "\"${y}\""])
		}
		
		binding.sendKeysToSelectorXpath = { x,y ->
			createJsSnippet(snippets["sendKeys.js"], ["selector" :"x('${x}')", "value" : "\"${y}\""])
		}
		
		// fill form fields
		binding.fillField = { formSelector, field, value, submit = false ->
			def map = [:]
			map[field] = "'${value}'"
			fill("fill", "\"${formSelector}\"", submit, map, snippets)
		}
		
		binding.fillFields = { formSelector, map, submit = false ->
			map.keySet().each{
				map[it] = "'${map[it]}'"
			}
			fill("fill", "\"${formSelector}\"", submit, map, snippets)
		}
		
		binding.fillFieldCss =
		binding.fillFieldSelectors = { formSelector, field, value, submit = false ->
			def map = [:]
			map[field] = "'${value}'"
			fill("fillSelectors", "\"${formSelector}\"", submit, map, snippets)
		}
		
		binding.fillFieldsCss =
		binding.fillFieldsSelectors = { formSelector, map, submit = false ->
			map.keySet().each{
				map[it] = "'${map[it]}'"
			}
			fill("fillSelectors", "\"${formSelector}\"", submit, map, snippets)
		}
		
		binding.fillFieldXpath = { formSelector, field, value, submit = false ->
			def map = [:]
			map[field] = "'${value}'"
			fill("fillXPath", "\"${formSelector}\"", submit, map, snippets)
		}
		
		binding.fillFieldsXpath = { formSelector, map, submit = false ->
			map.keySet().each{
				map[it] = "'${map[it]}'"
			}
			fill("fillXPath", "\"${formSelector}\"", submit, map, snippets)
		}
		
		binding.setValue = { x, y ->
			def c = "document.querySelector(\"${x}\").value = \"${y}\";"
			createJsSnippet(snippets["evaluate.js"], ["js" : c])
		}
		
		binding.declareVar = { varName, formSelector, fieldName ->
			createJsSnippet(snippets["declareVar.js"], ["variableName" : varName, "form" : formSelector, "field" : fieldName])
		}
		
		// add some nice shortcuts for checkbox, select something
		binding.check = { formSelector, field ->
			toggle("fill", formSelector, field, true, snippets)
		}

		binding.checkSelector = { formSelector, field ->
			toggle("fillSelectors", formSelector, field, true, snippets)
		}

		binding.checkXpath = { formSelector, field ->
			toggle("fillXPath", formSelector, field, true, snippets)
		}

		binding.uncheck = { formSelector, field ->
			toggle("fill", formSelector, field, false, snippets)
		}
		
		binding.uncheckSelector = { formSelector, field ->
			toggle("fillSelectors", formSelector, field, false, snippets)
		}
		
		binding.uncheckXpath = { formSelector, field ->
			toggle("fillXPath", formSelector, field, false, snippets)
		}
		
		binding.select = { formSelector, field, values ->
			select("fill", formSelector, field, values, snippets)
		}
		
		binding.selectCss = 
		binding.selectSelector = { formSelector, field, values ->
			select("fill", formSelector, field, values, snippets)
		}

		binding.selectXpath = { formSelector, field, values ->
			select("fill", formSelector, field, values, snippets)
		}
		
		binding.selectByIndex = 
		binding.selectIndex = {selector, index ->
			def x = "var sel = document.querySelector(\"${selector}\"); sel.selectedIndex = ${index};"
			createJsSnippet(snippets["evaluate.js"], ["js" : x])
		}
		
		binding.selectByValue = {selector, value ->
			def x = "var sel = document.querySelector(\"${selector}\"); sel.selectedIndex = document.querySelector(\"#cbOrders option[value='${value}']\").index;"
			createJsSnippet(snippets["evaluate.js"], ["js" : x])
		}
		
		binding.assertOptionCount = { x, y ->
			def selector = "${x} > option"
			//createJsSnippet(snippets["selectOptionCount.js"], ["selector" : "\"${selector}\"", "count" : y])
			createJsSnippet(snippets["elementCount.js"], ["selector" : "\"${selector}\"", "count" : y])
		}
		
		// logging and output
		binding.log = { x, y = null ->
			createJsSnippet(snippets["log.js"], ["text" : x, "level" : (y == null ? "debug" : y)])
		}
		
		binding.info = { x, form = null, field = null ->
			createJsSnippet(snippets["info.js"], ["msg" : x, "form" : "\"${form}\"", "field" : field])
		}
		
		binding.addMessageToTestresult= { x, form = null, field = null ->
			createJsSnippet(snippets["addMessageToTestresult.js"], ["msg" : x, "form" : "\"${form}\"", "field" : field])
		}
		
		// screenshot
		binding.screenshotCount = 0
		binding.screenshot = { x = null ->
			def c = x
			if (x == null){
				c = ++binding.screenshotCount
				c = String.format("%03d", c)
			}
			createJsSnippet(snippets["screenshot.js"], ["no" : c])
		}
		
		
		binding.login = { namefield, username, passwordfield, password, btn ->
			createJsSnippet(snippets["login.js"], ["login_field" : namefield, "user" : username, "password_field" : passwordfield, "password" : password, "login_button" : btn])
		}
		
		
		binding.include = { x ->
			def groovyScriptFile2 = new File("tests/"+x+".groovy")
			def script2 = groovyScriptFile2.getText('UTF-8')
			def test2 = shell.evaluate(script2)
			
			def settings2 = new ConfigSlurper().parse(new ClosureScript(closure: test2))
			
			def c2 = "// START INCLUDE " + x
			settings2.closures.each {it ->
				c2 += System.getProperty("line.separator") + it
			}
			c2 += System.getProperty("line.separator") +  "// END INCLUDE " + x
			return c2
		}
		
		File extsionsDir = new File("extensions")
		extsionsDir.listFiles({d, f-> f.endsWith(".js")} as FilenameFilter).each {
			def extName = it.name.substring(0, it.name.indexOf("."))
			if (binding.hasVariable(extName)){
				logger.warn(extName + " already exists")
			}  else {
				def tmpl = it.getText('UTF-8')
				def c =  { x ->
					createJsSnippet(tmpl, x)
				}
				binding.setVariable(extName, c)
				logger.info("bound " + it.name + " as " + extName)
			}
		}

		extsionsDir.listFiles({d, f-> f.endsWith(".groovy")} as FilenameFilter).each {
			def extName = it.name.substring(0, it.name.indexOf("."))
			if (binding.hasVariable(extName)){
				logger.warn(extName + " already exists")
			}  else {
				def script = it.getText('UTF-8')
				def cconf = new	org.codehaus.groovy.control.CompilerConfiguration(sourceEncoding: 'UTF-8')
				def shell = new GroovyShell(binding, cconf)
				def c = {x -> evalExtensionScript(script, x, binding)  }
				logger.info("bound " + it.name + " as " + extName)
				binding.setVariable(extName, c)
			}
		}
		return binding
	}
	
	
	def evalExtensionScript(String script, Map args, Binding binding){
		def cconf = new	org.codehaus.groovy.control.CompilerConfiguration(sourceEncoding: 'UTF-8')
		def shell = new GroovyShell(binding, cconf)
		def closure = shell.evaluate(script)
		closure(args)
	}
}