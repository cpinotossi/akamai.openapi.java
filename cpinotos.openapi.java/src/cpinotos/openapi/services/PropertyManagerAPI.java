package cpinotos.openapi.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.gson.Gson;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.CreateNewCPCodeResultV0;
import cpinotos.openapi.services.data.ListCPCodeResult;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.ProductIoT;
import cpinotos.openapi.services.data.ProductsResult;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseItemV0;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseV0;
import cpinotos.openapi.services.data.TranslatedError;
import cpinotos.openapi.services.data.UrlDebug;


public class PropertyManagerAPI {
private OpenAPI openAPI;

	public PropertyManagerAPI(OpenAPI openAPI){
		this.openAPI=openAPI;
}

public ListCPCodeResult doListCPCodes(){
	SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
	return doListCPCodes(psr.getVersions().getItems().get(0).getContractId(), psr.getVersions().getItems().get(0).getGroupId());
}
	
public ListCPCodeResult doListCPCodes(String contractId, String groupId){
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiPapiListCPCodesEndpoint(), "contractId", contractId);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
	Gson gson = new Gson();
	String jsonResult = this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint);
	return gson.fromJson(jsonResult, ListCPCodeResult.class);
}

public UrlDebug urlDebug(String url, String edgeIP){
	//TODO Maybe we should allow different search parameters
	String apiQuery = String.format("?url=%s&edgeIp=%s",url,edgeIP); 
	return doUrlDebug(apiQuery);
}
public UrlDebug urlDebug(String url, String edgeIP, ArrayList<String> headerList){
	String apiQuery = String.format("?url=%s&edgeIp=%s",url,edgeIP); 
	Iterator<String> i = headerList.iterator();
	while(i.hasNext()){
		apiQuery.concat("&header="+i.next());
	}
	return doUrlDebug(apiQuery);
}

public UrlDebug doUrlDebug(String apiQuery){
	UrlDebug urlDebugResult = null;
	String apiPath = this.openAPI.getApiDiagnosticToolsUrlDebugEndpoint()+apiQuery;
	// Use com.google.api.client.http Helper for HTTP Request
	HttpTransport httpTransport = new ApacheHttpTransport();
	HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	URI uri = null;
	HttpRequest request = null;
	try {
		uri = new URI("https://" + openAPI.getApiHost() + apiPath);		
		// Ensure to use Content-Type application/json
		request = requestFactory.buildGetRequest(new GenericUrl(uri));
		request.setReadTimeout(0);
		// Create a new EdgeGrid Signer Object
		GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
				openAPI.getApiCredential());
		// Sign the request
		requestSigner.sign(request);
		// send the request to the OPEN API Interface via HTTP POST
		HttpResponse response = request.execute();
		String diagnosticToolsUrlDebugResultJson = response.parseAsString();
		Gson gson = new Gson();
		urlDebugResult = gson.fromJson(diagnosticToolsUrlDebugResultJson, UrlDebug.class);
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RequestSigningException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return urlDebugResult;
}

//TODO move over to DiagnosticTools Class
public TranslatedError doTranslateError(String errorString){
	String currentApiPapiEndpoint = this.openAPI.getApiDiagnosticToolsTranslatedErrorEndpoint();
	currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{errorCode}", errorString);
	Gson gson = new Gson();
	return gson.fromJson(this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint), TranslatedError.class);
}

public LogLines doGetLogLinesFromIP(String ipAddress, String endTime, String arl, String clientIp, String cpCode, String duration, String hostHeader, String httpStatusCode, String logType, String maxLogLines, String objStatus, String requestId, String userAgent){
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiDiagnosticToolsGetLogLinesFromIPEndpoint(), "ipAddress", ipAddress);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "endTime", endTime);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "arl", arl);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "clientIp", clientIp);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "cpCode", cpCode);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "duration", duration);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "hostHeader", hostHeader);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "httpStatusCode", httpStatusCode);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "logType", logType);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "maxLogLines", maxLogLines);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "objStatus", objStatus);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "requestId", requestId);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "userAgent", userAgent);
	Gson gson = new Gson();
	return gson.fromJson(this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint), LogLines.class);
}

/*
 * No longer needed
public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration(String host, String searchJSON) {
	String apiRequestUrl = this.openAPI.getApiPapiSearchEndpoint();
	String jsonResult = this.openAPI.doEdgeGridAPIRequestPOST(apiRequestUrl, searchJSON);	
	Gson gson = new Gson();
	return gson.fromJson(jsonResult, SearchPropertyVersionsBySingleValueResponseV0.class);
}
*/

public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration(String host) {
	String searchJSON = "{\"hostname\":\"" + host + "\"}";
	String apiRequestUrl = this.openAPI.getApiPapiSearchEndpoint();
	String jsonResult = this.openAPI.doEdgeGridAPIRequestPOST(apiRequestUrl, searchJSON);	
	Gson gson = new Gson();
	return gson.fromJson(jsonResult, SearchPropertyVersionsBySingleValueResponseV0.class);
}

public SearchPropertyVersionsBySingleValueResponseV0 searchPAPIConfiguration() {
	return searchPAPIConfiguration(this.openAPI.getHost());
}

public String getPAPIRuletreeAsJSON(String propertyId, Integer propertyVersion, String contractId, String groupId,
		boolean validateRules) {	
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiPapiGetRuletreeEndpoint(), "propertyVersion", propertyVersion.toString());
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "validateRules", Boolean.toString(validateRules));
		return this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint);
}

public ProductIoT getPAPIRuletree(String propertyId, Integer propertyVersion, String contractId, String groupId,
		boolean validateRules) {	
		String jsonResult;
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiPapiGetRuletreeEndpoint(), "propertyVersion", propertyVersion.toString());
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "validateRules", Boolean.toString(validateRules));
		jsonResult = this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint);
		Gson gson = new Gson();
		return gson.fromJson(jsonResult, ProductIoT.class);
		
}

public String getPAPIConfiguration(String propertyId, String contractId, String groupId) {
		String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiPapiGetEndpoint(), "propertyId", propertyId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "contractId", contractId);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
		return this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint);
}

public CreateNewCPCodeResultV0 doCreateCPCodes(String cpCodeName, String productId) {
	SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
	SearchPropertyVersionsBySingleValueResponseItemV0  currentPAPI = psr.getVersions().getItems().get(0);
	return doCreateCPCodes(currentPAPI.getContractId(), currentPAPI.getGroupId(), cpCodeName, productId );
}

public CreateNewCPCodeResultV0 doCreateCPCodes(String contractId, String groupId, String cpCodeName, String productId) {
	String requestJson = String.format("{\"cpcodeName\": \"%s\",\"productId\": \"%s\"}", cpCodeName, productId);
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiPapiCreateCPCodesEndpoint(), "contractId", contractId);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "groupId", groupId);
	Gson gson = new Gson();
	String jsonResult = this.openAPI.doEdgeGridAPIRequestPOST(currentApiPapiEndpoint, requestJson);
	return gson.fromJson(jsonResult, CreateNewCPCodeResultV0.class);
}

public ProductsResult doListProducts(){
	SearchPropertyVersionsBySingleValueResponseV0 psr = searchPAPIConfiguration();
	SearchPropertyVersionsBySingleValueResponseItemV0  currentPAPI = psr.getVersions().getItems().get(0);
	return doListProducts(currentPAPI.getContractId());
}

public ProductsResult doListProducts(String contractId){
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiPapiListProductsEndpoint(), "contractId", contractId);
	String jsonResult = this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint);
	Gson gson = new Gson();
	return gson.fromJson(jsonResult, ProductsResult.class);
}
}
