package com.aman.test.commons.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;



/**
 * Reader impletementation of {@link Workbook} for an OOXML .xlsx file. This implementation is
 * suitable for low memory sax parsing or similar.
 *
 * @see XlsReader
 */
@SuppressWarnings("ALL")
public class XlsxReader implements SpreadsheetReader {

	private static Logger LOGGER = LogManager.getLogger(XlsxReader.class);


    // Constructor

    public XlsxReader() {
        super();
    }


    // SpreadsheetReader Impl
    // ------------------------------------------------------------------------

    @Override
    public <T> void read(Class<T> beanClz, InputStream is, RowListener<T> listener)
            throws Exception {


        try {
            final OPCPackage opcPkg = OPCPackage.open(is);

            // XSSF Reader
            XSSFReader xssfReader = new XSSFReader(opcPkg);

            // Content Handler
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable ssTable = new ReadOnlySharedStringsTable(opcPkg);
            SheetContentsHandler sheetHandler = new RowContentsHandler<T>(beanClz, listener, 0);

            ContentHandler handler = new XSSFSheetXMLHandler(styles, ssTable, sheetHandler, true);

            // XML Reader
            XMLReader xmlParser = SAXHelper.newXMLReader();
            xmlParser.setContentHandler(handler);

            // Iterate over sheets
            XSSFReader.SheetIterator worksheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            for (int i = 0; worksheets.hasNext(); i++) {
                InputStream sheetInpStream = worksheets.next();

                String sheetName = worksheets.getSheetName();

                // Parse sheet
                xmlParser.parse(new InputSource(sheetInpStream));
                sheetInpStream.close();
            }
        } catch (Exception ex) {
            String errMsg = String.format("Error reading sheet data, to Bean %s : %s", beanClz, ex.getMessage());
            LOGGER.error(errMsg, ex);
            throw new Exception(errMsg, ex);
        }
    }


    @Override
    public <T> void read(Class<T> beanClz, InputStream is, String sheetName, RowListener<T> listener)
            throws Exception {
        // Sanity checks

        try {
            final OPCPackage opcPkg = OPCPackage.open(is);

            // XSSF Reader
            XSSFReader xssfReader = new XSSFReader(opcPkg);

            // Content Handler
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable ssTable = new ReadOnlySharedStringsTable(opcPkg);
            SheetContentsHandler sheetHandler = new RowContentsHandler<T>(beanClz, listener, 0);

            ContentHandler handler = new XSSFSheetXMLHandler(styles, ssTable, sheetHandler, true);

            // XML Reader
            XMLReader xmlParser = SAXHelper.newXMLReader();
            xmlParser.setContentHandler(handler);

            // Iterate over sheets
            XSSFReader.SheetIterator worksheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            while (worksheets.hasNext() ) {
                InputStream sheetInpStream = worksheets.next();
                String sheetNameActual = worksheets.getSheetName();
                if(sheetNameActual.equals(sheetName)) {
                	 xmlParser.parse(new InputSource(sheetInpStream));
                     sheetInpStream.close();
                }
            }

        } catch (Exception ex) {
            String errMsg = String.format("Error reading sheet %d, to Bean %s : %s", sheetName, beanClz, ex.getMessage());
            LOGGER.error(errMsg, ex);
            throw new Exception(errMsg, ex);
        }
    }


    @Override
    public <T> void read(Class<T> beanClz, File file, RowListener<T> callback) throws Exception {
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {

            // chain
            this.read(beanClz, fis, callback);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new Exception(errMsg, ex);
        }
    }

    public <T> List<T> read(Class<T> beanClz, String fileName, String sheetName) throws Exception {
        File file = new File("./src/main/resources/testData/" + fileName);
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {
            return this.read(beanClz, fis, sheetName);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new Exception(errMsg, ex);
        }
    }



    @Override
    public <T> List<T> read(Class<T> beanClz, File file) throws Exception {
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {
            return this.read(beanClz, fis);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new Exception(errMsg, ex);
        }
    }

    @Override
    public <T> List<T> read(Class<T> beanClz, InputStream is) throws Exception {
        // Result
        final List<T> sheetBeans = new ArrayList<T>();

        // Read with callback to fill list
        this.read(beanClz, is, new RowListener<T>() {

            @Override
            public void row(int rowNum, T rowObj) {
                if (rowObj == null) {
                    LOGGER.error("Null object returned for row : {}", rowNum);
                    return;
                }

                sheetBeans.add(rowObj);
            }

        });

        return sheetBeans;
    }

    @Override
    public <T> List<T> read(Class<T> beanClz, File file, String sheetName) throws Exception {
        // Closeble
        try (InputStream fis = new FileInputStream(file)) {
            return this.read(beanClz, fis, sheetName);
        } catch (IOException ex) {
            String errMsg = String.format("Failed to read file as Stream : %s", ex.getMessage());
            throw new Exception(errMsg, ex);
        }
    }



    public <T> List<T> read(Class<T> beanClz, InputStream is, String sheetName) throws Exception {
        // Result
        final List<T> sheetBeans = new ArrayList<T>();

        // Read with callback to fill list
        this.read(beanClz, is, sheetName, new RowListener<T>() {

            @Override
            public void row(int rowNum, T rowObj) {
                if (rowObj == null) {
                    LOGGER.error("Null object returned for row : {}", rowNum);
                    return;
                }

                sheetBeans.add(rowObj);
            }

        });

        return sheetBeans;
    }


}
