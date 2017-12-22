package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.services.NetStorageAPI;

public class OpenCLITestEdgercNetStorage extends MyTestParameters {
		

	@Test
	public void test1() throws Exception {
		setNsapi(new NetStorageAPI(getHostname(), null, this.getApiUploadAccountName(), this.isDebug()));
		LOGGER.info(String.format("NetStorageAPI 1 %s", getFolderNameUpdates()));
		assertTrue(getNsapi().doNetstorageDir(getNetStorageRootPath(), false).getFile().get(0).getName().equals(getFolderNameUpdates()));
	}
	
	@Test
	public void test2() throws Exception {
		setNsapi(new NetStorageAPI(getHostname(), null, null, this.isDebug()));
		LOGGER.info(String.format("NetStorageAPI 2 %s", getFolderNameUpdates()));
		assertTrue(getNsapi().doNetstorageDir(getNetStorageRootPath(), false).getFile().get(0).getName().equals(getFolderNameUpdates()));
	}
	
	@Test
	public void test3() throws Exception {
		setNsapi(new NetStorageAPI(getHostname(), null, null, false));
		LOGGER.info(String.format("NetStorageAPI 3 %s", getFolderNameUpdates()));
		assertTrue(getNsapi().doNetstorageDir(getNetStorageRootPath(), false).getFile().get(0).getName().equals(getFolderNameUpdates()));
	}

	@Test
	public void test4() throws Exception {
		setNsapi(new NetStorageAPI(getHostname(), getEdgercFilePath1(), null, false));
		LOGGER.info(String.format("NetStorageAPI 4 %s", getFolderNameUpdates()));
		assertTrue(getNsapi().doNetstorageDir(getNetStorageRootPath(), false).getFile().get(0).getName().equals(getFolderNameUpdates()));
	}
	
	@Test
	public void test5() throws Exception {
		setNsapi(new NetStorageAPI(getHostname(), getEdgercFilePath1(), this.getApiUploadAccountName(), false));
		LOGGER.info(String.format("NetStorageAPI 5 %s", getFolderNameUpdates()));
		assertTrue(getNsapi().doNetstorageDir(getNetStorageRootPath(), false).getFile().get(0).getName().equals(getFolderNameUpdates()));
	}
	
	@Test
	public void test6() throws Exception {
		setNsapi(new NetStorageAPI(getHostname(), getEdgercFilePath1(), this.getApiUploadAccountName(), this.isDebug()));
		LOGGER.info(String.format("NetStorageAPI 6 %s", getFolderNameUpdates()));
		assertTrue(getNsapi().doNetstorageDir(getNetStorageRootPath(), false).getFile().get(0).getName().equals(getFolderNameUpdates()));
	}
	

}
