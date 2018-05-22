package cpinotos.openapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akamai.edgegrid.signer.ClientCredential;
import com.akamai.edgegrid.signer.apachehttpclient.ApacheHttpClientEdgeGridInterceptor;
import com.akamai.edgegrid.signer.apachehttpclient.ApacheHttpClientEdgeGridRoutePlanner;
import com.akamai.netstorage.DefaultCredential;
import com.google.gson.Gson;

import cpinotos.openapi.services.data.PollingResponse;
import lombok.Getter;
import lombok.Setter;

/*
 * TODO split into smaller pieces/classes. Single Responsibility Pattern.
 * TODO Use more interfaces
 * TODO Zip and Unzip for large Netstorage Uploads
 * TODO Async Purge 
 * TODO reactive streams, akka framework https://www.youtube.com/watch?v=qaiwalDyayA&t=2132s
 */
public class OpenAPI {
	// Instantiate an instance of slf4j logger
	// public Logger logger = null;

	// public static org.apache.log4j.Logger logger;
	public static Logger LOGGER = LoggerFactory.getLogger(OpenAPI.class);

	// Define all needed attributes
	@Getter
	@Setter
	private String netstorageClient, netstorageKey, netstorageHost, purgeClientSecret, purgeHost, purgeAccessToken,
			purgeClientToken, apiClientSecret, apiHost, apiAccessToken, apiClientToken;

	@Getter @Setter private String NetstorageCpcode;
	@Getter
	@Setter
	private String hostname;
	@Getter
	@Setter
	private String edgercFileName = ".edgerc";
	@Getter
	@Setter
	private String edgercFilePath = "";
	@Getter
	private String apiClientName;
	@Getter
	private String apiClientNamePurge;
	@Getter
	private String apiClientNameNetStorage;
	@Getter
	@Setter
	private String apiClientNameDefault;
	@Getter
	@Setter
	private String apiClientNamePurgeDefault;
	@Getter
	@Setter
	private String apiClientNameNetStorageDefault;
	@Getter
	@Setter
	private String apiPurgeInvalidateEndpoint;
	@Getter
	@Setter
	private String apiPurgeInvalidateCPCodeEndpoint;
	@Getter
	@Setter
	private String apiPurgeInvalidateTagEndpoint;
	@Getter
	@Setter
	private String apiPapiSearchEndpoint;
	@Getter
	@Setter
	private String apiPapiGetEndpoint;
	@Getter
	@Setter
	private String apiDiagnosticToolsUrlDebugEndpoint;
	@Getter
	@Setter
	private String apiPapiGetRuletreeEndpoint;
	@Getter
	@Setter
	private String apiDiagnosticToolsTranslatedErrorEndpoint;
	@Getter
	@Setter
	private String apiDiagnosticToolsGetLogLinesFromIPEndpoint;
	@Getter
	@Setter
	private String apiPapiListCPCodesEndpoint;
	@Getter
	@Setter
	private String apiPapiCreateCPCodesEndpoint;
	@Getter
	@Setter
	private String apiPapiListProductsEndpoint;
	@Getter @Setter private String apiOverTheAirDownloadNotificationsEndpoint;
	@Getter @Setter private Wini edgerc;
	@Getter @Setter private Wini appConfigProperties;

	// Credential objects needed for the different Akamai OPEN API endpoints
	@Getter @Setter private ClientCredential purgeCredential;
	@Getter @Setter private ClientCredential apiCredential;
	@Getter @Setter private DefaultCredential netstorageCredential;

	public OpenAPI() {
		InputStream is = OpenAPI.class.getResourceAsStream("/openapi.properties");
		try {
			this.setAppConfigProperties(new Wini(is));
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setApiClientNameDefault(this.getAppConfigProperties().get("client-names", "api-client"));
		this.setApiClientNamePurgeDefault(this.getAppConfigProperties().get("client-names", "api-client-purge"));

		this.setApiPurgeInvalidateEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiPurgeInvalidateEndpoint"));
		this.setApiPurgeInvalidateCPCodeEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiPurgeInvalidateCPCodeEndpoint"));
		this.setApiPapiSearchEndpoint(this.getAppConfigProperties().get("api-endpoints", "apiPapiSearchEndpoint"));
		this.setApiPapiGetEndpoint(this.getAppConfigProperties().get("api-endpoints", "apiPapiGetEndpoint"));
		this.setApiPapiGetRuletreeEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiPapiGetRuletreeEndpoint"));
		this.setApiDiagnosticToolsUrlDebugEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiDiagnosticToolsUrlDebugEndpoint"));
		this.setApiDiagnosticToolsTranslatedErrorEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiDiagnosticToolsTranslatedErrorEndpoint"));
		this.setApiDiagnosticToolsGetLogLinesFromIPEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiDiagnosticToolsGetLogLinesFromIPEndpoint"));
		this.setApiPapiListCPCodesEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiPapiListCPCodesEndpoint"));
		this.setApiPapiCreateCPCodesEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiPapiCreateCPCodesEndpoint"));
		this.setApiPapiListProductsEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiPapiListProductsEndpoint"));
		this.setApiOverTheAirDownloadNotificationsEndpoint(
				this.getAppConfigProperties().get("api-endpoints", "apiOverTheAirDownloadNotificationsEndpoint"));
	}

	public OpenAPI(String hostname) {
		this();
		this.setHostname(hostname);
		OpenAPI.LOGGER.debug("Host: " + this.getHostname());
	}

	public OpenAPI(String hostname, String edgercFilePath) {
		this(hostname);
		this.initEdgercFile(edgercFilePath);
	}

	public OpenAPI(String hostname, String edgercFilePath, boolean debug) {
		this(hostname, edgercFilePath);
		// If a boolean is not assigned a value (say a member of a class) then
		// it will be false by default.
		// Turn debug on or off
	}

	protected void initApiCredentials() {
		// By default we take the general API Client name
		this.initApiCredentials(this.getApiClientName());
	}
	protected void initApiCredentialsNetStorage() {
		// By default we take the general API Client name
		this.initApiCredentialsNetStorage(this.getApiClientName());
	}
	protected void initApiCredentialsNetStorage(String apiClientName) {
		this.initApiCredentials(apiClientName);
		if (getEdgerc().get(apiClientName, "ns_client") == null || getEdgerc().get(apiClientName, "ns_host") == null
				|| getEdgerc().get(apiClientName, "ns_key") == null) {
			OpenAPI.LOGGER.info(
					"Netstorage Credentials ns_client, ns_host, ns_key are missing under the edgerc section "+apiClientName);
			System.exit(1);
		} else {
			// Set Netstorage
			this.setNetstorageClient(getEdgerc().get(apiClientName, "ns_client"));
			OpenAPI.LOGGER.debug("Netstorage Client: " + this.getNetstorageClient());
			this.setNetstorageHost(getEdgerc().get(apiClientName, "ns_host"));
			OpenAPI.LOGGER.debug("Netstorage Host: " + this.getNetstorageHost());
			this.setNetstorageKey(getEdgerc().get(apiClientName, "ns_key"));
			OpenAPI.LOGGER.debug("Netstorage Key: " + this.getNetstorageKey());
			this.setNetstorageCpcode(getEdgerc().get(apiClientName, "ns_cpcode"));
			OpenAPI.LOGGER.debug("CPCode Key: " + this.getNetstorageCpcode());
			this.setNetstorageCredential(new DefaultCredential(this.getNetstorageHost(), this.getNetstorageClient(),
					this.getNetstorageKey()));
		}
	}

	protected void initApiCredentials(String apiClientName) {
		if (!getEdgerc().containsKey(apiClientName)) {
			OpenAPI.LOGGER.info("Credentials section is missing: " + apiClientName);
			System.exit(1);
		} else {
			// Set API Client
			this.setApiClientSecret(getEdgerc().get(apiClientName, "client_secret"));
			OpenAPI.LOGGER.debug("API Client Secret: " + this.getApiClientSecret());
			this.setApiHost(getEdgerc().get(apiClientName, "host"));
			OpenAPI.LOGGER.debug("API Host: " + this.getApiHost());
			this.setApiAccessToken(getEdgerc().get(apiClientName, "access_token"));
			OpenAPI.LOGGER.debug("API Access Token: " + this.getApiAccessToken());
			this.setApiClientToken(getEdgerc().get(apiClientName, "client_token"));
			OpenAPI.LOGGER.debug("API Client Token: " + this.getApiClientToken());

			this.setApiCredential(ClientCredential.builder().accessToken(this.getApiAccessToken())
					.clientSecret(this.getApiClientSecret()).clientToken(this.getApiClientToken())
					.host(this.getApiHost()).build());
		}
	}

	protected void initApiCredentialsPurge() {
		if (!getEdgerc().containsKey(this.getApiClientNamePurge())) {
			OpenAPI.LOGGER.info("Credentials section is missing: " + this.getApiClientNamePurge());
			System.exit(1);
		} else {
			this.setPurgeClientSecret(getEdgerc().get(this.getApiClientNamePurge(), "client_secret"));
			OpenAPI.LOGGER.debug("Purge Client Secret: " + this.getPurgeClientSecret());
			this.setPurgeHost(getEdgerc().get(this.getApiClientNamePurge(), "host"));
			OpenAPI.LOGGER.debug("Purge Client Host: " + this.getPurgeHost());
			this.setPurgeAccessToken(getEdgerc().get(this.getApiClientNamePurge(), "access_token"));
			OpenAPI.LOGGER.debug("Purge Access Token: " + this.getPurgeAccessToken());
			this.setPurgeClientToken(getEdgerc().get(this.getApiClientNamePurge(), "client_token"));
			OpenAPI.LOGGER.debug("Purge Client Token: " + this.getPurgeClientToken());

		}
		this.setPurgeCredential(ClientCredential.builder().accessToken(this.getPurgeAccessToken())
				.clientSecret(this.getPurgeClientSecret()).clientToken(this.getPurgeClientToken())
				.host(this.getPurgeHost()).build());
	}

	protected void initEdgercFile(String edgercFilePath) {
		if (edgercFilePath != null) {
			// Set the path to the edgerc file provided by the user
			this.setEdgercFilePath(edgercFilePath);
		} else {
			// Set default .edgerc path if no edgerc file path has been provided
			// by the user.
			this.setEdgercFilePath(System.getProperty("user.home") + "/" + getEdgercFileName());
		}
		OpenAPI.LOGGER.debug("edgercFilePath: " + this.getEdgercFileName());
		try {
			setEdgerc(new Wini(new File(getEdgercFilePath())));
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String jsonFileReader(String path) throws IOException {
		String content = null;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			content = new String(encoded, StandardCharsets.UTF_8);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return content;
	}

	public InputStream fileReader(String path) throws Exception {
		File file = new File(path);
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		OpenAPI.LOGGER.debug("filezise(" + path + "):" + fis.available());
		return fis;
	}

	public static String inputStreamReader(InputStream is) throws IOException {
		String response = null;
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}
		// StandardCharsets.UTF_8.name() > JDK 7
		try {
			response = buf.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static String xml2json(String xml) {
		String json = null;
		try {
			int PRETTY_PRINT_INDENT_FACTOR = 4;
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
			json = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		return json;
	}


	public String doEdgeGridAPIRequest(String apiRequestUrl)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		String jsonResult = null;
		HttpClient client = null;
		ApacheHttpClientEdgeGridInterceptor interceptor = new ApacheHttpClientEdgeGridInterceptor(
				this.getApiCredential());
		ApacheHttpClientEdgeGridRoutePlanner planner = new ApacheHttpClientEdgeGridRoutePlanner(
				this.getApiCredential());
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.addInterceptorFirst(interceptor);
		clientBuilder.setRoutePlanner((HttpRoutePlanner) planner);
		client = clientBuilder.build();
		HttpGet request = new HttpGet("https://" + this.getApiHost() + apiRequestUrl);
		jsonResult = executeHTTPResponseBody(client, request);
		// TODO Need to identify Errors and throw an exception
		return jsonResult;
	}

	public String doEdgeGridAPIRequestPOST(String apiRequestUrl, String json)
			throws ClientProtocolException, UnsupportedOperationException, IOException {
		String jsonResult = null;
		HttpClient client = null;
		ApacheHttpClientEdgeGridInterceptor interceptor = new ApacheHttpClientEdgeGridInterceptor(
				this.getApiCredential());
		ApacheHttpClientEdgeGridRoutePlanner planner = new ApacheHttpClientEdgeGridRoutePlanner(
				this.getApiCredential());
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.addInterceptorFirst(interceptor);
		clientBuilder.setRoutePlanner((HttpRoutePlanner) planner);
		client = clientBuilder.build();

		HttpPost request = new HttpPost("https://" + this.getApiHost() + apiRequestUrl);
		StringEntity entity = new StringEntity(json);
		entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		request.setEntity(entity);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		jsonResult = executeHTTPResponseBody(client, request);
		return jsonResult;
	}


	// This methode should only be called after we started a poll with
	// doEdgeGridAPIRequestPOSTAsynchronously
	public String doEdgeGridAPIRequestAsynchronously(String apiRequestUrl)
			throws ClientProtocolException, UnsupportedOperationException, IOException, InterruptedException {
		String jsonResult = null;
		//Build HttpClient
		HttpClient client = null;
		ApacheHttpClientEdgeGridInterceptor interceptor = new ApacheHttpClientEdgeGridInterceptor(
				this.getApiCredential());
		ApacheHttpClientEdgeGridRoutePlanner planner = new ApacheHttpClientEdgeGridRoutePlanner(
				this.getApiCredential());
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.addInterceptorFirst(interceptor);
		clientBuilder.setRoutePlanner((HttpRoutePlanner) planner);
		client = clientBuilder.build();

		HttpGet request = new HttpGet("https://" + this.getApiHost() + apiRequestUrl);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		jsonResult = executeHTTPResponseBodyAsynchronously(apiRequestUrl);
		return jsonResult;
	}

	public String doEdgeGridAPIRequestPOSTAsynchronously(String apiRequestUrl, String json)
			throws ClientProtocolException, UnsupportedOperationException, IOException, InterruptedException {
		String jsonResult = null;
		
		// Build HttpClient
		HttpClient client = null;
		ApacheHttpClientEdgeGridInterceptor interceptor = new ApacheHttpClientEdgeGridInterceptor(
				this.getApiCredential());
		ApacheHttpClientEdgeGridRoutePlanner planner = new ApacheHttpClientEdgeGridRoutePlanner(
				this.getApiCredential());
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.addInterceptorFirst(interceptor);
		clientBuilder.setRoutePlanner((HttpRoutePlanner) planner);
		client = clientBuilder.build();
		
		// Build HttpPost
		HttpPost request = new HttpPost("https://" + this.getApiHost() + apiRequestUrl);
		StringEntity entity = new StringEntity(json);
		entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		request.setEntity(entity);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		
		// Execute Request
		org.apache.http.HttpResponse response = client.execute(request);
		
		// Analyse Response
		if (response != null) {
			// Read response body.
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			// Result should be in Json format.
			jsonResult = result.toString();
			
			// Verify the response Code
			// 202 indicates that we will need to check after certain seconds
			if (response.getStatusLine().getStatusCode() == 202) {
				Gson gson = new Gson();
				PollingResponse pResponse = gson.fromJson(jsonResult, PollingResponse.class);
				jsonResult = executeHTTPResponseBodyAsynchronously(pResponse.getLink());
			// 303 indicates that we will redirected to the final result.	
			} else if (response.getStatusLine().getStatusCode() == 200) {
				Gson gson = new Gson();
				PollingResponse pResponse = gson.fromJson(jsonResult, PollingResponse.class);
				jsonResult = doEdgeGridAPIRequest(doEdgeGridAPIRequest(pResponse.getLink()));
			} else if (response.getStatusLine().getStatusCode() == 303) {
				Gson gson = new Gson();
				PollingResponse pResponse = gson.fromJson(jsonResult, PollingResponse.class);
				jsonResult = doEdgeGridAPIRequest(doEdgeGridAPIRequest(pResponse.getLink()));
			} else if (response.getStatusLine().getStatusCode() == 403) {
				OpenAPI.LOGGER.info(jsonResult);
				System.exit(1);
			}
		}
		return jsonResult;
	}

	//protected String executeHTTPResponseBodyAsynchronously(HttpClient client, HttpRequestBase request)
	protected String executeHTTPResponseBodyAsynchronously(String apiRequestUrl)
			throws IOException, ClientProtocolException, UnsupportedOperationException, InterruptedException {
		String jsonResult = null;
		// Build HttpClient
		HttpClient client = null;
		ApacheHttpClientEdgeGridInterceptor interceptor = new ApacheHttpClientEdgeGridInterceptor(
				this.getApiCredential());
		ApacheHttpClientEdgeGridRoutePlanner planner = new ApacheHttpClientEdgeGridRoutePlanner(
				this.getApiCredential());
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.addInterceptorFirst(interceptor);
		clientBuilder.setRoutePlanner((HttpRoutePlanner) planner);
		client = clientBuilder.build();
		org.apache.http.HttpResponse response;
		
		// Build HttpGet 
		HttpGet request = new HttpGet("https://" + this.getApiHost() + apiRequestUrl);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		
		// Execute Request
		response = client.execute(request);
		
		// Analyse Response
		if (response != null) {
			InputStream in = response.getEntity().getContent(); // Get the data
																// in the entity
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			jsonResult = result.toString();
			if (response.getStatusLine().getStatusCode() == 202) {
				Gson gson = new Gson();
				PollingResponse pResponse = gson.fromJson(jsonResult, PollingResponse.class);
				Thread.sleep(pResponse.getRetryAfter() * 1000);
				jsonResult = executeHTTPResponseBodyAsynchronously(pResponse.getLink());
				return jsonResult;
			} else if (response.getStatusLine().getStatusCode() == 200) {
				Gson gson = new Gson();
				PollingResponse pResponse = gson.fromJson(jsonResult, PollingResponse.class);
				jsonResult = doEdgeGridAPIRequest(doEdgeGridAPIRequest(pResponse.getLink()));
			} else if (response.getStatusLine().getStatusCode() == 303) {
				Gson gson = new Gson();
				PollingResponse pResponse = gson.fromJson(jsonResult, PollingResponse.class);
				jsonResult = doEdgeGridAPIRequest(doEdgeGridAPIRequest(pResponse.getLink()));
			} else if (response.getStatusLine().getStatusCode() == 403) {
				OpenAPI.LOGGER.info(jsonResult);
				System.exit(1);
			}

		}
		// TODO we need to throw an execption if the response is empty
		return jsonResult;
	}

	protected String executeHTTPResponseBody(HttpClient client, HttpRequestBase request)
			throws IOException, ClientProtocolException, UnsupportedOperationException {
		String jsonResult = null;
		org.apache.http.HttpResponse response;
		response = client.execute(request);
		if (response != null) {
			InputStream in = response.getEntity().getContent(); // Get the data
																// in the entity
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			jsonResult = result.toString();
		}
		// TODO we need to throw an execption if the response is empty
		return jsonResult;
	}

	public static String addValueToAPIEndPointURL(String apiEndPointURL, String key, String value) {
		if (value == null || value.isEmpty()) {
			apiEndPointURL = apiEndPointURL.replace("/{" + key + "}", "/");
			apiEndPointURL = apiEndPointURL.replace("&{" + key + "}", "");
		} else {
			apiEndPointURL = apiEndPointURL.replace("/{" + key + "}", "/" + value);
			apiEndPointURL = apiEndPointURL.replace("?&{" + key + "}", "?" + key + "=" + value);
			apiEndPointURL = apiEndPointURL.replace("&{" + key + "}", "&" + key + "=" + value);
		}
		return apiEndPointURL;
	}

	protected void setApiClientName(String s) {
		if (isNullOrBlank(s)) {
			this.apiClientName = this.getApiClientNameDefault();
		} else {
			this.apiClientName = s;
		}
		OpenAPI.LOGGER.debug("apiClientName: " + this.getApiClientName());
	}

	protected void setApiClientNamePurge(String s) {
		if (isNullOrBlank(s)) {
			this.apiClientNamePurge = this.getApiClientNamePurgeDefault();
		} else {
			this.apiClientNamePurge = s;
		}
		OpenAPI.LOGGER.debug("apiClientPurgeName: " + this.getApiClientNamePurge());
	}

	protected void setApiClientNameNetStorage(String s) {
		if (isNullOrBlank(s)) {
			this.apiClientNameNetStorage = this.getApiClientNameNetStorageDefault();
		} else {
			this.apiClientNameNetStorage = s;
		}
		OpenAPI.LOGGER.debug("apiUploadAccountName: " + this.getApiClientNameNetStorage());
	}

	private static boolean isNullOrBlank(String s) {
		return (s == null || s.trim().equals(""));
	}

}