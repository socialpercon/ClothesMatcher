package rice.clothesmatchingapplication;

import com.j256.ormlite.field.DatabaseField;

public class SimpleData {
@DatabaseField
String fileName;
@DatabaseField
static String type;
@DatabaseField
String[] matches;

SimpleData() {
		// needed by ormlite
}

public SimpleData(String fileName, String type, String[] matches) {
	this.fileName = fileName;
	SimpleData.type = type;
	this.matches = matches;
		
	}

public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("fileName=").append(fileName);
	sb.append(", ").append("type=").append(type);
	sb.append(", ").append("matches=").append(matches);
	return sb.toString();
	}
}
