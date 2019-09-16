package com.learning.framework.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataSheet {
	FileInputStream fs = null;
	int index = 0;
	File file = null;
	Workbook wb = null;

	public Object[][] readDataFromExcel(String filePath, String fileName, String sheetName) {
		int validCount = getValidScenariosCount(fileName, filePath, sheetName);
		Object[][] DataObjectArray = new Object[validCount][1];
		file = new File(filePath + fileName);
		try {
			fs = new FileInputStream(file);
			String fileExtension = fileName.substring(fileName.indexOf(".")+1);
			if (fileExtension.equalsIgnoreCase("xlsx")) {
				wb = new XSSFWorkbook(fs);
			}
			Sheet sheet = wb.getSheet(sheetName);
			int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
			Row header = sheet.getRow(0);
			for (int row = 1; row < totalRows + 1; row++) {

				Row currentRow = sheet.getRow(row);
				if (currentRow.getCell(1).getStringCellValue().equalsIgnoreCase("Yes")) {

					LinkedHashMap<String, String> mapDataSheet = new LinkedHashMap<String, String>();
					for (int col = 0; col < currentRow.getLastCellNum(); col++) {

								try {
								mapDataSheet.put(header.getCell(col).getStringCellValue(),
										currentRow.getCell(col).getStringCellValue().toString());
								}catch(IllegalStateException e) {
									mapDataSheet.put(header.getCell(col).getStringCellValue(),
											String.valueOf(currentRow.getCell(col).getNumericCellValue()));
								}
					}
					mapDataSheet.put("rowCount", String.valueOf(row));
					DataObjectArray[index] = new Object[] { mapDataSheet };
					index++;
				}
			}
			fs.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return DataObjectArray;

	}

	private int getValidScenariosCount(String fileName, String filePath, String sheetName) {
		int count = 0;
		file = new File(filePath + fileName);
		try {
			fs = new FileInputStream(file);
			String fileExtension = fileName.substring(fileName.indexOf(".") + 1);
			if (fileExtension.equalsIgnoreCase("xlsx")) {
				wb = new XSSFWorkbook(fs);
			}
			Sheet sheet = wb.getSheet(sheetName);
			int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
			Row header = sheet.getRow(0);
			for (int row = 1; row < totalRows + 1; row++) {
				Row currentRow = sheet.getRow(row);
				for (int col = 0; col < currentRow.getLastCellNum(); col++) {
					if (header.getCell(col).getStringCellValue().equalsIgnoreCase("Execution Flag")) {
						if (currentRow.getCell(col).getStringCellValue().equalsIgnoreCase("Yes")) {
							count++;
						}
					}
				}
			}
			fs.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}

}
