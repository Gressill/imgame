package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import util.Constant;

/** */
/**
 *
 */

public class DatabaseOperation {

	public String ClassString = null;
	public String ConnectionString = null;
	public String UserName = null;
	public String PassWord = null;

	public Connection Conn;
	public Statement Stmt;

	public DatabaseOperation() {
		//From System.xml
		ClassString = "com.mysql.jdbc.Driver";//"oracle.jdbc.driver.OracleDriver";
		ConnectionString = "jdbc:mysql://localhost:3306/imgame";//"jdbc:oracle:thin:@192.168.103.171:1521:jstrd";
		UserName = Constant.DB_USER_NAME;
		PassWord = Constant.DB_PASSWORD;

		//For MySQL Driver
		//ClassString="org.gjt.mm.mysql.Driver";
		//ConnectionString="jdbc:mysql://localhost/softforum?user=&password=&useUnicode=true&characterEncoding=8859_1";
		//ClassString="com.mysql.jdbc.Driver";
		//ConnectionString="jdbc:mysql://localhost/dbdemo?user=root&password=&useUnicode=true&characterEncoding=gb2312";
	}

	//打开连接
	public boolean OpenConnection() {
		boolean mResult = true;
		try {
			Class.forName(ClassString);
			if ((UserName == null) && (PassWord == null)) {
				Conn = DriverManager.getConnection(ConnectionString);
			} else {
				Conn = DriverManager.getConnection(ConnectionString, UserName,
						PassWord);
			}

			Stmt = Conn.createStatement();
			mResult = true;
		} catch (Exception e) {
			System.out.println(e.toString());
			mResult = false;
		}
		return (mResult);
	}

	//关闭数据库连接
	public void CloseConnection() {
		try {
			Stmt.close();
			Conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	//获取当前时间(JAVA)
	public String GetDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(cal.getTime());
		return (mDateTime);
	}

	//获取当前时间(T-SQL)
	public java.sql.Date GetDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		return (java.sql.Date.valueOf(mDateTime));
	}

	//生成新的ID
	public int GetMaxID(String vTableName, String vFieldName) {
		int mResult = 0;
		boolean mConn = true;
		String mSql = new String();
		mSql = "select max(" + vFieldName + ")+1 as MaxID from " + vTableName;
		try {
			if (Conn != null) {
				mConn = Conn.isClosed();
			}
			if (mConn) {
				OpenConnection();
			}

			ResultSet result = ExecuteQuery(mSql);
			if (result.next()) {
				mResult = result.getInt("MaxID");
			}
			result.close();

			if (mConn) {
				CloseConnection();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return (mResult);
	}

	//数据检索
	public ResultSet ExecuteQuery(String SqlString) {
		ResultSet result = null;
		try {
			result = Stmt.executeQuery(SqlString);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return (result);
	}

	//数据更新(增、删、改)
	public int ExecuteUpdate(String SqlString) {
		int result = 0;
		try {
			result = Stmt.executeUpdate(SqlString);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return (result);
	}

	public static void testSql() {
		String sqlString = "select * from mgtest";
		String sqlInsert = "INSERT INTO mgtest VALUES ('yu','2','3')";
		ResultSet res;
		DatabaseOperation databaseOperation = new DatabaseOperation();
		if(databaseOperation.OpenConnection())
		{
			int i = databaseOperation.ExecuteUpdate(sqlInsert);
			System.out.print("i="+i);
			res = databaseOperation.ExecuteQuery(sqlString);
			//处理结果集
			try {
				while (res.next()) {
					String name = res.getString("user_id");
					System.out.println(name);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				databaseOperation.CloseConnection();
			}
		}
	}
}

