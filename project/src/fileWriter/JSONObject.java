package fileWriter;

public class JSONObject {

    public String name = "";
    public Class content;
    public Object object;

    public JSONObject(String name, Class content) {
        this.name = name;
        this.content = content;
    }

    public JSONObject(String name, Class content, Object object) {
        this.name = name;
        this.content = content;
        this.object = object;
    }

}
