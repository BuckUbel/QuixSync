package fileWriter;

import java.util.ArrayList;
import java.util.List;

public class JSONCreator {

    private String path = "";

    private List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

    private String json = "";

    public JSONCreator(String path) {
        this.path = path;
    }

    public void createStructure(Class c) {


    }

    public void addObjectStructure(String name, Class c) {

        JSONObject jo = new JSONObject(name, c);
        this.jsonObjects.add(jo);

    }

    public void addObject(String objectName, Object object) {


        boolean exist = this.jsonObjects.stream().anyMatch(o -> o.name.equals(objectName));

        if (!exist) {
            addObjectStructure(objectName, object.getClass());
        }
        this.jsonObjects.stream().filter(o -> o.name.equals(objectName)).forEach(o -> {
            o.object = object;
        });
    }

    public String buildJSON() {

        this.json = "{";


        this.json = "}";
        return this.json;
    }

}
