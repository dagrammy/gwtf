package org.gwtf


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

class ScriptRunner{
	
	private static final Logger logger = LogManager.getLogger(ScriptRunner.class)
	
	def runScript(String casperPath, String jsFile, String testName, String testDir, boolean createTestResultFiles){
		
		// TODO:
		// copy js to test result
		
		if (testName == null){
			testName = new Date().format("yyyyMMdd_HHmmss")
		}
		
		def cmdString = "cmd /c "
		if (casperPath) cmdString = cmdString + casperPath + File.separator 
		cmdString = cmdString + "casperjs test "
		
		if (createTestResultFiles){
			cmdString = cmdString + "--xunit='"+ testDir +"\\testresults\\"+ testName +"\\test.xml' "
		}
		
		cmdString = cmdString + jsFile
		
		if (createTestResultFiles){
			cmdString = cmdString +" --curDir='"+ testDir +"\\testresults\\"+ testName + "'"
		}
		 
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







