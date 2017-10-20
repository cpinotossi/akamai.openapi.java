package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Invalidate Files from Akamai Edge Server via Purge by CPCode")
public class CommandPurgeCPCode {
	@Parameter(names = { "--in", "-i" }, description = "Enter a single CPCode like 599907", required = true)
	public String in;
}