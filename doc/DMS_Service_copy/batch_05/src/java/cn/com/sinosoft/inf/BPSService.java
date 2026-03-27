package cn.com.sinosoft.inf;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXParseException;

import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ErrorMessagePacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.SYNResPacket;

import com.sinosoft.sysframework.exception.BaseException;
import com.sinosoft.sysframework.exception.BusinessException;
import com.sinosoft.sysframework.exception.ExceptionCause;
import com.thoughtworks.xstream.XStream;

/**
 * servlet
 * @author hua
 * 鎶ユ枃鎺ユ敹鍜岃浆鍙戝苟涓斿湪姝ら泦涓鐞嗗紓甯�
 */
public class BPSService extends HttpServlet {
	private static Log log = LogFactory.getLog(BPSService.class);
	private static final long serialVersionUID = 1L;

	/** 鏂囨湰锛歵ext/html; charset=UTF-8 */
	public static final String CONTENT_TYPE = "text/html; charset="
			+ IConstants.ENCODING_UTF8 + "";

	/** 鏂囨湰锛�.. */
	public static final String TEXT_PACKET = "<PACKET>";

	/** 鏂囨湰锛�.. */
	public static final String TEXT_PACKET_EXT = "<PACKET type=\"RESPONSE\" version=\"1.0\">";

	/** 鏂囨湰锛歎TF-8 */
	public static final String TEXT_UTF = IConstants.ENCODING_UTF8;

	/** 鏂囨湰锛氾紙绌猴級 */
	public static final String TEXT_EMPTY = "";
	/** 鏂囨湰锛歍oo Long */
	public static final String TEXT_TOO_LONG = "Too Long";

	/** 鏂囨湰锛歔CommServer: */
	public static final String TEXT_INFO_01 = "[CommServer:";

	/** 鏂囨湰锛歖 */
	public static final String TEXT_INFO_02 = "]";

	/** 鏂囨湰锛�IP: */
	public static final String TEXT_INFO_03 = " Begin - RemoteIP: ";

	public static final String TEXT_INFO_08 = " / ";

	/** 鏂囨湰锛�URI: */
	public static final String TEXT_INFO_04 = "; URI: ";

	/** 鏂囨湰锛�IN: */
	public static final String TEXT_INFO_05 = " Info  - Input: ";

	/** 鏂囨湰锛�OUT: */
	public static final String TEXT_INFO_06 = " Info  - Output: ";

	/** 鏂囨湰锛�OUT: */
	public static final String TEXT_INFO_07 = " End   - Seconds: ";

	/** 鏂囨湰锛�... */
	public static final String TEXT_ERRPR_01 = "<?xml version=\"1.0\" encoding=\""
			+ IConstants.ENCODING_UTF8
			+ "\"?><PACKET type=\"RESPONSE\" version=\"1.0\" ><HEAD><REQUEST_TYPE>";

	/** 鏂囨湰锛�... */
	public static final String TEXT_ERRPR_02 = "</REQUEST_TYPE><RESPONSE_CODE>0</RESPONSE_CODE><ERROR_CODE>";

	/** 鏂囨湰锛�... */
	public static final String TEXT_ERRPR_03 = "</ERROR_CODE><ERROR_MESSAGE>";

	/** 鏂囨湰锛�... */
	public static final String TEXT_ERRPR_04 = "</ERROR_MESSAGE></HEAD></PACKET>";

	public BPSService() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/***********************************************************************
		 * 銆愭帴鏀舵暟鎹�鐢ㄤ簩杩涘埗娴佹帴鏀舵暟鎹�
		 **********************************************************************/
		// 鐢ㄤ簩杩涘埗娴佹帴鏀舵暟鎹�
		InputStream in = request.getInputStream();// 缂虹渷buffter涓�048

		// 杈撳叆娴�鐢ㄤ簬鎺ユ敹璇锋眰鐨勬暟鎹�
		BufferedInputStream input = null;

		// 璇锋眰鏁版嵁瀛樻斁瀵硅薄锛屼娇鐢ㄤ簡鍙傛暟2048
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(2048);

		// 鎺ュ彈鏁版嵁
		try {
			// 鏁版嵁缂撳啿鍖�
			byte[] bufferRead = new byte[1024];

			// 鐢ㄤ簩杩涘埗娴佹帴鏀秞ml璇锋眰淇℃伅
			input = new BufferedInputStream(in);

			// 姣忎釜缂撳啿鍖虹殑瀹為檯鏁版嵁闀垮害
			int count = 0;

			while ((count = input.read(bufferRead)) != -1) {
				byteOutput.write(bufferRead, 0, count);
			}
			// 濡傛灉闇�杈撳嚭鍘熷鐨剎ml淇℃伅灏辨妸涓嬮潰鐨勬敞閲婂幓鎺夛細
			// srvLogger.info("鎺ュ埌鍘熷XML淇℃伅锛�+ byteXML.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // --鍏抽棴杩炴帴
			if (input != null) {
				try {
					input.close();
				} catch (Exception e1) {
				}
			}
		}
		/**********************************************
		 * 杞崲鎴怱tring鐨剎ml
		 ************************************************* */
		byte[] requestByteArray = byteOutput.toByteArray();
		// int requestByteLength = requestByteArray.length;
		String requestMessage = new String(requestByteArray,
				IConstants.ENCODING_UTF8);
		log.debug("鈽呮帴鏀跺埌鐨勬姤鏂囷細" + requestMessage);
		// System.out.println(requestMessage);
		String requestType = getTagValue(requestMessage, "<REQUEST_TYPE>");
		System.out.println("进入DMS的服务中 ============================================================");
		/***********************************************************************
		 * 璇锋眰xml杞崲鎴怱chema瀵硅薄,涓氬姟澶勭悊骞惰繑鍥炲鐞嗙粨鏋滅殑xml淇℃伅
		 **********************************************************************/
		String responseMessage = "";
		try {
			responseMessage = Dispatcher.getInstance().handle(requestMessage);
		} catch (BaseException userException) {
			responseMessage = returnErrMessage(userException, requestType);
			// 鎹曡幏鑷畾涔夌殑寮傚父
			userException.printStackTrace();
		} catch (SQLException sqlException) {// 鏁版嵁搴撳紓甯�
			sqlException.printStackTrace();
			BusinessException userException = new BusinessException(
					ServiceInfoConst.ERROR_CODE_SQL,
					ServiceInfoConst.ERROR_MESSAGE_SQL);
			responseMessage = returnErrMessage(userException, requestType);

		} catch (SAXParseException saxParseException) {// xml瑙ｆ瀽寮傚父
			saxParseException.printStackTrace();
			BusinessException userException = new BusinessException(
					ServiceInfoConst.ERROR_CODE_JOX,
					ServiceInfoConst.ERROR_MESSAGE_JOX);
			responseMessage = returnErrMessage(userException, requestType);
		} catch (Exception exception) {// else
			exception.printStackTrace();
			BusinessException userException = new BusinessException(
					ServiceInfoConst.ERROR_CODE_EXC,
					exception.toString());//淇敼涓烘妸寮傚父淇℃伅鐩存帴鎶涘嚭銆�009-11-30 婊戠珛鏁�
			responseMessage = returnErrMessage(userException, requestType);
		}

		/***********************************************************************
		 * // * 銆愯繘琛屾墦鍖呭鐞嗐�娣诲姞蹇呰鐨勪俊鎭ご銆�
		 **********************************************************************/

		// StringBuilder sb = new StringBuilder();
		// int index = responseMessage.indexOf("<?xml");
		// if (index >= 0) {
		// sb.append(responseMessage.substring(0, index));
		// sb.append("<PACKET type=\"RESPONSE\" version=\"1.0\">");//娣诲姞<PACKET
		// type=\"RESPONSE\" version=\"1.0\">鏈変粈涔堜綔鐢紵
		// sb.append(responseMessage.substring(index + 8));
		// }
		// responseMessage = sb.toString();
		log.debug("鈽嗗噯澶囪繑鍥炵殑鎶ユ枃:" + responseMessage);
		byte[] byteMessages = responseMessage.getBytes(IConstants.ENCODING_GBK);
//		int responseByteLength = byteMessages.length;

		/***********************************************************************
		 * 銆愯緭鍑烘湇鍔＄殑缁撴灉銆�
		 **********************************************************************/
		OutputStream out = response.getOutputStream();

		response.setContentType(CONTENT_TYPE);// setContentType??鐢℅BK鏈変粈涔堣娉ㄦ剰鐨勫悧锛�

		response.setContentLength(byteMessages.length);// 娣诲姞鍝嶅簲鍖呴暱搴︼紝蹇呴』鐨勫悧锛�
		out.write(byteMessages); // 鍐欏叆鏁版嵁
		out.flush(); // 缁撴潫缂撳啿
		out.close(); // 鍏抽棴
	}

	/**
	 * 澶勭悊寮傚父鐨勬柟娉�缁勭粐杩斿洖閿欒淇℃伅銆�
	 */
	private String returnErrMessage(BaseException userException,
			String requestType) {
		String returnMessage = TEXT_EMPTY;
		ErrorMessagePacket schema = new ErrorMessagePacket();
		List aa = userException.getCauseList();
		ExceptionCause bb = (ExceptionCause) aa.get(0);
		String cc = bb.getMessageKey();
		String dd = (String) bb.getMessageArgs()[0];
		schema.getHEAD().setERROR_MESSAGE(dd);
		schema.getHEAD().setERROR_CODE(cc);
		schema.getHEAD().setREQUEST_TYPE(requestType);
		schema.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_FAIL);

		try {
			int type=Integer.parseInt(requestType.trim().substring(1));
			if(type >= 77){
				XStream xstream = new XStream();
				xstream.alias("SYNResPacket",SYNResPacket.class);
				xstream.alias("PageResPacket", ErrorMessagePacket.class);
				xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
				returnMessage = xstream.toXML(schema);
			}else{
				returnMessage = JoxSupport.getInstance().requestConvert(schema,
						requestType);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnMessage;
	}

	/** 閫氳繃瀛楃涓叉埅鍙栵紝鑾峰緱鏍囩鐨勫� */
	private String getTagValue(String requestMessage, String tag) {
		int beginIndex = requestMessage.indexOf(tag);// 寰楀埌寮�鏍囪<tag>涓殑"<"鐨勮捣濮嬩綅缃�
		/**瑙ｅ喅xstream涓嬪垝绾块棶棰�*************start*/
		if(beginIndex == -1){
			tag = tag.replace("_", "__");
			beginIndex = requestMessage.indexOf(tag);
		}
		/**瑙ｅ喅xstream涓嬪垝绾块棶棰�*************end*/
		int endIndex = -1;

		String tagValue = "";

		if (beginIndex >= 0) {
			String endTag = "</" + tag.substring(1);
			endIndex = requestMessage.indexOf(endTag); // 寰楀埌缁撴潫鏍囪</tag>涓殑"<"鐨勮捣濮嬩綅缃�
			tagValue = requestMessage.substring(beginIndex + tag.length(),
					endIndex);
			tagValue = tagValue.replaceAll("\r", TEXT_EMPTY);
			tagValue = tagValue.replaceAll("\n", TEXT_EMPTY);
			tagValue = tagValue.replaceAll("\t", TEXT_EMPTY);
			tagValue = tagValue.trim();
		}
		return tagValue;
	}
}
