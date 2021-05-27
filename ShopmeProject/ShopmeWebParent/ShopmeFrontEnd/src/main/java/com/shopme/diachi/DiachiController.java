package com.shopme.diachi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.KhachHang;
import com.shopme.khachhang.KhachHangService;
import com.shopme.security.CustomerUserDetails;

@Controller
public class DiachiController {

	@Autowired
	private DiachiService addressService;
	
	@Autowired
	private KhachHangService customerService;
//	@AuthenticationPrincipal Authentication authentication
	@GetMapping("/khachhang/diachi")
	public String listAddresses(Model model, @AuthenticationPrincipal CustomerUserDetails userDetails) {
//		KhachHang customer = customerService.getCurrentlyLoggedInCustomer(authentication);
		// refresh customer's info from database
//		customer = customerService.getCustomerByEmail(customer.getEmail());
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		List<DiaChi> listAddresses = addressService.listAddressesOf(customer);
		DiaChi defaultAddress = addressService.getDefaultAddressOf(customer);
//		System.out.println(defaultAddress);
		Integer defaultAddressId = 0;	// customer's address
		
		if (defaultAddress != null) {
			defaultAddressId = defaultAddress.getMa();
		}
		model.addAttribute("listAddresses", listAddresses);
		model.addAttribute("defaultAddressId", defaultAddressId);
		model.addAttribute("customer", customer);
		model.addAttribute("pageTitle", "Chọn một địa chỉ giao hàng");
		
		return "diachi/diachi";
	}
	
	@PostMapping("/khachhang/diachi/save")
	public String saveAddress(DiaChi address, HttpServletRequest request,
			@AuthenticationPrincipal CustomerUserDetails userDetails) {
//		Customer customer = customerService.getCurrentlyLoggedInCustomer(authentication);
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		String redirectOption = request.getParameter("redirect");
		String redirectURL = "redirect:/khachhang/diachi";
		
		address.setKhachHang(customer);
		
		if ("checkout".equals(redirectOption)) {
			address.setDefaultSelection(true);
			redirectURL = "redirect:/checkout";
		}
		
		addressService.save(address, customer);
		
		return redirectURL;
		
	}
	
	@GetMapping("/khachhang/diachi/chon/{id}")
	public String chooseDefaultAddress(@PathVariable(name = "id") Integer addressId,
			HttpServletRequest request,
			@AuthenticationPrincipal CustomerUserDetails userDetails) {
//		Customer customer = customerService.getCurrentlyLoggedInCustomer(authentication);
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		System.out.println(customer.getDiachi());
		addressService.setDefaultAddress(addressId, customer);		
		
		String redirectOption = request.getParameter("redirect");
		
		if ("checkout".equals(redirectOption)) {
			return "redirect:/checkout";
		}
		
		return "redirect:/khachhang/diachi";
	}
	
	@GetMapping("/khachhang/diachi/new")
	public String newAddress(Model model, @AuthenticationPrincipal CustomerUserDetails userDetails) {
//		KhachHang customer = customerService.getCurrentlyLoggedInCustomer(authentication);
		String userEmail = userDetails.getUsername();
		KhachHang customer = customerService.getCustomerByEmail(userEmail);
		List<DatNuoc> countries = customerService.listAllCountries();
		
		model.addAttribute("listCountries", countries);
		
		model.addAttribute("customer", customer);
		model.addAttribute("pageTitle", "Tạo địa chỉ mới");
		model.addAttribute("address", new DiaChi());
		
		return "diachi/diachi_form";		
	}
	
	@GetMapping("/khachhang/diachi/edit/{id}")
	public String editAddress(@PathVariable(name = "id") Integer addressId,
			Model model,
			@AuthenticationPrincipal CustomerUserDetails userDetails) {
		
//			Customer customer = customerService.getCurrentlyLoggedInCustomer(authentication);
			String userEmail = userDetails.getUsername();
			KhachHang customer = customerService.getCustomerByEmail(userEmail);
			DiaChi address = addressService.get(addressId, customer);
			
			if (address == null) {
				model.addAttribute("title", "Chỉnh sửa địa chỉ");
				model.addAttribute("message", "Không thể tìm thấy bất kỳ địa chỉ nào có ID " + addressId);
				return "message";
			}
			
			List<DatNuoc> countries = customerService.listAllCountries();
			
			model.addAttribute("listCountries", countries);
						model.addAttribute("address", address);
			model.addAttribute("pageTitle", "Chỉnh sửa địa chỉ (ID: " + addressId + ")");
			
			return "diachi/diachi_form";
	}

	@GetMapping("/diachi/delete/{id}")
	public String deleteAddress(@PathVariable(name = "id") Integer addressId,
			Model model,
			@AuthenticationPrincipal CustomerUserDetails userDetails) {
		
			String userEmail = userDetails.getUsername();
			KhachHang customer = customerService.getCustomerByEmail(userEmail);
			addressService.delete(addressId, customer);
			
			return "redirect:/khachhang/diachi";
	}	
}
