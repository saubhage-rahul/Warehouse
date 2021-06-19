package com.app.warehouse.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.app.warehouse.model.ShipmentType;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ShipmentTypePDFView extends AbstractPdfView {

	private static final Logger log = LoggerFactory.getLogger(ShipmentTypePDFView.class);

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		log.info("Inside buildPdfMetadata():");
		// to add header, 2nd param must be false, true means generates number
		HeaderFooter header = new HeaderFooter(new Phrase("ShipmentType PDF View"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		// add to document as header
		document.setHeader(header);

		// to add header, 2nd can be null/data(display after number) or true
		HeaderFooter footer = new HeaderFooter(new Phrase("Generated By Warehouse - Page#"), null);
		footer.setAlignment(Element.ALIGN_RIGHT);
		// add to document as footer
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Inside buildPdfDocument():");

		// file download
		response.addHeader("Content-Disposition", "attachment;filename=ShipmentType.pdf");

		// font familiy, size, style, color
		Font titleFont = new Font(Font.TIMES_ROMAN, 22, Font.BOLD, Color.RED);

		// paragraph -- text and font
		Paragraph p = new Paragraph("Shipment Type Data PDF", titleFont);

		// alignment , left/center/right
		p.setAlignment(Element.ALIGN_CENTER);

		// give space between paragraph and next element(table)
		p.setSpacingAfter(10.0f);

		// add paragraph to document
		document.add(p);

		@SuppressWarnings("unchecked")
		List<ShipmentType> list = (List<ShipmentType>) model.get("list");

		// font for table heading
		Font tableFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, new Color(51, 153, 102));

		// creating table with no.of column(not rows)
		PdfPTable table = new PdfPTable(6); // 1 row = 6 columns
		table.addCell(new Phrase("ID", tableFont));
		table.addCell(new Phrase("MODE", tableFont));
		table.addCell(new Phrase("CODE", tableFont));
		table.addCell(new Phrase("ENABLE", tableFont));
		table.addCell(new Phrase("GRADE", tableFont));
		table.addCell(new Phrase("DESC", tableFont));

		// add list data given by Controller
		for (ShipmentType st : list) {
			// convert non-String data(int,double..) to String type.
			table.addCell(st.getShipId().toString());
			table.addCell(st.getShipMode());
			table.addCell(st.getShipCode());
			table.addCell(st.getEnbleShip());
			table.addCell(st.getShipGrade());
			table.addCell(st.getShipDesc());
		}

		// add table to document
		document.add(table);

	}
}
