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

import com.app.warehouse.constant.SaleOrderStatus;
import com.app.warehouse.model.SaleOrder;
import com.app.warehouse.model.SaleOrderDetails;
import com.app.warehouse.service.IPartService;
import com.app.warehouse.service.ISaleOrderService;
import com.app.warehouse.service.IShipmentTypeService;
import com.app.warehouse.service.IWhUserTypeService;

@Controller
@RequestMapping("/sale")
public class SaleOrderController {

	private static final Logger log = LoggerFactory.getLogger(SaleOrderController.class);

	@Autowired
	private ISaleOrderService service;

	@Autowired
	private IShipmentTypeService shipmentService;

	@Autowired
	private IWhUserTypeService whUserService;

	@Autowired
	private IPartService partService;

	// Dynamic Drop Down for Common UI
	private void commonUI(Model model) {
		model.addAttribute("sts", shipmentService.getShipIdAndShipCodeByEnable("Yes"));
		model.addAttribute("customers", whUserService.getWhUserIdAndCodeByType("Customer"));
	}

	// 1. SaleOrder Register Page
	@GetMapping("/register")
	public String showSaleOrderRegisterPage(Model model) {
		log.info("Inside showSaleOrderRegisterPage():");

		// Calling Method For Dynamic Drop Down for Common UI
		commonUI(model);
		return "registerSaleOrder";
	}

	// 2. Save SaleOrder
	@PostMapping("/save")
	public String saveSaleOrder(@ModelAttribute SaleOrder saleOrder, Model model) {
		log.info("Inside saveSaleOrder():");

		try {
			Integer id = service.saveSaleOrder(saleOrder);
			String msg = "Sale Order Created : " + id;
			model.addAttribute("message", msg);

			log.debug("Sale Order Save : " + id);

		} catch (Exception e) {
			log.error("Exception inside saveSaleOrder():" + e.getMessage());
			e.printStackTrace();
		}

		// Calling Method For Dynamic Drop Down for Common UI
		commonUI(model);
		log.info("About registerSaleOrder UI Page:");
		return "registerSaleOrder";
	}

	// 3. Fetch All SaleOrder
	@GetMapping("/all")
	public String fetchAllSaleOrder(Model model) {
		log.info("Inside fetchAllSaleOrder():");

		try {
			List<SaleOrder> list = service.getAllSaleOrder();
			model.addAttribute("list", list);

			log.debug("Fetch All Sale Order Record:" + list);

		} catch (Exception e) {
			log.error("Exception inside fetchAllSaleOrder():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About saleOrderData UI Page:");
		return "saleOrderData";
	}

	// 4. Validate OrderCode(AJEX Validation)
	@GetMapping("/validateOrderCode")
	public String validateOrderCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Inside validateOrderCode():");
		String message = "";
		try {

			if (id == 0 && service.validateOrderCode(code))
				message = code + ",Already Exit";
			else if (id != 0 && service.validateOrderCodeAndId(code, id))
				message = code + ",Already Exit";
		} catch (Exception e) {
			log.error("Exception inside validateOrderCode():" + e.getMessage());
			e.printStackTrace();
		}

		return message;
	}

	// -----------------------------------
	// Screen#2-------------------------------------

	private void commonUIForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	// 5.
	@GetMapping("/parts")
	public String showSaleOrderPartPage(@RequestParam Integer id, Model model) {
		log.info("Inside showSaleOrderPartPage():");
		try {

			SaleOrder saleOrder = service.getOneSaleOrder(id);
			model.addAttribute("saleOrder", saleOrder);

		} catch (Exception e) {
			log.error("Exception inside showSaleOrderPartPage():" + e.getMessage());
			e.printStackTrace();
		}

		// Dynamic Drop Down for Parts
		commonUIForParts(model);

		// Fetch All
		List<SaleOrderDetails> list = service.getSaleDtlsBySaleOrderId(id);
		model.addAttribute("list", list);

		log.info("About saleOrderParts UI Page");
		return "saleOrderPart";

	}

	// 6. Add Part
	@PostMapping("/addPart")
	public String addPart(SaleOrderDetails saleOrderDetails) {
		service.savePurchaseDetails(saleOrderDetails);

		// For Status
		Integer soId = saleOrderDetails.getSaleOrder().getId();
		if (SaleOrderStatus.OPEN.name().equals(service.getCurrentStatusOfSaleOrder(soId))) {
			service.updateSaleOrderStatus(soId, SaleOrderStatus.READY.name());
		}

		return "redirect:parts?id=" + soId;

		// return "redirect:parts?id=" + saleOrderDetails.getSaleOrder().getId();
	}

	// 7. Remove Part

//	a. detailId--> to delete	row from SaleDtl table.
//	b.saleOrderId--> to redirect	back to same page.

	@GetMapping("/removePart")
	public String removePart(@RequestParam Integer detailId, @RequestParam Integer saleOrderId) {
		service.deleteSaleDetails(detailId);
		// For Status
		if (service.getSaleDtlsCountBySaleOrderId(saleOrderId) == 0) {
			service.updateSaleOrderStatus(saleOrderId, SaleOrderStatus.OPEN.name());
		}
		return "redirect:parts?id=" + saleOrderId;
	}

}
