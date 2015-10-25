package myutil;
import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	public  static Document createDocument() {
		try {
			// 创建saxReader对象  
	        SAXReader reader = new SAXReader();  
	        // 通过read方法读取一个文件 转换成Document对象  
	        Document document = reader.read(new File("src/info.xml")); 
	        
	        return document;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String getServerIP(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("serverIP").getTextTrim();
        
        return serverIP;
        
	}
	
	public static String getServerPort(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("serverPort").getTextTrim();
        
        return serverIP;
        
	}
	
	public static String getDBLinkURLPrefix(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String urlPrefix =  root.element("url").getTextTrim();
        
        return urlPrefix;
        
	}
	
	public static String getDBName(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String dbName =  root.element("dbname").getTextTrim();
        
        return dbName;
        
	}
	
	public static String getUserInfoTableName(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String tableName =  root.element("tablename").getTextTrim();
        
        return tableName;
        
	}
	
	public static String getDBUserName(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String dbUserName =  root.element("user").getTextTrim();
        
        return dbUserName;
	}
	
	public static String getDBPwd(Document document) {
		//获取根节点元素对象  
        Element root = document.getRootElement(); 
        
        String pwd =  root.element("password").getTextTrim();
        
        return pwd;
	}
}


