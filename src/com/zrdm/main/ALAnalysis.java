package com.zrdm.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.media.sound.AlawCodec;
import com.zrdm.db.DBBean;

/**
 * 
 * @author zrdm
 * 情感词汇分析
 * 
 */
public class ALAnalysis {
	
	private String words = "喜欢"; 
	private String sql = "";
	private int score = 0 ; // 情感强度
	private int jixing = 0; //极性 0代表中性，1代表褒义，2代表贬义，3代表兼有褒贬两性。

	public ALAnalysis(){
		DBBean db = new DBBean();
		sql = "select * from Alontology where 词语 = ?";
		ResultSet rs = db.executeQuery_Pr(sql, words);
		try {
			while (rs.next()) {
				System.out
						.println(rs.getString("词语") + " "
								+ rs.getString("词性种类") + " "
								+ rs.getString("情感分类") + " "
								+ rs.getInt("强度") + " " 
								+ rs.getInt("极性") + " " 
								+ rs.getString("辅助情感分类") + " "
								+ rs.getInt("强度1") + " "
								+ rs.getInt("极性1"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	public static void main(String[] args) {
		ALAnalysis al = new ALAnalysis();
	}
	
}
