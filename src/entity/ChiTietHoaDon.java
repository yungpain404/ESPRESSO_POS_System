package entity;

import java.util.Objects;

public class ChiTietHoaDon {
	private String maCTHD;
	private HoaDon hoaDon;
	private Mon mon;
	private int soLuongMon;
	private String ghiChuKhachHang;
	private double thanhTien;

	public String getMaCTHD() {
		return maCTHD;
	}

	public void setMaCTHD(String maCTHD) {
		this.maCTHD = maCTHD;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public Mon getMon() {
		return mon;
	}

	public void setMon(Mon mon) {
		this.mon = mon;
	}

	public int getSoLuongMon() {
		return soLuongMon;
	}

	public void setSoLuongMon(int soLuongMon) {
		this.soLuongMon = soLuongMon;
	}

	public String getGhiChuKhachHang() {
		return ghiChuKhachHang;
	}

	public void setGhiChuKhachHang(String ghiChuKhachHang) {
		this.ghiChuKhachHang = ghiChuKhachHang;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien() {
		this.thanhTien = mon.getDonGiaBan() * soLuongMon;
	}

	public ChiTietHoaDon() {

	}

	public ChiTietHoaDon(String maCTHD, HoaDon hoaDon, Mon mon, int soLuongMon, String ghiChuKhachHang) {
		setMaCTHD(maCTHD);
		setHoaDon(hoaDon);
		setMon(mon);
		setSoLuongMon(soLuongMon);
		setGhiChuKhachHang(ghiChuKhachHang);
		setThanhTien();
	}

	@Override
	public int hashCode() {
		return Objects.hash(maCTHD);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Objects.equals(maCTHD, other.maCTHD);
	}

	@Override
	public String toString() {
		return "ChiTietHoaDon [maCTHD=" + maCTHD + ", hoaDon=" + hoaDon + ", mon=" + mon + ", soLuongMon=" + soLuongMon
				+ ", ghiChuKhachHang=" + ghiChuKhachHang + ", thanhTien=" + thanhTien + "]";
	}

}
