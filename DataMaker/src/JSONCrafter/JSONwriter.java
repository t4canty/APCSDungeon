package JSONCrafter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONwriter {
	/**
	 * Game Object 
	 * @param name
	 * @param x
	 * @param y
	 * @param id
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public JSONwriter(String name, int x, int y, int id) throws FileNotFoundException {
		//Required code
		JSONObject jo = new JSONObject();
		jo.put("Name", name);
		jo.put("X", x);
		jo.put("Y", y);
		jo.put("Id", id);
		//FileIO
		PrintWriter w = new PrintWriter(new File(name + ".json"));
		w.write(jo.toJSONString());
		w.flush();
		w.close();
	}
	/**
	 * Enemy Object
	 * @param name
	 * @param x
	 * @param y
	 * @param id
	 * @param HP
	 * @param damage
	 * @param drops
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public JSONwriter(String name, int x, int y, int id, int HP, int damage, ArrayList<Integer> drops) throws FileNotFoundException {
		//Required code
		JSONObject jo = new JSONObject();
		jo.put("Name", name);
		jo.put("X", x);
		jo.put("Y", y);
		jo.put("Id", id);
		jo.put("HP", HP);
		jo.put("Damage", damage);
		
		JSONArray ja = new JSONArray();
		ja.add(drops);
		if(id == 1) {
			JSONArray j2 = new JSONArray();
			j2.add(new ArrayList<Integer>());
			jo.put("Inventory:",j2);
		}
		jo.put("Drops",ja);
		
		//FileIO
		PrintWriter w = new PrintWriter(new File(name + ".json"));
		w.write(jo.toJSONString());
		w.flush();
		w.close();
		
	}
	/**
	 * NPC Object
	 * @param name
	 * @param x
	 * @param y
	 * @param id
	 * @param XMLPath
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public JSONwriter(String name, int x, int y, int id, String XMLPath) throws FileNotFoundException {
		//Required code
		JSONObject jo = new JSONObject();
		jo.put("Name", name);
		jo.put("X", x);
		jo.put("Y", y);
		jo.put("Id", id);
		jo.put("XMLPath", XMLPath);
		//FileIO
		PrintWriter w = new PrintWriter(new File(name + ".json"));
		w.write(jo.toJSONString());
		w.flush();
		w.close();
	}
}
