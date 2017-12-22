package cpinotos.openapi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akamai.edgegrid.signer.ClientCredential;
import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.akamai.netstorage.DefaultCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;

import io.github.astinchoi.authtoken.AuthToken;
import io.github.astinchoi.authtoken.AuthTokenBuilder;
import io.github.astinchoi.authtoken.AuthTokenException;
import lombok.Getter;
import lombok.Setter;

/*
 * TODO split into smaller pieces/classes. Single Responsibility Pattern.
 * TODO Use more interfaces
 * TODO Zip and Unzip for large Netstorage Uploads
 * TODO reactive streams, akka framework https://www.youtube.com/watch?v=qaiwalDyayA&t=2132s
 */
public class OpenAPI {
	// Instantiate an instance of slf4j logger
	//public Logger logger = null;

	//public static org.apache.log4j.Logger logger;
	public static Logger LOGGER = LoggerFactory.getLogger(OpenAPI.class);	

	// Define all needed attributes
	@Getter @Setter private String netstorageClient, netstorageKey, netstorageHost, purgeClientSecret, purgeHost,
			purgeAccessToken, purgeClientToken, apiClientSecret, apiHost, apiAccessToken,
			apiClientToken;
	
	@Getter @Setter private String hostname;
	@Getter @Setter private String edgercFileName = ".edgerc";
	@Getter @Setter private String edgercFilePath = "";
	@Getter private String apiClientName;
	@Getter private String apiClientNamePurge;
	@Getter private String apiClientNameNetStorage;
	@Getter @Setter private String apiClientNameDefault;
	@Getter @Setter private String apiClientNamePurgeDefault;
	@Getter @Setter private String apiClientNameNetStorageDefault;
	@Getter @Setter private String apiPurgeInvalidateEndpoint;
	@Getter @Setter private String apiPurgeInvalidateCPCodeEndpoint;
	@Getter @Setter private String apiPurgeInvalidateTagEndpoint;
	@Getter @Setter private String apiPapiSearchEndpoint;
	@Getter @Setter private String apiPapiGetEndpoint;
	@Getter @Setter private String apiDiagnosticToolsUrlDebugEndpoint;
	@Getter @Setter private String apiPapiGetRuletreeEndpoint;
	@Getter @Setter private String apiDiagnosticToolsTranslatedErrorEndpoint;
	@Getter @Setter private String apiDiagnosticToolsGetLogLinesFromIPEndpoint;
	@Getter @Setter private String apiPapiListCPCodesEndpoint;
	@Getter @Setter private String apiPapiCreateCPCodesEndpoint;
	@Getter @Setter private String apiPapiListProductsEndpoint;
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

				
		this.setApiClientNameDefault(this.getAppConfigProperties().get("client-names","api-client"));
		this.setApiClientNamePurgeDefault(this.getAppConfigProperties().get("client-names","api-client-purge"));
		this.setApiClientNameNetStorageDefault(this.getAppConfigProperties().get("client-names","api-client-netstorage"));
		
		this.setApiPurgeInvalidateEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPurgeInvalidateEndpoint"));			
		this.setApiPurgeInvalidateCPCodeEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPurgeInvalidateCPCodeEndpoint"));
		this.setApiPapiSearchEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPapiSearchEndpoint"));
		this.setApiPapiGetEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPapiGetEndpoint"));
		this.setApiPapiGetRuletreeEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPapiGetRuletreeEndpoint"));
		this.setApiDiagnosticToolsUrlDebugEndpoint(this.getAppConfigProperties().get("api-endpoints","apiDiagnosticToolsUrlDebugEndpoint"));
		this.setApiDiagnosticToolsTranslatedErrorEndpoint(this.getAppConfigProperties().get("api-endpoints","apiDiagnosticToolsTranslatedErrorEndpoint"));
		this.setApiDiagnosticToolsGetLogLinesFromIPEndpoint(this.getAppConfigProperties().get("api-endpoints","apiDiagnosticToolsGetLogLinesFromIPEndpoint"));
		this.setApiPapiListCPCodesEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPapiListCPCodesEndpoint"));
		this.setApiPapiCreateCPCodesEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPapiCreateCPCodesEndpoint"));
		this.setApiPapiListProductsEndpoint(this.getAppConfigProperties().get("api-endpoints","apiPapiListProductsEndpoint"));
	}
	
	public OpenAPI(String hostname) {
		this();
		this.setHostname(hostname);
		OpenAPI.LOGGER.debug("Host: " + this.getHostname());
	}
	
	public OpenAPI(String hostname, String edgercFilePath){
		this(hostname);
		this.initEdgercFile(edgercFilePath);			
	}

	public OpenAPI(String hostname, String edgercFilePath, boolean debug){
		this(hostname, edgercFilePath);
		//If a boolean is not assigned a value (say a member of a class) then it will be false by default.
		//Turn debug on or off
	}
	protected void initApiCredentialsPurge() {
		if(!getEdgerc().containsKey(this.getApiClientNamePurge())){
			OpenAPI.LOGGER.info("Credentials section api-client-purge is missing");
			System.exit(1);
		}else{
			this.setPurgeClientSecret(getEdgerc().get(this.getApiClientNamePurge(),"client_secret"));
			OpenAPI.LOGGER.debug("Purge Client Secret: " + this.getPurgeClientSecret());
			this.setPurgeHost(getEdgerc().get(this.getApiClientNamePurge(),"host"));
			OpenAPI.LOGGER.debug("Purge Client Host: " + this.getPurgeHost());
			this.setPurgeAccessToken(getEdgerc().get(this.getApiClientNamePurge(),"access_token"));
			OpenAPI.LOGGER.debug("Purge Access Token: " + this.getPurgeAccessToken());
			this.setPurgeClientToken(getEdgerc().get(this.getApiClientNamePurge(),"client_token"));
			OpenAPI.LOGGER.debug("Purge Client Token: " + this.getPurgeClientToken());
	
		}
		this.setPurgeCredential(ClientCredential.builder().accessToken(this.getPurgeAccessToken())
				.clientSecret(this.getPurgeClientSecret()).clientToken(this.getPurgeClientToken())
				.host(this.getPurgeHost()).build());
	}
	
	protected void initApiCredentialsNetStorage() {
		if(!getEdgerc().containsKey(this.getApiClientNameNetStorage())){
			OpenAPI.LOGGER.info("Credentials section api-client-netstorage is missing");
			System.exit(1);
		}else{
			this.setNetstorageClient(getEdgerc().get(getApiClientNameNetStorage(),"client"));
			OpenAPI.LOGGER.debug("Netstorage Client: " + this.getNetstorageClient());
			this.setNetstorageHost(getEdgerc().get(getApiClientNameNetStorage(),"host"));
			OpenAPI.LOGGER.debug("Netstorage Host: " + this.getNetstorageHost());
			this.setNetstorageKey(getEdgerc().get(getApiClientNameNetStorage(),"key"));
			OpenAPI.LOGGER.debug("Netstorage Key: " + this.getNetstorageKey());
		}
		
		this.setNetstorageCredential(new DefaultCredential(this.getNetstorageHost(), this.getNetstorageClient(),
				this.getNetstorageKey()));
	}
	
	protected void initApiCredentials() {
		if(!getEdgerc().containsKey(this.getApiClientName())){
			OpenAPI.LOGGER.info("Credentials section api-client is missing");
			System.exit(1);
		}else{
			this.setApiClientSecret(getEdgerc().get(this.getApiClientName(),"client_secret"));
			OpenAPI.LOGGER.debug("API Client Secret: " + this.getApiClientSecret());
			this.setApiHost(getEdgerc().get(this.getApiClientName(),"host"));
			OpenAPI.LOGGER.debug("API Host: " + this.getApiHost());
			this.setApiAccessToken(getEdgerc().get(this.getApiClientName(),"access_token"));
			OpenAPI.LOGGER.debug("API Access Token: " + this.getApiAccessToken());
			this.setApiClientToken(getEdgerc().get(this.getApiClientName(),"client_token"));
			OpenAPI.LOGGER.debug("API Client Token: " + this.getApiClientToken());				
		}
		this.setApiCredential(
				ClientCredential.builder().accessToken(this.getApiAccessToken()).clientSecret(this.getApiClientSecret())
						.clientToken(this.getApiClientToken()).host(this.getApiHost()).build());
	}
	
	protected void initEdgercFile(String edgercFilePath) {
		if(edgercFilePath != null){ 
			//Set the path to the edgerc file provided by the user
			this.setEdgercFilePath(edgercFilePath);
		}else{
			//Set default .edgerc path if no edgerc file path has been provided by the user.
			this.setEdgercFilePath(System.getProperty("user.home")+"/"+getEdgercFileName());
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
	

	public String getEdgeAuthToken(String path, String encrpytionKey, Integer duration, String tokenName, Integer startTime){
		String url = null;
		try {
			  AuthToken at = new AuthTokenBuilder()
			      .key(encrpytionKey)
			      .windowSeconds(duration)
			      .escapeEarly(false)
			      .startTime(startTime)
			      .build();
			  at.setTokenName("token");
			  String token = at.generateURLToken(path);
			  //TODO Find a better way to select the protocol
			  url = String.format("http://%s%s?%s=%s", this.getHostname(), path,at.getTokenName(), token);
			  // => Link or Request "url" /w Query string
			} catch (AuthTokenException e) {
			  e.printStackTrace();
			}
		return url;
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

	public InputStream fileReader(String path) throws Exception{
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

	private static Properties propertyFromFileReader(String propFileName) {
		Properties prop = new Properties();
		File file = new File(propFileName);
		InputStream input = null;

		try {

			input = new FileInputStream(file);
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	public String doEdgeGridAPIRequest(String apiRequestUrl) {
		String jsonResult = null;
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https://" + this.getApiHost() + apiRequestUrl);		
			// Ensure to use Content-Type application/json
			request = requestFactory.buildGetRequest(new GenericUrl(uri));
			request.setReadTimeout(0);
			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getApiCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			jsonResult = response.parseAsString();
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
		return jsonResult;
	}
	
	public String doEdgeGridAPIRequestPOST(String apiRequestUrl, String searchJSON) {
		String jsonResult = null;
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https://" + this.getApiHost() + apiRequestUrl);		
			// Ensure to use Content-Type application/json
			request = requestFactory.buildPostRequest(new GenericUrl(uri),
					ByteArrayContent.fromString("application/json", searchJSON));
			request.setReadTimeout(0);
			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getApiCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			jsonResult = response.parseAsString();
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
		return jsonResult;
	}
	
	public static String addValueToAPIEndPointURL(String apiEndPointURL, String key, String value){
		if(value.isEmpty()){
			apiEndPointURL = apiEndPointURL.replace("/{"+key+"}", "/");
			apiEndPointURL = apiEndPointURL.replace("&{"+key+"}", "");			
		}else{
			apiEndPointURL = apiEndPointURL.replace("/{"+key+"}", "/"+value);
			apiEndPointURL = apiEndPointURL.replace("&{"+key+"}", "&"+key+"="+value);			
		}
		return apiEndPointURL;
	}
	
	protected void setApiClientName(String s){
		if(isNullOrBlank(s)){
			this.apiClientName = this.getApiClientNameDefault();
		}else{
			this.apiClientName = s;
		}	
		OpenAPI.LOGGER.debug("apiClientName: " + this.getApiClientName());
	}
	
	protected void setApiClientNamePurge(String s){
		if(isNullOrBlank(s)){
			this.apiClientNamePurge = this.getApiClientNamePurgeDefault();
		}else{
			this.apiClientNamePurge = s;
		}	
		OpenAPI.LOGGER.debug("apiClientPurgeName: " + this.getApiClientNamePurge());
	}
	
	protected void setApiClientNameNetStorage(String s){
		if(isNullOrBlank(s)){
			this.apiClientNameNetStorage = this.getApiClientNameNetStorageDefault();
		}else{
			this.apiClientNameNetStorage = s;
		}	
		OpenAPI.LOGGER.debug("apiUploadAccountName: " + this.getApiClientNameNetStorage());
	}	
	private static boolean isNullOrBlank(String s)
	{
	  return (s==null || s.trim().equals(""));
	}
}