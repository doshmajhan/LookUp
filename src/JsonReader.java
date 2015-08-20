import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by MC on 4/23/2015.
 */
public class JsonReader {

    String URL;

    public JsonReader(String URL){
        this.URL = URL;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl() throws IOException, JSONException {
        InputStream is = new URL(this.URL).openStream();
        JSONObject json;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);
            return json;
        }
        catch (IOException e){
            e.printStackTrace();
            json = new JSONObject();
        } finally{
            is.close();
        }

        return json;
    }

}
