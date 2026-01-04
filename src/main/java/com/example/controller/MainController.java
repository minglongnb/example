package com.example.controller;

import com.example.helper.ButtonHelper;
import com.example.helper.DataHelper;
import com.example.helper.DialogHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

public class MainController {

    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Label dataCountLabel;

    @FXML
    public void initialize() {
        // 使用lambda表达式简化按钮初始化
        ButtonHelper.initButton(addBtn, this::handleAddAction);
        ButtonHelper.initButton(deleteBtn, this::handleDeleteAction);
    }

    // 添加按钮点击事件
    private void handleAddAction() {
        executeDataOperation("添加待办成功！");
    }

    // 删除按钮点击事件
    private void handleDeleteAction() {
        executeDataOperation("删除待办成功！");
    }

    /**
     * 通用数据操作执行方法
     * 合并了重复的计时、数据加载和弹窗逻辑
     */
    private void executeDataOperation(String successMessage) {
        final long startTime = System.currentTimeMillis();

        DataHelper.loadTodoDataAsync("todo.txt", new DataHelper.DataCallback() {
            @Override
            public void onDataLoaded(List<String> todoList) {
                updateDataCountLabel(todoList);

                long endTime = System.currentTimeMillis();
                double durationSec = (endTime - startTime) / 1000.0;
                String message = String.format("%s\n耗时：%.3f 秒", successMessage, durationSec);

                DialogHelper.showTipDialog(message);
            }
        });
    }

    // 更新待办计数标签
    private void updateDataCountLabel(List<String> todoList) {
        dataCountLabel.setText("待办总数：" + todoList.size());
    }
}