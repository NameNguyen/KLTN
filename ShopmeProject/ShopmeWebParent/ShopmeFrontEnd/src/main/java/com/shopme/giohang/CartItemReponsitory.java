package com.shopme.giohang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.MatHangGioHang;

@Repository
public interface CartItemReponsitory extends JpaRepository<MatHangGioHang, Integer> {

	
	
}
