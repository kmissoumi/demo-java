package com.realdevice;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class IOSNativeAppTest {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();
    public AppiumDriver<MobileElement> driver;
    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }
    

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("language", "en"); 
        capabilities.setCapability("appium:newCommandTimeout", "90");
        capabilities.setCapability("appium:deviceName", "iPhone 12.*");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:app",
                "https://github.com" +
                "/saucelabs/sample-app-mobile" +
                "/releases/download" + 
                "/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");
        sauceOptions.setCapability("idleTimeout", "90");
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("noReset", "true");
        //sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        //sauceOptions.setCapability("accesskey", System.getenv("SAUCE_ACCESS_KEY"));
        capabilities.setCapability("sauce:options", sauceOptions);
        
       // System.out.println(capabilities.toJson());

        driver = new IOSDriver(
                    new URL("https://" +  System.getenv("SAUCE_USERNAME") + ":" +
                    System.getenv("SAUCE_ACCESS_KEY") + "@" +
                    "ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void shouldOpenApp() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10000);
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("test-Username")));
        assertTrue(loginField.isDisplayed());
    }
}
