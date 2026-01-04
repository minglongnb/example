package com.example.helper;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class DialogHelper {

    private static Dialog instance;

    private DialogHelper() {
        // 私有构造函数，防止外部实例化
    }

    /**
     * 获取Dialog单例实例（懒加载）
     */
    private static synchronized Dialog getDialogInstance() {
        if (instance == null) {
            instance = new Dialog();
            instance.setTitle("操作结果");
            instance.setHeaderText(null);
            instance.getDialogPane().getButtonTypes().add(ButtonType.OK);
        }
        return instance;
    }

    /**
     * 显示提示对话框
     */
    public static void showTipDialog(String message) {
        Dialog dialog = getDialogInstance();
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * 显示信息对话框
     */
    public static void showInfoDialog(String title, String message) {
        Dialog dialog = getDialogInstance();
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * 显示错误对话框
     */
    public static void showErrorDialog(String title, String message) {
        Dialog dialog = getDialogInstance();
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.showAndWait();
    }
}