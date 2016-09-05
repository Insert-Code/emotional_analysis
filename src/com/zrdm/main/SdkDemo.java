package com.zrdm.main;

/**
 * 通过java处理时调用API的方法(示例)<br>
 *
 * @author datatang
 * @version 1.0
 * @create date 2014/08
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import org.apache.log4j.Logger;
 
/**
 * 使用java发送GET请求获取数据
 * @author datatang
 *
 */
public class SdkDemo {
	static Logger log = Logger.getLogger(SdkDemo.class);
	
	//测试主程序
	public static void main(String[] args) {
		//连接url地址
		//http://apidata.datatang.com/data/getData.htm 	固定地址 (必须项目)
		String strUrl = "http://apidata.datatang.com/data/getData.htm";
		//dtkey 	通过页面申请的API KEY。(必须项目)
		String strKey = "44ff18e26c48a646c9e30802dd162d6e";
		//apicode 	各API的代码 (必须项目)
		String strApicode = "emotional_analysis"; 
		//rettype   需要返回的格式（支持XML及JSON）(必须项目)
		String strRettype = "JSON";
		//各API需要参数（详细参考画面-各API参数不同）
		String strparam = "";
		//例如
		strparam = "今天天气不错";
		//访问URL地址 	
		String url = strUrl + "?apikey=" + strKey + "&apicode=" + strApicode + "&rettype=" + strRettype
						+ "&text="+ strparam;
		try {
			String res = readByGet(url);
			System.out.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
   /**
    * 通过GET请求调用url获取结果
    * @param inUrl 请求url
    * @throws IOException 
    * @return String  获取的结果
    */
   private static String readByGet(String inUrl) throws IOException {
	   StringBuffer sbf = new StringBuffer();
	   String strRead = null;
	   
	   //模拟浏览器
	   String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
	   		+ "(KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	   		
	   //连接URL地址		
	   URL url = new URL(inUrl);
	   //根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型,
	   //返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection  
	   HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	   
	   //设置连接访问方法及超时参数
	   connection.setRequestMethod("GET");
	   connection.setReadTimeout(30000);
	   connection.setConnectTimeout(30000);
	   connection.setRequestProperty("User-agent",userAgent);
	   //进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到 服务器
	   connection.connect();
	   //取得输入流，并使用Reader读取
	   InputStream is = connection.getInputStream();
	   //读取数据编码处理
	   BufferedReader reader = new BufferedReader(new InputStreamReader(
				is, "UTF-8"));
		while ((strRead = reader.readLine()) != null) {
			sbf.append(strRead);
			sbf.append("\r\n");
		}
		reader.close();
		//断开连接
		connection.disconnect(); 
	    return sbf.toString();
   }
}   