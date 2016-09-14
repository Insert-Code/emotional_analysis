package com.zrdm.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.media.sound.AlawCodec;
import com.zrdm.db.DBBean;

/**
 * 
 * @author zrdm 情感词汇分析
 * 
 */
public class ALAnalysis {

	private String word = "";
	private String sql = "";
	private int single_score = 0;// 单个词的情感强度
	private int score; // 总情感强度
	private int jixing = 0; // 极性 0代表中性，1代表褒义，2代表贬义，3代表兼有褒贬两性。
	private DBBean db = null;// 数据库连接；
	private int semparent = -1; // 关联词汇id
	private String semrelate = ""; // 关联词汇的关系
	private boolean word_Negative = false; // 默认否定词不存在
	private int word_Negative_num = 1; // 分析否定情况
	private int id = -1;
	private String[] negative_Array = { "不", "不好", "否", "不许", "无", "勿", "毋",
			"非", "莫", "禁止", "禁", "防止", "杜绝", "拒绝" };

	public ALAnalysis() throws Exception {
		alAnalysis();
	}

	public void alAnalysis() throws Exception {
		db = new DBBean();
		SplitStatement sps = new SplitStatement();
		sql = "select * from weibo;";
		ResultSet rs1 = db.executeQuery(sql);
		while (rs1.next()) {
			score = 0;
			String text = rs1.getString("博文内容");
			sps.setText(text);
			sps.analysis();

			sql = "select * from Alontology where 词语 = ?;";

			JSONArray jsonArray = new JSONArray(sps.getJsonStr());
			JSONObject jsonObj = null;

			for (int i = 0; i < jsonArray.length(); i++) {
				single_score = 0;
				word_Negative = false; // 初始化否定词会不存在；
				jsonObj = jsonArray.getJSONObject(i);

				id = jsonObj.getInt("id");
				word = jsonObj.getString("cont");
				semrelate = jsonObj.getString("semrelate");

				System.out.print(word + " ");
				ResultSet rs = db.executeQuery_Pr(sql, word);
				for (int j = 0; j < negative_Array.length; j++) {
					// 如果有否定词，且该否定词为另一个词的否定标记，取得另一个词的id;
					if ((word.equals(negative_Array[j]) || word == negative_Array[j])
							&& (semrelate.equals("mNeg") || semrelate == "mNeg")) {
						semparent = jsonObj.getInt("semparent");
						word_Negative = true;
						word_Negative_num *= -1;
					}
				}
				//System.out.print(" " + word_Negative_num + " ");
				if (word_Negative) {
					continue;
				}
				while (rs.next()) {
					if (word == rs.getString("词语")
							|| word.equals(rs.getString("词语"))) {
						single_score = rs.getInt("强度");
						if(semparent == id){
							single_score *= word_Negative_num;
							id = -1;
						}
						if(rs.getString("情感分类").equals("NJ") || rs.getString("情感分类").equals("NE") || rs.getString("情感分类").equals("ND") || rs.getString("情感分类").equals("NN") ){
							single_score *= -1;
						}
						score += single_score;
						System.out.print("single_score = " + single_score + " ");
					}
				}

			}
			System.out.println();
			System.out.println("score = " + score + " ");
		}
	}

	public static void main(String[] args) throws Exception {
		ALAnalysis al = new ALAnalysis();

	}

}
