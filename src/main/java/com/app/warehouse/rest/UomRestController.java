package com.app.warehouse.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.warehouse.Exception.UomNotFoundException;
import com.app.warehouse.model.Uom;
import com.app.warehouse.service.IUomService;

@RestController
@RequestMapping("/rest/api/uom")
public class UomRestController {

	private static final Logger log = LoggerFactory.getLogger(UomRestController.class);

	@Autowired
	private IUomService service;

	// 1. Get All Uom
	@GetMapping("/findAll")
	public ResponseEntity<List<Uom>> getAllUom() {
		log.info("Inside getAllUom():");
		try {
			List<Uom> list = service.fetchAllUom();
			log.debug("Fetching All UOM: " + list);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception inside getAllUom():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 2. Get One Uom
	@GetMapping("/find/{id}")
	public ResponseEntity<Uom> getOneUom(@PathVariable Integer id) {
		log.info("Inside getOneUom():");
		try {
			Uom uom = service.getUom(id);
			log.debug("Fetch one Uom Based on Id :" + uom);
			return new ResponseEntity<>(uom, HttpStatus.OK);
		} catch (UomNotFoundException unfe) {
			log.error("Unable to fetch UOM:" + unfe.getMessage());
			throw unfe;

		} catch (Exception e) {
			log.error("Exception inside getOneUom():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 3. Create Uom
	@PostMapping("/create")
	public ResponseEntity<String> createUom(@RequestBody Uom uom) {
		log.info("Inside createUom():");
		try {
			Integer id = service.saveUom(uom);
			String msg = "Uom Created :" + id;
			log.debug(msg);
			return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Unable to create Uom :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 4. Update Uom
	@PutMapping("/update")
	public ResponseEntity<String> updateUom(@RequestBody Uom uom) {
		log.info("Inside updateUom():");
		try {
			service.updateUom(uom);
			String msg = "Uom Updated:";
			log.debug(msg);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (UomNotFoundException unfe) {
			log.error("Unable to Update UOM:" + unfe.getMessage());
			throw unfe;
		} catch (Exception e) {
			log.error("Exception inside updateUom():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 5. Delete Uom
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUom(@PathVariable Integer id) {
		log.info("Inside deleteUom():");
		try {
			service.deleteUom(id);
			String msg = "Uom Deleted:" + id;
			log.debug(msg);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (UomNotFoundException unfe) {
			log.error("Unable to fetch UOM:" + unfe.getMessage());
			throw unfe;
		} catch (Exception e) {
			log.error("Exception inside deleteUom():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
