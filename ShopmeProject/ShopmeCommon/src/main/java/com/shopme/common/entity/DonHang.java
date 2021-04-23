package com.shopme.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "donhang")
public class DonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDonHang;
	
	@Column(name = "ho")
	private String ho;
	
	@Column(name = "ten")
	private String ten;
	
	@Column(name = "so_dien_thoai")
	private String soDienThoai;
	
	@Column(name = "dia_chi1")
	private String diaChi1;
	
	@Column(name = "dia_chi2")
	private String diaChi2;
	
	@Column(name = "ma_buu_dien")
	private String maBuuDien;
	
	private String thanhPho;
	private String tinh;
	private String quocGia;
	
	private Date thoiGianDatHang;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "phuong_thuc_thanh_toan")
	private PhuongThucThanhToan phuongThucThanhToan;
	
	@Column(name = "gia_van_chuyen")
	private float giaVanChuyen;
	
	private float chiPhi;
	private float thue;
	private float tongPhu;
	private float tong;
	
	@Enumerated(EnumType.STRING)
	private TinhTrangDonHang tinhTrangDH;
	
	@Column(name = "giao_ngay")
	private int giaoNgay;
	
	@Column(name = "ngay_giao_hang")
	private Date ngayGiaoHang;
	
	@ManyToOne
	@JoinColumn(name = "khachhang_id")
	private KhachHang khachHang;
	
	@OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ChiTietDonHang> chiTietDH = new HashSet<>();
	
	@OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TheoDoiDonHang> theoDoiDH = new ArrayList<>();
	
	
	
	
	
	
	
}
