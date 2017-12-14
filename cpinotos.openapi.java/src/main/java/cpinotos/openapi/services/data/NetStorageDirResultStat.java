package cpinotos.openapi.services.data;

import java.util.List;

public class NetStorageDirResultStat {
	private List<NetStorageDirResultFile> file;
	
	private String directory;
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public List<NetStorageDirResultFile> getFile() {
		return file;
	}
	public void setFile(List<NetStorageDirResultFile> file) {
		this.file = file;
	}

}
