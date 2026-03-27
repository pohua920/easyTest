package cn.com.sinosoft.saa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OpenExcel {
	/**
	 * 从第一行第一列读入excel文件
	 * 
	 * @param fileName
	 *            文件路径及名称
	 * @param sheetNum
	 *            读入第几个sheet
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Object[]> readExcel(File file, int sheetNum)
			throws FileNotFoundException, IOException {
		return this.readExcel(file, sheetNum, 0, (short) 0);
	}

	/**
	 * 从指定的行和列读入excel文件
	 * 
	 * @param fileName
	 *            文件路径及名称
	 * @param sheetNum
	 *            读入第几个sheet
	 * @param startRowNum
	 *            从第几行开始读入
	 * @param startCellNum
	 *            从第几列开始读入
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Object[]> readExcel(File file, int sheetNum, int startRowNum,
			int startCellNum) throws FileNotFoundException, IOException {
		String type = "unkown";
		XSSFWorkbook xwb = null;
		HSSFWorkbook wb = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(fs);
			type = "excel_2003-";
		} catch (Exception e1) {
			try {
				xwb = new XSSFWorkbook(file.getPath());
				type = "excel_2007+";
			} catch (Exception e) {
				System.out.println("导入的excel格式错误，请检查");
				e.printStackTrace();
			}
		}

		List<Object[]> rowList = new ArrayList<Object[]>(0);
		if (type.equals("excel_2003-")) {
			HSSFSheet fsheet = wb.getSheetAt(sheetNum - 1);
			if (startRowNum == 0) {
				startRowNum = 1;
			}
			for (int m = startRowNum ; m < fsheet.getLastRowNum() + 1; m++) {
				// 取得第m行
				HSSFRow frow = fsheet.getRow(m);
				if (frow != null && frow.getPhysicalNumberOfCells() > 0) {
					// 取得单元格
//					startCellNum = startCellNum - 1;
					if (startCellNum < frow.getFirstCellNum()) {
						startCellNum = frow.getFirstCellNum();
					}
					Object[] objs = new Object[frow.getLastCellNum()
							- startCellNum];
					for (short n = (short) startCellNum; n < frow
							.getLastCellNum(); n++) {
						HSSFCell fcell = frow.getCell(n);
						if (fcell != null
								&& !"".equals(fcell.toString().trim())) {
							// 根据类型不同，使用不同的函数
							int celltype = fcell.getCellType();
							if (celltype == HSSFCell.CELL_TYPE_NUMERIC) {
								objs[n - startCellNum] = new Double(fcell
										.getNumericCellValue()).toString();
							} else if (celltype == HSSFCell.CELL_TYPE_STRING) {
								objs[n - startCellNum] = fcell
										.getStringCellValue().trim();
							} else {
								objs[n - startCellNum] = "";
							}
						} else {
							objs[n - startCellNum] = "";
						}
					}
					rowList.add(objs);
				}

			}
		} else if (type.equals("excel_2007+")) {
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(sheetNum - 1);
			// 定义 row、cell
			XSSFRow row;
			XSSFCell cell;
			// 循环输出表格中的内容
			for (int i = startRowNum; i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				Object[] objs = new Object[row.getPhysicalNumberOfCells()];
				for (int j = startCellNum; j < row
						.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					 cell = row.getCell(j);
					 cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					 objs[j] = cell;
				}
				rowList.add(objs);
			}
		}
		// 根据excel文件创建一个POIFSFileSystem对象

		return rowList;
	}

	public static void main(String[] args) {

		// Workbook wb = wb = (Workbook) new XSSFWorkbook("c://prpdcode.xlsx");
		XSSFWorkbook xwb = null;
		try {
			xwb = new XSSFWorkbook("c://prpdcode.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		// 定义 row、cell
		XSSFRow row;
		String cell;
		// 循环输出表格中的内容
		for (int i = sheet.getFirstRowNum(); i < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			for (int j = row.getFirstCellNum(); j < row
					.getPhysicalNumberOfCells(); j++) {
				// 通过 row.getCell(j).toString() 获取单元格内容，
				cell = row.getCell(j).toString();
				System.out.print(cell + "\t");
			}
			System.out.println("");
		}

	}
}