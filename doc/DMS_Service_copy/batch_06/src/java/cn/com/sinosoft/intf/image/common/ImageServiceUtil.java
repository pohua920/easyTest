package cn.com.sinosoft.intf.image.common;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.intf.image.model.vo.BASE_DATA;
import cn.com.sinosoft.intf.image.model.vo.META_DATA;
import cn.com.sinosoft.intf.image.model.vo.OTHER_DATA;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @功能：影像系统xml转换类 <p>
 *                主要包括：影像系统xml转换类
 *                </p>
 * @作者：乌志强
 * @日期：2010-07-02
 * @修改记录：
 */
public class ImageServiceUtil {

	/** 日志 */
	private static Log log = LogFactory.getLog(ImageServiceUtil.class);

	// /** 接口工具接口 */
	// private CommonServiceInf commonServiceInf;
	//
	// /**
	// * @功能：构造方法
	// * @param
	// * @return
	// * @throws
	// * @作者：陶宇杰
	// * @日期：2010-08-10
	// * @修改记录：
	// */
	// public ImageServiceUtil(CommonServiceInf commonServiceInf){
	// this.commonServiceInf = commonServiceInf;
	// }

	/**
	 * @功能：配置Xml和Bean的映射关系
	 * @param xs  --**XStream对象
	 * @return xs --**返回XStream对象
	 * @throws
	 * @作者：乌志强
	 * @日期：2010-06-29
	 * @修改记录：
	 */
	public XStream xSteamConfig(XStream xs) {
		xs.alias("BASE_DATA", BASE_DATA.class);
		xs.alias("META_DATA", META_DATA.class);
		return xs;
	}

	/**
	 * @功能：将组装对象转为影像接口所需xml
	 * @param bdata  -**BASE_DATA对象
	 * @param mdatas -**List<META_DATA>对象
	 * @return returnXML -**返回的影像接口所需xml
	 * @throws
	 * @作者：乌志强
	 * @日期：2010-07-02
	 * @修改记录：
	 */
	public String dtoToXML(BASE_DATA bdata, List<META_DATA> mdatas) {

		XStream xs = new XStream(new DomDriver());
		xs.alias("BASE_DATA", BASE_DATA.class);
		xs.alias("META_DATA", META_DATA.class);

		StringBuffer returnXML = new StringBuffer();

		returnXML.append("<?xml version=\'1.0\' encoding=\'UTF-8\' ?>");
		returnXML.append("<root>");

		String strBdata = xs.toXML(bdata);
		returnXML.append(strBdata);

		returnXML.append("<META_DATAS>");
		String strMdata = "";
		for (META_DATA mdata : mdatas) {
			strMdata = xs.toXML(mdata);
			returnXML.append(strMdata);
		}
		returnXML.append("</META_DATAS>");

		returnXML.append("</root>");

		return returnXML.toString();
	}

	/**
	 * @功能：将组装对象转为影像接口所需xml
	 * @param bdata  -**BASE_DATA对象
	 * @param mdatas -**List<META_DATA>对象
	 * @param imgtypedata --**IMGTYPE_DATA对象
	 * @return returnXML -**返回的影像接口所需xml
	 * @throws
	 * @作者：乌志强
	 * @日期：2010-07-02
	 * @修改记录：
	 */
	public String dtoToXML(BASE_DATA bdata, List<META_DATA> mdatas,OTHER_DATA odata) {

		XStream xs = new XStream(new DomDriver());
		xs.alias("BASE_DATA", BASE_DATA.class);
		xs.alias("META_DATA", META_DATA.class);

		StringBuffer returnXML = new StringBuffer();

		returnXML.append("<?xml version=\'1.0\' encoding=\'UTF-8\' ?>");
		returnXML.append("<root>");

		String strBdata = xs.toXML(bdata);
		returnXML.append(strBdata);

		String strMdata = "";
		for (META_DATA mdata : mdatas) {
			returnXML.append("<META_DATA BATCH_ID=\'001\'>");
			returnXML.append("<APP_CODE>UW</APP_CODE>");
			returnXML.append("<INS_TYPE>UW_D</INS_TYPE>");
			returnXML.append("<BUSI_NUM>UWD1105091612496870631389</BUSI_NUM>");
			returnXML.append("</META_DATA>");
			//strMdata = xs.toXML(mdata);
			//returnXML.append(strMdata);
		}
		
		if(odata != null){
			returnXML.append("<OTHER_DATA>");
			returnXML.append("<IMGTYPE_DATA ID=\'"+mdatas.get(0).getBUSI_NUM()+"\'>");
			for (String imgType : odata.getImgTypes()) {
				returnXML.append("<IMGTYPE>");
				returnXML.append(imgType);
				returnXML.append("</IMGTYPE>");
			}
			returnXML.append("</IMGTYPE_DATA>");
			for (String itemcode : odata.getItemcodes()) {
				returnXML.append("<ITEM_CODE>");
				returnXML.append(itemcode);
				returnXML.append("</ITEM_CODE>");
			}
			
			returnXML.append("</OTHER_DATA>");
		}
		returnXML.append("</root>");

		return returnXML.toString();
	}
	
	/*
	 * add by duanfa 2011-05-27 
	 */
	public String dtoToXML(BASE_DATA bdata, META_DATA mdata) {

		XStream xs = new XStream(new DomDriver());
		xs.alias("BASE_DATA", BASE_DATA.class);
		xs.alias("META_DATA", META_DATA.class);

		StringBuffer returnXML = new StringBuffer();

		returnXML.append("<?xml version=\'1.0\' encoding=\'UTF-8\' ?>");
		returnXML.append("<root>");

		String strBdata = xs.toXML(bdata);
		returnXML.append(strBdata);

		String strMdata = xs.toXML(mdata);
		returnXML.append(strMdata);

		returnXML.append("</root>");

		return returnXML.toString();
	}

	
}
