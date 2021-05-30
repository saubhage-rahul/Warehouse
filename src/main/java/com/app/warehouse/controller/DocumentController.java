package com.app.warehouse.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.warehouse.Exception.DocumentNotFoundException;
import com.app.warehouse.model.Document;
import com.app.warehouse.service.IDocumentService;

@Controller
@RequestMapping("/doc")
public class DocumentController {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private IDocumentService service;

	// 1. Get All Documents
	@GetMapping("/all")
	public String showDocuments(Model model) {
		LOG.info("Inside showDocuments():");
		try {
			List<Object[]> list = service.getDocumentIDAndName();
			LOG.debug("Fetch All Docs : " + list);
			model.addAttribute("list", list);

		} catch (Exception e) {
			LOG.error("Exception inside showDocuments(): " + e.getMessage());
			e.printStackTrace();
		}
		LOG.info("About Documents UI Page:");
		return "Documents";
	}

	// 2. Upload Documents
	@PostMapping("/upload")
	public String uploadDocument(@RequestParam MultipartFile docObj) {
		LOG.info("Inside uploadDocument():");
		try {
			Document document = new Document();
			// document.setDocId(docId);
			document.setDocName(docObj.getOriginalFilename());
			document.setDocData(docObj.getBytes());
			LOG.debug("Docs Uploaded : " + document);
			service.saveDocument(document);

		} catch (Exception e) {
			LOG.error("Exception inside uploadDocument():" + e.getMessage());
			e.printStackTrace();
		}
		LOG.info("About redirect UI Page:");
		return "redirect:all";
	}

	// 3. Download Document
	@GetMapping("/download")
	public void downloadDocument(@RequestParam Integer id, HttpServletResponse response) {
		LOG.info("Inside downloadDocument():");

		try {
			Document document = service.getDocumentById(id);
			// 2. provide file name using header
			response.setHeader("Content-Disposition", "attachment;filename=" + document.getDocName());

			// 3. copy data from Doc to Response object

			FileCopyUtils.copy(document.getDocData(), response.getOutputStream());

		} catch (Exception e) {
			LOG.error("Exception inside downloadDocument():");
			e.printStackTrace();
		}
	}

	// 4. Delete Document
	@GetMapping("/delete")
	public String deleteDocunemt(@RequestParam Integer id) {
		LOG.info("Inside deleteDocunemt():");
		try {

			service.deleteDocumentById(id);

		} catch (DocumentNotFoundException e) {
			LOG.error("Exception inside deleteDocunemt(): " + e.getMessage());
			e.printStackTrace();
		}
		LOG.info("About redirect UI Page");
		return "redirect:all";
	}

}
