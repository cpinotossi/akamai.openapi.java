package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.services.DiagnosticToolsAPI;
import cpinotos.openapi.services.data.UrlDebug;

public class OpenCLITestDebugUrl extends MyTestParameters {

	@Before
	public void before() {
		this.setDapi(new DiagnosticToolsAPI(this.getHostname(), this.getEdgercFilePath1(), this.getApiClientNameDefault(), this.isDebug()));
	}
	
	@Test
	public void test() throws Exception {
		UrlDebug responseUrlDebug = this.getDapi().doUrlDebugAsynchronously(this.getTestPurgeURL3(),null, null);
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		LOGGER.info(gsonBuilder.toJson(responseUrlDebug.getUrlDebug().getDnsInformation().get(0)));
		//TODO should not be null
		assertNull(gsonBuilder.toJson(responseUrlDebug.getUrlDebug().getDnsInformation().get(0)));
	}

}
