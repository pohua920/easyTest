package cn.com.sinosoft.inf.dict.util;

import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.dms.web.PrpDbankAction;
import cn.com.sinosoft.ims.util.IConstants;

import com.wutka.dtd.DTD;
import com.wutka.jox.JOXBeanOutputStream;

/**
 * Jox支持类，提供本系统扩展方法
 * 
 * @version 2009-07-17 李子扬 初始化版本
 */
public class JoxSupport {

	// 定义常量
	private static final String ENCODING = IConstants.ENCODING_UTF8;
	private static final String ROOT_NAME = "PACKET";
	private static Log log = LogFactory.getLog(JoxSupport.class);

	// 单实例
	private static final JoxSupport instance = new JoxSupport();

	/** 获取实例 */
	public static JoxSupport getInstance() {
		return instance;
	}

	/** 构造函数 */
	private JoxSupport() {
		// Do Nothing.
	}

	/** 按照指定DTD转换对象，得到XML字符串 */
	public String requestConvert(Object obj, String requestType)
			throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
		try {
			log.debug("::::JoxSupport::::::requestType:" + requestType);
//			System.out
//					.println("::::JoxSupport::::::requestType:" + requestType);
			DTD dtd = ResSupport.getInstance().getReqDtdObject(requestType);
			JOXBeanOutputStream joxOut = new JOXBeanOutputStream(dtd, out,
					IConstants.ENCODING_UTF8);

			joxOut.writeObject(ROOT_NAME, obj);
			// System.out.println("---接口-------服务器端 --------报文:" +
			// out.toString());
			return out.toString(IConstants.ENCODING_UTF8);
		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
