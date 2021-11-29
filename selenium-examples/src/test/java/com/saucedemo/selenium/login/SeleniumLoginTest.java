package com.saucedemo.selenium.login;

import org.junit.jupiter.api.Assertions;
// import org.apache.http.client.ClientProtocolException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;

//import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
//import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;
import java.util.Base64;

//import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.CredentialsProvider;
// import org.apache.http.auth.AuthScope;
// import org.apache.http.auth.UsernamePasswordCredentials;
// import org.apache.http.impl.client.BasicCredentialsProvider;
// import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

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
    public void swagLabsLoginTest() throws Exception {
        // Retrieve credentials from Sauce API
        String response;
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        String hookId = System.getenv().getOrDefault("hookId", "83485139-58df-4a13-8e4b-4cd96e64117f");
        String testId = System.getenv().getOrDefault("testId", "619ee0855d058a1953bdd21b");
        String projectId = System.getenv().getOrDefault("testId", "618f37f15d606a1fe7cbab3f");

        String runPath = "api-testing/rest/v4/" + hookId + "/tests/" + testId + "/_run-sync";

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.us-west-1.saucelabs.com")
                .setPort(443)
                .setPath(runPath)
                .setParameter("format", "json")
                .build();
        String encoding = Base64.getEncoder().encodeToString((sauceUserName + ":" + sauceAccessKey).getBytes());
        //System.out.println("----------------------------------------");
        //System.out.println(encoding);
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
        HttpClient httpClient = HttpClientBuilder.create().build();
        System.out.println("----------------------------------------");
        System.out.println("Executing request " + httpPost.getMethod() + ":");
        System.out.println(httpPost.getURI());
        System.out.println("----------------------------------------");
        HttpResponse r = httpClient.execute(httpPost);
        StatusLine s = r.getStatusLine();
        System.out.printf("HTTP Response: %d, %s\n", s.getStatusCode(), s.getReasonPhrase());
        System.out.println("----------------------------------------");
        HttpEntity w = r.getEntity();
        response = EntityUtils.toString(w);
        System.out.println(response);
        System.out.println("----------------------------------------");
        JsonArray j = (JsonArray) new JsonParser().parse(response);
        JsonObject item = (JsonObject) j.get(0);
        String eventId = item.get("id").getAsString();
        JsonObject facts = item.getAsJsonObject("facts");
        System.out.printf("API eventId=%s \n", eventId);
        System.out.printf("API facts=%s \n", facts.toString());
        System.out.println("----------------------------------------");
        String userName = facts.getAsJsonObject("userName").get("value").getAsString();
        String password = facts.getAsJsonObject("password").get("value").getAsString();
        System.out.printf("userName=%s \n", userName);
        System.out.printf("password=%s \n", password);
        System.out.println("----------------------------------------");
        String reportURL = "project/" + projectId + "/event/" + eventId;
        System.out.println("API  Report URL: https://api.us-west-1.saucelabs.com:443/api-testing/" + reportURL);

        driver.get("https://www.saucedemo.com");
        ((JavascriptExecutor) driver).executeScript("sauce:context=Sauce API URL: https://api.us-west-1.saucelabs.com:443/api-testing/");
        ((JavascriptExecutor) driver).executeScript("sauce:context=" + reportURL);

        String job_id = driver.getSessionId().toString();
        System.out.println("Test Report URL: https://www.saucelabs.com/tests/" + job_id);
        System.out.println("----------------------------------------");
        By usernameFieldLocator = By.cssSelector("#user-name");
        By passwordFieldLocator = By.cssSelector("#password");
        By submitButtonLocator = By.cssSelector(".btn_action");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until((driver) -> driver.findElement(usernameFieldLocator).isDisplayed());

        WebElement userNameField = driver.findElement(usernameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement submitButton = driver.findElement(submitButtonLocator);

        userNameField.sendKeys(userName);
        passwordField.sendKeys(password);
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
