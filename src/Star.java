import java.util.Comparator;

/**
 * Class to hold the properties of a star
 */
public class Star {

    String name;

    Double mag;

    Double color;

    String strColor;

    /**
     * Constructer for a star
     * @param name - name of the star
     * @param mag - luminosity of the star
     */
    public Star(String name, Double mag, Double color){
        this.name = name;
        this.mag = mag;
        this.color = color;
        if(this.color < 0.0){
            this.strColor = "Blue";
        }
        else if(this.color < 0.3){
            this.strColor = "White";
        }
        else if(this.color < 0.58){
            this.strColor = "Yellow";
        }
        else if(this.color < 0.81){
            this.strColor = "Orange";
        }
        else{
            this.strColor = "Red";
        }
    }

    @Override
    public String toString(){
        return "Name: " + this.name + " Mag : " + this.mag + " Color : " + this.strColor;
    }
}
