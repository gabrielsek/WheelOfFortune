import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu {

    Stage primaryStage;

    public Menu(){
        primaryStage = new Stage();
        Group root = new Group();
        Button start = new Button("Start");
        Word word = new Word();
        start.setLayoutY(50);
        start.setLayoutX(80);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (word.isPickedEmpty()) {
                        primaryStage.close();
                        Game game = new Game(word);
                        game.setMenu(Menu.this);
                    }
                }catch (NoSentenceChoosen ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You dont't choose any words");
                    alert.setContentText("Go to settings and pick one !!!");
                    alert.showAndWait();
                }
            }
        });

        Button settings = new Button("Settings");
        settings.setLayoutY(100);
        settings.setLayoutX(72);
        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                Group group = new Group();
                group.getChildren().add(word);

                Scene scene = new Scene( group, 500, 450, Color.WHEAT);
                stage.setScene(scene);
                stage.show();
            }
        });

        root.getChildren().add(start);
        root.getChildren().add(settings);

        Scene scene = new Scene(root, 200, 200, Color.PINK);
        primaryStage.setScene(scene);
    }

    public void start(){
        primaryStage.show();
    }

}
