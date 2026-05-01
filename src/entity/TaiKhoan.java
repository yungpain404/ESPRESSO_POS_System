package entity;

import java.util.Objects;
import java.util.UUID;

public class TaiKhoan {
	private String maTaiKhoan;
	private String tenTaiKhoan;
	private String matKhau;
	private boolean trangThaiHoatDong;

	public TaiKhoan() {
	}

	public TaiKhoan(String tenTaiKhoan, String matKhau, boolean trangThaiHoatDong) {
	    this.maTaiKhoan = UUID.randomUUID().toString();
	    this.tenTaiKhoan = tenTaiKhoan;
	    this.matKhau = matKhau;
	    this.trangThaiHoatDong = trangThaiHoatDong;
	}

	public TaiKhoan(String maTaiKhoan, String tenTaiKhoan, String matKhau, boolean trangThaiHoatDong) {
	    this.maTaiKhoan = maTaiKhoan;
	    this.tenTaiKhoan = tenTaiKhoan;
	    this.matKhau = matKhau;
	    this.trangThaiHoatDong = trangThaiHoatDong;
	}

	public String getMaTaiKhoan() {
		return maTaiKhoan;
	}
	public void setMaTaiKhoan(String maTaiKhoan) {
		this.maTaiKhoan = maTaiKhoan;
	}
	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}
	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public boolean isTrangThaiHoatDong() {
		return trangThaiHoatDong;
	}
	public void setTrangThaiHoatDong(boolean trangThaiHoatDong) {
		this.trangThaiHoatDong = trangThaiHoatDong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTaiKhoan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(maTaiKhoan, other.maTaiKhoan);
	}

	@Override
	public String toString() {
		return "TaiKhoan [maTaiKhoan=" + maTaiKhoan + ", tenTaiKhoan=" + tenTaiKhoan + ", trangThaiHoatDong=" + trangThaiHoatDong + "]";
	}


}
