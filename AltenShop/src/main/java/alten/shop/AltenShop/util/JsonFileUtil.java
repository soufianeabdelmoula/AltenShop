package alten.shop.AltenShop.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;




@Component
	public class JsonFileUtil {

	    private final ObjectMapper objectMapper = new ObjectMapper();
	    private final String filePath = "products.json";

	    public <T> List<T> readJsonFile(Class<T> clazz) throws IOException {
	        File file = new File(filePath);
	        if (!file.exists()) {
	            file.createNewFile();
	            objectMapper.writeValue(file, List.of());
	        }
	        return objectMapper.readValue(file, new TypeReference<List<T>>() {});
	    }

	    public <T> void writeJsonFile(List<T> data) throws IOException {
	        objectMapper.writeValue(new File(filePath), data);
	    }


}
