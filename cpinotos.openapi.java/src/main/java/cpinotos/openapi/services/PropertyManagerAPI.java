package cpinotos.openapi.services;

import java.io.IOException;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.CreateNewCPCodeResultV0;
import cpinotos.openapi.services.data.ListCPCodeResult;
import cpinotos.openapi.services.data.ProductIoT;
import cpinotos.openapi.services.data.ProductsResult;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseItemV0;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseV0;
import io.github.astinchoi.authtoken.AuthToken;
import io.github.astinchoi.authtoken.AuthTokenBuilder;
import io.github.astinchoi.authtoken.AuthTokenException;

public class PropertyManagerAPI extends OpenAPI {

	public PropertyManagerAPI(String hostname, String edgercFilePath, String apiClientNameSection, boolean debug) {
		super(hostname, edgercFilePath, debug);
		this.setApiClientName(apiClientNameSection);
		initApiCredentials();
	}

	public ListCPCodeResult doListCPCodes() throws ClientProtocolException, UnsupportedOperationException, IOException {
		SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
		return doListCPCodes(psr.getVersions().getItems().get(0).getContractId(),
				psr.getVersions().getItems().get(0).getGroupId());
	}

	public ListCPCodeResult doListCPCodes(String contractId, String groupId)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		String apiPapiEndpointPath = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiListCPCodesEndpoint(),
				"contractId", contractId);
		apiPapiEndpointPath = OpenAPI.addValueToAPIEndPointURL(apiPapiEndpointPath, "groupId", groupId);
		Gson gson = new Gson();
		String jsonResult = this.doEdgeGridAPIRequest(apiPapiEndpointPath);
		return gson.fromJson(jsonResult, ListCPCodeResult.class);
	}

	public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration(String host)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		String searchJSON = "{\"hostname\":\"" + host + "\"}";
		String apiRequestUrl = this.getApiPapiSearchEndpoint();
		String jsonResult = this.doEdgeGridAPIRequestPOST(apiRequestUrl, searchJSON);
		Gson gson = new Gson();
		return gson.fromJson(jsonResult, SearchPropertyVersionsBySingleValueResponseV0.class);
	}

	public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration()
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		return searchPAPIConfiguration(this.getHostname());
	}

	public String getPAPIRuletreeAsJSON(String propertyId, Integer propertyVersion, String contractId, String groupId,
			boolean validateRules) throws ClientProtocolException, UnsupportedOperationException, IOException {
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiGetRuletreeEndpoint(),
				"propertyVersion", propertyVersion.toString());
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "validateRules",
				Boolean.toString(validateRules));
		return this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
	}

	public ProductIoT getPAPIRuletree(String propertyId, Integer propertyVersion, String contractId, String groupId,
			boolean validateRules) throws ClientProtocolException, UnsupportedOperationException, IOException {
		String jsonResult;
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiGetRuletreeEndpoint(),
				"propertyVersion", propertyVersion.toString());
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "validateRules",
				Boolean.toString(validateRules));
		jsonResult = this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
		Gson gson = new Gson();
		return gson.fromJson(jsonResult, ProductIoT.class);

	}

	public String getPAPIConfiguration(String propertyId, String contractId, String groupId)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiGetEndpoint(), "propertyId",
				propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		return this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
	}

	public CreateNewCPCodeResultV0 doCreateCPCodes(String cpCodeName, String productId)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
		SearchPropertyVersionsBySingleValueResponseItemV0 currentPAPI = psr.getVersions().getItems().get(0);
		return doCreateCPCodes(currentPAPI.getContractId(), currentPAPI.getGroupId(), cpCodeName, productId);
	}

	public CreateNewCPCodeResultV0 doCreateCPCodes(String contractId, String groupId, String cpCodeName,
			String productId) throws ClientProtocolException, UnsupportedOperationException, IOException {
		String requestJson = String.format("{\"cpcodeName\": \"%s\",\"productId\": \"%s\"}", cpCodeName, productId);
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiCreateCPCodesEndpoint(),
				"contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		Gson gson = new Gson();
		String jsonResult = this.doEdgeGridAPIRequestPOST(currentApiPapiEndpoint, requestJson);
		return gson.fromJson(jsonResult, CreateNewCPCodeResultV0.class);
	}

	public ProductsResult doListProducts() throws ClientProtocolException, UnsupportedOperationException, IOException {
		SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
		SearchPropertyVersionsBySingleValueResponseItemV0 currentPAPI = psr.getVersions().getItems().get(0);
		return doListProducts(currentPAPI.getContractId());
	}

	public ProductsResult doListProducts(String contractId)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiListProductsEndpoint(),
				"contractId", contractId);
		String jsonResult = this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
		Gson gson = new Gson();
		return gson.fromJson(jsonResult, ProductsResult.class);
	}


	public String getEdgeAuthToken(String hostname, String urlpath)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		Integer startTime = (int) Instant.now().getEpochSecond();
		Integer duration = 3600;
		return getEdgeAuthToken(hostname, urlpath, startTime, duration);
	}

	public String getEdgeAuthToken(String hostname, String urlpath, Integer startTime, Integer duration) throws ClientProtocolException, IOException {
		String encryptionKey = null;
		String edgeauthTokenName = null;
		OpenAPI.LOGGER.info("edgeURL:step1/7: found Property Configuration for Hostname " + hostname);
		SearchPropertyVersionsBySingleValueResponseV0 psr = this.searchPAPIConfiguration();
		// TODO Handle exception no config for provided hostname
		// We expect only one entry in the result.
		SearchPropertyVersionsBySingleValueResponseItemV0 psri = psr.getVersions().getItems().get(0); 
		OpenAPI.LOGGER.info("edgeURL:step2/7: found Property Configuration " + psri.getPropertyName() + "  version "
				+ psri.getPropertyVersion());
		String prt = this.getPAPIRuletreeAsJSON(psri.getPropertyId(), psri.getPropertyVersion(), psri.getContractId(),
				psri.getGroupId(), false);
		OpenAPI.LOGGER.info("edgeURL:step3/7: downloaded Property Configuration " + psri.getPropertyName() + " version "
				+ psri.getPropertyVersion());
		OpenAPI.LOGGER.debug(prt.toString());

		if (prt.equals(null)) {
			OpenAPI.LOGGER.info("Could not retrieve Property Configuration");
		} else {
			OpenAPI.LOGGER.info("edgeURL:step4/7: extract edgeauth secret key from Property Configuration "
					+ psri.getPropertyName() + " version " + psri.getPropertyVersion());
			// Retrive EdgeAuth Shared Secret / Key from ruleset.
			// NOTE: We expect the EdgeAuth to be setup via regular Papi
			// behavior inside the Ruleset, not via Advanced Metadata.
			encryptionKey = this.getEdgeAuthKeyFromPapiRuleSet(prt);
			edgeauthTokenName = this.getEdgeAuthTokenNameFromPapiRuleSet(prt);
			OpenAPI.LOGGER.info("edgeURL:step5/7: extract edgeauth query name from Property Configuration "
					+ psri.getPropertyName() + " version " + psri.getPropertyVersion());

		}
		OpenAPI.LOGGER.info("edgeURL:step6/7: generate edgeauth token with startime: " + startTime);
		return this.generateEdgeAuthToken(hostname, urlpath, edgeauthTokenName, startTime, duration, encryptionKey);
	}
	
	private String generateEdgeAuthToken(String hostname, String urlpath, String tokenName, Integer startTime,
			Integer duration, String encrpytionKey) {
		String token = null;
		try {
			AuthToken at = new AuthTokenBuilder().key(encrpytionKey).windowSeconds(duration).escapeEarly(false)
					.startTime(startTime).build();
			at.setTokenName(tokenName);
			token = String.format("%s=%s", at.getTokenName(), at.generateURLToken(urlpath));
			// => Link or Request "url" /w Query string
		} catch (AuthTokenException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	private String getEdgeAuthKeyFromPapiRuleSet(String papiRuleSetJson) {
		String edgeauthKey = null;
		// Try to find inside Advanced Section
		try {
			String pattern = "<key>(.+?)</key>";
			Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
			Matcher m = r.matcher(papiRuleSetJson);
			m.find();
			edgeauthKey = m.group(1);
			OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));
		} catch (java.lang.IllegalStateException e) {
			// Try to find inside JSON
			String pattern = ".*\"key\".:.\"(.+?)\",.*";
			Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
			Matcher m = r.matcher(papiRuleSetJson);
			m.find();
			edgeauthKey = m.group(1);
			OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));
		}

		return edgeauthKey;
	}
	
	private String getEdgeAuthTokenNameFromPapiRuleSet(String papiRuleSetJson) {
		String edgeauthTokenName = null;
		// Try to find inside Advanced Section
		try {
			String pattern = "<token>(.+?)</token>";
			Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
			Matcher m = r.matcher(papiRuleSetJson);
			m.find();
			edgeauthTokenName = m.group(1);
			OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));
		} catch (java.lang.IllegalStateException e) {
			// Try to find inside JSON
			String pattern2 = ".*\"locationId\".:.\"(.+?)\",.*\"key\".*";
			Pattern r2 = Pattern.compile(pattern2, Pattern.DOTALL);
			Matcher m2 = r2.matcher(papiRuleSetJson);
			m2.find();
			edgeauthTokenName = m2.group(1);
			OpenAPI.LOGGER.debug("m2.group(1):" + m2.group(1));
		}

		return edgeauthTokenName;
	}


}
