import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HUD
    extends Group {

    private int lifecounter;
    Text score;
    Circle life[];
    Button settings;
    Word word;

    public HUD(){
        lifecounter = 0;

        settings = new Button("Settings");
        settings.setLayoutX(0);
        settings.setLayoutY(0);

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

        Text lif = new Text("LIFE");
        lif.setLayoutX(550);
        lif.setLayoutY(30);
        lif.setFont(new Font(30));

        Text textScore = new Text(535, 100, "SCORE");
        textScore.setFont(new Font(30));

        score = new Text( 550, 130, "0");
        score.setFont(new Font(30));

        life = new Circle[3];
        for(int i = 0; i < life.length; i++) {
            life[i] = new Circle(15, Color.RED);
            life[i].setLayoutX(540 + i*40);
            life[i].setLayoutY(55);
            this.getChildren().add(life[i]);
        }
        this.getChildren().add(score);
        this.getChildren().add(textScore);
        this.getChildren().add(lif);
        this.getChildren().add(settings);
    }

    public void removeLife() throws EndOfLifeException{
        this.getChildren().remove(life[lifecounter]);
        if(lifecounter >= 2)
            throw new EndOfLifeException();
        lifecounter++;
    }

    public void changeScore(int value){
        Integer var = Integer.parseInt(score.getText()) + value;
        score.setText(var.toString());
    }

    public String getPassword(){
        return word.generatePassword();
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
