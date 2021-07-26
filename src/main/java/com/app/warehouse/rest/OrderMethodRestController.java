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

import com.app.warehouse.Exception.OrderMethodNotFoundException;
import com.app.warehouse.model.OrderMethod;
import com.app.warehouse.service.IOrderMethodService;

@RestController
@RequestMapping("/rest/api/orderMethod")
public class OrderMethodRestController {

	private static final Logger log = LoggerFactory.getLogger(OrderMethodRestController.class);

	@Autowired
	private IOrderMethodService service;

	// 1. Fetch All Order Method
	@GetMapping("/all")
	public ResponseEntity<List<OrderMethod>> getAllOrderMethod() {
		log.info("Inside getAllOrderMethod():");
		try {

			List<OrderMethod> list = service.getAllOrderMethods();
			log.debug("Fetch Order Method: " + list);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception inside getAllOrderMethod(): " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 2. Create Order Method
	@PostMapping("/create")
	public ResponseEntity<String> createOrderMethod(@RequestBody OrderMethod orderMethod) {
		log.info("Inside createOrderMethod():");
		try {

			Integer id = service.saveOrderMethod(orderMethod);
			String msg = "OrderMethod is Created : " + id;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception inside createOrderMethod : " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 3. Fetch Single Order Method
	@GetMapping("/find/{id}")
	public ResponseEntity<OrderMethod> getOneOrderMethod(@PathVariable Integer id) {
		log.info("Inside getOneOrderMethod():");
		try {
			OrderMethod orderMethod = service.getorderMethod(id);
			log.debug("Fetch Order Method Based on id : " + orderMethod);
			return new ResponseEntity<>(orderMethod, HttpStatus.OK);
		} catch (OrderMethodNotFoundException onfe) {
			log.error("Unable to find Order Method : " + onfe.getMessage());

			throw onfe;

		} catch (Exception e) {
			log.error("Exception inside getOneOrderMethod():" + e.getMessage());
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 4. Update Order Method
	@PutMapping("/update")
	public ResponseEntity<String> updateOrderMethod(@RequestBody OrderMethod orderMethod) {
		log.info("Inside updateOrderMethod():");
		try {
			service.updateOrderMethod(orderMethod);
			String msg = "Order Method is Updated: " + orderMethod;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (OrderMethodNotFoundException onfe) {
			log.error("Unable to Update Order Method: " + onfe.getMessage());
			throw onfe;
		} catch (Exception e) {
			log.error("Exception inside updateOrderMethod():" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 5. Delete Order Method
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteOrderMethod(@PathVariable Integer id) {
		log.info("Inside deleteOrderMethod():");
		try {
			service.deleteOrderMethod(id);
			String msg = "Order Method is Deleted : " + id;
			log.debug(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (OrderMethodNotFoundException onfe) {
			log.error("Unable to delete Order Method : " + onfe.getMessage());
			throw onfe;
		} catch (Exception e) {
			log.error("Exception inside deleteOrderMethod():" + e.getMessage());
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
