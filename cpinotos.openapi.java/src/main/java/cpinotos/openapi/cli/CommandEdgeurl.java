package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;



@Parameters(commandDescription = "Retrieve URL with EdgeAuth Token. NOTE: Only if EdgeAuth has been implemented.")
public class CommandEdgeurl {
	@Parameter(names = { "--in", "-i" }, description = "Path Section of the url (e.g. /path/to/file.txt)", required = true)
	public String in;
	@Parameter(names = { "--duration", "-d" }, description = "Duration the token will be valid in seconds (default 3600sec)", required = false)
	public Integer duration = 3600;
	//TODO review this implementation.
	@Parameter(names = { "--starttime", "-s" }, description = "Start time of the token in epoch unix timestamp (default: now)", required = false)
	public Integer starttime = null;
}
