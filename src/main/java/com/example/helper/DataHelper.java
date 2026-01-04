package com.example.helper;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据辅助类
 * 职责：处理数据加载和文件操作
 */
public class DataHelper {

    // 线程池（避免频繁创建线程）
    private static final ExecutorService executorService =
            Executors.newFixedThreadPool(2);

    /**
     * 数据回调接口
     */
    public interface DataCallback {
        void onDataLoaded(List<String> todoList);

        void onError(Exception e);
    }

    /**
     * 异步加载待办数据
     *
     * @param filePath 文件路径
     * @param callback 数据回调
     */
    public static void loadTodoDataAsync(String filePath, DataCallback callback) {
        executorService.submit(() -> {
            try {
                List<String> todoList = readFile(filePath);
                Platform.runLater(() -> {
                    if (callback != null) {
                        callback.onDataLoaded(todoList);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    if (callback != null) {
                        callback.onError(e);
                    }
                });
            }
        });
    }

    /**
     * 同步加载待办数据
     *
     * @param filePath 文件路径
     * @return 待办列表
     * @throws Exception 读取异常
     */
    public static List<String> loadTodoDataSync(String filePath) throws Exception {
        return readFile(filePath);
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 待办列表
     * @throws Exception 读取异常
     */
    private static List<String> readFile(String filePath) throws Exception {
        List<String> todoList = new ArrayList<>();
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            throw new RuntimeException("文件不存在: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    todoList.add(trimmedLine);
                }
            }

            // 模拟耗时操作
            Thread.sleep(1000);
        }

        return todoList;
    }

    /**
     * 关闭线程池（应用退出时调用）
     */
    public static void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}