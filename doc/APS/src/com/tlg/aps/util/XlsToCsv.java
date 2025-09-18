package com.tlg.aps.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


public class XlsToCsv {
  
    private class SheetToCSV implements SheetContentsHandler {
    	private boolean firstCellOfRow = false;
        private int currentRow = -1;
        private int currentCol = -1;

        private void outputMissingRows(int number) {
        	 for (int i = 0; i < number; i++) {  
             	curstr = new ArrayList<String>();
                 for (int j = 0; j < minColumns; j++) {  
                 	curstr.add(null);  
                 }  
                 output.add(curstr);
                 curstr = new ArrayList<String>();
             }  
        }

        @Override
        public void startRow(int rowNum) {
            outputMissingRows(rowNum - currentRow - 1);
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
        	 for (int i = currentCol; i < minColumns ; i++) {  
                 curstr.add(null);  
             }  
             output.add(curstr);
             curstr = new ArrayList<String>();
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        	if (cellReference == null) {  
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();  
            }

        	 int thisCol = (new CellReference(cellReference)).getCol();  
             int missedCols = thisCol - currentCol - 1;  
             for (int i = 0; i < missedCols; i++) {  
                 curstr.add(null);  
             }  
             currentCol = thisCol;  

             try {  
                 Double.parseDouble(formattedValue);  
                 curstr.add(formattedValue);  
             } catch (NumberFormatException e) {  
             	curstr.add(formattedValue);  
             }  
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
            // Skip, no headers or footers in CSV
        }
    }

    private final OPCPackage xlsxPackage;

    private final int minColumns;

    private ArrayList<ArrayList<String>> output;

    private ArrayList<String> curstr = new ArrayList<String>();
    
    public  ArrayList<ArrayList<String>> getOutput(){
    	return output;
    }

    public XlsToCsv(OPCPackage pkg, int minColumns) {
        this.xlsxPackage = pkg;
        this.minColumns = minColumns;
    }

    /**
     * Parses and shows the content of one sheet
     * using the specified styles and shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            SheetContentsHandler sheetHandler,
            InputStream sheetInputStream)
            throws IOException, ParserConfigurationException, SAXException {
    	 DataFormatter formatter = new DataFormatter() {
             @Override
             public String formatRawCellContents(double value, int formatIndex, String formatString, boolean use1904Windowing) {
                 if ("m/d/yy".equals(formatString)) formatString = "yyyy/MM/dd";
                 return super.formatRawCellContents(value, formatIndex, formatString, use1904Windowing);
             }
         };
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(
                    styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public ArrayList<ArrayList<String>> process() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
    	 try{
             ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
             XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
             StylesTable styles = xssfReader.getStylesTable();
             XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
             while (iter.hasNext()) {
                 output = new ArrayList<ArrayList<String>> ();
                 InputStream stream = iter.next();
                 processSheet(styles, strings, new SheetToCSV(), stream);
                 close(stream);
                 return output;
             }
         } catch (OpenXML4JException open) {
             open.printStackTrace();
         } catch (ParserConfigurationException parser) {
             parser.printStackTrace();
         } catch (SAXException sax) {
             sax.printStackTrace();
         } catch (IOException io) {
             io.printStackTrace();
         }
         return null;
    }
    
    private void close(InputStream stream){
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}