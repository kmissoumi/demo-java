

## Sauce Labs Quick Start

### `java` Edition

<br>
<img align="right" src="assets/logo_7.png">  



| :rocket: [Sign Up for a _free_ trial at Sauce Labs][00] :bangbang:        |
|:-------------------------------------------------------------------------|
| :page_facing_up: [Source `java` Repo][31] and [original `README`][32]    |
| :page_facing_up: [Dynamic Device Allocation][50]                         |
| :page_facing_up: [Selenium Java Course][40]                              |
| :page_facing_up: _`saucelabs`_ [Documentation Home][10]                  |
| :page_facing_up: _`saucelabs`_ [Knowledge Base][11]                      |
| :page_facing_up: _`saucelabs`_ [API Reference][12]                        |





&nbsp;

### _Quick Start_

- _`maven`_ Required
- Set Environment Variables
  - _`SAUCE_USERNAME`_
  - _`SAUCE_ACCESS_KEY`_
- Clone Repo, Setup Environment, and Go!

&nbsp;

```sh
# clone repo
git clone https://github.com/kmissoumi/demo-java && cd demo-java 

# resolve dependencies and test compile maven 
mvn dependency:resolve && mvn test-compile

# run test
mvn test -pl appium-examples -Dtest=AndroidNativeAppTest,IOSNativeAppTest

```

</br>

## _Device Allocation_

### _[Dynamic Device Allocation][50]_  

- [deviceName][51]
- [platformVersion][52]
- [platformName][53]
  


### Other Selection Criteria

#### Device _Type_

- [tabletOnly][54]
- [phoneOnly][55]

#### Device _Pool_

- [privateDevicesOnly][56]
- [publicDevicesOnly][57]

</br>

 > _Hint:_ Run multiple tests during the same device session with _[cacheId!][58]_

</br>


## Example Capabilities


> _Hint_: For optimized request routing, _only_ set the _needed_ capabilities.  
> (i.e. less is more, less is easier to trace)

</br>

```java
MutableCapabilities capabilities = new MutableCapabilities();
    capabilities.setCapability("platformName", "iOS");
    capabilities.setCapability("appium:newCommandTimeout", "90");
    capabilities.setCapability("appium:deviceName", "iPhone 12.*");
    capabilities.setCapability("appium:automationName", "XCUITest");
    capabilities.setCapability("appium:app",
        "https://github.com" +
        "/saucelabs/sample-app-mobile" +
        "/releases/download" + 
        "/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");

MutableCapabilities sauceOptions = new MutableCapabilities();
    sauceOptions.setCapability("idleTimeout", "90");
    sauceOptions.setCapability("name", name.getMethodName());
    sauceOptions.setCapability("noReset", "true");
    capabilities.setCapability("sauce:options", sauceOptions);
```

```java
MutableCapabilities capabilities = new MutableCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("appium:newCommandTimeout", "90");
    capabilities.setCapability("appium:deviceName", "Google Pixel.*");
    capabilities.setCapability("appium:automationName", "UiAutomator2");
    capabilities.setCapability("appium:app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

MutableCapabilities sauceOptions = new MutableCapabilities();
    sauceOptions.setCapability("idleTimeout", "90");
    sauceOptions.setCapability("name", "Android Native App W3C);
    sauceOptions.setCapability("noReset", "true");
    capabilities.setCapability("sauce:options", sauceOptions);

    driver = new AndroidDriver(new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" + System.getenv("SAUCE_ACCESS_KEY") + "@ondemand.us-west-1.saucelabs.com/wd/hub"),
    capabilities);

```


```java
// specific device by id
// ** valid, yet not recommended, as this is unnecessarily specific **
    capabilities.setCapability("deviceName", "iPhone_12_Pro_real_us");
```

> _Hint_: [Find Devices _theEasyWay!_][200]

</br>

```java
// any iPhone from my private device pool
    capabilities.setCapability("deviceName", "iPhone.*");
    capabilities.setCapability("privateDevicesOnly", "true");
```

</br>

```java
// any iPhone 12 (regular, mini, Pro, Pro Max) from my private device pool
    capabilities.setCapability("deviceName", "iPhone 12.*");
    capabilities.setCapability("privateDevicesOnly", "true");
// set cacheId value to unique string per test suite
// all test methods referencing this cacheId, will run on the same device
    capabilities.setCapability("cacheId", "9776a1231a5f30a24");
```

</br>

```java
// any iOS 14.x phone from the public device pool
    capabilities.setCapability("deviceName", ".*");
    capabilities.setCapability("platformName", "iOS");
    capabilities.setCapability("platformVersion", "14");
    capabilities.setCapability("publicDevicesOnly", "true");
    capabilities.setCapability("phoneOnly", "true");
```


</br>

```java
// any Android 11 phone or tablet
    capabilities.setCapability("deviceName", ".*");
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("platformVersion", "11");
```

</br>




## Device Cleanup

### Per Session _[Device Cleanup][70]_ Steps

- accounts and data _cleared_
- default browser history and user data _removed_
- non-default browsers _uninstalled_
- network settings _reset_
- device settings _reset_
- app _uninstalled_
- cached data _deleted_
- device _de-allocated_

 </br>


## References

- [Sauce Labs Documentation Home][10]
- [Sauce Labs Demo Repos and More!][30]
  - [Source `java` Repo][31] and [original `README`][32]
- [Support Central][01]
- [Knowledge Base Articles][11]
  - [Troubleshooting Tips for Device][61]
  - [Session, Job, and Appium Ids Explained][62]
- [Sauce School][02]
  - [Selenium Java Course][40]
- [Videos][05] & [Webinars][06]
- [Data Sheets][03]
  - [Device Cleanup Steps][70] and [Procedures][71]
- [Papers & Reports][20]
- [Case Studies][21]
- [YouTube Channel][22]
- [API Reference][12]
- [CLi Reference][13]
- [Glossary of Sauce Labs Terminology][15]
- [Common Error Messages][16]
- [System Status][17] & [Maintenance Windows][18]
- [Selenium Platform Configurator][19]


&nbsp;


[00]: https://saucelabs.com/sign-up
[01]: https://support.saucelabs.com/hc/en-us
[02]: https://training.saucelabs.com
[03]: https://saucelabs.com/resources/data-sheets

[05]: https://saucelabs.com/resources/videos
[06]: https://saucelabs.com/resources/webinars

[10]: https://docs.saucelabs.com
[11]: https://support.saucelabs.com/hc/en-us#knowledge-base
[12]: https://docs.saucelabs.com/dev/api/#accessing-the-apis
[13]: https://docs.saucelabs.com/dev/cli

[15]: https://docs.saucelabs.com/dev/glossary
[16]: https://docs.saucelabs.com/dev/error-messages
[17]: https://status.saucelabs.com
[18]: https://docs.saucelabs.com/dev/data-center-maint
[19]: https://saucelabs.com/platform/platform-configurator#

[20]: https://saucelabs.com/resources/white-papers
[21]: https://saucelabs.com/resources/case-studies
[22]: https://www.youtube.com/user/saucelabs/videos

[30]: https://github.com/saucelabs-training
[31]: https://github.com/saucelabs-training/demo-java
[32]: //README2.md

[40]: https://training.saucelabs.com/SeleniumJava
[41]: https://training.saucelabs.com/seleniumpython

[50]: https://docs.saucelabs.com/mobile-apps/supported-devices/#dynamic-device-allocation
[51]: https://docs.saucelabs.com/dev/test-configuration-options/#devicename
[52]: https://docs.saucelabs.com/dev/test-configuration-options/#platformversion
[53]: https://docs.saucelabs.com/mobile-apps/automated-testing/appium/real-devices/#specifying-the-platformname
[54]: https://docs.saucelabs.com/dev/test-configuration-options/#tabletonly
[55]: https://docs.saucelabs.com/dev/test-configuration-options/#phoneonly
[56]: https://docs.saucelabs.com/dev/test-configuration-options/#privatedevicesonly
[57]: https://docs.saucelabs.com/dev/test-configuration-options/#publicdevicesonly
[58]: https://docs.saucelabs.com/dev/test-configuration-options/#cacheid

[61]: https://support.saucelabs.com/hc/en-us/sections/115000518514-RDC-Mobile-Application-Testing-Tips-and-Troubleshooting
[62]: https://support.saucelabs.com/hc/en-us/articles/360062316954-Session-ID-Job-ID-and-Appium-Session-ID-What-is-the-difference-

[70]: https://docs.saucelabs.com/mobile-apps/supported-devices/#real-device-cleaning
[71]: https://saucelabs.com/assets/19LV8PISelZ5na3uwghC1x/5e2846c6c4c4aed55e97db30a301f2b6/DS__Device_Cleanup_Procedure.pdf

[80]: https://docs.saucelabs.com/testrunner-toolkit/configuration/common-syntax/#mode
    "Test Runner Toolkit Common Syntax"
[81]: https://docs.saucelabs.com/testrunner-toolkit/ide-integrations/vscode
    "Test Runner Toolkit IDE Integration w/ Visual Studio Code"
[82]: https://docs.saucelabs.com/testrunner-toolkit
    "_saucectl_ Docs"
[83]: https://docs.saucelabs.com/testrunner-toolkit/saucectl
    "_saucectl_ CLI Reference"
[84]: https://docs.saucelabs.com/testrunner-toolkit/configuration/espresso
    "_saucectl_ YML Reference"

[200]: https://gist.github.com/kmissoumi/b54d5abc87658e8e30314175be2c61a5
    "Find Device Id, Details, and Availability _theEasyWay!_"

[500]: https://docs.saucelabs.com/visual/e2e-testing/setup
    "Getting Started with Sauce Visual"
[501]: https://github.com/saucelabs-training/demo-java/blob/dff5fd61b8e152efe59e4a8c9e75c644de4e51e0/selenium-junit4-examples/src/test/java/com/saucedemo/SimpleVisualE2ETest.java
 "Basic Visual E2E Test"
[505]: https://docs.saucelabs.com/visual/acct-team-mgmt/
    "Add Teams and Users"

