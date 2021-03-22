import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

public class Game
    implements InputListener{

    Stage primaryStage;
    Menu menu;
    HUD hud;
    int var;
    TextInputDialog inputDialog;
    Display display;

    public Game(Word word){
        primaryStage = new Stage();
        Group root = new Group();
        hud = new HUD();
        hud.setWord(word);
        Wheel wheel = new Wheel(280,280, 150, 12);
        wheel.addListenr(this);
        display = new Display(hud.getPassword());

        root.getChildren().add(wheel);
        root.getChildren().add(hud);
        root.getChildren().add(display);

        Button spin = new Button("SPIN");
        spin.setLayoutX(260);
        spin.setLayoutY(440);
        inputDialog = new TextInputDialog("");
        inputDialog.setHeaderText("Enter the single Character: ");
        inputDialog.setContentText("Character: ");
        inputDialog.setTitle("");


        spin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var = wheel.spin();

                int goodCharacters = 0;
                inputDialog.setX(900);
                inputDialog.setY(400);
                Optional<String> guess = inputDialog.showAndWait();
                inputDialog.getEditor().clear();
                if(guess.isPresent()){
                    String characther = guess.get();
                    goodCharacters = display.checkCharacter(characther.toUpperCase());
                    hud.changeScore(goodCharacters*var);
                    if(goodCharacters == 0) {
                        try {
                            hud.removeLife();
                        }catch (EndOfLifeException ex){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            ButtonType optionGoToMenu = new ButtonType("Back to Menu");
                            ButtonType optionQuit = new ButtonType("Quit Game");
                            alert.setTitle("LOOSER");
                            alert.setHeaderText("You Loose");
                            alert.setContentText("What you want to do ?");
                            alert.getButtonTypes().addAll(optionGoToMenu, optionQuit);
                            Optional<ButtonType> result = alert.showAndWait();
                            if(result.get() == optionGoToMenu)
                                menu.start();
                            else if(result.get() == optionQuit)
                                primaryStage.close();
                            primaryStage.close();
                        }
                    }
                }
            }
        });
        root.getChildren().add(spin);
        Scene scene = new Scene(root, 640, 480, Color.DARKGRAY);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setMenu(Menu menu){
        this.menu = menu;
    }

    @Override
    public void endOfSpin() {
    }
}
