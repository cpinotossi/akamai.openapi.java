package cpinotos.openapi.cli;

import java.util.ArrayList;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;




@Parameters(commandDescription = "Delete file or folder from Netstorage")
public class CommandUrlDebug {
	@Parameter(names = { "--url", "-u" }, description = "The URL for which to gather error statistics.", required = true)
	public String url;
	@Parameter(names = { "--edgeip", "-e" }, description = "The edge server IP address to test the URL against, otherwise a random server by default.", required = false)
	public String edgeip;
	@Parameter(names = { "--header", "-h" }, description = "Any additional headers to add to the request. Repeat the parameter to specify more than one header.", required = false)
	public ArrayList<String> header = new ArrayList<>();
}
