package server;

import java.sql.*;

public class DatabaseOperation {
	
	public void sqlTest(String sqlStr) {
		try {
			Connection conn;
			Statement stmt;
			ResultSet res;
			//加载Connector/J驱动
			//这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//建立到MySQL的连接
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "uestc");
			//执行SQL语句
			stmt = conn.createStatement();
			//res = stmt.executeQuery(sqlStr);
			int executeUpdate = stmt.executeUpdate(sqlStr);
			res = stmt.executeQuery("select * from mgtest");
			//处理结果集
			while (res.next()) {
				String name = res.getString("user_id");
				System.out.println(name);
			}
			res.close();

		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}

	}
} 