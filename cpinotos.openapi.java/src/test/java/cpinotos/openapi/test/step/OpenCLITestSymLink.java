package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;

public class OpenCLITestSymLink extends MyTestParameters {

	@Before
	public void before() {
		setNsapi(new NetStorageAPI(getHostname(), getEdgercFilePath1(), getApiUploadAccountName(), isDebug()));
	}

	@Test
	public void test() {
		LOGGER.info(String.format("Symlink source %s, target %s", getNetStorageTestFolderPath(), getNetStorageTestSymLinkPath()));
		assertTrue(getNsapi().doNetstorageSymLink(getNetStorageTestSymLinkPath(), getNetStorageTestSymLinkPath()));
	}

}
