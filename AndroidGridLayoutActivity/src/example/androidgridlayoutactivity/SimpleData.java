package example.androidgridlayoutactivity;


import com.j256.ormlite.field.DatabaseField;

public class SimpleData {
@DatabaseField(generatedId = true)
int id;
@DatabaseField
String fileName;
@DatabaseField
String type;
@DatabaseField
String color;
@DatabaseField
String explain;

SimpleData() {
	// needed by ormlite
}

public SimpleData(String fileName, String type, String color) {
	this.fileName = fileName;
	this.type = type;
	this.color = color;
	
}

public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("id=").append(id);
	sb.append(", ").append("fileName=").append(fileName);
	sb.append(", ").append("type=").append(type);
	sb.append(", ").append("color=").append(color);
	return sb.toString();
}
}

