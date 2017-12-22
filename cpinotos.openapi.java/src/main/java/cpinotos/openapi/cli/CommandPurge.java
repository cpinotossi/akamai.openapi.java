package cpinotos.openapi.cli;

import java.util.ArrayList;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Invalidate Files from Akamai Edge Server via URL based Purge. NOTE: Make sure the API Client is allowed to purge this CPCode")
public class CommandPurge {
	//TODO Transform into list array so people can provide more than one url
	@Parameter(names = { "--urllist", "-u" }, description = "Enter a space seperated list of URL (e.g. http://www.test.com/file1.txt http://www.test.com/file2.txt ..). NOTE: Purge does not ignore a URL's protocol therefore you need to purgeh HTTP and HTTPS or use the flag -b", required = true)
	public ArrayList<String> urllist = new ArrayList<String>();
	
	//TODO get it implemented
	@Parameter(names = { "--jsonPath", "-j" }, description = "Absolute File Path to an JSON file. JSON Example: \n{\"objects\":[\n\"http://www.test.com/path/to/file.txt\",\n\"https://www.test.com/path/to/file.txt\"\n]}\nNOTE: Purge does not ignore a URL's protocol therefore you need to purgeh HTTP and HTTPS or use the flag -b", required = true)
	public String jsonPath;
	
	@Parameter(names = { "--both", "-b" }, description = "Purge automaticly both Protocol Version (HTTP and HTTPS) even if only one of both have been mentioned on --in", required = false)
	//TODO get it immplemented
	public boolean both = false;
	
	@Parameter(names = { "--staging", "-s" }, description = "Purge on Staging instead of Production", required = false)
	public boolean staging = false;
}