package rice.clothesmatchingapplication;
import com.j256.ormlite.field.DatabaseField;
public class MatchesData {
@DatabaseField(columnName="type1")
String type1;
@DatabaseField(columnName="type2")
String type2;

MatchesData(){
	
}

public MatchesData(String type1, String type2){
	this.type1 = type1;
	this.type2 = type2;
}

public String getType1(){
	return this.type1;
}

public String getType2(){
	return this.type2;
}


public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("type1").append(type1);
	sb.append(" ,").append("type2").append(type2);
	return sb.toString();
}

}


