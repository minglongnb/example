package com.example.helper;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public interface DataCallback {
        void onDataLoaded(List<String> todoList);
    }

    /**
     * 异步加载待办数据
     * 在子线程中执行文件读取，避免阻塞UI线程
     */
    public static void loadTodoDataAsync(final String filePath, final DataCallback callback) {
        new Thread(() -> {
            List<String> todoList = loadTodoData(filePath);

            // 使用Platform.runLater确保UI更新在JavaFX应用线程执行
            Platform.runLater(() -> callback.onDataLoaded(todoList));
        }).start();
    }

    /**
     * 同步加载待办数据（供需要同步调用的场景使用）
     */
    public static List<String> loadTodoData(String filePath) {
        List<String> todoList = new ArrayList<>();

        try (FileReader reader = new FileReader(new File(filePath))) {
            int character;
            StringBuilder sb = new StringBuilder();
            while ((character = reader.read()) != -1) {
                sb.append((char) character);
            }

            // 模拟耗时操作
            Thread.sleep(1000);

            String[] dataArray = sb.toString().split("\n");
            for (String data : dataArray) {
                if (!data.trim().isEmpty()) {
                    todoList.add(data.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return todoList;
    }
}