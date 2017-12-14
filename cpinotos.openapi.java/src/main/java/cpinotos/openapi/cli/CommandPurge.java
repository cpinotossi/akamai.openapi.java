package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Invalidate Files from Akamai Edge Server via Purge")
public class CommandPurge {
	@Parameter(names = { "--in", "-i" }, description = "Path of the URL (e.g. /path/to/file.txt). Content of the JSON file should look as follow \n{\"objects\":[\n\"https://foo1.bar.com/some/path\",\n\"http://foo2.bar.com/some/other/path\"\n]}", required = true)
	public String in;
	@Parameter(names = { "--staging", "-s" }, description = "Purge on Staging instead of Production", required = false)
	public boolean staging = false;
}