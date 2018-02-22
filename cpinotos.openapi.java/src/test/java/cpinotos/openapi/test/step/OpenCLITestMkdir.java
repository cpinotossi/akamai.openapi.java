package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.akamai.netstorage.NetStorageException;

import cpinotos.openapi.services.NetStorageAPI;


public class OpenCLITestMkdir extends MyTestParameters {

	
	@Before
	public void before() {
		setNsapi(new NetStorageAPI(getHostname(), getEdgercFilePath1(), getApiClientNameDefault(), isDebug()));
	}
	
	@Test
	public void test() throws NetStorageException, IOException {		
		LOGGER.info(String.format("mkdir %s", getNetStorageTestFolderPath()));
		assertNotNull(getNsapi().doNetstorageMkdir(getNetStorageTestFolderPath()));
	}

}
