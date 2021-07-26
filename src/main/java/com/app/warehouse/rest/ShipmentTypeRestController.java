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

import com.app.warehouse.Exception.ShipmentTypeNotFoundException;
import com.app.warehouse.model.ShipmentType;
import com.app.warehouse.service.IShipmentTypeService;

@RestController
@RequestMapping("/rest/api/shipmentType")
public class ShipmentTypeRestController {

	private static final Logger log = LoggerFactory.getLogger(ShipmentTypeRestController.class);

	@Autowired
	private IShipmentTypeService service;

	@GetMapping("/all")
	public ResponseEntity<List<ShipmentType>> getAllShipmentType() {
		log.info("Inside getAllShipmentType():");
		try {
			List<ShipmentType> list = service.fetchAllShipmentType();
			log.debug("Fetch All ShipmentTypes:" + list);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Unable to Fetch All Shipments:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<ShipmentType> getOneShipmentType(@PathVariable Integer id) {
		log.info("Inside getOneShipmentType():");
		try {
			ShipmentType shipmentType = service.getShipmentType(id);
			log.debug("Fetch ShipmentType Based on Id:" + shipmentType);
			return new ResponseEntity<>(shipmentType, HttpStatus.OK);
		} catch (ShipmentTypeNotFoundException snfe) {
			log.error("Unable to Fetch ShipmentType: " + snfe.getMessage());
			throw snfe;

		} catch (Exception e) {
			log.error("Exception inside getOneShipmentType():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<String> createShipmentType(@RequestBody ShipmentType shipmentType) {
		log.info("Inside createShipmentType():");
		try {
			Integer id = service.saveShipmentType(shipmentType);
			String msg = "Shipment Type is Created: " + id;
			log.debug(msg);

			return new ResponseEntity<>(msg, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Unble to Create New Shipment Type:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateShipmentType(@RequestBody ShipmentType shipmentType) {
		log.info("Inside updateShipmentType():");
		try {
			service.updateShipmentType(shipmentType);
			String msg = "Shipment Type Updated : " + shipmentType;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (ShipmentTypeNotFoundException snfe) {
			log.error("Unable to Update Shipment Type:" + snfe.getMessage());
			throw snfe;
		} catch (Exception e) {
			log.error("Exception inside updateShipmentType():" + e.getMessage());
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteShipmentType(@PathVariable Integer id) {
		log.info("Inside deleteShipmentType():");
		try {
			service.deleteShipmentType(id);
			String msg = "Shipment Type is Deleted:" + id;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (ShipmentTypeNotFoundException snfe) {
			log.error("Unable to delete Shipment Type:" + snfe.getMessage());
			throw snfe;
		} catch (Exception e) {
			log.error("Exception inside deleteShipmentType():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
