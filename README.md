# DESCRIPTION

This java api uses the "testng-result.xml" file to generate the excel report. The excel report file will have "TestCase Name" which will be in the form package name appended with class name and then appended with method name.
  
ex: com.testcase.Testclass.testcaseOne  

The second column is "Status" which will have pass/fail. 
The 3rd column is the "Exception" which will have exception name. 
The last column is "Exception Message" which will have the stack trace. 


# REQUIREMENTS

	* JDK 1.7 & Apache Poi Ver3.10.1 

# COMMAND LINE

	* java -jar TestNgToExcel.jar "<path to testng-result.xml>"
	
# IMPORT JAR	
 
	* ExcelReport.generateReport("<path to testng-result.xml>")
	
# DEPENDENCY 
	
	* Apache Poi Ver3.10.1