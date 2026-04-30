package entity;

import java.util.Objects;

public class Mon {
	private String maMon;
	private String tenMon;
	private String moTaMon;
	private double donGiaMua;
	private String duongDanAnh;
	private double donGiaBan;
	private boolean trangThai;
	private PhanLoaiMonAn phanLoaiMonAn;
	public String getDuongDanAnh() {
		return duongDanAnh;
	}
	public void setDuongDanAnh(String duongDanAnh) {
		this.duongDanAnh = duongDanAnh;
	}
	public String getMaMon() {
		return maMon;
	}
	public void setMaMon(String maMon) {
		this.maMon = maMon;
	}
	public String getTenMon() {
		return tenMon;
	}
	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}
	public double getDonGiaMua() {
		return donGiaMua;
	}
	public void setDonGiaMua(double donGiaMua) {
		this.donGiaMua = donGiaMua;
	}
	public double getDonGiaBan() {
		return donGiaBan;
	}
	public void setDonGiaBan(double donGiaBan) {
		this.donGiaBan = donGiaBan;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public PhanLoaiMonAn getPhanLoaiMonAn() {
		return phanLoaiMonAn;
	}
	public void setPhanLoaiMonAn(PhanLoaiMonAn phanLoaiMonAn) {
		this.phanLoaiMonAn = phanLoaiMonAn;
	}
	
	public String getMoTaMon() {
		return moTaMon;
	}
	public void setMoTaMon(String moTaMon) {
		this.moTaMon = moTaMon;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maMon);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mon other = (Mon) obj;
		return Objects.equals(maMon, other.maMon);
	}
	public Mon() {
		
	}
	public Mon(String maMon, String tenMon, double donGiaMua, double donGiaBan, boolean trangThai, 
			PhanLoaiMonAn phanLoaiMonAn, String moTamon, String duongDanAnh) {
		setMaMon(maMon);
		setTenMon(tenMon);
		setDonGiaBan(donGiaBan);
		setDonGiaMua(donGiaMua);
		setTrangThai(trangThai);
		setPhanLoaiMonAn(phanLoaiMonAn);
		setMoTaMon(moTamon);
		setDuongDanAnh(duongDanAnh);
	}
	@Override
	public String toString() {
		return "Mon [maMon=" + maMon + ", tenMon=" + tenMon + ", moTaMon=" + moTaMon + ", donGiaMua=" + donGiaMua
				+ ", duongDanAnh=" + duongDanAnh + ", donGiaBan=" + donGiaBan + ", trangThai=" + trangThai
				+ ", phanLoaiMonAn=" + phanLoaiMonAn + "]";
	}
	
	
}
