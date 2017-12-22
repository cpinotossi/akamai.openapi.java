package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import cpinotos.openapi.services.PurgeAPI;

public class OpenCLITestEdgercPurge extends MyTestParameters {
		

	@Test
	public void test1() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), null, this.getApiClientPurgeName(), this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 1 %s", this.getTestPurgeURL()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeURL(), true));
	}
	
	@Test
	public void test2() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), null, null, this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 2 %s", this.getTestPurgeURL()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeURL(), true));
	}
	
	@Test
	public void test3() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), null, null, false));
		LOGGER.info(String.format("PurgeAPI 3 %s", this.getTestPurgeURL()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeURL(), true));
	}

	@Test
	public void test4() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), null, false));
		LOGGER.info(String.format("PurgeAPI 4 %s", this.getTestPurgeURL()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeURL(), true));
	}
	
	@Test
	public void test5() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), this.getApiClientPurgeName(), false));
		LOGGER.info(String.format("PurgeAPI 5 %s", this.getTestPurgeURL()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeURL(), true));
	}
	
	@Test
	public void test6() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), this.getApiClientPurgeName(), this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 6 %s", this.getTestPurgeURL()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeURL(), true));
	}
	

}
