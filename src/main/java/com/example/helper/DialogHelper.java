package com.example.helper;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * 弹窗功能统一管理，单例模式复用实例，提升响应速度
 */
public class DialogHelper {
    // 单例弹窗实例（懒加载）
    private static Dialog<ButtonType> instance;

    // 私有构造，防止外部实例化
    private DialogHelper() {}

    // 线程安全获取单例实例
    private static synchronized Dialog<ButtonType> getDialogInstance() {
        if (instance == null) {
            instance = new Dialog<>();
            instance.setTitle("操作结果");
            instance.setHeaderText(null);
            instance.getDialogPane().getButtonTypes().add(ButtonType.OK);
        }
        return instance;
    }

    // 对外提供统一弹窗接口
    public static void showTipDialog(String message) {
        Dialog<ButtonType> dialog = getDialogInstance();
        dialog.setContentText(message);
        dialog.showAndWait();
    }
}