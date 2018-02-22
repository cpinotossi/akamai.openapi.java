package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import cpinotos.openapi.services.PropertyManagerAPI;
import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class OpenCLITestGet extends MyTestParameters {
	static ChromeDriver chromeDriver;
	
	 @BeforeClass
     public static void openBrowser(){
		 chromeDriver = new ChromeDriver();
		} 
	 

	@Test
    public void testWithChrome() throws ClientProtocolException, UnsupportedOperationException, IOException {
		// Generate EdgeAuth Token
		this.setPapi(new PropertyManagerAPI(this.getHostname(), this.getEdgercFilePath1(), this.getApiClientNameDefault(), this.isDebug()));
		String edgeAuthTokenQueryString = this.getPapi().getEdgeAuthToken(this.getHostname(), this.getNetStorageTestUrlPath());
		// Create Test URL
		String url = String.format("http://%s%s?%s",this.getHostname(), getNetStorageTestUrlPath(),edgeAuthTokenQueryString);
		LOGGER.info(String.format("get %s", url));
		
		chromeDriver.get(url);
        String pageSource = chromeDriver.getPageSource();
        LOGGER.info(String.format("Response Body %s", pageSource));
        //TODO i believe something is wrong here, we do not get the expected result.
        assertTrue(pageSource.contains("Lorem ipsum"));
    }
	
	 @AfterClass
	 public static void closeBrowser(){
		 chromeDriver.quit();
	 }
}
