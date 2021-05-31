package com.shopme.giohang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.KhachHang;
import com.shopme.khachhang.KhachHangService;
import com.shopme.security.CustomerUserDetails;

@RestController
public class GioHangRestController {

	@Autowired
	private GioHangService cartService;
	
	@Autowired
	private KhachHangService khachHangService;
	
	@PostMapping("/cart/add/{pid}/{qty}")
	public String addProductToCart(
			@PathVariable("pid") Integer maSanpham,
			@PathVariable("qty") Integer soLuong,
			@AuthenticationPrincipal CustomerUserDetails userDetail) {
		
		System.out.println("addProductToCart " + maSanpham + " - " + soLuong);
		
		if(userDetail == null ) {
			return "Bạn cần đăng nhập trước khi thêm hàng vào giỏ";
		}
		String userEmail = userDetail.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
		
		
		
		Integer addedQuantity = cartService.addProduct(maSanpham, soLuong, khachHang);
		
		System.out.println("Thêm thành công");
		return addedQuantity + "Mặt hàng của sản phẩm này đã được thêm vào giỏ hàng của bạn";
	}
	
	@PostMapping("/cart/update/{pid}/{qty}")
	public String updateToCart(
			@PathVariable("pid") Integer maSanpham,
			@PathVariable("qty") Integer soLuong,
			@AuthenticationPrincipal CustomerUserDetails userDetail) {
		
		
		if(userDetail == null ) {
			return "Bạn cần đăng nhập trước khi chỉnh sửa hàng trong giỏ";
		}
		String userEmail = userDetail.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
		
		
		
		float subTotal = cartService.updateSoluong(maSanpham, soLuong, khachHang);
		
		
		return String.valueOf(subTotal);
	}
	
	@PostMapping("/cart/remove/{pid}")
	public String deleteSanphamToCart(
			@PathVariable("pid") Integer maSanpham,
			@AuthenticationPrincipal CustomerUserDetails userDetail) {
		
		
		if(userDetail == null ) {
			return "Bạn cần đăng nhập trước khi xóa hàng vào giỏ";
		}
		String userEmail = userDetail.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
		
		
		
		cartService.removeSanpham(maSanpham, khachHang);
		
		
		return "Sản phẩm đã được removed xóa trong giỏ";
	}
}
