package com.example.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class DialogHelper {

    /**
     * 显示信息对话框
     */
    public void showInfoDialog(String title, String message) {
        showDialog(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * 显示错误对话框
     */
    public void showErrorDialog(String title, String message) {
        showDialog(Alert.AlertType.ERROR, title, message);
    }

    /**
     * 显示警告对话框
     */
    public void showWarningDialog(String title, String message) {
        showDialog(Alert.AlertType.WARNING, title, message);
    }

    /**
     * 显示确认对话框
     */
    public Optional<Boolean> showConfirmDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.map(buttonType -> buttonType == ButtonType.OK);
    }

    /**
     * 显示输入对话框
     */
    public Optional<String> showInputDialog(String title, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        return dialog.showAndWait();
    }

    /**
     * 通用对话框显示方法
     */
    private void showDialog(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}