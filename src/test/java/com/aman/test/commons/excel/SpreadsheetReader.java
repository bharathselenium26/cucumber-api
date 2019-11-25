package com.aman.test.commons.excel;

import java.io.File;
import java.io.InputStream;
import java.util.List;



/**
 * An Abstract representation of a Spreadsheet Reader. Any reader implementation (HSSF or XSSF) is
 * expected to implement and provide the below APIs.
 * 
 */
public interface SpreadsheetReader {


    // Read with Custom RowListener

    <T> void read(Class<T> beanClz, File file, RowListener<T> listener) throws Exception;
    <T> void read(Class<T> beanClz, InputStream is, RowListener<T> listener) throws Exception;
    <T> List<T> read(Class<T> beanClz, File file) throws Exception;
    <T> List<T> read(Class<T> beanClz, InputStream is) throws Exception;
    <T> List<T> read(Class<T> beanClz, File file, String sheetName) throws Exception;
    <T> void read(Class<T> beanClz, InputStream is, String sheetName, RowListener<T> listener)
			throws Exception;


}
