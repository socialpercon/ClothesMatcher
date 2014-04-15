package rice.clothesmatchingapplication;

import com.j256.ormlite.field.DatabaseField;

public class SimpleData {
@DatabaseField(generatedId = true)
int id;
@DatabaseField
String fileName;
@DatabaseField
String type;

SimpleData() {
		// needed by ormlite
}

public SimpleData(String fileName, String type) {
	this.fileName = fileName;
	this.type = type;
		
	}

public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("id=").append(id);
	sb.append(", ").append("fileName=").append(fileName);
	sb.append(", ").append("type=").append(type);
	return sb.toString();
	}
}
