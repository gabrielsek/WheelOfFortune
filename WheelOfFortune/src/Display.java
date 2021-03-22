import javafx.animation.FillTransition;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Display
    extends Group {

    String password;
    Text words[];
    Rectangle square[];

    public Display(String password){
        this.password = password.toUpperCase();
        words = new Text[password.length()];
        square = new Rectangle[password.length()];
        int rowLength = 10;
        int max = rowLength;
        int centering = 0;
        for(int j = 0; j <= square.length/rowLength; j++) {
            if(j == square.length/rowLength) {
                max = square.length % rowLength;
                centering = (10 - max) * 20; //
            }
            for(int i = 0 ; i < max; i++) {
                Character temp = this.password.charAt(i + j*rowLength);
                words[i + j*rowLength] = new Text(temp.toString());
                words[i + j*rowLength].setFont(new Font(20));
                square[i + j*rowLength] = new Rectangle(40, 40);
                square[i + j*rowLength].setLayoutX(80 + centering + i * 40);
                square[i + j*rowLength].setLayoutY(20 + j*50);
                square[i + j*rowLength].setStroke(Color.PINK);
                square[i + j*rowLength].setStrokeWidth(2);
                if(temp == ' ')
                    square[i + j*rowLength].setFill(Color.WHITE);
                words[i + j*rowLength].setLayoutX(square[i + j*10].getLayoutX() + 15);
                words[i + j*rowLength].setLayoutY(square[i + j*10].getLayoutY() + 30);

                this.getChildren().add(square[i + j*rowLength]);
                this.getChildren().add(words[i + j*rowLength]);
            }
        }
    }

    public int checkCharacter(String character){
        if(character.toUpperCase().equals(password))
            win();
        int var = 0;
        for(int i = 0; i < square.length; i++)
        {
            if(words[i].getText().equals(character) && square[i].getFill() != Color.WHITE){
                FillTransition ft = new FillTransition(
                        Duration.seconds(2), square[i],
                        Color.BLACK, Color.WHITE
                );
                ft.play();
                var++;
            }
        }
        boolean won = true;

        for(int i = 0; i < square.length; i++){
            if(square[i].getFill() != Color.WHITE)
                won = false;
        }

        if(won)
            win();

        return var;
    }

    public void win(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WINNER");
        alert.setHeaderText("CONGRATULATIONS!!!");
        alert.setContentText("YOU WIN");
        alert.show();
    }

}
