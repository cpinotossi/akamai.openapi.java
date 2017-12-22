package cpinotos.openapi.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.CreateNewCPCodeResultV0;
import cpinotos.openapi.services.data.ListCPCodeResult;
import cpinotos.openapi.services.data.ProductIoT;
import cpinotos.openapi.services.data.ProductsResult;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseItemV0;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseV0;


public class PropertyManagerAPI extends OpenAPI {

	public PropertyManagerAPI(String hostname, String edgercFilePath, String apiClientNameSection, boolean debug){
		super(hostname, edgercFilePath, debug);
		this.setApiClientName(apiClientNameSection);
		initApiCredentials();
}



public ListCPCodeResult doListCPCodes(){
	SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
	return doListCPCodes(psr.getVersions().getItems().get(0).getContractId(), psr.getVersions().getItems().get(0).getGroupId());
}
	
public ListCPCodeResult doListCPCodes(String contractId, String groupId){
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiListCPCodesEndpoint(), "contractId", contractId);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
	Gson gson = new Gson();
	String jsonResult = this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
	return gson.fromJson(jsonResult, ListCPCodeResult.class);
}


/*
 * No longer needed
public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration(String host, String searchJSON) {
	String apiRequestUrl = this.getApiPapiSearchEndpoint();
	String jsonResult = this.doEdgeGridAPIRequestPOST(apiRequestUrl, searchJSON);	
	Gson gson = new Gson();
	return gson.fromJson(jsonResult, SearchPropertyVersionsBySingleValueResponseV0.class);
}
*/

public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration(String host) {
	String searchJSON = "{\"hostname\":\"" + host + "\"}";
	String apiRequestUrl = this.getApiPapiSearchEndpoint();
	String jsonResult = this.doEdgeGridAPIRequestPOST(apiRequestUrl, searchJSON);	
	Gson gson = new Gson();
	return gson.fromJson(jsonResult, SearchPropertyVersionsBySingleValueResponseV0.class);
}

public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration() {
	return searchPAPIConfiguration(this.getHostname());
}

public String getPAPIRuletreeAsJSON(String propertyId, Integer propertyVersion, String contractId, String groupId,
		boolean validateRules) {	
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiGetRuletreeEndpoint(), "propertyVersion", propertyVersion.toString());
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "validateRules", Boolean.toString(validateRules));
		return this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
}

public ProductIoT getPAPIRuletree(String propertyId, Integer propertyVersion, String contractId, String groupId,
		boolean validateRules) {	
		String jsonResult;
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiGetRuletreeEndpoint(), "propertyVersion", propertyVersion.toString());
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "validateRules", Boolean.toString(validateRules));
		jsonResult = this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
		Gson gson = new Gson();
		return gson.fromJson(jsonResult, ProductIoT.class);
		
}

public String getPAPIConfiguration(String propertyId, String contractId, String groupId) {
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiGetEndpoint(), "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		return this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
}

public CreateNewCPCodeResultV0 doCreateCPCodes(String cpCodeName, String productId) {
	SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
	SearchPropertyVersionsBySingleValueResponseItemV0  currentPAPI = psr.getVersions().getItems().get(0);
	return doCreateCPCodes(currentPAPI.getContractId(), currentPAPI.getGroupId(), cpCodeName, productId );
}

public CreateNewCPCodeResultV0 doCreateCPCodes(String contractId, String groupId, String cpCodeName, String productId) {
	String requestJson = String.format("{\"cpcodeName\": \"%s\",\"productId\": \"%s\"}", cpCodeName, productId);
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiCreateCPCodesEndpoint(), "contractId", contractId);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
	Gson gson = new Gson();
	String jsonResult = this.doEdgeGridAPIRequestPOST(currentApiPapiEndpoint, requestJson);
	return gson.fromJson(jsonResult, CreateNewCPCodeResultV0.class);
}

public ProductsResult doListProducts(){
	SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
	SearchPropertyVersionsBySingleValueResponseItemV0  currentPAPI = psr.getVersions().getItems().get(0);
	return doListProducts(currentPAPI.getContractId());
}

public ProductsResult doListProducts(String contractId){
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiPapiListProductsEndpoint(), "contractId", contractId);
	String jsonResult = this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
	Gson gson = new Gson();
	return gson.fromJson(jsonResult, ProductsResult.class);
}

private String getEdgeAuthFromPapiRuleSet(String papiRuleSetJson) {
	String papiRuleSetJsonNew = papiRuleSetJson.replaceAll("\\r?\\n?\\s", "");
	OpenAPI.LOGGER.debug("papiRuleSetJson:" + papiRuleSetJsonNew);
	String edgeauth = null;
	// String pattern =
	// "\\{\"name\":\"verifyTokenAuthorization\"[^\\}\\}]*";
	// String pattern = ".*verifyTokenAuthorization.*";
	String pattern = "verifyTokenAuthorization.*";

	Pattern r = Pattern.compile(pattern);
	Matcher m = r.matcher(papiRuleSetJsonNew);
	if (m.matches()) {
		OpenAPI.LOGGER.debug("m1.group().length():" + m.group().length());
		OpenAPI.LOGGER.debug("m1.group(1):" + m.group(0));
		edgeauth = m.group(0);
	}
	return edgeauth;
}

public String getEdgeAuthKeyFromPapiRuleSet_(String papiRuleSetJson) {
	String edgeauthKey = null;
	String pattern = "^.*key\".:.\"(.*)\",.*failureResponse.*";
	// String pattern = "^.*key\".:.\"(.*)\",.*";
	Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
	Matcher m = r.matcher(papiRuleSetJson);
	if (m.matches()) {
		OpenAPI.LOGGER.debug("m.group().length():" + m.group().length());
		OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));
		edgeauthKey = m.group(1);
	}
	return edgeauthKey;

}
public String getEdgeAuthKeyFromPapiRuleSet(String papiRuleSetJson) {
	
	String edgeauthKey = null;
	//Try to find inside Advanced Section
	try{
		String pattern = "<key>(.+?)</key>";
		Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = r.matcher(papiRuleSetJson);
		m.find();
		edgeauthKey = m.group(1);
		OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));			
	}catch(java.lang.IllegalStateException e){
		//Try to find inside JSON
		String pattern = ".*\"key\".:.\"(.+?)\",.*";
		Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = r.matcher(papiRuleSetJson);
		m.find();
		edgeauthKey = m.group(1);
		OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));
	}

	return edgeauthKey;
}

public String getEdgeAuthLocationFromPapiRuleSet(String papiRuleSetJson) {
	String edgeauthKey = null;
	String pattern = "^.*locationId\".:.\"(.*)\",.*key.*";
	Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
	Matcher m = r.matcher(papiRuleSetJson);
	if (m.matches()) {
		OpenAPI.LOGGER.debug("m.group().length():" + m.group().length());
		OpenAPI.LOGGER.debug("m.group(1):" + m.group(1));
		edgeauthKey = m.group(1);
	}
	return edgeauthKey;
}

}
