package rice.clothesmatchingapplication;

import com.j256.ormlite.field.DatabaseField;

public class MatchesData {

@DatabaseField(columnName = "type")
String type1;
@DatabaseField
String type2;

MatchesData() {
		// needed by ormlite
}

public MatchesData(String type1, String type2){
	
	this.type1 = type1;
	this.type2 = type2;
	}

//public String getMatches(){
//	return this.type1, this.type2;
//}

public String toString(){
	StringBuilder sb = new StringBuilder();
	
	sb.append("type1=").append(type1);
	sb.append(", ").append("type2=").append(type2);
	return sb.toString();
	}
}
