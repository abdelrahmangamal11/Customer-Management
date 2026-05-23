package com.customer.desktop;

import com.customer.desktop.ui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}