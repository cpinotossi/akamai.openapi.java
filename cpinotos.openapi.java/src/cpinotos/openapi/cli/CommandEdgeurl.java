package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;



@Parameters(commandDescription = "Invalidate/Purge Files from Akamai Edge Server via Purge")
public class CommandEdgeurl {
	@Parameter(names = { "--in", "-i" }, description = "Path of the url (e.g. /path/to/file.txt)", required = true)
	public String in;
}
