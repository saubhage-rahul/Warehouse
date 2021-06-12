package com.app.warehouse.controller;

import java.util.List;
import java.util.Optional;

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

import com.app.warehouse.constant.PurchaseOrderStatus;
import com.app.warehouse.model.PurchaseDetails;
import com.app.warehouse.model.PurchaseOrder;
import com.app.warehouse.service.IPartService;
import com.app.warehouse.service.IPurchaseOrderService;
import com.app.warehouse.service.IShipmentTypeService;
import com.app.warehouse.service.IWhUserTypeService;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

	private static final Logger log = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	private IPurchaseOrderService service;

	@Autowired
	private IShipmentTypeService shipmentTypeService;

	@Autowired
	private IWhUserTypeService whUserTypeService;

	@Autowired
	private IPartService partService;

	// Integrations Dynamic Drop Down
	private void commonUI(Model model) {
		model.addAttribute("sts", shipmentTypeService.getShipIdAndShipCodeByEnable("Yes"));
		model.addAttribute("vendors", whUserTypeService.getWhUserIdAndCodeByType("Vendor"));
	}

	// 1. Show Purchase Order Register Page
	@GetMapping("/register")
	public String showPurchaseOrderRegister(Model model) {
		log.info("Inside showPurchaseOrderRegister():");
		// Integrations Dynamic Drop Down
		// Calling Common UI Method
		commonUI(model);
		return "registerPurchaseOrder";
	}

	// 2. Save Purchase Order
	@PostMapping("/save")
	public String savePurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
		log.info("Inside savePurchaseOrder():");
		try {
			Integer id = service.savePurchaseOrder(purchaseOrder);
			String msg = "Purchase Order Created:" + id;
			log.debug("Purchase Order Created:" + id);
			model.addAttribute("message", msg);

		} catch (Exception e) {
			log.error("Exception inside savePurchaseOrder():" + e.getMessage());
			e.printStackTrace();
		}
		// Integrations Dynamic Drop Down
		// Calling Common UI Method
		commonUI(model);
		log.info("About registerPurchaseOrder UI Page:");
		return "registerPurchaseOrder";
	}

	// 3. Fetch All Purchase Order
	@GetMapping("/all")
	public String findAllPurchaseOrder(Model model) {
		log.info("findAllPurchaseOrder():");
		try {
			List<PurchaseOrder> list = service.getAllPurchaseOrders();
			model.addAttribute("list", list);
			log.debug("Purchase Order Data Fetch: " + list);

		} catch (Exception e) {
			log.error("Exception inside findAllPurchaseOrder():" + e.getMessage());
			e.printStackTrace();
		}

		log.info("About purchaseOrderData UI Page:");
		return "purchaseOrderData";
	}

	// 4. AJEX Validations For Purchase OrderCode
	@GetMapping("/validateOrderCode")
	public String validatePurchaseOrderCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Inside validatePurchaseOrderCode():");
		String message = "";
		try {
			if (id == 0 && service.validateGetOrderCode(code))
				message = code + ",Already Exit";
			else if (id != 0 && service.validateGetOrderCodeAndId(code, id))
				message = code + ",Already Exit";
		} catch (Exception e) {
			log.error("Exception inside validatePurchaseOrderCode(): " + e.getMessage());
			e.printStackTrace();
		}

		return message;
	}

	// ----------------------------- Screen(#2) -------------------

	// 5. Common UI FOR Parts

	private void commonUIForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	// 6. Purchase Order
	@GetMapping("/parts")
	public String showPurchaseOrderPartPage(@RequestParam Integer id, Model model) {
		log.info("Inside showPurchaseOrderPartPage():");

		try {
			PurchaseOrder purchaseOrder = service.getOnePurchaseOrder(id);
			model.addAttribute("purchaseOrder", purchaseOrder);
		} catch (Exception e) {
			log.error("Exception inside showPurchaseOrderPartPage():" + e.getMessage());
			e.printStackTrace();
		}
		// Dynamic Drop Down for Parts
		commonUIForParts(model);

		// Fetch All
		List<PurchaseDetails> list = service.getPurchaseDtlsByPurchaseOrderId(id);
		model.addAttribute("list", list);

		log.info("About purchaseOrderPart UI Page:");
		return "purchaseOrderPart";
	}

	// 7. Add Parts
	@PostMapping("/addPart")
	public String addPart(PurchaseDetails purchaseDetails) {

		// For Status
		Integer poId = purchaseDetails.getPurchaseOrder().getId();
		Integer partId = purchaseDetails.getPart().getId();

		// Increase Part Quantity
		Optional<PurchaseDetails> optional = service.getPurchaseDetailsByPartIdAndPurchaseOrderId(partId, poId);
		if (optional.isPresent()) {
			service.updatePurchaseDetailsQtyByDetailId(purchaseDetails.getQty(), optional.get().getId());
		} else {
			service.savePurchaseDetails(purchaseDetails);
		}

		if (PurchaseOrderStatus.OPEN.name().equals(service.getCurrentStatusOfPurchaseOrder(poId))) {
			service.updatePurchaseOrderStatus(poId, PurchaseOrderStatus.PICKING.name());
		}

		return "redirect:parts?id=" + poId;
	}

	// 8. Remove Parts
	@GetMapping("/removePart")
	public String removePart(@RequestParam Integer purchaseOrderId, @RequestParam Integer DetailId) {
		service.deletePurchaseDetails(DetailId);
		// For Status
		if (service.getPurchaseDetailsCountByPurchaseOrderId(purchaseOrderId) == 0) {
			service.updatePurchaseOrderStatus(purchaseOrderId, PurchaseOrderStatus.OPEN.name());
		}
		return "redirect:parts?id=" + purchaseOrderId;
	}

	// 9. Increase Qty
	@GetMapping("/increaseQty")
	public String increaseQty(@RequestParam Integer purchaseOrderId, @RequestParam Integer DetailId) {
		service.updatePurchaseDetailsQtyByDetailId(1, DetailId);
		return "redirect:parts?id=" + purchaseOrderId;
	}

	// 10. Decrease Qty
	@GetMapping("/decreaseQty")
	public String decreaseQty(@RequestParam Integer purchaseOrderId, @RequestParam Integer DetailId) {
		service.updatePurchaseDetailsQtyByDetailId(-1, DetailId);
		return "redirect:parts?id=" + purchaseOrderId;
	}

}
