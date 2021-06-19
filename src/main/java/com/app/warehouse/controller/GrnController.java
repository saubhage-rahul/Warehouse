package com.app.warehouse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.app.warehouse.constant.PurchaseOrderStatus;
import com.app.warehouse.service.IGrnService;
import com.app.warehouse.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {

	private static final Logger log = LoggerFactory.getLogger(GrnController.class);

	@Autowired
	private IGrnService service;

	@Autowired
	private IPurchaseOrderService orderService;

	// Dynamic DropDown
	private void commonUI(Model model) {
		model.addAttribute("pos", orderService.findPurchaseOrderIdAndCodeByStatus(PurchaseOrderStatus.INVOICED.name()));
	}

	// 1. Show GRN Register Page
	@GetMapping("/register")
	public String showGrnRegisterPage(Model model) {
		log.info("Inside showGrnRegisterPage():");
		commonUI(model);
		return "GrnRegister";
	}

	// 2. Save GRN
	@PostMapping("/save")
	public String saveGrn(Model model) {
		log.info("Inside saveGrn():");
		commonUI(model);
		return "GrnRegister";
	}

}
