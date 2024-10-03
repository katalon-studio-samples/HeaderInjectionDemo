import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration

// Set up Chrome options
ChromeOptions options = new ChromeOptions()

// Specify the path to your unpacked Manifest V3 extension
String extensionPath = RunConfiguration.getProjectDir() + "/Include/extensions/CustomHeaders"
options.addArguments("--load-extension=" + extensionPath)

// Check if extension directory exists
File extensionDir = new File(extensionPath)
if (!extensionDir.exists() || !extensionDir.isDirectory()) {
	println "ERROR: Extension directory does not exist or is not a directory!"
	return
}

// Enable verbose logging
options.addArguments("--verbose")
options.addArguments("--log-level=0")

// Create a new Chrome driver instance with the options
System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath())
WebDriver driver

try {
	driver = new ChromeDriver(options)
	DriverFactory.changeWebDriver(driver)
	
	// Print Chrome version
	def browserVersion = ((ChromeDriver)driver).getCapabilities().getVersion()
	println "Chrome version: ${browserVersion}"

	// Navigate to chrome://extensions
	driver.get("chrome://extensions")
	WebUI.delay(5)

	// Navigate to a website that echoes headers
	WebUI.navigateToUrl("https://httpbin.org/headers")
	WebUI.delay(5)
	
	
} catch (Exception e) {
	println "An error occurred: ${e.getMessage()}"
	e.printStackTrace()
} finally {
	// Close the browser
	if (driver != null) {
		driver.quit()
	}
}