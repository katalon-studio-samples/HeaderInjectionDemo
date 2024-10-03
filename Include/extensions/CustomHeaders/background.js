// background.js
chrome.declarativeNetRequest.updateDynamicRules({
    removeRuleIds: [1],
    addRules: [{
      "id": 1,
      "priority": 1,
      "action": {
        "type": "modifyHeaders",
        "requestHeaders": [
          { "header": "SFTCAPTCHA", "operation": "set", "value": "KATALON" }
        ]
      },
      "condition": {
        "urlFilter": "*",
        "resourceTypes": ["main_frame"]
      }
    },
    {
      "id": 2,
      "priority": 1,
      "action": {
        "type": "modifyHeaders",
        "requestHeaders": [
          { "header": "foo", "operation": "set", "value": "bar" }
        ]
      },
      "condition": {
        "urlFilter": "*",
        "resourceTypes": ["main_frame"]
      }
    }]
  });
  
  console.log("Header modification rule added");