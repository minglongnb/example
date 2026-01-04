package com.example.controller;

import com.example.helper.ButtonHelper;
import com.example.helper.DataHelper;
import com.example.helper.DialogHelper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class MainController {
    @FXML private VBox mainContainer;
    @FXML private Button addBtn;
    @FXML private Button deleteBtn;
    @FXML private Label dataCountLabel;

    private ButtonHelper buttonHelper;
    private DialogHelper dialogHelper;
    private DataHelper dataHelper;

    @FXML
    public void initialize() {
        initHelpers();
        setupButtonActions();
        loadDataAsync();
    }

    private void initHelpers() {
        if (buttonHelper == null) buttonHelper = new ButtonHelper();
        if (dialogHelper == null) dialogHelper = new DialogHelper();
        if (dataHelper == null) dataHelper = new DataHelper();
    }

    private void setupButtonActions() {
        addBtn.setOnAction(event -> {
            buttonHelper.disableButtonsTemporarily(addBtn, deleteBtn);
            handleAddAction();
        });

        deleteBtn.setOnAction(event -> {
            buttonHelper.disableButtonsTemporarily(addBtn, deleteBtn);
            handleDeleteAction();
        });
    }

    private void handleAddAction() {
        long startTime = System.currentTimeMillis(); // 开始计时

        dialogHelper.showInputDialog("添加数据", "请输入数据内容：")
                .ifPresent(input -> {
                    Task<Void> saveTask = new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            dataHelper.saveData(input);
                            return null;
                        }

                        @Override
                        protected void succeeded() {
                            long endTime = System.currentTimeMillis();
                            double durationSec = (endTime - startTime) / 1000.0;

                            Platform.runLater(() -> {
                                buttonHelper.enableButtons(addBtn, deleteBtn);
                                loadDataAsync();
                                dialogHelper.showInfoDialog("成功",
                                        String.format("数据添加成功！\n耗时：%.3f 秒", durationSec));
                            });
                        }

                        @Override
                        protected void failed() {
                            Platform.runLater(() -> {
                                buttonHelper.enableButtons(addBtn, deleteBtn);
                                dialogHelper.showErrorDialog("错误", "添加数据失败！");
                            });
                        }
                    };
                    new Thread(saveTask).start();
                });
    }

    private void handleDeleteAction() {
        long startTime = System.currentTimeMillis(); // 开始计时

        dialogHelper.showConfirmDialog("确认删除", "确定要删除选中的数据吗？")
                .ifPresent(confirmed -> {
                    if (confirmed) {
                        Task<Void> deleteTask = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                dataHelper.deleteSelectedData();
                                return null;
                            }

                            @Override
                            protected void succeeded() {
                                long endTime = System.currentTimeMillis();
                                double durationSec = (endTime - startTime) / 1000.0;

                                Platform.runLater(() -> {
                                    buttonHelper.enableButtons(addBtn, deleteBtn);
                                    loadDataAsync();
                                    dialogHelper.showInfoDialog("成功",
                                            String.format("数据删除成功！\n耗时：%.3f 秒", durationSec));
                                });
                            }

                            @Override
                            protected void failed() {
                                Platform.runLater(() -> {
                                    buttonHelper.enableButtons(addBtn, deleteBtn);
                                    dialogHelper.showErrorDialog("错误", "删除数据失败！");
                                });
                            }
                        };
                        new Thread(deleteTask).start();
                    } else {
                        buttonHelper.enableButtons(addBtn, deleteBtn);
                    }
                });
    }

    private void loadDataAsync() {
        Task<List<String>> loadTask = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return dataHelper.loadData();
            }

            @Override
            protected void succeeded() {
                List<String> data = getValue();
                Platform.runLater(() -> updateDataCount(data.size()));
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    dialogHelper.showErrorDialog("加载失败", "无法加载数据！");
                    updateDataCount(0);
                });
            }
        };
        new Thread(loadTask).start();
    }

    private void updateDataCount(int count) {
        dataCountLabel.setText("数据数量: " + count);
    }
}
