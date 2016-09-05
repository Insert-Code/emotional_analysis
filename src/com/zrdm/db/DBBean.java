package com.zrdm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author zrdm 数据库连接
 * 
 */
public class DBBean {

	private String driverStr = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
	private String connStr = "jdbc:sqlserver://localhost:1433; DatabaseName=EA"; // 连接服务器和数据库sample
	private String dbuserName = "sa"; // 默认用户名
	private String dbuserPwd = "sql"; // 密码
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;

	public DBBean() {
		try {
			Class.forName(driverStr);
			conn = DriverManager.getConnection(connStr, dbuserName, dbuserPwd);
			stmt = conn.createStatement();
		} catch (Exception ex) {
			System.out.println("无法同数据库建立连接！");
		}
	}

	// 字符串更新
	public int executeUpdate(String s) {
		int result = 0;
		try {
			result = stmt.executeUpdate(s);
		} catch (Exception ex) {
			System.out.println("执行更新错误！");
		}
		return result;
	}

	// 参数更新str
	public int executeUpdate_Pre(String s, String str) {
		int result = 0;
		try {
			ps = conn.prepareStatement(s);
			ps.setString(1, str);
			result = ps.executeUpdate();
		} catch (Exception ex) {
			System.out.println("1个参数执行更新错误");
		}
		return result;
	}

	// 字符串查询
	public ResultSet executeQuery(String s) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(s);
		} catch (Exception ex) {
			System.out.println("执行查询错误！");
		}
		return rs;
	}

	// 参数查询str
	public ResultSet executeQuery_Pr(String sql, String str) {
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			rs = ps.executeQuery();
		} catch (Exception ex) {
			System.out.println("执行查询错误！");
		}
		return rs;
	}

	// 参数模糊查询str
	public ResultSet executeQuery_Pre(String s, String str) {
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(s);
			ps.setString(1, "%" + str + "%");
			rs = ps.executeQuery();
		} catch (Exception ex) {
			System.out.println("执行查询错误！");
		}
		return rs;
	}

	public void close() {
		try {
			stmt.close();
			conn.close();
		} catch (Exception ex) {
		}
	}

}
