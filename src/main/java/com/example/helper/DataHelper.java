package com.example.helper;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 数据操作统一管理，异步加载避免阻塞 UI 线程
 */
public class DataHelper {

    /**
     * 异步加载待办数据（子线程执行耗时操作）
     * @param filePath 数据文件路径（项目根目录下）
     * @param callback 加载完成后的回调（UI 线程更新界面）
     */
    public static void loadTodoDataAsync(String filePath, Consumer<List<String>> callback) {
        new Thread(() -> {
            List<String> todoList = new ArrayList<>();
            try (FileReader reader = new FileReader(new File(filePath))) {
                int character;
                StringBuilder sb = new StringBuilder();
                // 读取文件内容
                while ((character = reader.read()) != -1) {
                    sb.append((char) character);
                }
                // 模拟耗时操作（1秒，便于观察无卡顿效果）
                Thread.sleep(1000);
                // 分割数据（按换行符拆分）
                String[] dataArray = sb.toString().split("\n");
                for (String data : dataArray) {
                    if (!data.trim().isEmpty()) {
                        todoList.add(data.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 异常时返回空列表，避免程序崩溃
                todoList = new ArrayList<>();
            }
            // 回调结果到 UI 线程（JavaFX 规范：UI 操作必须在 UI 线程执行）
            List<String> finalTodoList = todoList;
            javafx.application.Platform.runLater(() -> callback.accept(finalTodoList));
        }).start();
    }
}