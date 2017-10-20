package cpinotos.openapi.netstorage;

import java.util.List;

public class NetStorageDirResultFile {
private String name, type, mtime, size, md5;
private List<NetStorageDirResultFile> file;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getMtime() {
	return mtime;
}

public void setMtime(String mtime) {
	this.mtime = mtime;
}

public String getSize() {
	return size;
}

public void setSize(String size) {
	this.size = size;
}

public String getMd5() {
	return md5;
}

public void setMd5(String md5) {
	this.md5 = md5;
}

public List<NetStorageDirResultFile> getFile() {
	return file;
}

public void setFile(List<NetStorageDirResultFile> file) {
	this.file = file;
}
}
