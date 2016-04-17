package org.gwtf


import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout;;

class ScriptRunner{
	
	private static final Logger logger = LogManager.getLogger(ScriptRunner.class)
	
	def runScript(String casperPath, String jsFile, String testName, String testDir, boolean createTestResultFiles){
		// TODO:
		// copy js to test result
		def cmdString = "cmd /c "
		if (casperPath) cmdString = cmdString + casperPath + File.separator 
		cmdString = cmdString + "casperjs"
		
		cmdString = cmdString +" --verbose --curDir='"+ testDir +"\\testresults\\"+ testName + "'"

		if (createTestResultFiles){
			cmdString = cmdString + " --xunit='"+ testDir +"\\testresults\\"+ testName +"\\test.xml'"
		}
		
		cmdString = cmdString + " test '" + jsFile + "'"
				 
		logger.info(cmdString)
		
		def proc = cmdString.execute()
		proc.in.eachLine { line ->
			logger.debug(line)
		}
		proc.out.close()
		proc.waitFor()
		
		if (proc.exitValue()) {
			logger.fatal(proc.getErrorStream())
		}
		
	}
	
}







