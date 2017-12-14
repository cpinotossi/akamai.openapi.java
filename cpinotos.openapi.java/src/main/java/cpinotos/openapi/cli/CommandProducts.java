package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Delete file or folder from Netstorage")
public class CommandProducts {
	@Parameter(names = { "--contractId", "-c" }, description = "The error reference code to translate.", required = false)
	public String contractId;
}
