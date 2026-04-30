package dao;

import entity.Mon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Mon_DAO {
    private String filePath = "data/Mon.json";
    private Gson gson = new Gson();
    
    public List<Mon> getAll() {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<List<Mon>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); 
        }
    }

    public boolean addMon(Mon mon) {
        List<Mon> list = getAll();
        list.add(mon);
        return saveData(list);
    }

    private boolean saveData(List<Mon> list) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}