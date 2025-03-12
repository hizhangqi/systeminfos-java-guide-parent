package com.systeminfos.jvm.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        webView.getEngine().load("https://www.baidu.com");

        Scene scene = new Scene(webView, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX WebView");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}