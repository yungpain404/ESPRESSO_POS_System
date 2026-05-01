package dao;

import com.google.gson.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;
import entity.HoaDon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {
    private String filePath = "data/HoaDon.json";
 // Tạo Gson với khả năng đọc/ghi LocalDate
    private Gson gson = new GsonBuilder()
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
        .setPrettyPrinting() // Giúp file JSON dễ đọc hơn
        .create();

    public HoaDon_DAO() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public List<HoaDon> getAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(filePath)) {
            List<HoaDon> list = gson.fromJson(reader, new TypeToken<List<HoaDon>>(){}.getType());
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); 
        }
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