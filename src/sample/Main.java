package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Sample CRUD");
        Scene sceneCRUD = (new Scene(root, 1000, 700));
        sceneCRUD.getStylesheets().add("css/crud.css");
        primaryStage.setScene(sceneCRUD);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
