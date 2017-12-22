package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.services.PropertyManagerAPI;

public class OpenCLITestEdgerc extends MyTestParameters {
		

	@Test
	public void test1() throws Exception {
		setPapi(new PropertyManagerAPI(this.getHostname(), null, this.getApiClientName(), this.isDebug()));
		LOGGER.info("PropertyManagerAPI 1");
		assertTrue(!this.getPapi().doListCPCodes().equals(null));
	}
	
	@Test
	public void test2() throws Exception {
		setPapi(new PropertyManagerAPI(getHostname(), null, null, this.isDebug()));
		LOGGER.info("PropertyManagerAPI 2");
		assertTrue(!this.getPapi().doListCPCodes().equals(null));
	}
	
	@Test
	public void test3() throws Exception {
		setPapi(new PropertyManagerAPI(getHostname(), null, null, false));
		LOGGER.info("PropertyManagerAPI 3");
		assertTrue(!this.getPapi().doListCPCodes().equals(null));
	}

	@Test
	public void test4() throws Exception {
		setPapi(new PropertyManagerAPI(getHostname(), getEdgercFilePath1(), null, false));
		LOGGER.info("PropertyManagerAPI 4");
		assertTrue(!this.getPapi().doListCPCodes().equals(null));
	}
	
	@Test
	public void test5() throws Exception {
		setPapi(new PropertyManagerAPI(getHostname(), getEdgercFilePath1(), this.getApiClientName(), false));
		LOGGER.info("PropertyManagerAPI 5");
		assertTrue(!this.getPapi().doListCPCodes().equals(null));
	}
	
	@Test
	public void test6() throws Exception {
		setPapi(new PropertyManagerAPI(getHostname(), getEdgercFilePath1(), this.getApiClientName(), this.isDebug()));
		LOGGER.info("PropertyManagerAPI 6");
		assertTrue(!this.getPapi().doListCPCodes().equals(null));
	}
	

}
