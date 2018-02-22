package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;



@Parameters(commandDescription = "Create EdgeAuth Token.")
public class CommandEdgeAuth {
	@Parameter(names = { "--in", "-i" }, description = "Path Section of the url (e.g. /path/to/file.txt)", required = true)
	public String in;
	//@Parameter(names = { "--name", "-n" }, description = "Name of the token parameter (default token)", required = false)
	//public String name = "token";
	@Parameter(names = { "--duration", "-d" }, description = "Duration the token will be valid in seconds (default 3600sec)", required = false)
	public Integer duration = 3600;
	//TODO review this implementation.
	@Parameter(names = { "--starttime", "-s" }, description = "Start time of the token in epoch unix timestamp (default: now)", required = false)
	public Integer starttime = null;
	@Parameter(names = { "--network", "-n" }, description = "Define from which network to retrieve the the EdgeAuth Settings values. Possible values production or staging (default: production)", required = false)
	public String network = "production";
}
