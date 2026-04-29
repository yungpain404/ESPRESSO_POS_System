package entity;

import java.util.Objects;

public class Mon {
	private String maMon;
	private String tenMon;
	private double donGiaMua;
	private double donGiaBan;
	private boolean trangThai;
	private LoaiMon loaiMon;
	private PhanLoaiMonAn phanLoaiMonAn;
	private static int counter = 1;
	private String generateMaMon() {
		return "M" + counter++;
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
	public LoaiMon getLoaiMon() {
		return loaiMon;
	}
	public void setLoaiMon(LoaiMon loaiMon) {
		this.loaiMon = loaiMon;
	}
	public PhanLoaiMonAn getPhanLoaiMonAn() {
		return phanLoaiMonAn;
	}
	public void setPhanLoaiMonAn(PhanLoaiMonAn phanLoaiMonAn) {
		this.phanLoaiMonAn = phanLoaiMonAn;
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
	public Mon(String tenMon, double donGiaMua, double donGiaBan, boolean trangThai, LoaiMon loaiMon,
			PhanLoaiMonAn phanLoaiMonAn) {
		super();
		setMaMon(generateMaMon());
		setTenMon(tenMon);
		setDonGiaBan(donGiaBan);
		setDonGiaMua(donGiaMua);
		setTrangThai(trangThai);
		setLoaiMon(loaiMon);
		setPhanLoaiMonAn(phanLoaiMonAn);
	}
	@Override
	public String toString() {
		return "Mon [maMon=" + maMon + ", tenMon=" + tenMon + ", donGiaMua=" + donGiaMua + ", donGiaBan=" + donGiaBan
				+ ", trangThai=" + trangThai + ", loaiMon=" + loaiMon + ", phanLoaiMonAn=" + phanLoaiMonAn + "]";
	}
	
}
