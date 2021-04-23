package com.shopme.common.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "datnuoc")
public class DatNuoc {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDatnuoc;
	
	@NotBlank
	private String ten;
	
	private String maVung;
	
	@OneToMany(mappedBy = "datnuoc")
	private Set<Tinh> dsTinh;

	public DatNuoc() {
		
	}

	public DatNuoc(Integer ma, String ten) {
		this.maDatnuoc = ma;
		this.ten = ten;
	}

	public DatNuoc(String ten, String maSo) {
		this.ten = ten;
		this.maVung = maSo;
	}

	public DatNuoc(Integer ma) {
		this.maDatnuoc = ma;
	}

	public DatNuoc( String ten) {
		this.ten = ten;
	}

	public Integer getMa() {
		return maDatnuoc;
	}

	public void setMa(Integer ma) {
		this.maDatnuoc = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMaSo() {
		return maVung;
	}

	public void setMaSo(String maSo) {
		this.maVung = maSo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maDatnuoc == null) ? 0 : maDatnuoc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatNuoc other = (DatNuoc) obj;
		if (maDatnuoc == null) {
			if (other.maDatnuoc != null)
				return false;
		} else if (!maDatnuoc.equals(other.maDatnuoc))
			return false;
		return true;
	}
	
	
	
	
	

}
