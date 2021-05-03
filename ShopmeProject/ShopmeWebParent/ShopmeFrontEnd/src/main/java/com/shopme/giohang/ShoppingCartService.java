package com.shopme.giohang;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.common.entity.SanPham;
import com.shopme.sanpham.SanPhamRepository;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired 
	private CartItemReponsitory cartRepo;
	
	@Autowired private SanPhamRepository sanPhamRepo;
	
	public List<MatHangGioHang> listCartItem(KhachHang khachHang) {
		return cartRepo.findByKhachhang(khachHang);
	}
	
	public Integer addProduct(Integer maSanpham, Integer soLuong, KhachHang khachHang) {
		Integer addedQuantity = soLuong;
		
		SanPham sanPham = sanPhamRepo.findById(maSanpham).get();
		
		MatHangGioHang gioHang = cartRepo.findByKhachhangAndSanpham(khachHang, sanPham);
		
		if(gioHang != null) {
			addedQuantity = gioHang.getSoLuong() + soLuong;
			gioHang.setSoLuong(addedQuantity);
		} else {
			gioHang = new MatHangGioHang();
			gioHang.setSoLuong(soLuong);
			gioHang.setKhachhang(khachHang);
			gioHang.setSanpham(sanPham);
		}
		cartRepo.save(gioHang);
		return addedQuantity;
	}
	
	public float updateSoluong(Integer maSanpham, Integer soLuong, KhachHang khachHang) {
		
		cartRepo.updateSoluong(soLuong, maSanpham, khachHang.getMaKhachHang());
		SanPham sanPham = sanPhamRepo.findById(maSanpham).get();
		
		float subTotal = sanPham.getGiaBan() * soLuong;
		return subTotal;
	}
	
	public void removeSanpham(Integer maSanpham, KhachHang khachHang) {
		cartRepo.deleteByKhachhangAndSanpham(maSanpham, khachHang.getMaKhachHang());
	}
}
