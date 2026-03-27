package cn.com.sinosoft.saa.util;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.com.sinosoft.saa.vo.SaaUserPowerVO;

public class WriteExcel {
	public static HSSFWorkbook book = new HSSFWorkbook();
	public static HSSFSheet sheet = book.createSheet();// 

	@SuppressWarnings("deprecation")
	public void createTableHeader(String[] tableHeader) {
		HSSFHeader header = sheet.getHeader();
		header.setCenter("人员权限信息"); // 
		HSSFRow headerRow = sheet.createRow((short) 0);
		for (int i = 0; i < tableHeader.length; i++) {
			HSSFCell headerCell = headerRow.createCell((short) i);
			//headerCell.setEncoding(HSSFCell.ENCODING_UTF_16);
			headerCell.setCellValue(tableHeader[i]);
		}
	}

	@SuppressWarnings("deprecation")
	public void createTableRow(List<String> cells, short rowIndex) {
		HSSFRow row = sheet.createRow((short) rowIndex);
		for (short i = 0; i < cells.size(); i++) {
			HSSFCell cell = row.createCell((short) i);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(cells.get(i));
		}
	}

	public void createExcelSheeet(String[] tableHeader,	List<SaaUserPowerVO> saaUserPowerVOList) {
		createTableHeader(tableHeader);
		int rowIndex = 1;
		for(SaaUserPowerVO power:saaUserPowerVOList){
			List<String> list = new ArrayList<String>();
			list.add(power.getUserCode());
			list.add(power.getGradeId());
			list.add(power.getPermitComCodes());
			list.add(power.getExceptComCodes());
			list.add(power.getPermitProductCodes());
			createTableRow(list, (short) rowIndex);
			rowIndex++;
		}
	}

	public void exportExcel(OutputStream os)
			throws IOException {
		sheet.setGridsPrinted(true);
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "
				+ HSSFFooter.numPages());
		book.write(os);
	}

//	public static void main(String ags[]) {
//		String[] tableHeader = { "员工代码", "机构ID", "允许产品", "允许机构", "禁止机构" };
//		List<SaaUserPowerVO> saaUserPowerVOList = new ArrayList<SaaUserPowerVO>(
//				0);
//
//		SaaUserPowerVO a = new SaaUserPowerVO();
//		SaaUserPowerVO b = new SaaUserPowerVO();
//		a.setUserCode("001");
//		a.setGradeId("001");
//		a.setPermitProductCodes("100.0.1");
//		a.setPermitComCodes("001");
//		a.setExceptComCodes("001");
//		b.setUserCode("002");
//		b.setGradeId("002");
//		b.setPermitProductCodes("100.0.2");
//		b.setPermitComCodes("002");
//		b.setExceptComCodes("002");
//		saaUserPowerVOList.add(b);
//		saaUserPowerVOList.add(a);
//
//		String fileName = "c:\\员工信息.xls";
//		FileOutputStream fos = null;
//		WriteExcel writeExcel = new WriteExcel();
//		try {
//			writeExcel.createExcelSheeet(tableHeader, saaUserPowerVOList);
//			fos = new FileOutputStream(fileName);
//			writeExcel.exportExcel(sheet, fos);
//			JOptionPane.showMessageDialog(null, "表格已成功导出到 : " + fileName);
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "表格导出出错，错误信息 ：" + e
//					+ "\n错误原因可能是表格已经打开。");
//			e.printStackTrace();
//		} finally {
//			try {
//				fos.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
}

