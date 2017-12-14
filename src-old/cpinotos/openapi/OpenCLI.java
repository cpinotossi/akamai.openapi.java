package cpinotos.openapi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.AkamaiTokenGenerator;
import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import ch.qos.logback.classic.Level;
import cpinotos.openapi.cli.CommandDU;
import cpinotos.openapi.cli.CommandDelete;
import cpinotos.openapi.cli.CommandDir;
import cpinotos.openapi.cli.CommandDownload;
import cpinotos.openapi.cli.CommandEdgeurl;
import cpinotos.openapi.cli.CommandGetLogLinesByIP;
import cpinotos.openapi.cli.CommandMkdir;
import cpinotos.openapi.cli.CommandPapiCPCode;
import cpinotos.openapi.cli.CommandProducts;
import cpinotos.openapi.cli.CommandPurge;
import cpinotos.openapi.cli.CommandPurgeCPCode;
import cpinotos.openapi.cli.CommandReName;
import cpinotos.openapi.cli.CommandSymLink;
import cpinotos.openapi.cli.CommandTranslatedError;
import cpinotos.openapi.cli.CommandUpload;
import cpinotos.openapi.cli.CommandUrlDebug;
import cpinotos.openapi.cli.Commands;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;
import cpinotos.openapi.services.DiagnosticToolsAPI;
import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.PropertyManagerAPI;
import cpinotos.openapi.services.data.Child;
import cpinotos.openapi.services.data.CreateNewCPCodeResultV0;
import cpinotos.openapi.services.data.ListCPCode;
import cpinotos.openapi.services.data.ListCPCodeResult;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.ProductIoT;
import cpinotos.openapi.services.data.ProductsResult;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseItemV0;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseV0;
import cpinotos.openapi.services.data.TranslatedError;

public class OpenCLI {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		if (null == args || args.length == 0) {
			System.out.println("try '--help' or '-h' for more information");
			System.exit(1);
		}
		// Read user input parameter.
		Commands commands = new Commands();
		CommandUpload cmdUpload = new CommandUpload();
		CommandSymLink cmdSymLink = new CommandSymLink();
		CommandDownload cmdDownload = new CommandDownload();
		CommandReName cmdReName = new CommandReName();
		CommandDU cmdDu = new CommandDU();
		CommandDir cmdDir = new CommandDir();
		CommandDelete cmdDelete = new cpinotos.openapi.cli.CommandDelete();
		CommandMkdir cmdMkdir = new CommandMkdir();
		CommandEdgeurl cmdEdgeurl = new CommandEdgeurl();
		CommandPurge cmdPurge = new CommandPurge();
		CommandPurgeCPCode cmdPurgeCPCode = new CommandPurgeCPCode();
		CommandUrlDebug cmdUrlDebug = new CommandUrlDebug();
		CommandTranslatedError cmdTranslateError = new CommandTranslatedError();
		CommandGetLogLinesByIP cmdGetLogLinesByIP = new CommandGetLogLinesByIP();
		CommandPapiCPCode cmdCpCode = new CommandPapiCPCode();
		CommandProducts cmdProducts = new CommandProducts();
		JCommander jc = new JCommander();
		jc.addObject(commands);
		jc.addCommand("du", cmdDu);
		jc.addCommand("dir", cmdDir);
		jc.addCommand("mkdir", cmdMkdir);
		jc.addCommand("upload", cmdUpload);
		jc.addCommand("symlink", cmdSymLink);
		jc.addCommand("download", cmdDownload);
		jc.addCommand("rename", cmdReName);
		jc.addCommand("delete", cmdDelete);
		jc.addCommand("edgeurl", cmdEdgeurl);
		jc.addCommand("invalidate", cmdPurge);
		jc.addCommand("invalidate_cpcode", cmdPurgeCPCode);
		jc.addCommand("url_debug",cmdUrlDebug);
		jc.addCommand("translate_error",cmdTranslateError);
		jc.addCommand("logs_by_ip", cmdGetLogLinesByIP);
		jc.addCommand("cpcode", cmdCpCode);
		jc.addCommand("products", cmdProducts);
	    try {
	        jc.parse(args);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        jc.usage();
	        System.exit(1);
	    }
	    if (commands.help || jc.getParsedCommand() == null) {
	        jc.usage();
	        System.exit(0);
	    }
        String currentCmd = jc.getParsedCommand();
		// Instantiate an instance of
		OpenAPI openAPI = new OpenAPI(commands.config, commands.verbose);
	 
		if (commands.verbose) {
			openAPI.logger.setLevel(Level.DEBUG);
		} else {
			openAPI.logger.setLevel(Level.INFO);
		}
		openAPI.logger.debug("Host: " + openAPI.getHost());
		openAPI.logger.debug("Netstorage Client: " + openAPI.getNetstorageClient());
		openAPI.logger.debug("Netstorage Host: " + openAPI.getNetstorageHost());
		openAPI.logger.debug("Netstorage Key: " + openAPI.getNetstorageKey());
		openAPI.logger.debug("Purge Access Token: " + openAPI.getPurgeAccessToken());
		openAPI.logger.debug("Purge Client Secret: " + openAPI.getPurgeClientSecret());
		openAPI.logger.debug("Purge Client Token: " + openAPI.getPurgeClientToken());
		openAPI.logger.debug("Purge Client Host: " + openAPI.getPurgeHost());
		openAPI.logger.debug("Purge Invalidation Endpoint: " + openAPI.getApiPurgeInvalidateEndpoint());
		openAPI.logger.debug("API Client Secret: " + openAPI.getApiClientSecret());
		openAPI.logger.debug("API Host: " + openAPI.getApiHost());
		openAPI.logger.debug("API Access Token: " + openAPI.getApiAccessToken());
		openAPI.logger.debug("API Client Token: " + openAPI.getApiClientToken());
		openAPI.logger.debug("API PAPI Search Endpoint: " + openAPI.getApiPapiSearchEndpoint());
		openAPI.logger.debug("API PAPI GET Endpoint: " + openAPI.getApiPapiGetEndpoint());
		openAPI.logger.debug("API PAPI GET Ruletree Endpoint: " + openAPI.getApiPapiGetRuletreeEndpoint());

		boolean result = false;
		PropertyManagerAPI papi = new PropertyManagerAPI(openAPI);
		NetStorageAPI nsapi = new NetStorageAPI(openAPI);
		Gson gsonBuilder;
		
		switch (currentCmd) {
		case "mkdir":
			openAPI.logger.info("Start mkdir:");
			openAPI.logger.info("Netstorage Location (out):" + cmdMkdir.out);
			openAPI.logger.info("Netstorage created:" + nsapi.doNetstorageMkdir(cmdMkdir.out));
			openAPI.logger.debug("done");
			break; // optional
		case "upload":
			openAPI.logger.info("Start upload:");
			openAPI.logger.info("Local File (in):" + cmdUpload.in);
			openAPI.logger.info("Netstorage Location (out):" + cmdUpload.out);
			if(cmdUpload.release!=null){
				result = nsapi.doNetstorageUpload(cmdUpload.out, cmdUpload.in, cmdUpload.release);
			}else{
				result = nsapi.doNetstorageUpload(cmdUpload.out, cmdUpload.in);	
			}
			
			openAPI.logger.info("done");
			break; // optional
		case "download":
			openAPI.logger.info("Start download:");
			openAPI.logger.info("settings.downloadpath:" + cmdDownload.out);
			result = nsapi.doNetstorageDownload(cmdDownload.out, cmdDownload.in);
			openAPI.logger.info("download Response:" + result);
			openAPI.logger.info("done");
			break; // optional
		case "rename":
			openAPI.logger.info("Start rename:");
			openAPI.logger.info("old path:" + cmdReName.in);
			openAPI.logger.info("new path:" + cmdReName.out);
			result = nsapi.doNetstorageReName(cmdReName.in, cmdReName.out);
			openAPI.logger.info("rename:" + result);
			openAPI.logger.info("done");
			break; // optional
		case "symlink":
			openAPI.logger.info("Start download:");
			openAPI.logger.info("Netstorage SymLink Location (out):" + cmdSymLink.out);
			result = nsapi.doNetstorageSymLink(cmdSymLink.out, cmdSymLink.in);
			openAPI.logger.info("SymLink Response:" + result);
			openAPI.logger.info("done");
			break; // optional
		case "edgeurl":
			openAPI.logger.info("Start edgeurl:");
			openAPI.logger.info("edgeURL:step1/7: found Property Configuration for Hostname " + openAPI.getHost());
			SearchPropertyVersionsBySingleValueResponseV0 psr = papi.searchPAPIConfiguration();
			//TODO Handle exception no config for provided hostname
			SearchPropertyVersionsBySingleValueResponseItemV0 psri = psr.getVersions().getItems().get(0);
			openAPI.logger.info("edgeURL:step2/7: found Property Configuration "+ psri.getPropertyName() +"  version "+ psri.getPropertyVersion());
			String prt = papi.getPAPIRuletreeAsJSON(psri.getPropertyId(), psri.getPropertyVersion(), psri.getContractId(),
					psri.getGroupId(), false);
			openAPI.logger.info("edgeURL:step3/7: downloaded Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
			openAPI.logger.debug(prt.toString());
			if (prt.equals(null)) {
				openAPI.logger.info("Could not retrieve Property Configuration");
			} else {
				openAPI.logger.info("edgeURL:step4/7: extract edgeauth secret key from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
				/*
				ArrayList<Child> childList = (ArrayList<Child>) prt.getRules().getChildren();
				Iterator<Child> iteratoChild = childList.iterator();
				while(iteratoChild.hasNext()){
					Child child = null;
					child = iteratoChild.next();
					if(child.getName().equals("Access Control")){
						ArrayList<Object> behaviorList;
						behaviorList = (ArrayList<Object>) child.getChildren().get(0).getBehaviors();
						LinkedTreeMap<String, Object> behaviorMapEntry = (LinkedTreeMap<String, Object>) behaviorList.get(1);
						behaviorMapEntry.get("verifyTokenAuthorization");
						Iterator<Object> iteratorChildBehaviors = behaviorList.iterator();
						while(iteratorChildBehaviors.hasNext()){
							//behavior = 
							LinkedTreeMap<String, Object> behaviorMap = (LinkedTreeMap<String, Object>) iteratorChildBehaviors.next();
							behaviorMap.get("key");
							//if(behavior.startsWith("key"));
							//edgeAuthKey = behavior.substring(3);
							break;
						}
					};
				};
				*/
				String edgeAuthKey = openAPI.getEdgeAuthKeyFromPapiRuleSet(prt);
				
				openAPI.logger.info("edgeURL:step5/7: extract edgeauth query name from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
				//String edgeAuthLocationId = openAPI.getEdgeAuthLocationFromPapiRuleSet(prt);
				
				//Integer startTime = (int) Instant.now().getEpochSecond();
				Integer startTime = ((cmdEdgeurl.starttime == null) ?  (int) Instant.now().getEpochSecond() : cmdEdgeurl.starttime);				
				openAPI.logger.info("edgeURL:step6/7: generate edgeauth token with startime: "+startTime);

				String edgeAuthToken;
				//AkamaiTokenConfig conf = openAPI.GenerateConfig(cmdEdgeurl.in, edgeAuthKey, 3600);
				//String edgeAuthToken = AkamaiTokenGenerator.generateToken(conf);
				edgeAuthToken = openAPI.getEdgeAuthToken(cmdEdgeurl.in, edgeAuthKey, cmdEdgeurl.duration, "token", startTime);
				openAPI.logger.info("edgeURL:step7/7: EdgeURL: \n" + edgeAuthToken);
				//openAPI.logger.info("edgeURL:step7/7: EdgeURL: \nhttps://" + openAPI.getHost() + cmdEdgeurl.in + "?" + edgeAuthLocationId + "="+ edgeAuthToken);
			}
			openAPI.logger.info("done");
			break; // optional
		case "invalidate":
			// invalidate content based on
			openAPI.logger.info("Start invalidate:");
			String jsonPurge = "{\"objects\":[\""+cmdPurge.in+"\"]}";
			//openAPI.doPurgeInvalidate(openAPI.jsonFileReader(jsonPurge));
			openAPI.doPurgeInvalidate(jsonPurge, cmdPurge.staging);
			openAPI.logger.info("done");
			break; // optional
		case "invalidate_cpcode":
			// invalidate content based on
			openAPI.logger.info("Start invalidate by CPCode:");
			openAPI.doPurgeInvalidateCPCode(cmdPurgeCPCode.in, cmdPurgeCPCode.staging);
			openAPI.logger.info("done");
			break; // optional			
		case "dir":
			if(cmdDir.out.endsWith("/")){
				openAPI.logger.info("ERROR: path should not end with an '/': " + cmdDir.out);
				break;
			};
			if(cmdDir.recursive){
				openAPI.logger.info("Start dir recursive:");
				openAPI.logger.info("list the folder recursive:" + cmdDir.out);
				NetStorageDirResultStat netStorageDirResultStat = nsapi.doNetstorageDir(cmdDir.out, cmdDir.recursive);
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				openAPI.logger.info("ns.dir.recursive(" + cmdDir.out + "):\n"+gsonBuilder.toJson(netStorageDirResultStat));
				openAPI.logger.info("done");						
			}else{
				openAPI.logger.info("Start dir:");
				openAPI.logger.info("list the folder:" + cmdDir.out);
				nsapi = new NetStorageAPI(openAPI);
				NetStorageDirResultStat netStorageDirResultStat = nsapi.doNetstorageDir(cmdDir.out);
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				openAPI.logger.info("ns.dir(" + cmdDir.out + "):\n" + gsonBuilder.toJson(netStorageDirResultStat));
				openAPI.logger.info("done");				
			}
			break; // optional
		case "du":
			// disk utilisation
			openAPI.logger.info("settings.path:" + cmdDu.out);
			nsapi = new NetStorageAPI(openAPI);
			String responseDu = nsapi.doNetstorageDu(cmdDu.out, true);
			openAPI.logger.info("ns.du(" + cmdDu.out + "):\n" + responseDu);
			openAPI.logger.info("done");
			break; // optional
		case "delete":
			// delete
				openAPI.logger.info("settings.path:" + cmdDelete.out);
				nsapi = new NetStorageAPI(openAPI);
				boolean responseDelete = nsapi.doNetstorageDelete(cmdDelete.out);
				openAPI.logger.info("ns.delete(" + cmdDelete.out + "): " + responseDelete);
				openAPI.logger.info("done");	
			break; // optional	
		case "url_debug":
			openAPI.logger.info("Start URL Debug for URL:" + cmdUrlDebug.url);
			String responseUrlDebug = openAPI.doUrlDebug(cmdUrlDebug.url,cmdUrlDebug.edgeip, cmdUrlDebug.header);
			openAPI.logger.info("Result: " + responseUrlDebug);
			openAPI.logger.info("done");
			break; // optional
		case "translate_error":
			openAPI.logger.info("Start Translate ErrorCode:" + cmdTranslateError.errorCode);
			DiagnosticToolsAPI dt = new DiagnosticToolsAPI(openAPI);
			TranslatedError responseTranslateError = dt.doTranslateError(cmdTranslateError.errorCode);
			openAPI.logger.info("ReasonForFailure: " + responseTranslateError.getTranslatedError().getReasonForFailure());
			openAPI.logger.info("done");
			break; // optional		
		case "logs_by_ip":
			openAPI.logger.info("Start Get Log Lines by IP:" + cmdGetLogLinesByIP.ipAddress);
			String endTime = cmdGetLogLinesByIP.endTime;
			try {
				//Transform Date at HTTP Response Header in RFC2616 into Date needed in ISO 8601
				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				java.util.Date date = format.parse(endTime);
				TimeZone tz = TimeZone.getTimeZone("UTC");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
				df.setTimeZone(tz);
				endTime = df.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openAPI.logger.info("endTime:" + endTime);
			DiagnosticToolsAPI dt2 = new DiagnosticToolsAPI(openAPI);
			LogLines logLines = dt2.doGetLogLinesFromIP(cmdGetLogLinesByIP.ipAddress, endTime, "", "", "", "", "", "", "", "", "", "", "");
			openAPI.logger.info("Log Lines: " + logLines.getLogLines());
			openAPI.logger.info("done");
			break; // optional	
			
		case "products":
			ProductsResult productsResults;
			papi = new PropertyManagerAPI(openAPI);
			if(cmdProducts.contractId==null){
				productsResults = papi.doListProducts();
			}else{
				productsResults =papi.doListProducts(cmdProducts.contractId);
			}
			gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gsonBuilder.toJson(productsResults));
			break; // optional		
		case "cpcode":
			papi = new PropertyManagerAPI(openAPI);
			if (cmdCpCode.action.equals("list")){
				ListCPCodeResult listCPCodeResult;
				if(cmdCpCode.contractId == null || cmdCpCode.groupId == null){
					listCPCodeResult = papi.doListCPCodes();	
				}
				else{
					listCPCodeResult = papi.doListCPCodes(cmdCpCode.contractId,cmdCpCode.groupId);	
				}
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				Iterator<ListCPCode> i = listCPCodeResult.getCpcodes().getItems().iterator();
				while(i.hasNext()){
					System.out.println(gsonBuilder.toJson(i.next()));
				}				
			}else if (cmdCpCode.action.equals("search")){
				//TBD
			}else if (cmdCpCode.action.equals("create")){
				CreateNewCPCodeResultV0 resultCP = null;
				if(cmdCpCode.name == null || cmdCpCode.productId == null){
					openAPI.logger.info("Please enter an CPCode and Product ID.");
				}
				else if(cmdCpCode.contractId == null || cmdCpCode.groupId == null){
					resultCP = papi.doCreateCPCodes(cmdCpCode.name, cmdCpCode.productId);	
					gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
					System.out.println(gsonBuilder.toJson(resultCP));
				}
				else{
					resultCP = papi.doCreateCPCodes(cmdCpCode.contractId,cmdCpCode.groupId, cmdCpCode.name, cmdCpCode.productId);	
					gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
					System.out.println(gsonBuilder.toJson(resultCP));
				}

			};
			break; // optional	
		default: // default
			openAPI.logger.info("please use a command:");
		}

	}
}

