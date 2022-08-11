package vttp.ssf.Day18ShoppingCart.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Giphy {
    
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Giphy create(String s) {
        Reader r = new StringReader(s);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    public static Giphy create(JsonObject jo) {
        Giphy gif = new Giphy();
        gif.setUrl(jo.getJsonObject("images").getJsonObject("fixed_width").getString("url"));
        return gif;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder().add("url", url).build();
    }
}
