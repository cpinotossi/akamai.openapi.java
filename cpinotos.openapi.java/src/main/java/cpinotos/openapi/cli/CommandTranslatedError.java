package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;




@Parameters(commandDescription = "Delete file or folder from Netstorage")
public class CommandTranslatedError {
	@Parameter(names = { "--errorCode", "-e" }, description = "The error reference code to translate.", required = true)
	public String errorCode;
}
