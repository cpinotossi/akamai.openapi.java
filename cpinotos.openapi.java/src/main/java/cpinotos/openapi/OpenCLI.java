package cpinotos.openapi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Iterator;
import java.util.TimeZone;

//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.AkamaiTokenGenerator;
import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import ch.qos.logback.classic.Level;
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
import cpinotos.openapi.services.DiagnosticToolsAPI;
import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.PropertyManagerAPI;
import cpinotos.openapi.services.PurgeAPI;
import cpinotos.openapi.services.data.CreateNewCPCodeResultV0;
import cpinotos.openapi.services.data.ListCPCode;
import cpinotos.openapi.services.data.ListCPCodeResult;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.NetStorageDirResultStat;
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
		//OpenAPI openAPI = null;
	 
		/*
		if (commands.verbose) {
			openAPI.LOGGER.setLevel(Level.DEBUG);
		} else {
			openAPI.LOGGER.setLevel(Level.INFO);
		}
		*/

		boolean result = false;
		PropertyManagerAPI papi = null;
		NetStorageAPI nsapi = null;
		PurgeAPI puapi = null;
		DiagnosticToolsAPI dapi = null;
		Gson gsonBuilder;
		
		switch (currentCmd) {
		case "mkdir":
			//we will only need the Netstorage credentials:
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			OpenAPI.LOGGER.info("Start mkdir:");
			OpenAPI.LOGGER.info("Netstorage Location (out):" + cmdMkdir.out);
			OpenAPI.LOGGER.info("Netstorage created:" + nsapi.doNetstorageMkdir(cmdMkdir.out));
			OpenAPI.LOGGER.debug("done");
			break; // optional
		case "upload":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			OpenAPI.LOGGER.info("Start upload:");
			OpenAPI.LOGGER.info("Local File (in):" + cmdUpload.in);
			OpenAPI.LOGGER.info("Netstorage Location (out):" + cmdUpload.out);
			if(cmdUpload.release!=null){
				result = nsapi.doNetstorageUpload(cmdUpload.out, cmdUpload.in, cmdUpload.release);
			}else{
				result = nsapi.doNetstorageUpload(cmdUpload.out, cmdUpload.in);	
			}
			
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "download":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			OpenAPI.LOGGER.info("Start download:");
			OpenAPI.LOGGER.info("settings.downloadpath:" + cmdDownload.out);
			result = nsapi.doNetstorageDownload(cmdDownload.out, cmdDownload.in);
			OpenAPI.LOGGER.info("download Response:" + result);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "rename":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			OpenAPI.LOGGER.info("Start rename:");
			OpenAPI.LOGGER.info("old path:" + cmdReName.in);
			OpenAPI.LOGGER.info("new path:" + cmdReName.out);
			result = nsapi.doNetstorageReName(cmdReName.in, cmdReName.out);
			OpenAPI.LOGGER.info("rename:" + result);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "symlink":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			OpenAPI.LOGGER.info("Start download:");
			OpenAPI.LOGGER.info("Netstorage SymLink Location (out):" + cmdSymLink.out);
			result = nsapi.doNetstorageSymLink(cmdSymLink.out, cmdSymLink.in);
			OpenAPI.LOGGER.info("SymLink Response:" + result);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "dir":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			if(cmdDir.out.endsWith("/")){
				OpenAPI.LOGGER.info("ERROR: path should not end with an '/': " + cmdDir.out);
				break;
			};
			if(cmdDir.recursive){
				OpenAPI.LOGGER.info("Start dir recursive:");
				OpenAPI.LOGGER.info("list the folder recursive:" + cmdDir.out);
				NetStorageDirResultStat netStorageDirResultStat = nsapi.doNetstorageDir(cmdDir.out, cmdDir.recursive);
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				OpenAPI.LOGGER.info("ns.dir.recursive(" + cmdDir.out + "):\n"+gsonBuilder.toJson(netStorageDirResultStat));
				OpenAPI.LOGGER.info("done");						
			}else{
				OpenAPI.LOGGER.info("Start dir:");
				OpenAPI.LOGGER.info("list the folder:" + cmdDir.out);
				NetStorageDirResultStat netStorageDirResultStat = nsapi.doNetstorageDir(cmdDir.out);
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				OpenAPI.LOGGER.info("ns.dir(" + cmdDir.out + "):\n" + gsonBuilder.toJson(netStorageDirResultStat));
				OpenAPI.LOGGER.info("done");				
			}
			break; // optional
		case "du":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			// disk utilisation
			OpenAPI.LOGGER.info("settings.path:" + cmdDu.out);
			//TODO need to fix this
			String responseDu = nsapi.doNetstorageDu(cmdDu.out).toString();
			OpenAPI.LOGGER.info("ns.du(" + cmdDu.out + "):\n" + responseDu);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "delete":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			// delete
			OpenAPI.LOGGER.info("settings.path:" + cmdDelete.out);
				//TODO fix the recursive hard coding
				boolean responseDelete = nsapi.doNetstorageDelete(cmdDelete.out);
				OpenAPI.LOGGER.info("ns.delete(" + cmdDelete.out + "): " + responseDelete);
				OpenAPI.LOGGER.info("done");	
			break; // optional	
		case "products":
			papi = new PropertyManagerAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			ProductsResult productsResults;
			if(cmdProducts.contractId==null){
				productsResults = papi.doListProducts();
			}else{
				productsResults =papi.doListProducts(cmdProducts.contractId);
			}
			gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gsonBuilder.toJson(productsResults));
			break; // optional		
		case "cpcode":
			papi = new PropertyManagerAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
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
					OpenAPI.LOGGER.info(gsonBuilder.toJson(i.next()));
				}				
			}else if (cmdCpCode.action.equals("search")){
				//TBD
			}else if (cmdCpCode.action.equals("create")){
				CreateNewCPCodeResultV0 resultCP = null;
				if(cmdCpCode.name == null || cmdCpCode.productId == null){
					OpenAPI.LOGGER.info("Please enter an CPCode and Product ID.");
				}
				else if(cmdCpCode.contractId == null || cmdCpCode.groupId == null){
					resultCP = papi.doCreateCPCodes(cmdCpCode.name, cmdCpCode.productId);	
					gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
					OpenAPI.LOGGER.info(gsonBuilder.toJson(resultCP));
				}
				else{
					resultCP = papi.doCreateCPCodes(cmdCpCode.contractId,cmdCpCode.groupId, cmdCpCode.name, cmdCpCode.productId);	
					gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
					OpenAPI.LOGGER.info(gsonBuilder.toJson(resultCP));
				}

			};
			break; // optional	
		case "edgeurl":
			papi = new PropertyManagerAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.info("Start edgeurl:");
			OpenAPI.LOGGER.info("edgeURL:step1/7: found Property Configuration for Hostname " + papi.getHostname());
			SearchPropertyVersionsBySingleValueResponseV0 psr = papi.searchPAPIConfiguration();
			//TODO Handle exception no config for provided hostname
			SearchPropertyVersionsBySingleValueResponseItemV0 psri = psr.getVersions().getItems().get(0);
			OpenAPI.LOGGER.info("edgeURL:step2/7: found Property Configuration "+ psri.getPropertyName() +"  version "+ psri.getPropertyVersion());
			String prt = papi.getPAPIRuletreeAsJSON(psri.getPropertyId(), psri.getPropertyVersion(), psri.getContractId(),
					psri.getGroupId(), false);
			OpenAPI.LOGGER.info("edgeURL:step3/7: downloaded Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
			OpenAPI.LOGGER.debug(prt.toString());
			if (prt.equals(null)) {
				papi.LOGGER.info("Could not retrieve Property Configuration");
			} else {
				OpenAPI.LOGGER.info("edgeURL:step4/7: extract edgeauth secret key from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
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
				String edgeAuthKey = papi.getEdgeAuthKeyFromPapiRuleSet(prt);
				
				OpenAPI.LOGGER.info("edgeURL:step5/7: extract edgeauth query name from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
				//String edgeAuthLocationId = openAPI.getEdgeAuthLocationFromPapiRuleSet(prt);
				
				//Integer startTime = (int) Instant.now().getEpochSecond();
				Integer startTime = ((cmdEdgeurl.starttime == null) ?  (int) Instant.now().getEpochSecond() : cmdEdgeurl.starttime);				
				OpenAPI.LOGGER.info("edgeURL:step6/7: generate edgeauth token with startime: "+startTime);

				String edgeAuthToken;
				//AkamaiTokenConfig conf = openAPI.GenerateConfig(cmdEdgeurl.in, edgeAuthKey, 3600);
				//String edgeAuthToken = AkamaiTokenGenerator.generateToken(conf);
				edgeAuthToken = papi.getEdgeAuthToken(cmdEdgeurl.in, edgeAuthKey, cmdEdgeurl.duration, "token", startTime);
				OpenAPI.LOGGER.info("edgeURL:step7/7: EdgeURL: \n" + edgeAuthToken);
				//openAPI.LOGGER.info("edgeURL:step7/7: EdgeURL: \nhttps://" + openAPI.getHost() + cmdEdgeurl.in + "?" + edgeAuthLocationId + "="+ edgeAuthToken);
			}
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "invalidate":
			puapi = new PurgeAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			// invalidate content based on
			OpenAPI.LOGGER.info("Start invalidate:");
			puapi.doPurgeInvalidate(cmdPurge.in, cmdPurge.staging);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "invalidate_cpcode":
			puapi = new PurgeAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			// invalidate content based on
			OpenAPI.LOGGER.info("Start invalidate by CPCode:");
			puapi.doPurgeInvalidateCPCode(cmdPurgeCPCode.in, cmdPurgeCPCode.staging);
			OpenAPI.LOGGER.info("done");
			break; // optional			
		case "url_debug":
			dapi = new DiagnosticToolsAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.info("Start URL Debug for URL:" + cmdUrlDebug.url);
			String responseUrlDebug = dapi.doUrlDebug(cmdUrlDebug.url,cmdUrlDebug.edgeip, cmdUrlDebug.header);
			OpenAPI.LOGGER.info("Result: " + responseUrlDebug);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "translate_error":
			dapi = new DiagnosticToolsAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.info("Start Translate ErrorCode:" + cmdTranslateError.errorCode);
			TranslatedError responseTranslateError = dapi.doTranslateError(cmdTranslateError.errorCode);
			OpenAPI.LOGGER.info("ReasonForFailure: " + responseTranslateError.getTranslatedError().getReasonForFailure());
			OpenAPI.LOGGER.info("done");
			break; // optional		
		case "logs_by_ip":
			dapi = new DiagnosticToolsAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.info("Start Get Log Lines by IP:" + cmdGetLogLinesByIP.ipAddress);
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
			OpenAPI.LOGGER.info("endTime:" + endTime);
			LogLines logLines = dapi.doGetLogLinesFromIP(cmdGetLogLinesByIP.ipAddress, endTime, "", "", "", "", "", "", "", "", "", "", "");
			OpenAPI.LOGGER.info("Log Lines: " + logLines.getLogLines());
			OpenAPI.LOGGER.info("done");
			break; // optional				
		default: // default
			OpenAPI.LOGGER.info("please use a command:");
		}

	}
}

