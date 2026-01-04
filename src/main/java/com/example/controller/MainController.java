package com.example.controller;

import com.example.helper.ButtonHelper;
import com.example.helper.DataHelper;
import com.example.helper.DialogHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;


/**
 * 优化后的 MainController
 * 职责：协调UI组件与业务逻辑，处理用户交互
 */
public class MainController {

    // UI组件
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Label dataCountLabel;

    // 常量
    private static final String FILE_PATH = "todo.txt";

    /**
     * 初始化方法
     */
    @FXML
    public void initialize() {
        initializeButtons();
    }

    /**
     * 初始化按钮
     */
    private void initializeButtons() {
        // 使用ButtonHelper初始化按钮
        ButtonHelper.initButton(addBtn, this::handleAddAction);
        ButtonHelper.initButton(deleteBtn, this::handleDeleteAction);
    }

    /**
     * 处理添加操作
     */
    private void handleAddAction() {
        processDataOperation("添加待办成功！");
    }

    /**
     * 处理删除操作
     */
    private void handleDeleteAction() {
        processDataOperation("删除待办成功！");
    }

    /**
     * 通用数据处理方法
     *
     * @param actionMessage 操作成功消息
     */
    private void processDataOperation(String actionMessage) {
        final long startTime = System.currentTimeMillis();

        // 异步加载数据
        DataHelper.loadTodoDataAsync(FILE_PATH, new DataHelper.DataCallback() {
            @Override
            public void onDataLoaded(List<String> todoList) {
                processTodoList(todoList, actionMessage, startTime);
            }

            @Override
            public void onError(Exception e) {
                DialogHelper.showErrorDialog("错误", "数据加载失败: " + e.getMessage());
            }
        });
    }

    /**
     * 处理待办列表
     *
     * @param todoList      待办列表
     * @param actionMessage 操作消息
     * @param startTime     开始时间
     */
    private void processTodoList(List<String> todoList, String actionMessage, long startTime) {
        // 更新UI
        updateDataCountLabel(todoList);

        // 计算耗时
        long endTime = System.currentTimeMillis();
        double durationSec = (endTime - startTime) / 1000.0;

        // 构建消息
        String message = String.format("%s\n耗时：%.3f 秒", actionMessage, durationSec);

        // 显示提示对话框
        DialogHelper.showTipDialog(message);
    }

    /**
     * 更新数据计数标签
     *
     * @param todoList 待办列表
     */
    private void updateDataCountLabel(List<String> todoList) {
        int count = todoList.size();
        dataCountLabel.setText("待办总数：" + count);
    }
}