package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;
import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class OpenCLITestGet extends MyTestParameters {
	static ChromeDriver chromeDriver;

	 @BeforeClass
     public static void openBrowser(){
		 chromeDriver = new ChromeDriver();
		} 
	 
	@Before
	public void before() {
		setOpenAPI(new OpenAPI(getConfigPath(), getHost(), isDebug()));
		setNsapi(new NetStorageAPI(getOpenAPI()));
	}


	@Test
    public void testWithChrome() {
		String host = this.getOpenAPI().getHostname();
		String url = String.format("http://%s%s",host, getNetStorageTestUrlPath());
		LOGGER.info(String.format("get %s", url));
		chromeDriver.get(url);
        String pageSource = chromeDriver.getPageSource();
        assertTrue(pageSource.contains("Lorem ipsum"));
    }
	/*
    @Test
    public void testWithHeadlessBrowsers(HtmlUnitDriver htmlUnit) {        
		String host = this.getOpenAPI().getHost();
		String url = String.format("http://%s%s",host, getNetStorageTestUrlPath());
		LOGGER.info(String.format("get %s", url));
        htmlUnit.get(url);
        String pageSource = htmlUnit.getPageSource();
        assertTrue(pageSource.startsWith("Lorem ipsum"));
    }
	*/
	
	 @AfterClass
	 public static void closeBrowser(){
		 chromeDriver.quit();
	 }
}
