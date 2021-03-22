import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.StringTokenizer;

public class Word
    extends Group {

    Button newWord;
    Button removeWord;
    ListView<String> base;
    ListView<String> picked;
    private ObservableList<String> obBase;
    private ObservableList<String> obPicked;

    public Word(){
        newWord = new Button("Add new word");
        newWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = input();
                if(!str.equals(""))
                    obBase.add(str);
                save();
            }
        });
        newWord.setLayoutY(400);
        newWord.setLayoutX(210);

        removeWord = new Button("Remove word");
        removeWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(base.getSelectionModel().getSelectedItem() != null) {
                    obBase.remove(base.getSelectionModel().getSelectedItem());
                }
                save();
            }
        });
        removeWord.setLayoutY(425);
        removeWord.setLayoutX(212);

        base = new ListView<>();
        base.setLayoutX(0);
        base.setLayoutY(0);
        picked = new ListView<>();
        picked.setLayoutX(260);
        picked.setLayoutY(0);

        base.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                change(event);
            }
        });
        picked.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                change(event);
            }
        });

        this.getChildren().add(base);
        this.getChildren().add(removeWord);
        this.getChildren().add(picked);
        this.getChildren().add(newWord);

        obBase = FXCollections.observableArrayList();
        obPicked = FXCollections.observableArrayList();

        readFile();
    }

    private String input() {
        TextInputDialog inputDialog = new TextInputDialog("");
        inputDialog.setHeaderText("Enter the sentence: ");
        inputDialog.setContentText("Sentence: ");
        inputDialog.setTitle("");
        String characther = "";

        Optional<String> guess = inputDialog.showAndWait();
        inputDialog.getEditor().clear();
        if (guess.isPresent())
            characther = guess.get();

        return characther;
    }

    public void readFile(){
        try {
            FileReader fileReader = new FileReader("Slogans");
            StringBuffer stringBuffer = new StringBuffer();
            int var = fileReader.read();
            while (var != -1) {
                //System.out.println((char)var);
                stringBuffer.append((char) var);
                var = fileReader.read();
            }
            fileReader.close();
            StringTokenizer tokenizer = new StringTokenizer(stringBuffer.toString(), "\n" + "\r");
            while (tokenizer.hasMoreElements()) {
                obBase.add(tokenizer.nextToken());
            }
            base.setItems(obBase);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void save(){
        try {
            FileWriter fileWriter = new FileWriter("Slogans");
            for(String string : obBase)
            fileWriter.write(string + "\n");
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void change(MouseEvent event){
        Node node = (Node)event.getSource();
        int know;

        if(event.getClickCount() == 2){
            if(node.equals(base)) {
                if(!obPicked.contains(base.getSelectionModel().getSelectedItem())) {
                    obPicked.add(base.getSelectionModel().getSelectedItem());
                    picked.setItems(obPicked);
                }else
                    System.out.println("ALREADY THERE");
            }else{
                know = picked.getSelectionModel().getSelectedIndex();
                obPicked.remove(know);
            }
        }
    }

    public String generatePassword(){
        Random random = new Random();
        return obPicked.get(random.nextInt(obPicked.size()));
    }

    public boolean isPickedEmpty() throws NoSentenceChoosen{
        if(obPicked.isEmpty())
            throw new NoSentenceChoosen();
            else
                return true;
    }

}
