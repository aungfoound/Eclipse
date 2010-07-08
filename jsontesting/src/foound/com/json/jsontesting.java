package foound.com.json;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;

public class jsontesting extends Activity {
	private JSONObject jObject;
	private String jString = "{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": { \"menuitem\": [ {\"value\": \"New\",   \"onclick\": \"CreateNewDoc()\"}, {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"}, {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void parse() throws Exception {
		System.out.println(jString);
		jObject = new JSONObject(jString);

		JSONObject menuObject = jObject.getJSONObject("menu");
		String attributeId = menuObject.getString("id");
		System.out.println(attributeId);

		String attributeValue = menuObject.getString("value");
		System.out.println(attributeValue);

		JSONObject popupObject = menuObject.getJSONObject("popup");
		JSONArray menuitemArray = popupObject.getJSONArray("menuitem");

		for (int i = 0; i < 3; i++) {
			System.out.println(menuitemArray.getJSONObject(i)
					.getString("value").toString());
			System.out.println(menuitemArray.getJSONObject(i).getString(
					"onclick").toString());
		}
	}
}
