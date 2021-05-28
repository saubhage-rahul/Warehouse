package com.app.warehouse.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.warehouse.Exception.ShipmentTypeNotFoundException;
import com.app.warehouse.model.ShipmentType;
import com.app.warehouse.service.IShipmentTypeService;
import com.app.warehouse.util.ShipmentTypeUtil;
import com.app.warehouse.view.ShipmentTypeExcelView;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {

	private static final Logger log = LoggerFactory.getLogger(ShipmentTypeController.class);

	@Autowired
	private IShipmentTypeService service;

	@Autowired
	private ShipmentTypeUtil util;

	@Autowired
	private ServletContext context;

	// Show ShipmentType Page
	@GetMapping("/register")
	public String showRegister() {
		log.info("Inside showRegister():");
		return "ShipmentTypeRegister";
	}

	// Save ShipmentType
	@PostMapping("/saveShipment")
	public String saveShipmentType(@ModelAttribute ShipmentType shipmentType, Model model) {

		log.info("Inside saveShipmentType():");
		try {

			Integer id = service.saveShipmentType(shipmentType);

			log.debug("Record is Inserted With Id : " + id);

			String msg = "Shipment Type is Created!!! : " + id;
			model.addAttribute("message", msg);

		} catch (Exception e) {
			log.error("Unable to Process saveShipmentType Request:" + e.getMessage());
			e.printStackTrace();

		}

		log.info("About to UI Page:");
		return "ShipmentTypeRegister";
	}

	// Fetch All ShipmentType
	@GetMapping("/all")
	public String getAllShipmentType(Model model) {

		log.info("Inside getAllShipmentType():");
		try {
			List<ShipmentType> list = service.fetchAllShipmentType();
			log.debug("Fetching All Records : " + list);
			model.addAttribute("list", list);
		} catch (Exception e) {

			log.error("Unable to Process AllShipmentType Request:" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About ShipmentTypeData UI Page:");
		return "ShipmentTypeData";
	}

	// Delete ShipmentType
	@GetMapping("/delete")
	public String deleteShipmentType(@RequestParam Integer id, Model model) {
		log.info("Inside deleteShipmentType():");
		try {
			// call service
			service.deleteShipmentType(id);

			// Create Message
			String msg = "Shipment Type is Deleted!! : " + id;

			log.debug("Record is Deleted with Id :" + msg);

			model.addAttribute("message", msg);

		} catch (ShipmentTypeNotFoundException e) {

			log.error("Unable to Process Delete Request:" + e.getMessage());
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());

		}
		// load new data
		List<ShipmentType> list = service.fetchAllShipmentType();

		// send data to UI
		model.addAttribute("list", list);

		log.info("About ShipmentTypeData UI Page:");
		// goto UI page
		return "ShipmentTypeData";
	}

	// Get ShipmentType based on Id
	@GetMapping("/edit")
	public String showShipmentTypeEdit(@RequestParam Integer id, Model model) {

		log.info("Inside showShipmentTypeEdit():");

		String page = null;
		try {
			// fetch from DB using service
			ShipmentType shipmentType = service.getShipmentType(id);

			// send object to UI as FORM DATA
			model.addAttribute("shipmentType", shipmentType);

			// show edit if record exist
			page = "ShipmentTypeEdit";
		} catch (ShipmentTypeNotFoundException e) {
			log.error("Unable to Process showShipmentTypeEdit Request :" + e.getMessage());
			e.printStackTrace();

			// if row not exist
			page = "ShipmentTypeData";
			model.addAttribute("message", e.getMessage());

			// load new data
			List<ShipmentType> list = service.fetchAllShipmentType();
			log.debug("Fetching All Data :" + list);
			// send data to UI
			model.addAttribute("list", list);
		}
		// GoTo UI
		log.info("About page:");
		return page;
	}

	// Update ShipmentType
	@PostMapping("/update")
	public String updateShipmentType(@ModelAttribute ShipmentType shipmentType) {
		log.info("Inside updateShipmentType():");
		try {
			service.updateShipmentType(shipmentType);
			log.debug("Shipment Type is Updated :");
		} catch (Exception e) {
			log.error("Unable to Process Update Request :" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About Redirect All:");
		return "redirect:all";
	}

	// AJEX Call
	@GetMapping("/validate")
	@ResponseBody
	public String validateShipmentTypeCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Inside validateShipmentTypeCode():");
		String message = "";
		if (id == 0 && service.isShipmentTypecodeExist(code)) {
			message = code + ",already exist";
		} else if (id != 0 && service.isShipmentTypeCodeExistForEdit(code, id)) {

			message = code + ", already exist";
		}
		return message;
	}

	// Download Excel
	@GetMapping("/excel")
	public ModelAndView exportData() {
		log.info("Inside exportData():");
		ModelAndView mav = new ModelAndView();
		mav.setView(new ShipmentTypeExcelView());
		List<ShipmentType> list = service.fetchAllShipmentType();
		log.debug("list");
		mav.addObject("list", list);
		return mav;
	}

	// Generate Free Chart
	@GetMapping("/chart")
	public String genereateFreeChart() {
		log.info("Inside genereateFreeChart() :");
		List<Object[]> list = service.getShipmentTypeModeAndCount();
		String path = context.getRealPath("/");
		util.generateFreeChart(path, list);
		return "shipmentTypeFreeChart";
	}
}
