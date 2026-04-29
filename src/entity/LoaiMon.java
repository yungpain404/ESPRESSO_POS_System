package entity;

import java.util.Objects;

public class LoaiMon {
	private String maLoaiMon;
	private String tenLoaiMon;
	private static int count = 1;
	
	private String generateMaLoaiMon() {
		return "LM" + count++;
	}
	
	public String getMaLoaiMon() {
		return maLoaiMon;
	}

	public void setMaLoaiMon(String maLoaiMon) {
		this.maLoaiMon = maLoaiMon;
	}

	public String getTenLoaiMon() {
		return tenLoaiMon;
	}

	public void setTenLoaiMon(String tenLoaiMon) {
		this.tenLoaiMon = tenLoaiMon;
	}

	public LoaiMon(String tenLoai) {
		setMaLoaiMon(generateMaLoaiMon());
		setTenLoaiMon(tenLoai);
	}

	@Override
	public int hashCode() {
		return Objects.hash(maLoaiMon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiMon other = (LoaiMon) obj;
		return Objects.equals(maLoaiMon, other.maLoaiMon);
	}

	@Override
	public String toString() {
		return "LoaiMon [maLoaiMon=" + maLoaiMon + ", tenLoaiMon=" + tenLoaiMon + "]";
	}
	
}
