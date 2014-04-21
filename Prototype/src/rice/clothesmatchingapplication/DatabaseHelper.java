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


	public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
		private static final String DATABASE_NAME = "ClothesMatchingDb.db";
		private static final int DATABASE_VERSION = 2;
		private Dao<SimpleData, Integer> simpleDao = null;
	
		public static DatabaseHelper helper = null;
		private static final AtomicInteger usageCounter = new AtomicInteger(0);
		
		private DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public static synchronized DatabaseHelper getHelper(Context context){
			if(helper == null){
				helper = new DatabaseHelper(context);
			}
			usageCounter.incrementAndGet();
			return helper;
		}

		@Override
		public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
			try{
				Log.i(DatabaseHelper.class.getName(), "onCreate");
				TableUtils.createTable(connectionSource, SimpleData.class);
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
				TableUtils.dropTable(connectionSource, SimpleData.class, true);
				onCreate(db, connectionSource);
			}
			catch(SQLException e){
			throw new RuntimeException(e);
			}
			
		}
		
	
		
		
		public Dao<SimpleData, Integer> getSimpleDataDao() throws SQLException {
			if (simpleDao == null) {
				simpleDao = getDao(SimpleData.class);
			}
			return simpleDao;
		}
		
		
		
		public void close() {
			if (usageCounter.decrementAndGet() == 0) {
				super.close();
				simpleDao = null;
				
				helper = null;
			}
		}
		
	}

