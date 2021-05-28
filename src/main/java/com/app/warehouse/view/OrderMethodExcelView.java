package com.app.warehouse.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.app.warehouse.model.OrderMethod;

public class OrderMethodExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.addHeader("Content-Disposition", "attachment;filename=orderMethod.xlsx");

		@SuppressWarnings("unchecked")
		List<OrderMethod> list = (List<OrderMethod>) model.get("list");

		Sheet sheet = workbook.createSheet("orderMethod");

		addHeader(sheet);

		addBody(sheet, list);

	}

	private void addHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("OrderMode");
		row.createCell(2).setCellValue("OrderCode");
		row.createCell(3).setCellValue("OrderType");
		row.createCell(4).setCellValue("Order Acceptence");
		row.createCell(5).setCellValue("Description");

	}

	private void addBody(Sheet sheet, List<OrderMethod> list) {
		int rowNum = 1;

		for (OrderMethod orderM : list) {

			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(orderM.getId());
			row.createCell(1).setCellValue(orderM.getOrderMode());
			row.createCell(2).setCellValue(orderM.getOrderCode());
			row.createCell(3).setCellValue(orderM.getOrderType());
			row.createCell(4).setCellValue(orderM.getOrderAccept().toString());
			row.createCell(5).setCellValue(orderM.getOrderDesc());
		}

	}

}
