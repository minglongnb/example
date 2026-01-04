package com.example.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

/**
 * 对话框辅助类
 * 职责：处理各种类型的对话框显示
 */
public class DialogHelper {

    // 单例对话框实例（懒加载）
    private static Dialog<String> tipDialog;

    /**
     * 显示提示对话框
     *
     * @param message 提示消息
     */
    public static void showTipDialog(String message) {
        if (message == null) return;

        Dialog<String> dialog = getTipDialogInstance();
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * 显示信息对话框
     *
     * @param title   对话框标题
     * @param message 信息消息
     */
    public static void showInfoDialog(String title, String message) {
        showAlertDialog(AlertType.INFORMATION, title, message);
    }

    /**
     * 显示错误对话框
     *
     * @param title   对话框标题
     * @param message 错误消息
     */
    public static void showErrorDialog(String title, String message) {
        showAlertDialog(AlertType.ERROR, title, message);
    }

    /**
     * 显示警告对话框
     *
     * @param title   对话框标题
     * @param message 警告消息
     */
    public static void showWarningDialog(String title, String message) {
        showAlertDialog(AlertType.WARNING, title, message);
    }

    /**
     * 显示确认对话框
     *
     * @param title   对话框标题
     * @param message 确认消息
     * @return 用户选择结果
     */
    public static boolean showConfirmDialog(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * 获取提示对话框实例（懒加载）
     *
     * @return 对话框实例
     */
    private static synchronized Dialog<String> getTipDialogInstance() {
        if (tipDialog == null) {
            tipDialog = new Dialog<>();
            tipDialog.setTitle("操作结果");
            tipDialog.setHeaderText(null);
            tipDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        }
        return tipDialog;
    }

    /**
     * 显示通用警告对话框
     *
     * @param type    警告类型
     * @param title   标题
     * @param message 消息
     */
    private static void showAlertDialog(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}