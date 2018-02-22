package cpinotos.openapi.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.TranslatedError;
import cpinotos.openapi.services.data.UrlDebug;


public class DiagnosticToolsAPI extends OpenAPI {

public DiagnosticToolsAPI(String hostname, String edgercFilePath, String apiClientNameSection, boolean debug){
	super(hostname, edgercFilePath, debug);
	this.setApiClientName(apiClientNameSection);
	initApiCredentials();
}

public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException{
	//Expected IP 23.50.55.45
	System.out.println("2d373217");	
	String ip = getGHostIPfromDebugString("2d373217");
	System.out.println(ip);	
	String hex = getDebugStringforGHostIP(ip);
	System.out.println(hex);
	
}


public static String getGHostIPfromDebugString(String hexStringIp)
{

	//Convert hex string to int
	long intIP = Long.parseLong(hexStringIp, 16);
	System.out.println(intIP);
	//Convert int to InetAddress	
	String ipStr = String.format("%d.%d.%d.%d",
			         (intIP & 0xff),   
			         (intIP >> 8 & 0xff),             
			         (intIP >> 16 & 0xff),    
			         (intIP >> 24 & 0xff));
	return ipStr;
}

public static String getDebugStringforGHostIP(String reqIpAddr){
	String hex = "";
	String[] part = reqIpAddr.split("[\\.,]");
	if (part.length < 4) {
		return "00000000";
	}
	for (int i = 3; i >= 0; i--) {
		int decimal = Integer.parseInt(part[i]);
		if (decimal < 16) // Append a 0 to maintian 2 digits for every
							// number
		{
			hex += "0" + String.format("%01X", decimal);
		} else {
			hex += String.format("%01X", decimal);
		}
	}
	return hex;
}

public UrlDebug doUrlDebug(String url, String edgeip, ArrayList<String> header) throws ClientProtocolException, UnsupportedOperationException, IOException{
	UrlDebug urlDebugResult = null;
	String apiPapiEndpointPath = OpenAPI.addValueToAPIEndPointURL(this.getApiDiagnosticToolsUrlDebugEndpoint(), "url", url);
	apiPapiEndpointPath = OpenAPI.addValueToAPIEndPointURL(apiPapiEndpointPath, "edgeIp", edgeip);
	//TODO need to transform Array into string
	apiPapiEndpointPath = OpenAPI.addValueToAPIEndPointURL(apiPapiEndpointPath, "header", null);
	String jsonResult = this.doEdgeGridAPIRequest(apiPapiEndpointPath);
		Gson gson = new Gson();
		urlDebugResult = gson.fromJson(jsonResult, UrlDebug.class);
	return urlDebugResult;
}

public UrlDebug doUrlDebugAsynchronously(String url, String edgeip, ArrayList<String> header) throws ClientProtocolException, UnsupportedOperationException, IOException, InterruptedException{
	UrlDebug urlDebugResult = null;
	String apiPapiEndpointPath = this.getApiDiagnosticToolsUrlDebugEndpoint();
	//TODO Create Json from input parameters
	String jsonRequest = "{\"url\": \""+url+"\"}";
	String jsonResult = this.doEdgeGridAPIRequestPOSTAsynchronously(apiPapiEndpointPath, jsonRequest);
		Gson gson = new Gson();
		urlDebugResult = gson.fromJson(jsonResult, UrlDebug.class);
	return urlDebugResult;
}


public TranslatedError doTranslateError(String errorString) throws JsonSyntaxException, ClientProtocolException, UnsupportedOperationException, IOException{
	String currentApiPapiEndpoint = this.getApiDiagnosticToolsTranslatedErrorEndpoint();
	currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{errorCode}", errorString);
	Gson gson = new Gson();
	return gson.fromJson(this.doEdgeGridAPIRequest(currentApiPapiEndpoint), TranslatedError.class);
}



public LogLines doGetLogLinesFromIP(String ipAddress, String endTime, String arl, String clientIp, String cpCode, String duration, String hostHeader, String httpStatusCode, String logType, String maxLogLines, String objStatus, String requestId, String userAgent) throws JsonSyntaxException, ClientProtocolException, UnsupportedOperationException, IOException{
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.getApiDiagnosticToolsGetLogLinesFromIPEndpoint(), "ipAddress", ipAddress);
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
	return gson.fromJson(this.doEdgeGridAPIRequest(currentApiPapiEndpoint), LogLines.class);
}



}
