package QAPractice.ExcelDataDrivenPractice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	XSSFWorkbook wrkbook;
	XSSFSheet sheet;
	FileInputStream fis;

	public void getSheet(String workBookName, String sheetName) throws IOException {
		fis = new FileInputStream(System.getProperty("user.dir") + "\\" + workBookName); //reading workbook name - comment edited - again-again
		wrkbook = new XSSFWorkbook(fis);
		sheet = wrkbook.getSheet(sheetName);
	}

	public ArrayList<ArrayList<String>> getMatchingRecords(String filterCol, String filterVal, List<String> getColumns)
			throws IOException {
		ArrayList<String> columnHeaders = new ArrayList<String>();
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> cellsIndex = new ArrayList<Integer>();

		int rowCount = sheet.getLastRowNum() + 1;
		Row firstRow = sheet.getRow(0);
		Iterator<Cell> cellsFirstRow = firstRow.cellIterator();
		int runColPos = 0;

		while (cellsFirstRow.hasNext()) {
			Cell crntCell = cellsFirstRow.next();
			columnHeaders.add(crntCell.getStringCellValue());
		}

		for (String header : getColumns) {
			cellsIndex.add(columnHeaders.indexOf(header));
			System.out.println("index of column '" + header + "' is " + columnHeaders.indexOf(header));
		}

		runColPos = columnHeaders.indexOf(filterCol);

		System.out.println("Total rows: " + rowCount);
		System.out.println("'" + filterCol + "' available at column index: " + runColPos);

		for (int i = 1; i < rowCount; i++) {
			String rowRunValue = sheet.getRow(i).getCell(runColPos).getStringCellValue();
			System.out.println("'" + filterCol + "' value at row: " + i + " is: " + rowRunValue);
			if (rowRunValue.equalsIgnoreCase(filterVal)) {
				ArrayList<String> rowValues = new ArrayList<String>();
				for (Integer colIndex : cellsIndex) {
					rowValues.add(sheet.getRow(i).getCell(colIndex).getStringCellValue());
				}
				values.add(rowValues);
			}
		}
		for (ArrayList<String> valuesRead : values) {
			System.out.println("Row values:");
			for (String valueInRow : valuesRead) {
				System.out.println(valueInRow);
			}
		}
		return values;

	}

}
