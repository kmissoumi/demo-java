package com.saucedemo.selenium.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * Login tests with Selenium.
 */
public class SeleniumLoginTest {
    protected RemoteWebDriver driver;

    @RegisterExtension
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @BeforeEach
    public void setup(TestInfo testInfo) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.put("name", testInfo.getDisplayName());

        options.setCapability("sauce:options", sauceOptions);
        URL url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");

        driver = new RemoteWebDriver(url, options);
    }

    @DisplayName("Swag Labs Login with Selenium")
    @Test
    public void swagLabsLoginTest() {
        // Retreive credentials from Sauce API
        try {
          final CloseableHttpClient httpclient = HttpClients.createDefault();
          final HttpGet httpget = new HttpGet("https://api.us-west-1.saucelabs.com/rest/v1/public/tunnels/info/versions");
          System.out.println("----------------------------------------");
          System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getUri());
          final HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
              @Override
              public String handleResponse(
                      final ClassicHttpResponse response) throws IOException {
                  final int status = response.getCode();
                  if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                      final HttpEntity entity = response.getEntity();
                      try {
                          return entity != null ? EntityUtils.toString(entity) : null;
                      } catch (final ParseException ex) {
                          throw new ClientProtocolException(ex);
                      }
                  } else {
                      throw new ClientProtocolException("Unexpected response status: " + status);
                  }
              }
          };
          final String responseBody = httpclient.execute(httpget, responseHandler);
          System.out.println("----------------------------------------");
          System.out.println(responseBody);
          System.out.println("----------------------------------------");
        } catch (Exception e) {
            System.out.println("API DANGER");
            e.printStackTrace();
            System.exit(1);
        }

        driver.get("https://www.saucedemo.com");

        By usernameFieldLocator = By.cssSelector("#user-name");
        By passwordFieldLocator = By.cssSelector("#password");
        By submitButtonLocator = By.cssSelector(".btn_action");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until((driver) -> driver.findElement(usernameFieldLocator).isDisplayed());

        WebElement userNameField = driver.findElement(usernameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement submitButton = driver.findElement(submitButtonLocator);

        userNameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        submitButton.click();

        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    /**
     * Custom TestWatcher for Sauce Labs projects.
     */
    public class SauceTestWatcher implements TestWatcher {
        @Override
        public void testSuccessful(ExtensionContext context) {
            endSession(true);
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            endSession(false);
        }

        private void endSession(boolean passed) {
            String result = passed ? "passed" : "failed"; 
            driver.executeScript("sauce:job-result=" + result);

            driver.quit();
        }
    }
}
