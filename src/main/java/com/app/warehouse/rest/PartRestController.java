package com.app.warehouse.rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.warehouse.model.Part;
import com.app.warehouse.service.IPartService;

@RestController
@RequestMapping("/rest/api/part")
public class PartRestController {

	private static final Logger log = LoggerFactory.getLogger(PartRestController.class);

	@Autowired
	private IPartService service;

	@GetMapping("/all")
	public ResponseEntity<List<Part>> getAllParts() {
		log.info("Inside getAllParts():");
		try {
			List<Part> list = service.getAllParts();
			log.debug("Fetch All Parts : " + list);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Unable to Fetch All Parts : " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<String> createPart(@RequestBody Part part) {
		log.info("Inside createPart():");
		try {
			Integer id = service.savePart(part);
			String msg = "Part is Created:" + id;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Unable to Create New Part :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
