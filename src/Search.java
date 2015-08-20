import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Search to find what you can see in the night sky by your current location
 * @author Doshmajhan
 * 4/22/15
 */


public class Search extends Observable {

    public Search(){

    }
    /**
     * Search the api site for the specific star.
     * @return the info received
     */
    public static ArrayList<Star> getHTML() {
        String[] result;
        ArrayList<Star> starList = new ArrayList<Star>();

        try {
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod("http://star-api.herokuapp.com/api/v1/stars");
            get.setRequestHeader("X-App-Token", "Yapjl2ZIsl9BKPwG22Om5d5Gs");
            client.executeMethod(get);
            result = get.getResponseBodyAsString().split("},");
            for(String x : result) {
                String name = x.substring(x.indexOf("label") + 8, x.indexOf("x") - 3);
                String mag = x.substring(x.indexOf("appmag") + 8, x.indexOf("texnum") - 2);
                String color = x.substring(x.indexOf("colorb_v") + 10, x.indexOf("absmag") - 2);
                starList.add(new Star(name, Double.parseDouble(mag), Double.parseDouble(color)));
            }
            get.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return starList;
    }

    public static void test(){

        Soda2Consumer consumer = Soda2Consumer.newConsumer("https://data.nasa.gov/resource/5bv2-dyn2.json",
                "cpc1007@g.rit.edu",
                "Halfway23",
                "Yapjl2ZIsl9BKPwG22Om5d5Gs");

        //To get a raw String of the results
        try {
            java.net.URI uri = new java.net.URI("https://data.nasa.gov/resource/5bv2-dyn2.json");
            ClientResponse response = consumer.getHttpLowLevel().queryRaw(uri,
                    HttpLowLevel.JSON_TYPE);

            System.out.println(response.getLanguage());
            String payload = response.getEntity(String.class);
            System.out.println(payload);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Finds the brightest star out of the list
     * @return the brightest star
     */
    public void findBrightest (){

        ArrayList<Star> lst = getHTML();

        Star max = new Star("", 0.0, 0.0);

        for(Star x : lst){
            if(x.mag > max.mag){
                max = x;
            }
        }

        setChanged();
        notifyObservers(max);
    }

    /**
     * Find stars by its color
     */
    public void findColor(String color){
        ArrayList<Star> red = new ArrayList<Star>();
        ArrayList<Star> white = new ArrayList<Star>();
        ArrayList<Star> blue = new ArrayList<Star>();
        ArrayList<Star> yellow = new ArrayList<Star>();
        ArrayList<Star> orange = new ArrayList<Star>();

        ArrayList<Star> lst = getHTML();
        for(Star x : lst ){
            if(x.color < 0.0){
                blue.add(x);
            }
            else if(x.color < 0.3){
                white.add(x);
            }
            else if(x.color < 0.58){
                yellow.add(x);
            }
            else if(x.color < 0.81){
                orange.add(x);
            }
            else{
                red.add(x);
            }
        }

        if(color.equals("red")){
            setChanged();
            notifyObservers(red);
        }
        else if(color.equals("white")){
            setChanged();
            notifyObservers(white);
        }
        else if(color.equals("blue")){
            setChanged();
            notifyObservers(blue);
        }
        else if(color.equals("yellow")){
            setChanged();
            notifyObservers(yellow);
        }
        else if(color.equals("orange")){
            setChanged();
            notifyObservers(orange);
        }
        else{
            setChanged();
            notifyObservers(1);
        }

    }
    public static void main(String args[]) throws IOException{
        ArrayList<Star> lst = getHTML();
        for(Star x : lst){
            System.out.println(x);
        }

        //Star brightest = findBrightest(lst);
        //System.out.println("Brightest star : " + brightest);

        //test();
        /*
        JsonReader read = new JsonReader("https://data.nasa.gov/resource/5bv2-dyn2.json");
        JSONObject json = read.readJsonFromUrl();
        System.out.println(json.toString());
        System.out.println(json.get("id"));*/

    }
}
