package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Mon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDon_DAO {

	public List<ChiTietHoaDon> getAll() {
	    List<ChiTietHoaDon> list = new ArrayList<>();
	    String sql = "SELECT ct.*, m.tenMon, m.donGiaBan, m.duongDanAnh, m.phanLoaiMonAn " +
	                 "FROM ChiTietHoaDon ct " +
	                 "JOIN Mon m ON ct.maMon = m.maMon";
	    
	    try (Connection con = ConnectDB.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            HoaDon hd = new HoaDon();
	            hd.setMaHD(rs.getString("maHD"));
	            
	            Mon m = new Mon();
	            m.setMaMon(rs.getString("maMon"));
	            m.setTenMon(rs.getNString("tenMon"));
	            m.setDonGiaBan(rs.getDouble("donGiaBan"));
	            m.setDuongDanAnh(rs.getString("duongDanAnh"));
	            
	            String loaiMon = rs.getString("phanLoaiMonAn");
	            if (loaiMon != null) {
	                m.setPhanLoaiMonAn(entity.PhanLoaiMonAn.valueOf(loaiMon));
	            }
	            
	            ChiTietHoaDon ct = new ChiTietHoaDon();
	            ct.setMaCTHD(rs.getString("maCTHD"));
	            ct.setHoaDon(hd);
	            ct.setMon(m);
	            ct.setSoLuongMon(rs.getInt("soLuongMon"));
	            ct.setGhiChuKhachHang(rs.getNString("ghiChuKhachHang"));
	            
	            ct.setThanhTien();
	            
	            list.add(ct);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
    
    public List<ChiTietHoaDon> getChiTietByMaHD(String maHD) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        // JOIN với bảng Mon để lấy thông tin chi tiết của món ăn
        String sql = "SELECT ct.*, m.tenMon, m.donGiaBan, m.duongDanAnh, m.phanLoaiMonAn " +
                     "FROM ChiTietHoaDon ct " +
                     "JOIN Mon m ON ct.maMon = m.maMon " +
                     "WHERE ct.maHD = ?";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	// 1. Tạo đối tượng HoaDon cha (chỉ nạp mã để định danh)
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaHD(maHD);
                    
                    // 2. Tạo đối tượng Mon với đầy đủ thông tin từ kết quả JOIN
                    Mon mon = new Mon();
                    mon.setMaMon(rs.getString("maMon"));
                    mon.setTenMon(rs.getNString("tenMon"));
                    mon.setDonGiaBan(rs.getDouble("donGiaBan"));
                    mon.setDuongDanAnh(rs.getString("duongDanAnh"));

                    // 3. Tạo đối tượng ChiTietHoaDon
                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setMaCTHD(rs.getString("maCTHD"));
                    ct.setMon(mon); // Gán đối tượng Mon đã "đầy đủ" vào đây
                    ct.setSoLuongMon(rs.getInt("soLuongMon"));
                    ct.setGhiChuKhachHang(rs.getNString("ghiChuKhachHang"));
                    ct.setHoaDon(hoaDon);
                    ct.setThanhTien();
                    
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveData(List<ChiTietHoaDon> list) {
        return addAll(list);
    }

    public boolean addAll(List<ChiTietHoaDon> newDetails) {
        String sql = "INSERT INTO ChiTietHoaDon (maCTHD, maHD, maMon, soLuongMon, ghiChuKhachHang, thanhTien) VALUES (?,?,?,?,?,?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            con.setAutoCommit(false); 
            for (ChiTietHoaDon ct : newDetails) {
                ps.setString(1, ct.getMaCTHD());
                ps.setString(2, ct.getHoaDon().getMaHD());
                ps.setString(3, ct.getMon().getMaMon());
                ps.setInt(4, ct.getSoLuongMon());
                ps.setNString(5, ct.getGhiChuKhachHang());
                ps.setDouble(6, ct.getThanhTien());
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}