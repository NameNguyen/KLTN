package com.shopme.diachi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.DiaChi;

@Repository
public interface DiachiReponsitory extends JpaRepository<DiaChi, Integer>{

	@Query("SELECT a FROM DiaChi a WHERE a.khachHang.maKhachHang = ?1 AND a.chonMacdinh = true")
	public DiaChi findDefaultByKhachhang(Integer maKhachhang);
}
