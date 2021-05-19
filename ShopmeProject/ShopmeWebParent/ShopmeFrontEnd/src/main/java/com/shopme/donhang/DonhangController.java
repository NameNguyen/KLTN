package com.shopme.donhang;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.TinhTrangDonHang;
import com.shopme.khachhang.KhachHangService;
import com.shopme.security.CustomerUserDetails;

@Controller
public class DonhangController {
	
	@Autowired
	private DonhangService orderService;
	
	@Autowired
	private KhachHangService customerService;	

	@GetMapping("/khachhang/donhang")
	public String listOrders(Model model, HttpServletRequest request,
			@AuthenticationPrincipal CustomerUserDetails userDetails) {
		return listOrdersByPage(model, userDetails, request, 1, "thoiGianDatHang", "desc", null);
	}
	@GetMapping("/khachhang/donhang/page/{pageNum}")
	public String listOrdersByPage(Model model,
						@AuthenticationPrincipal CustomerUserDetails userDetails,			
						HttpServletRequest request,
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		
		Page<DonHang> page = orderService.getOrdersForCustomer(
								customer, pageNum, sortField, sortDir, keyword);
		List<DonHang> listOrders = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		long startCount = (pageNum - 1) * DonhangService.ORDERS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + DonhangService.ORDERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		if (page.getTotalPages() > 1) {
			model.addAttribute("pageTitle", "Đơn đặt hàng của tôi (page " + pageNum + ")");
		} else {
			model.addAttribute("pageTitle", "Đơn hàng của tôi");
		}
		
		return "donhang/donhang";		
	}
	@GetMapping("/khachhang/donhang/detail/{id}")
	public String viewOrderDetails(Model model,
			@PathVariable(name = "id") Integer id,
			@AuthenticationPrincipal CustomerUserDetails userDetails) {
//		Customer customer = customerService.getCurrentlyLoggedInCustomer(authentication);
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		
		DonHang order = orderService.getOrderDetails(id, customer);
		
		model.addAttribute("pageTitle", "Order Details");
		model.addAttribute("order", order);
		
		return "donhang/order_detail_modal";
	}	
	@GetMapping("/donhang/delete/{id}")
	public String deleteOrder(
			@PathVariable(name = "id") Integer id, 
			Model model, RedirectAttributes ra,
			@AuthenticationPrincipal 
			CustomerUserDetails userDetails) {
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		DonHang order = orderService.getOrderDetails(id, customer);
		order.setTinhTrangDH(TinhTrangDonHang.CANCELLED);
		
		orderService.saveDonhang(order);
		
		return "redirect:/khachhang/donhang";
	}
	
}
