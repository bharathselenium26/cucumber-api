package com.aman.test.commons.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;


public class RowContentsHandler<T> implements SheetContentsHandler {

	private static Logger LOGGER = LogManager.getLogger(RowContentsHandler.class);

	private final Class<T> beanClz;
	private int currentRow = 0;
	private Map<String, Object> currentRowObj;
	private final int headerRow;
	private final Map<String, String> cellPropertyMap;
	private final RowListener<T> rowListener;

	public RowContentsHandler(Class<T> beanClz, RowListener<T> rowListener) {
		this(beanClz, rowListener, 0);
	}

	public RowContentsHandler(Class<T> beanClz, RowListener<T> rowListener, int headerRow) {
		super();
		this.beanClz = beanClz;
		this.headerRow = headerRow;
		this.cellPropertyMap = new HashMap<String, String>();
		this.rowListener = rowListener;
	}

	@Override
	public void startRow(int rowNum) {
		this.currentRow = rowNum;
		this.currentRowObj = new HashMap<String, Object>();
	}

	@Override
	public void endRow(int rowNum) {
		this.afterRowEnd(rowNum, new HashMap<String, Object>(currentRowObj));
	}

	@Override
	public void cell(String cellRef, String cellVal, XSSFComment comment) {
		if (StringUtils.isEmpty(cellRef)) {
			LOGGER.error("Row[#] {} : Cell reference is empty - {}", currentRow, cellRef);
			return;
		}

		if (StringUtils.isEmpty(cellVal)) {
			LOGGER.warn("Row[#] {} - Cell[ref] formatted value is empty : {} - {}", currentRow, cellRef, cellVal);
			return;
		}

		String cellColRef = Spreadsheet.getCellColumnReference(cellRef);

		LOGGER.debug("cell - Saving Column value : {} - {}", cellColRef, cellVal);
		currentRowObj.put(cellColRef, cellVal);
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {

	}

	void afterRowEnd(int rowNum, Map<String, Object> rowDataMap) {
		if (rowDataMap == null || rowDataMap.isEmpty()) {
			return;
		}
		if (rowNum < headerRow) {
			return;
		}

		if (rowNum == headerRow) {
			final Map<String, String> headerMap = this.prepareHeaderMap(rowNum, rowDataMap);
			cellPropertyMap.putAll(headerMap);
			return;
		}

		T rowBean = Spreadsheet.rowAsBean(beanClz, cellPropertyMap, rowDataMap);
		try {
			rowListener.row(rowNum, rowBean);
		} catch (Exception ex) {
			String errMsg = String.format("Error calling listener callback  row - %d, bean - %s", rowNum, rowBean);
			LOGGER.error(errMsg, ex);
		}
	}


	private Map<String, String> prepareHeaderMap(int rowNo, Map<String, Object> rowDataMap) {
		if (rowDataMap == null || rowDataMap.isEmpty()) {
			String errMsg = String.format("Invalid Header data found - Row #%d", rowNo);
			throw new RuntimeException(errMsg);
		}

		final Map<String, String> colToBeanPropMap = Spreadsheet.getColumnToPropertyMap(beanClz);

		final Map<String, String> headerMap = new HashMap<String, String>();
		for (String collRef : rowDataMap.keySet()) {
			String colName = String.valueOf(rowDataMap.get(collRef));
			String propName = colToBeanPropMap.get(colName);
			if (StringUtils.isNotEmpty(propName)) {
				headerMap.put(collRef, String.valueOf(propName));
			}
		}

		LOGGER.debug("Header DataMap prepared : {}", headerMap);
		return headerMap;
	}

}
