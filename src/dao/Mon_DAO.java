package dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.Mon;

public class Mon_DAO {
    private String filePath = "data/Mon.json";
    private Gson gson = new Gson();

    public List<Mon> getAll() {
        try (FileReader reader = new FileReader(filePath)) {
        	List result = gson.fromJson(reader, new TypeToken<List<Mon>>(){}.getType());
        	if(result != null)
        		return result;
        	else 
        		return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Trả về list rỗng");
            return new ArrayList<>();
        }
    }
    
    public Mon getMonById(String maMon) {
    	List<Mon> dsMon = getAll();
    	
    	Mon monCanTim = null;
    	
    	for(Mon m : dsMon) {
    		if(m.getMaMon().contains(maMon)) {
    			monCanTim = m;
    		}
    	}
    	
    	return monCanTim;
    }
    
    public boolean deleteMonById(String maMon) {
    	List<Mon> dsMon = getAll();
        boolean isSuccess = dsMon.removeIf(m -> m.getMaMon().equalsIgnoreCase(maMon));

        if (isSuccess) {
            saveData(dsMon);
        }
        return isSuccess;
    }

    public boolean addMon(Mon mon) {
        List<Mon> list = getAll();
        list.add(mon);
        return saveData(list);
    }
    
    public boolean updateMon(Mon mon) {
    	List<Mon> dsMon = getAll();
    	boolean isSuccess = false;
    	for(int i = 0; i < dsMon.size(); i++) {
    		Mon m = dsMon.get(i);
    		if(m.equals(mon)) {
    			dsMon.set(i, mon);
    			isSuccess = true;
    		}
    	}
    	
    	if(isSuccess == true) 
    		saveData(dsMon);
    	return isSuccess;
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