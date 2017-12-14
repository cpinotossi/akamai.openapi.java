package cpinotos.openapi.netstorage;

import java.util.List;
//import cpinotos.openapi.OpenAPI;
import lombok.Getter;
import lombok.Setter;

public class NetStorageDirResultFile {
@Getter @Setter private String name;
@Getter @Setter private String type;
@Getter @Setter private String mtime;
@Getter @Setter private String size;
@Getter @Setter private String md5;
@Getter @Setter private String target;
@Getter @Setter private List<NetStorageDirResultFile> file;
}
