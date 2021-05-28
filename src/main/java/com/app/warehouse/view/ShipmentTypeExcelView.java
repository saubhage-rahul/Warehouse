package com.app.warehouse.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.app.warehouse.model.ShipmentType;

public class ShipmentTypeExcelView extends AbstractXlsxView {

	private static final Logger log = LoggerFactory.getLogger(ShipmentTypeExcelView.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside buildExcelDocument():");

		response.addHeader("Content-Disposition", "attachment;filename=Shipments.xlsx");

		@SuppressWarnings("unchecked")
		List<ShipmentType> list = (List<ShipmentType>) model.get("list");

		Sheet sheet = workbook.createSheet("Shipments");

		addHeader(sheet);

		addBody(sheet, list);

	}

	private void addHeader(Sheet sheet) {
		log.info("Inside addHeader():");

		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Mode");
		row.createCell(2).setCellValue("Code");
		row.createCell(3).setCellValue("Enable");
		row.createCell(4).setCellValue("Grade");
		row.createCell(5).setCellValue("Desc");

	}

	private void addBody(Sheet sheet, List<ShipmentType> list) {
		log.info("Inside addBody():");

		int rowNum = 1;
		for (ShipmentType st : list) {

			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(st.getShipId());
			row.createCell(1).setCellValue(st.getShipMode());
			row.createCell(2).setCellValue(st.getShipCode());
			row.createCell(3).setCellValue(st.getEnbleShip());
			row.createCell(4).setCellValue(st.getShipGrade());
			row.createCell(5).setCellValue(st.getShipDesc());
		}

	}

}
