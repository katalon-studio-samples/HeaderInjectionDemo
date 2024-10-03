# HeaderInjectionDemo

## Overview

HeaderInjectionDemo is a project designed to demonstrate the use of a custom browser extension for injecting HTTP headers into web requests. This project includes a Chrome extension that modifies request headers to include specific values, which can be useful for testing and development purposes.

## Features

- **Custom Header Injection**: Automatically adds predefined headers to all outgoing HTTP requests.
- **Manifest V3 Extension**: Utilizes the latest Chrome extension manifest version for enhanced security and performance.
- **Selenium Integration**: Includes scripts to automate browser interactions using Selenium WebDriver.

## Components

- **Chrome Extension**: Located in the `Include/extensions/CustomHeaders` directory, this extension modifies request headers.
- **Selenium Scripts**: Found in the `Scripts` directory, these scripts demonstrate how to use the extension with Selenium WebDriver.

## Usage

1. **Load the Extension**: Use the provided path to load the unpacked extension into Chrome.
2. **Run Selenium Scripts**: Execute the provided Groovy scripts to see the extension in action.

## Requirements

- Google Chrome
- Selenium WebDriver
- Katalon Studio

## License

This project is licensed under the MIT License.
