package com.example.helper;

import javafx.scene.control.Button;

/**
 * 按钮辅助类
 * 职责：处理按钮相关的初始化和样式设置
 */
public class ButtonHelper {

    // 统一按钮样式
    private static final String UNIFIED_BUTTON_STYLE =
            "-fx-background-color: #2196F3; " +
                    "-fx-text-fill: white; " +
                    "-fx-padding: 6px 12px; " +
                    "-fx-border-radius: 4px;";

    /**
     * 初始化按钮
     *
     * @param button      按钮对象
     * @param clickAction 点击事件处理
     */
    public static void initButton(Button button, Runnable clickAction) {
        if (button != null) {
            button.setStyle(UNIFIED_BUTTON_STYLE);
            button.setOnAction(e -> {
                if (clickAction != null) {
                    clickAction.run();
                }
            });
        }
    }

    /**
     * 设置按钮样式
     *
     * @param button 按钮对象
     * @param style  CSS样式字符串
     */
    public static void setButtonStyle(Button button, String style) {
        if (button != null && style != null) {
            button.setStyle(style);
        }
    }
}