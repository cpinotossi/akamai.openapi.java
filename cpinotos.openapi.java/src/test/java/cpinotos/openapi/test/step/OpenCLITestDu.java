package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.data.NetStorageDuResult;

public class OpenCLITestDu extends MyTestParameters {
		
	@Before
	public void before() {
		setOpenAPI(new OpenAPI(getConfigPath(), isDebug()));
		setNsapi(new NetStorageAPI(getOpenAPI()));
	}
	
	@Test
	public void test() throws Exception {
		LOGGER.info(String.format("du %s", getNetStorageTestFolderPath()));
		NetStorageDuResult nsresult = getNsapi().doNetstorageDu(getNetStorageTestFolderPath());
		assertNotNull(nsresult);
	}

}
