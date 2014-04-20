package rice.clothesmatchingapplication;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

	
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
	
	private static final Class<?>[] classes = new Class[] {
		SimpleData.class,
		MatchesData.class,

	};
	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
