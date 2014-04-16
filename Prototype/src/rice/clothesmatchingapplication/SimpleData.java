package rice.clothesmatchingapplication;

import com.j256.ormlite.field.DatabaseField;

public class SimpleData {
@DatabaseField(generatedId = true)
int id;
@DatabaseField
String fileName;
@DatabaseField
String type;
@DatabaseField
String[] matches;

SimpleData() {
		// needed by ormlite
}

public SimpleData(String fileName, String type, String[] matches) {
	this.fileName = fileName;
	this.type = type;
	this.matches = matches;
		
	}

public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("id=").append(id);
	sb.append(", ").append("fileName=").append(fileName);
	sb.append(", ").append("type=").append(type);
	sb.append(", ").append("matches=").append(matches);
	return sb.toString();
	}
}
