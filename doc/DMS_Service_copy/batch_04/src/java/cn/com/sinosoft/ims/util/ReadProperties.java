package cn.com.sinosoft.ims.util;

import java.util.ResourceBundle;

/**
 * ��ȡ�����ļ�risk-config.properties
 * 
 * @author liyu
 * 
 */
public class ReadProperties {

	public static ResourceBundle resources = ResourceBundle
			.getBundle("config/"+IConstants.Properties_Path);

	// ResourceBundle messages = ResourceBundle.getBundle("bundle.messages");
	// String message = messages.getString("welcome.message");

	public static String getString(String str) {
		return resources.getString(str);
	}

	public static void main(String[] args) {
		System.out.println(ReadProperties.getString("deployCom"));
	}
}
