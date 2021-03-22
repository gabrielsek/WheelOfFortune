import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class Triangle
    extends Polygon {

    private Integer value;
    Text text;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, int var, double angle){
        this.getPoints().addAll(new Double[]{
               x1,y1,
                x2,y2,
                x3,y3
        });
        this.value = var;
        Random random = new Random();
        this.setFill(Color.rgb(
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)
        ));
        this.
        text = new Text(value.toString());
        text.setFill(Color.WHITE);
        text.rotateProperty().setValue(angle);
        text.setLayoutX((x1 + x2 + x3) / 3 );
        text.setLayoutY((y1 + y2 + y3) /3 );
        text.setX(text.getX() - text.getLayoutBounds().getWidth() / 2 );
        text.setY(text.getY() + text.getLayoutBounds().getHeight() / 4 );
        text.setFont(new Font(25));
    }

    public int getValue(){
        return value;
    }

    public Text getText(){
        return text;
    }

}
