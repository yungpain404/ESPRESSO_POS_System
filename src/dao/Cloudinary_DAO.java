package dao;

import java.io.File;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class Cloudinary_DAO {
    private Cloudinary cloudinary;

    public Cloudinary_DAO() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "drfkacsvn",
            "api_key", "133518529112266",
            "api_secret", "p2LaoWsqgAutUXdQTsO2c9W14HE",
            "secure", true
        ));
    }

    @SuppressWarnings("rawtypes")
	public String uploadImage(String filePath) {
        try {
            File file = new File(filePath);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "espresso"));
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}