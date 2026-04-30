package dao;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mindrot.jbcrypt.BCrypt;

import entity.TaiKhoan;
public class TaiKhoan_DAO {
	private static final String FILE_PATH = "data//TaiKhoan.json";
	public List<TaiKhoan> docDanhSachTaiKhoan() {
		List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
		try (Reader reader = new FileReader(FILE_PATH)) {
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<TaiKhoan>>(){}.getType();
			dsTaiKhoan = gson.fromJson(reader, listType);
		
			if (dsTaiKhoan == null) {
				dsTaiKhoan = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsTaiKhoan;
	}

	public TaiKhoan kiemTraDangNhap(String tenDangNhap, String matKhauNhapVao) {
		List<TaiKhoan> danhSach = docDanhSachTaiKhoan();
		
		for (TaiKhoan tk : danhSach) {
			if (tk.getTenTaiKhoan().equals(tenDangNhap)) {
				if (!tk.isTrangThaiHoatDong()) {
					return null;
				}
				if (BCrypt.checkpw(matKhauNhapVao, tk.getMatKhau())) {
					return tk;
				}
			}
		}
		return null;
	}
}
