package com.worldclock.utility;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.config.PropertyConfig;
import com.worldclock.utility.constants.ErrorConstants;

public class ExcelUtils extends BaseTestConfig{

	private String path;
	private static FileOutputStream fileOutputStream;
	private static Workbook workBook;
	private static CellStyle cellStyle;
	
	private static final Logger logger = LogManager.getLogger(ExcelUtils.class);

	public ExcelUtils() {
		this.path = PropertyConfig.getExcelFilePath();
		File fileDir;
		File file;
		try {
//			to access excel file
			file = new File(path);
//			to access excel file directory
			fileDir = file.getParentFile();
			if (!fileDir.exists()) {
				logger.debug("excel-output directory absent. Creating directory...");
				fileDir.mkdirs();
			}
			if (!file.exists()) {
				logger.debug("Excel file absent. Creating new excel file...");
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.EXCEL_FILE_IO_ERR);
		}
		if (workBook == null) {
			logger.debug("Workbook absent. Creating new excel workbook...");
			workBook = new XSSFWorkbook();
			cellStyle = workBook.createCellStyle();
			cellStyle.setWrapText(true);
		}
	}

	public Sheet getExcelSheet(String sheetName) {
//		if sheet is not present with the designated name then create it
		if (workBook.getSheet(sheetName) == null)
		{
			logger.debug("Excel sheet with the name "+sheetName+" absent. Creating sheet...");
			return workBook.createSheet(sheetName);
		}
		return workBook.getSheet(sheetName);
	}

	public void writeData(String data, Sheet sheet, int rowNum, int cellNum) {
		Row row;
		Cell cell;

		row = sheet.getRow(rowNum);
		if (row == null) {
			logger.debug("Excel row no. "+rowNum+" absent. Creating row...");
			row = sheet.createRow(rowNum);
		}
		cell = row.getCell(cellNum);
		if (cell == null)
		{
			logger.debug("Excel cell no. "+cellNum+" absent for row "+rowNum+". Creating cell...");
			cell = row.createCell(cellNum);
		}
		cell.setCellValue(data);
		cell.setCellStyle(cellStyle);
		sheet.setColumnWidth(cellNum, 40 * 256);
	}

	public static void saveData() {
		try {
			workBook.write(fileOutputStream);
			logger.info("Excel workbook saved.");
		} catch (Exception e) {
			logger.warn("Unable to save excel workbook (if created) in directory(if present).");
		}
	}
}
