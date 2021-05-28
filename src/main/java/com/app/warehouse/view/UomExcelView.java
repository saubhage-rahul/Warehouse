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

import com.app.warehouse.model.Uom;

public class UomExcelView extends AbstractXlsxView {

	private static final Logger log = LoggerFactory.getLogger(UomExcelView.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside buildExcelDocument(): ");

		response.addHeader("Content-Disposition", "attachment;filename=uom.xlsx");

		@SuppressWarnings("unchecked")
		List<Uom> list = (List<Uom>) model.get("list");

		Sheet sheet = workbook.createSheet("uom");

		addHeader(sheet);

		addBody(sheet, list);

	}

	private void addHeader(Sheet sheet) {
		log.info("Inside addHeader(): ");

		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Type");
		row.createCell(2).setCellValue("Model");
		row.createCell(3).setCellValue("Desc");
	}

	private void addBody(Sheet sheet, List<Uom> list) {
		log.info("Inside addBody(): ");
		int rowNum = 1;

		for (Uom u : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(u.getUid());
			row.createCell(1).setCellValue(u.getUomType());
			row.createCell(2).setCellValue(u.getUomModel());
			row.createCell(3).setCellValue(u.getUomDesc());
		}
	}

}
