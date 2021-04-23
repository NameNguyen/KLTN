package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chitiet_donhang")
public class ChiTietDonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maChiTietDonHang;
	
	private int soLuong;
	
	private float chiPhi;
	
	private float ship;
	
	@Column(name = "don_gia")
	private float donGia;
	
	private float tongphu;
	
	@ManyToOne
	@JoinColumn(name = "donhang_id")
	private DonHang donhang;
	
	@ManyToOne
	@JoinColumn(name = "sanpham_id")
	private SanPham sanpham;
}
