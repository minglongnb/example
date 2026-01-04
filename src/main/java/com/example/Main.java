package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 加载 FXML 界面（后续创建，路径需与实际一致）
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/hello-view.fxml"));
        primaryStage.setTitle("LLM 优化 JavaFX 实验"); // 窗口标题
        primaryStage.setScene(new Scene(root, 400, 300)); // 窗口大小（宽400，高300）
        primaryStage.show(); // 显示窗口
    }

    public static void main(String[] args) {
//        System.out.println(System.getProperty("user.dir"));

        launch(args); // 启动 JavaFX 应用
    }
}