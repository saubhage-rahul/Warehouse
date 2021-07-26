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

import com.app.warehouse.Exception.WhUserTypeNotFoundException;
import com.app.warehouse.model.WhUserType;
import com.app.warehouse.service.IWhUserTypeService;

@RestController
@RequestMapping("/rest/api/whUser")
public class WHUserTypeRestController {

	private static final Logger log = LoggerFactory.getLogger(WHUserTypeRestController.class);

	@Autowired
	private IWhUserTypeService service;

	@GetMapping("/all")
	public ResponseEntity<List<WhUserType>> getAllWhUser() {
		log.info("Inside getAllWhUser():");
		try {
			List<WhUserType> list = service.getAllWhUserType();
			log.debug("Fetch All WHUserType:" + list);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Unable to Fetch All WhUserType : " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<String> createWHUser(@RequestBody WhUserType whUserType) {
		log.info("Inside createWHUser():");
		try {
			Integer id = service.saveWhUserType(whUserType);
			String msg = "WHUser is Created: " + id;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Unable to create new WHUser : " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<WhUserType> getWhUser(@PathVariable Integer id) {
		log.info("Inside getWhUser():");
		try {
			WhUserType whUserType = service.getWhUserType(id);
			log.debug("Get WhUser : " + whUserType);
			return new ResponseEntity<>(whUserType, HttpStatus.OK);
		} catch (WhUserTypeNotFoundException wnfe) {
			log.error("Unable to Find WhUserType : " + wnfe.getMessage());
			throw wnfe;
		} catch (Exception e) {
			log.error("Exception Inside getWhUser() : " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateWhUserType(@RequestBody WhUserType whUserType) {
		log.info("Inside updateWhUserType():");
		try {
			service.updateWhUserType(whUserType);
			String msg = "WhUser Type Updated : " + whUserType;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (WhUserTypeNotFoundException wnfe) {
			log.error("Unable to Update WhUserType : " + wnfe.getMessage());
			throw wnfe;
		} catch (Exception e) {
			log.error("Exception Inside updateWhUserType(): " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteWhUserType(@PathVariable Integer id) {
		log.info("Inside deleteWhUserType():");
		try {
			service.deleteWhUserType(id);
			String msg = "WhUser Type is Deleted : " + id;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (WhUserTypeNotFoundException wnfe) {
			log.error("Unable to Delete WhUserType : " + wnfe.getMessage());
			throw wnfe;
		} catch (Exception e) {
			log.error("Exception Inside deleteWhUserType(): " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
