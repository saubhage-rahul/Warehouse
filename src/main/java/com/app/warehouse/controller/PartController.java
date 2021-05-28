package com.app.warehouse.controller;

import java.util.List;

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

import com.app.warehouse.model.Part;
import com.app.warehouse.service.IOrderMethodService;
import com.app.warehouse.service.IPartService;
import com.app.warehouse.service.IUomService;

@Controller
@RequestMapping("/part")
public class PartController {

	private static final Logger log = LoggerFactory.getLogger(PartController.class);

	@Autowired
	private IPartService service;

	@Autowired
	private IUomService Uomservice;

	@Autowired
	private IOrderMethodService orderMethodService;

	// For Integrations(Dynamic Drop down for UOM)
	private void commonUIForUOM(Model model) {
		model.addAttribute("uoms", Uomservice.getUomIdAndModel());
	}
	
	// For Integrations(Dynamic Drop down for OrderMethod)
	private void commonUIForOrderMethod(Model model)
	{
		model.addAttribute("orderMethods", orderMethodService.getOrderMethodIdAndMode());
	}

	// 1. Part Service
	@GetMapping("/register")
	public String registerPart(Model model) {
		log.info("Inside savePart():");

		// Dynamic Drop down for UOM
		commonUIForUOM(model);
		commonUIForOrderMethod(model);
		return "partRegister";
	}

	// 2. Save Part
	@PostMapping("/save")
	public String savePart(@ModelAttribute Part part, Model model) {
		log.info("Inside savePart():");
		try {
			Integer id = service.savePart(part);
			String msg = "Part Created : " + id;
			log.debug("Paer Created: " + id);
			model.addAttribute("message", msg);

			// Dynamic Drop down for UOM
			commonUIForUOM(model);
			commonUIForOrderMethod(model);

		} catch (Exception e) {
			log.error("Exception inside savePart():");
		}
		log.info("About partRegister UI Page:");
		return "partRegister";
	}

	// 2. Fetch All Part
	@GetMapping("/all")
	public String fetchAllParts(Model model) {
		log.info("Inside fetchAllParts():");
		try {
			List<Part> list = service.getAllParts();
			log.debug("Record is Fetch : " + list);
			model.addAttribute("list", list);

		} catch (Exception e) {
			log.error("Exception inside fetchAllParts():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About partData UI Page:");
		return "partData";
	}

	// AJEX Call
	@GetMapping("/validate")
	@ResponseBody
	public String validatePartCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Inside validatePartCode():");
		String message = "";
		if (id == 0 && service.getPartCount(code))

			message = code + ",Already Exist";
		else if (id != 0 && service.getPartCountForEdit(code, id))
			message = code + ",Already Exist";
		return message;
	}

}
