package com.dc.esb.servicegov.tranlink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
	private static Connection connection;
	public static String driver = "com.ibm.db2.jcc.DB2Driver";
	private static String url = "jdbc:db2://10.112.13.68:50000/esbsg";
	public static String username = "esb";
	public static String password = "esb1234";
	static{
		try {
			Class.forName(driver);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean batchExcute(List<String> sqlList) throws Exception{
		int length = 0;
		Statement stmt = null;
		try{
			connection = DriverManager.getConnection(url, username, password);
			stmt = connection.createStatement();
			for(String sql : sqlList){
				stmt.addBatch(sql);
			}
			length = stmt.executeBatch().length;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=stmt){
				stmt.close();
			}
			if(null!=connection){
				connection.close();
			}
		}

		return length==sqlList.size();
	}
	public static List<Map<String,String>> query(String sql){
		Statement stmt = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			connection = DriverManager.getConnection(url, username, password);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String trancode = rs.getString("TRANCODE");
				String tranname = rs.getString("TRANNANE");
				String provider = rs.getString("PROVIDER");
				String consumer = rs.getString("CONSUMER");
				if(consumer.indexOf("（")<0  && consumer.indexOf("/")>0){
					String[] cons = consumer.split("/");
					for (int i = 0; i < cons.length; i++) {
						Map<String,String> resultMap = new HashMap<String,String>();
						resultMap.put("TRANCODE", trancode);
						resultMap.put("TRANNANE", tranname);
						resultMap.put("PROVIDER", provider);
						resultMap.put("CONSUMER", cons[i]);
						list.add(resultMap);
					}
				}else{
					Map<String,String> resultMap = new HashMap<String,String>();
					resultMap.put("TRANCODE", trancode);
					resultMap.put("TRANNANE", tranname);
					resultMap.put("PROVIDER", provider);
					resultMap.put("CONSUMER", consumer);
					list.add(resultMap);
				}
				//System.out.println("交易码："+trancode+" 交易名称："+tranname+" 交易链路："+consumer+"-->"+provider);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=stmt){
					stmt.close();
				}
				if(null!=connection){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public static boolean isTranExist(String tableName,String tranCode){
		Statement stmt = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			connection = DriverManager.getConnection(url, username, password);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName+" WHERE TRANCODE='"+tranCode+"'");
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=stmt){
					stmt.close();
				}
				if(null!=connection){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public static void closeConnection(){
		if(null!=connection){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
//		List<String> sqlList = new ArrayList<String>();
//		sqlList.add("INSERT INTO ESB.TEST(NAME, ID) VALUES('q','99')");
//		sqlList.add("INSERT INTO ESB.TEST(NAME, ID) VALUES('w','98')");
//		try {
//			DBUtil.batchInsert(sqlList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		DBUtil.query("SELECT T1.TRANCODE TRANCODE,T1.TRANNANE TRANNANE,T1.CONSUMER CONSUMER,T2.PROVIDER PROVIDER FROM ESB.TRANCONSUMER T1,TRANPROVIDER T2  WHERE  T1.TRANCODE = T2.TRANCODE");
	}
}
