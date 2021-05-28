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

import com.app.warehouse.Exception.UomNotFoundException;
import com.app.warehouse.model.Uom;
import com.app.warehouse.service.IUomService;
import com.app.warehouse.util.UomUtil;
import com.app.warehouse.view.UomExcelView;

@Controller
@RequestMapping("/uom")
public class UomController {

	private static final Logger log = LoggerFactory.getLogger(UomController.class);

	@Autowired
	private IUomService service;

	@Autowired
	private UomUtil util;

	@Autowired
	private ServletContext context;

	// UOM Registration Page
	@GetMapping("/uomRegister")
	public String showUomPage() {
		log.info("Inside showUomPage():");
		return "uomRegister";
	}

	// Save UOM
	@PostMapping("/save")
	public String saveUom(@ModelAttribute Uom uom, Model model) {
		log.info("Inside saveUom():");
		try {
			Integer id = service.saveUom(uom);
			String msg = "UOM is Save! : " + id;
			log.debug("UOM is Save with id :" + msg);
			model.addAttribute("message", msg);
		} catch (Exception e) {
			log.error("Unable to Process SaveUom Request" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About uomRegister UI Page: ");
		return "uomRegister";
	}

	// Fetch All UOM'S
	@GetMapping("/all")
	public String fetchAllUom(Model model) {
		log.info("Inside fetchAllUom():");
		try {
			List<Uom> list = service.fetchAllUom();
			log.debug("Fetching All UOM Records" + list);
			model.addAttribute("list", list);
		} catch (Exception e) {
			log.error("Enable to Process Fetch Uom Request" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About uomData UI Page");
		return "uomData";
	}

	// Delete UOM
	@GetMapping("/delete")
	public String deleteUom(@RequestParam Integer id, Model model) {
		log.info("Inside deleteUom():");
		try {
			service.deleteUom(id);
			String msg = "UOM Deleted Successfully! : " + id;
			log.debug("Record Deleted :" + msg);
			model.addAttribute("message", msg);
			List<Uom> list = service.fetchAllUom();
			log.debug("Fetching UOM Records" + list);
			model.addAttribute("list", list);
		} catch (UomNotFoundException e) {
			log.debug("Unable to Delete" + e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}

		log.info("About uomData UI Page:");
		return "uomData";
	}

	// Get UOM
	@GetMapping("/edit")
	public String editUom(@RequestParam Integer id, Model model) {
		log.info("Inside editUom():");
		String page = null;
		try {
			Uom uom = service.getUom(id);
			model.addAttribute("uom", uom);
			List<Uom> list = service.fetchAllUom();
			log.debug("Fetching All UOM Records" + list);
			model.addAttribute("list", list);
			page = "uomEdit";
		} catch (UomNotFoundException e) {
			log.debug("Unable to Edit" + e.getMessage());
			model.addAttribute("message", e.getMessage());
			page = "uomData";
			e.printStackTrace();
		}
		log.info("About uomEdit UI Page:");
		return page;
	}

	// Update UOM
	@PostMapping("/update")
	public String updateUom(@ModelAttribute Uom uom) {
		log.info("Inside updateUom():");
		try {
			service.updateUom(uom);
			log.debug("UOM Updated Successfully");
		} catch (Exception e) {
			log.error("Unable to Process Update Uom Request" + e.getMessage());
		}
		log.info("About Redirect All Page");
		return "redirect:all";
	}

	// AJEX Validation
	@GetMapping("/validate")
	@ResponseBody
	public String validateUomModel(@RequestParam String model, @RequestParam Integer id) {
		log.info("Inside validateUomModel():");
		String message = "";
		if (id == 0 && service.isUomModelExit(model))
			message = model + ",Already Exit";
		else if (id != 0 && service.isUomModelExitForEdit(model, id))
			message = model + ",Already Exit";
		return message;
	}

	// Download Excel
	@GetMapping("/excel")
	public ModelAndView exportUomData() {
		log.info("Inside exportUomData():");

		ModelAndView mav = new ModelAndView();
		mav.setView(new UomExcelView());
		List<Uom> list = service.fetchAllUom();
		log.debug("list");
		mav.addObject("list", list);
		return mav;
	}

	// Generate Free Chart
	@GetMapping("/chart")
	public String generateFreeChart() {
		log.info("Inside generateFreeChart():");
		try {
			List<Object[]> list = service.getUomTypeChart();
			String path = context.getRealPath("/");
			util.generateFreeChart(path, list);
		} catch (Exception e) {
			log.error("Exception inside generateFreeChart() :" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About uomFreeChart UI Page :");
		return "uomFreeChart";
	}
}
