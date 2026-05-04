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
        String sql = "SELECT * FROM ChiTietHoaDon";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setMaCTHD(rs.getString("maCTHD"));
                
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));
                ct.setHoaDon(hd);
                
                Mon m = new Mon();
                m.setMaMon(rs.getString("maMon"));
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