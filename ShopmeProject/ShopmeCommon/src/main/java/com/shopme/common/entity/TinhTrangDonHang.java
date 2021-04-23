package com.shopme.common.entity;

public enum TinhTrangDonHang {
	NEW {
		public String getDescription() {
			return "Đơn hàng đã được đặt bởi khách hàng";
		}
	},
	
	CANCELLED {
		public String getDescription() {
			return "Đơn hàng đã bị hủy";
		}		
	}, 
	
	PROCESSING {
		public String getDescription() {
			return "Yêu cầu đang được xử lý";
		}		
	},
	
	PACKAGED {
		public String getDescription() {
			return "Sản phẩm được đóng gói để vận chuyển";
		}		
	},
	
	PICKED {
		public String getDescription() {
			return "Người giao hàng đã chọn gói hàng";
		}		
	},
	
	SHIPPING {
		public String getDescription() {
			return "Gói hàng đang được chuyển đến";
		}		
	},
	
	DELIVERED {
		public String getDescription() {
			return "Gói hàng đã được giao";
		}		
	},
	
	RETURNED {
		public String getDescription() {
			return "Gói hàng đã được trả lại";
		}		
	},
	
	PAID {
		public String getDescription() {
			return "Khách hàng đã thanh toán đơn đặt hàng này";
		}		
	},
	
	REFUNDED {
		public String getDescription() {
			return "Đơn đặt hàng đã được hoàn lại cho khách hàng";
		}		
	};
	
	public String getDescription() {
		return "";
	}
}
