package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.PhuongThucThanhToan;
import entity.TaiKhoan;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));
                
                TaiKhoan tk = new TaiKhoan();
                tk.setMaTaiKhoan(rs.getString("maTaiKhoanLap"));
                hd.setTaiKhoanLap(tk);
                
                hd.setNgayGioLap(rs.getDate("ngayGioLap").toLocalDate());
                hd.setTrangThaiTT(rs.getBoolean("trangThaiTT"));
                
                String ptt = rs.getString("phuongThucTT");
                hd.setPhuongThucTT(ptt != null ? PhuongThucThanhToan.valueOf(ptt) : PhuongThucThanhToan.TIENMAT);
                
                hd.setTongTien(rs.getDouble("tongTien"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDon> getByDate(LocalDate ngay) {
        List<HoaDon> result = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE ngayGioLap = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(ngay));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHD(rs.getString("maHD"));
                    // ... Map tương tự như getAll()
                    result.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (maHD, maTaiKhoanLap, ngayGioLap, trangThaiTT, phuongThucTT, tongTien) VALUES (?,?,?,?,?,?)";
        Connection con = null;
        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false); // Bắt đầu Transaction

            // 1. Lưu Hóa Đơn
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, hd.getMaHD());
                ps.setString(2, hd.getTaiKhoanLap().getMaTaiKhoan());
                ps.setDate(3, Date.valueOf(hd.getNgayGioLap()));
                ps.setBoolean(4, hd.isTrangThaiTT());
                ps.setString(5, hd.getPhuongThucTT().name());
                ps.setDouble(6, hd.getTongTien());
                ps.executeUpdate();
            }

            // 2. Lưu Chi Tiết Hóa Đơn (Tận dụng addAll nhưng dùng chung connection)
            if (hd.getDsChiTiet() != null && !hd.getDsChiTiet().isEmpty()) {
                String sqlCT = "INSERT INTO ChiTietHoaDon (maCTHD, maHD, maMon, soLuongMon, ghiChuKhachHang, thanhTien) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement psCT = con.prepareStatement(sqlCT)) {
                    for (entity.ChiTietHoaDon ct : hd.getDsChiTiet()) {
                        psCT.setString(1, ct.getMaCTHD());
                        psCT.setString(2, hd.getMaHD());
                        psCT.setString(3, ct.getMon().getMaMon());
                        psCT.setInt(4, ct.getSoLuongMon());
                        psCT.setNString(5, ct.getGhiChuKhachHang());
                        psCT.setDouble(6, ct.getThanhTien());
                        psCT.addBatch();
                    }
                    psCT.executeBatch();
                }
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
    
    private boolean saveData(List<HoaDon> list) {
        return true; 
    }
}