import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Wheel
    extends Group {

    private int round;
    private Triangle triangle[];
    private RotateTransition spinning;
    private RotateTransition back;
    private int slice;
    private double angle;
    private int var;
    ArrayList<InputListener> listeners;

    public Wheel(int x, int y, int arm, int numb){
        Group wheelGroup = new Group();
        this.getChildren().add(wheelGroup);
        listeners = new ArrayList<>();

        //320,240 poczatek ukladu przesuniety o ten wektor
        round = 360;
        slice = numb; // ilos kawalkow i trojkatow
        double zeroX = x; // poczatek ukladu x, wekto
        double zeroY = y; // poczatek ukladu y
        int side = arm; // dlugosc ramienia trojkatow
        angle = 360/slice; // kat przy robieniu trojkatow

        int values[] = {750, 250, 50, 450, 500, 10, 900, 25, 100, 650, 0, 80};
        triangle = new Triangle[slice];

        for(int i = 0; i < triangle.length; i++){
            double radian = Math.toRadians(angle*i);
            double nextRadian = Math.toRadians(angle*(i+1));
            triangle[i] = new Triangle(
                    zeroX,  zeroY,
                    zeroX + side*Math.cos(radian),  zeroY + side*Math.sin(radian),
                    zeroX + side*Math.cos(nextRadian),  zeroY + side*Math.sin(nextRadian),
                    values[i],angle*i
            );
            wheelGroup.getChildren().add(triangle[i]);
        }
        triangle[slice - 1] = new Triangle(
                zeroX,  zeroY,
                zeroX + side*Math.cos(Math.toRadians(angle*(slice-1))),  zeroY + side*Math.sin(Math.toRadians(angle*(slice-1))),
                zeroX + side*Math.cos(Math.toRadians(angle*slice)),  zeroY + side*Math.sin(Math.toRadians(angle*slice)),
                values[slice-1],angle*(slice-1)
        );
        wheelGroup.getChildren().add(triangle[slice - 1]);

        for(int i = 0 ; i < triangle.length; i++){
            wheelGroup.getChildren().add(triangle[i].getText());
        }

        Polygon pointer  = new Polygon(zeroX+side-20, zeroY, zeroX+side+10, zeroY + 20, zeroX +side+10, zeroY - 20);
        pointer.setFill(Color.BLACK);
        this.getChildren().add(pointer);
        spinning = new RotateTransition(Duration.seconds(10), wheelGroup);
        back = new RotateTransition(Duration.millis(1), wheelGroup);
    }

    public int spin(){
        Random random = new Random();
        back.setByAngle(360 - round);
        round = 360 + random.nextInt(720);
        var = (int)(round/angle);
        back.play();
        back.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(triangle[slice - (var)%slice - 1].getValue());
                spinning.setInterpolator(Interpolator.EASE_OUT);
                spinning.setByAngle(round);
                spinning.play();
                spinning.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for(InputListener inputListener : listeners)
                            inputListener.endOfSpin();
                    }
                });
            }
        });
        return triangle[slice - (var)%slice - 1].getValue();
    }

    public void addListenr(InputListener inputListener){
        listeners.add(inputListener);
    }

}
