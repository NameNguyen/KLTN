package com.shopme.admin.sanpham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SanPhamRestController {
	@Autowired private SanPhamService service;

	@PostMapping("/sanpham/check_unique")
	public String checkUnique(@Param("maSanPham") Integer maSanPham, @Param("ten") String ten) {
		return service.checkUnique(maSanPham, ten);
	}
}
