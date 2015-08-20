import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * GUI to hold the show the stars
 */
public class StarGUI extends Application implements Observer {

    boolean findCheck = false;
    boolean colorSearch = false;

    public Search searchClass;

    ListView result;

    BorderPane main;

    HBox resultInfo;

    @Override
    public void init(){
        searchClass = new Search();
        searchClass.addObserver(this);
    }

    @Override
    public void start(Stage stage){

        //Set up panes
        main = new BorderPane();
        FlowPane buttons = new FlowPane();
        resultInfo = new HBox();
        resultInfo.setPadding(new Insets(10, 10, 10, 10));
        buttons.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //Set up text fields
        TextField query = new TextField();
        Text warning = new Text();
        query.setPromptText("Search here...");
        query.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!(query.getText().equals("yellow") || query.getText().equals("red")
                        || query.getText().equals("blue") || query.getText().equals("orange")
                        || query.getText().equals("white"))){
                    warning.setText("Enter valid color");
                }
                else{
                    colorSearch = true;
                    searchClass.findColor(query.getText());
                }
            }
        });
        result = new ListView();
        Label queryLabel = new Label("Search Star by color : ");
        Label resultLabel = new Label("Result : ");
        Label biggestLabel = new Label("Find Brightest Star: ");

        //Set up buttons
        Button find = new Button("Find");
        find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findCheck = true;
                searchClass.findBrightest();
            }
        });

        //Put everything together
        GridPane.setConstraints(queryLabel, 0, 0);
        GridPane.setConstraints(query, 1, 0);
        GridPane.setConstraints(warning, 2, 0);
        GridPane.setConstraints(biggestLabel, 0 , 1);
        GridPane.setConstraints(find, 1, 1);
        grid.getChildren().addAll(queryLabel, query, warning, biggestLabel, find);

        resultInfo.getChildren().addAll(resultLabel, result);
        resultInfo.setSpacing(10);


        main.setCenter(grid);
        main.setBottom(resultInfo);

        stage.setMinWidth(450);
        stage.setMinHeight(500);
        //Set scene and show
        stage.setTitle("Star Gazing");
        stage.setScene(new Scene(main));
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg){

        if (findCheck){
            result.getItems().clear();
            result.getItems().add(arg);
            findCheck = false;
        }
        if(colorSearch){
            result.getItems().clear();
            int max = 0;
            for(Object x: (ArrayList) arg){
                result.getItems().add(x);
                if(x.toString().length() > max){
                    max = x.toString().length();
                }
            }
            Comparator<Star> comp = new Comparator<Star>() {
                @Override
                public int compare(Star one, Star two){
                    return two.mag.compareTo(one.mag);
                }
            };

            result.getItems().sort(comp);
            result.setPrefWidth(max * 7);
            colorSearch = false;
        }
    }

    public static void main(String args[]){
        Application.launch(args);
    }
}
