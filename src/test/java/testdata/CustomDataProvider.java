package testdata;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.DataProvider;

public class CustomDataProvider {

	@DataProvider(name = "search-data")
	
	public static Object[][] getSearchData() {
	  Map<String, String> map = new HashMap<String, String>();
	    map.put("city", "Goa");
	    map.put("date", "15");
	    map.put("adults", "2");
	    map.put("child", "1");
	    map.put("childAge", "5");
	    map.put("budget", "8,000");
	    map.put("rating", "Wonderful");
	    map.put("meals", "2");
	    map.put("meal1", "Lunch");
	    map.put("meal2", "Dinner");
	    map.put("reserve", "true"); // reserve flag will execute the additional reserve & booking test
	  return new Object[][] 
	       {
	        {map} 
	       };
	}

}
