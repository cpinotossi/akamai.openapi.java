package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;


public class OpenCLITestMkdir extends MyTestParameters {

	@Before
	public void before() {
		setOpenAPI(new OpenAPI(getConfigPath(), isDebug()));
		setNsapi(new NetStorageAPI(getOpenAPI()));
	}
	
	@Test
	public void test() {		
		LOGGER.info(String.format("mkdir %s", getNetStorageTestFolderPath()));
		assertNotNull(getNsapi().doNetstorageMkdir(getNetStorageTestFolderPath()));
	}

}
