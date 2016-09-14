package com.zrdm.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.media.sound.AlawCodec;

/**
 * 
 * @author zrdm 拆分语句 分词
 * 
 */
public class SplitStatement {
	
	private String api_key = "C7J8P5E7DgOkFsiK4KCdyEwNapjcPWgBJPhJ6BxK"; //API密钥
	private String pattern = "all"; //模式
	private String format = "json"; //输出格式json or xml
	private String text = ""; //分词语句	
	private String jsonStr = "";  // json格式的分词结果

	public SplitStatement(){}
	
	public void analysis() throws Exception{
		jsonStr = "";
		text = text.replaceAll("？", " ");
		text = text.replaceAll("。", " ");
		text = text.replaceAll("！", " ");
		text = text.replaceAll("；", " ");
		text = URLEncoder.encode(text, "utf-8");		
		
		URL url = new URL("http://api.ltp-cloud.com/analysis/?" + "api_key="
				+ api_key + "&" + "text=" + text + "&" + "format=" + format
				+ "&" + "pattern=" + pattern);
		URLConnection conn = url.openConnection();
		conn.connect();

		BufferedReader innet = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line = "";
		int i = 0;
		while ((line = innet.readLine()) != null) {
			if(i++ < 2 && (line.trim().equals("[") || line == "[")){
				continue;
			}
			jsonStr += line;
			//System.out.println(line);
		}		
		jsonStr = jsonStr.substring(0,jsonStr.length()-2);
		innet.close();
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
}
