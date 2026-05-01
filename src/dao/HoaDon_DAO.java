package dao;

import entity.HoaDon;
import entity.PhuongThucThanhToan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {
    private String filePath = "data/HoaDon.json";
    private Gson gson;
    
    public HoaDon_DAO() {
        // Cấu hình GSON với LocalDate deserializer
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                return LocalDate.parse(json.getAsString());
            })
            .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> {
                return context.serialize(src.toString());
            })
            .create();
    }
    
    /**
     * Lấy tất cả hóa đơn từ JSON
     * @return List<HoaDon>
     */
    public List<HoaDon> getAll() {
        try (FileReader reader = new FileReader(filePath)) {
            List<HoaDon> list = gson.fromJson(reader, new TypeToken<List<HoaDon>>(){}.getType());
            
            for (HoaDon hd : list) {
                if (hd.getPhuongThucTT() == null) {
                    hd.setPhuongThucTT(PhuongThucThanhToan.TIENMAT);
                }
            }
            
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); 
        }
    }

    /**
     * Lấy danh sách hóa đơn theo ngày
     * @param ngay - Ngày cần lấy
     * @return List<HoaDon>
     */
    public List<HoaDon> getByDate(LocalDate ngay) {
        List<HoaDon> list = getAll();
        List<HoaDon> result = new ArrayList<>();
        for (HoaDon hd : list) {
            if (hd.getNgayGioLap() != null && hd.getNgayGioLap().equals(ngay)) {
                result.add(hd);
            }
        }
        return result;
    }

    /**
     * Lưu dữ liệu vào file JSON
     * @param list
     * @return true nếu lưu thành công
     */
    private boolean saveData(List<HoaDon> list) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}