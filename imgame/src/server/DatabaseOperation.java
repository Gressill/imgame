package server;

import java.sql.*;

public class DatabaseOperation {
	
	public void sqlTest(String sqlStr) {
		try {
			Connection conn;
			Statement stmt;
			ResultSet res;
			//����Connector/J����
			//��һ��Ҳ��дΪ��Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//������MySQL������
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "uestc");
			//ִ��SQL���
			stmt = conn.createStatement();
			//res = stmt.executeQuery(sqlStr);
			int executeUpdate = stmt.executeUpdate(sqlStr);
			res = stmt.executeQuery("select * from mgtest");
			//��������
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