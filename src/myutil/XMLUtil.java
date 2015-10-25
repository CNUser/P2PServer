package myutil;
import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	public  static Document createDocument() {
		try {
			// ����saxReader����  
	        SAXReader reader = new SAXReader();  
	        // ͨ��read������ȡһ���ļ� ת����Document����  
	        Document document = reader.read(new File("src/info.xml")); 
	        
	        return document;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String getServerIP(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("serverIP").getTextTrim();
        
        return serverIP;
        
	}
	
	public static String getServerPort(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("serverPort").getTextTrim();
        
        return serverIP;
        
	}
	
	public static String getDBLinkURLPrefix(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String urlPrefix =  root.element("url").getTextTrim();
        
        return urlPrefix;
        
	}
	
	public static String getDBName(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String dbName =  root.element("dbname").getTextTrim();
        
        return dbName;
        
	}
	
	public static String getUserInfoTableName(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String tableName =  root.element("tablename").getTextTrim();
        
        return tableName;
        
	}
	
	public static String getDBUserName(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String dbUserName =  root.element("user").getTextTrim();
        
        return dbUserName;
	}
	
	public static String getDBPwd(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String pwd =  root.element("password").getTextTrim();
        
        return pwd;
	}
}


