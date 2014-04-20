package rice.clothesmatchingapplication;
	
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


	public class DatabaseHelperM extends OrmLiteSqliteOpenHelper{
		private static final String DATABASE_NAME = "ClothesMatchingDb.db";
		private static final int DATABASE_VERSION = 1;
		
		private Dao<MatchesData, Integer> matchDao = null;
		public static DatabaseHelperM helper = null;
		private static final AtomicInteger usageCounter = new AtomicInteger(0);
		
		private DatabaseHelperM(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public static synchronized DatabaseHelperM getHelper(Context context){
			if(helper == null){
				helper = new DatabaseHelperM(context);
			}
			usageCounter.incrementAndGet();
			return helper;
		}

		@Override
		public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
			try{
				Log.i(DatabaseHelper.class.getName(), "onCreate");
				TableUtils.createTable(connectionSource, MatchesData.class);
				//TableUtils.createTable(connectionSource, MatchesData.class);
			} 
			catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
				throw new RuntimeException(e);
		}
		}
		
			

		@Override
		public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
				int newVer) {
			try{
				TableUtils.dropTable(connectionSource, MatchesData.class, true);
				onCreate(db, connectionSource);
			}
			catch(SQLException e){
			throw new RuntimeException(e);
			}
			
		}
		
	
		
		
		public Dao<MatchesData, Integer> getMatchesDataDao() throws SQLException {
			if (matchDao == null) {
				matchDao = getDao(MatchesData.class);
			}
			return matchDao;
		}
		
		public void close() {
			if (usageCounter.decrementAndGet() == 0) {
				super.close();
				matchDao = null;
				helper = null;
			}
		}
		
	}