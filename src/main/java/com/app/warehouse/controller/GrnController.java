package com.app.warehouse.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.app.warehouse.constant.GrnDetailStatus;
import com.app.warehouse.constant.PurchaseOrderStatus;
import com.app.warehouse.model.Grn;
import com.app.warehouse.model.GrnDtl;
import com.app.warehouse.model.PurchaseDetails;
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
	public String saveGrn(@ModelAttribute Grn grn, Model model) {
		log.info("Inside saveGrn():");
		try {
			// prepare GrnDetail and add to Grn
			createGrnDetailsByPurchaseOrder(grn);
			Integer id = service.saveGrn(grn);
			// update purchase order status
			if (id != null)
				orderService.updatePurchaseOrderStatus(grn.getPo().getId(), PurchaseOrderStatus.RECEIVED.name());
			model.addAttribute("message", "GRN Created:" + id);
		} catch (Exception e) {
			log.error("Exception inside saveGrn():" + e.getMessage());
			e.printStackTrace();
		}

		commonUI(model);
		return "GrnRegister";
	}

	private void createGrnDetailsByPurchaseOrder(Grn grn) {
		// read po id from Grn
		Integer poId = grn.getPo().getId();
		// get List<PurchaseDtl> by poId
		List<PurchaseDetails> poDtls = orderService.getPurchaseDtlsByPurchaseOrderId(poId);

		// GrnDtls set (empty collection)
		Set<GrnDtl> grnSet = new HashSet<>();

		// one PoDtl ---> one GrnDtl
		for (PurchaseDetails pdtl : poDtls) {
			GrnDtl grnDtl = new GrnDtl();
			grnDtl.setPartCode(pdtl.getPart().getPartCode());
			grnDtl.setBaseCost(pdtl.getPart().getPartBaseCost());
			grnDtl.setQty(pdtl.getQty());

			// add grnDtl to Set<GrnDtl>
			grnSet.add(grnDtl);
		}

		grn.setDtls(grnSet);
	}

	// Show All GRN
	@GetMapping("/all")
	public String showAll(Model model) {
		log.info("Inside showAll():");
		try {

			List<Grn> list = service.getAllGrns();
			model.addAttribute("list", list);
		} catch (Exception e) {
			log.error("Exception inside showAll():");
			e.printStackTrace();
		}
		log.info("About grnData UI Page");
		return "grnData";
	}

	// -----------------------Screen # 2-------------------------------
	@GetMapping("/parts")
	public String showGrnDetailByGrnId(@RequestParam Integer id, Model model) {
		log.info("Inside showGrnDetailByGrnId():");
		Grn grn = service.getOneGrn(id);
		model.addAttribute("grn", grn);
		model.addAttribute("list", grn.getDtls());
		return "GrnParts";
	}

	// ---------------------Status Updated---------------------------
	@GetMapping("/accept")
	public String updateAccepted(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateGrnDtlStatus(dtlId, GrnDetailStatus.ACCEPTED.name());
		return "redirect:parts?id=" + id;
	}

	/**
	 * grnId -- to redirect /parts URL grnDtlId -- todo status
	 * 
	 */
	@GetMapping("/reject")
	public String updateRejected(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateGrnDtlStatus(dtlId, GrnDetailStatus.REJECTED.name());
		return "redirect:parts?id=" + id;
	}
}
