package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;




@Parameters(commandDescription = "Get information about error strings produced by edge servers when a request to retrieve content fails. The error represents an instance of a problem, and this operation gets details on what happened.")
public class CommandTranslatedError {
	@Parameter(names = { "--errorCode", "-e" }, description = "The error reference code to translate.", required = true)
	public String errorCode;
}
