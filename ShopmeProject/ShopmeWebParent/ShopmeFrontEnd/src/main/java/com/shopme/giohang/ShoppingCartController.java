package com.shopme.giohang;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.khachhang.KhachHangService;
import com.shopme.security.CustomerUserDetails;

@Controller
public class ShoppingCartController {

	@Autowired private ShoppingCartService cartService;
	
	@Autowired private KhachHangService khachHangService;
	
	@GetMapping("/giohang")
	public String showShoppingCart(Model model,
			@AuthenticationPrincipal  CustomerUserDetails userDetails) {
		
		
		String userEmail = userDetails.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
//		KhachHang khachHang = khachHangService.getCurrentlyLoggedInCustomer(authentication);
		
		
		List<MatHangGioHang> dsGioHang = cartService.listCartItem(khachHang);
		
		
		
		model.addAttribute("dsGioHang", dsGioHang);
//		model.addAttribute("pageTitle", "Shopping Cart");
		
		return "giohang";
	}
	
}
