package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;




@Parameters(commandDescription = "Delete file or folder from Netstorage")
public class CommandDelete {
	@Parameter(names = { "--out", "-o" }, description = "Path to netstorage, including CPCode Folder (.e.g Folder = /599907/my/folder, File = /599907/my/file.txt). WARNING: Does delete Folder recursive without warning. So please be carefully.", required = true)
	public String out;
}
