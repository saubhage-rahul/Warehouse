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

import com.app.warehouse.Exception.OrderMethodNotFoundException;
import com.app.warehouse.model.OrderMethod;
import com.app.warehouse.service.IOrderMethodService;
import com.app.warehouse.util.OrderMethodUtil;
import com.app.warehouse.view.OrderMethodExcelView;

@Controller
@RequestMapping("/order")
public class OrderMethodController {

	private static final Logger log = LoggerFactory.getLogger(OrderMethodController.class);

	@Autowired
	private IOrderMethodService service;

	@Autowired
	private OrderMethodUtil util;

	@Autowired
	private ServletContext context;

	// 1. show Register Page
	@GetMapping("/register")
	public String showRegisterPage() {
		log.info("Inside showRegisterPage(): ");
		return "orderMethodRegister";
	}

	// 2. Save Order Method
	@PostMapping("/save")
	public String saveOrderMethod(@ModelAttribute OrderMethod orderMethod, Model model) {
		log.info("Inside saveOrderMethod():");
		try {
			Integer id = service.saveOrderMethod(orderMethod);
			String msg = "Order Created : " + id;
			model.addAttribute("message", msg);
			log.debug(msg);
		} catch (Exception e) {
			log.error("Exception inside saveOrderMethod():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About Order Method Register UI Page: ");
		return "orderMethodRegister";
	}

	// 3. Fetch All Order Method Data
	@GetMapping("/all")
	public String fetchAllOrderMethod(Model model) {
		log.info("Inside fetchAllOrderMethod():");
		try {
			// Common Method Logic Call
			commonLogic(model);

		} catch (Exception e) {
			log.error("Exception inside fetchAllOrderMethod():" + e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		log.info("About Order Method Data UI Page:");
		return "orderMethodData";
	}

	// 4. Common Logic Method
	public void commonLogic(Model model) {
		log.info("Inside commonLogic():");
		List<OrderMethod> list = service.getAllOrderMethods();
		log.debug("Fetch All Order Method Data : " + list);
		model.addAttribute("list", list);
	}

	// 5. Delete Order Method
	@GetMapping("/delete")
	public String deleteorderMethod(@RequestParam Integer id, Model model) {
		log.info("Inside deleteorderMethod():");
		try {
			service.deleteOrderMethod(id);
			log.debug("Record Deleted: " + id);

			// Common Method Logic Call
			commonLogic(model);

		} catch (OrderMethodNotFoundException e) {
			log.error("Unable to Delete Record :" + e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();

		}
		log.info("About Order Method Data UI Page:");
		return "orderMethodData";
	}

	// 6. Get Order Method based on id
	@GetMapping("/edit")
	public String getOrderMethod(@RequestParam Integer id, Model model) {
		log.info("Inside getOrderMethod():");
		String page = null;
		try {
			OrderMethod orderMethod = service.getorderMethod(id);
			model.addAttribute("orderMethod", orderMethod);
			log.debug("orderMethod");

			// Common Method Logic Call
			commonLogic(model);
			page = "orderMethodEdit";
		} catch (OrderMethodNotFoundException e) {
			log.error("Unable to Edit Record : " + e.getMessage());
			model.addAttribute("message", e.getMessage());
			page = "orderMethodData";

			e.printStackTrace();
		}

		return page;
	}

	// 7. Update Order Method
	@PostMapping("/update")
	public String updateOrderMethod(@ModelAttribute OrderMethod orderMethod, Model model) {
		log.info("Inside updateOrderMethod() :");

		try {
			service.updateOrderMethod(orderMethod);
			model.addAttribute("message", "Order Data Updated");
			log.debug("Record Updated Successfully:");

			// Common Logic Method Call
			commonLogic(model);
		} catch (Exception e) {
			log.info("Exception inside updateOrderMethod():" + e.getMessage());
			model.addAttribute("message", e.getMessage());
		}
		log.info("About Order Method Data UI Page:");
		return "orderMethodData";
	}

	// 8. AJEX Validations
	@GetMapping("/validate")
	@ResponseBody
	public String validateOrderCount(@RequestParam String code, @RequestParam Integer id) {
		log.info("InSide validateOrderCount(): ");
		String message = "";
		if (id == 0 && service.isOrderCountExit(code))

			message = code + ",Already Exit";

		else if (id != 0 && service.isOrderCountExitForEdit(code, id))

			message = code + ",Already Exit";
		return message;

	}

	// 9. Download Excel
	@GetMapping("/excel")
	public ModelAndView exportData(Model model) {
		log.info("Inside exportData():");

		ModelAndView mav = new ModelAndView();
		mav.setView(new OrderMethodExcelView());

		// Common Logic Method Call
		commonLogic(model);

		return mav;
	}

	// 10. Generate FreeChart for OrderMethodMode
	@GetMapping("/chart")
	public String generateFreeChart() {
		log.info("Inside generateFreeChart():");
		try {
			List<Object[]> list = service.generatechartForOrderMethodMode();
			String path = context.getRealPath("/");
			util.generateChartForOrderMethodMode(path, list);
			util.generateBarChartForOrderMethodMode(path, list);
		} catch (Exception e) {
			log.error("Exception inside generateFreeChart():" + e);
			e.printStackTrace();
		}
		log.info("About orderMethodModeChart UI Page:");
		return "orderMethodModeChart";
	}
}
