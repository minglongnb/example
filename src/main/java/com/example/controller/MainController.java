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

    // 初始化方法：FXML 加载完成后自动执行
    @FXML
    public void initialize() {
        // 调用 ButtonHelper 初始化按钮（样式 + 点击事件）
        ButtonHelper.initButton(addBtn, this::handleAddAction);
        ButtonHelper.initButton(deleteBtn, this::handleDeleteAction);
    }

    // 添加按钮点击事件
    private void handleAddAction() {
        DialogHelper.showTipDialog("添加待办成功！");
        // 异步加载数据，更新标签（无 UI 阻塞）
        DataHelper.loadTodoDataAsync("todo.txt", this::updateDataCountLabel);
    }

    // 删除按钮点击事件
    private void handleDeleteAction() {
        DialogHelper.showTipDialog("删除待办成功！");
        DataHelper.loadTodoDataAsync("todo.txt", this::updateDataCountLabel);
    }

    // 更新待办计数标签（UI 操作，确保在 UI 线程执行）
    private void updateDataCountLabel(List<String> todoList) {
        dataCountLabel.setText("待办总数：" + todoList.size());
    }
}