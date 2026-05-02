package dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.HoaDon;
import entity.PhuongThucThanhToan;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {
    private String filePath = "data/HoaDon.json";
    private Gson gson;

    public HoaDon_DAO() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdir();

        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                }
            })
            .setPrettyPrinting()
            .create();
    }

    public List<HoaDon> getAll() {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            List<HoaDon> list = gson.fromJson(reader, new TypeToken<List<HoaDon>>(){}.getType());
            if (list == null) return new ArrayList<>();

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

    public List<HoaDon> getByDate(LocalDate ngay) {
        List<HoaDon> result = new ArrayList<>();
        for (HoaDon hd : getAll()) {
            if (hd.getNgayGioLap() != null && hd.getNgayGioLap().equals(ngay)) {
                result.add(hd);
            }
        }
        return result;
    }

    public boolean addHoaDon(HoaDon hd) {
        List<HoaDon> list = getAll();
        list.add(hd);
        return saveData(list);
    }

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