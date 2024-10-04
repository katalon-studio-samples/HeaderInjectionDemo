package com.katalon.keywords
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonBuilder
import groovy.json.JsonOutput

class CustomWebDriver {
	def generateHeaderRules(Map<String, String> headers) {
		def theRules = []
		headers.eachWithIndex { headerName, headerValue, index ->
			def rule = [
				id: index + 1,
				priority: 1,
				action: [
					type: "modifyHeaders",
					requestHeaders: [
						[
							header: headerName,
							operation: "set",
							value: headerValue
						]
					]
				],
				condition: [
					urlFilter: "*",
					resourceTypes: [
						"main_frame",
						"xmlhttprequest"
					]
				]
			]
			theRules << rule
		}

		def jsonBuilder = new JsonBuilder(theRules)

		return jsonBuilder.toPrettyString()
	}

	def writeManifestFile(String filePath) {
		def manifestJson = [
			manifest_version: 3,
			name: "Katalon Header Injector",
			version: "1.0",
			description: "Adds custom headers to all requests",
			permissions: ["declarativeNetRequest"],
			declarative_net_request: [
				rule_resources: [
					[
						id: "ruleset_1",
						enabled: true,
						path: "rules.json"
					]
				]
			],
			host_permissions: ["<all_urls>"]
		]
		def file = new File(filePath)
		file.parentFile.mkdirs() // Create parent directories if they don't exist
		file.write(JsonOutput.prettyPrint(JsonOutput.toJson(manifestJson)))
	}

	def writeJsonToFile(String json, String filePath) {
		def file = new File(filePath)
		file.parentFile.mkdirs() // Create parent directories if they don't exist
		file.write(json)
		println "JSON rules have been written to: ${file.absolutePath}"
	}

	/**
	 * Refresh browser
	 */
	@Keyword(keywordObject = "Browser")
	def openBrowserWithHeaders(Map<String, String> customHeaders) {
		String manifestPath = RunConfiguration.getProjectDir() + "/Include/extensions/CustomHeaders/" + "manifest.json"
		writeManifestFile(manifestPath)
		String headerRules = generateHeaderRules(customHeaders)
		String jsonPath = RunConfiguration.getProjectDir() + "/Include/extensions/CustomHeaders/" + "rules.json"
		writeJsonToFile(headerRules, jsonPath)

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
		} catch (Exception e) {
			println "An error occurred: ${e.getMessage()}"
			e.printStackTrace()
		}
	}
}