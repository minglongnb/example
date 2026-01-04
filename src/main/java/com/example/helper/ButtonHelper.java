package com.example.helper;

import javafx.scene.control.Button;

public class ButtonHelper {

    public static final String UNIFIED_BUTTON_STYLE =
            "-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 6px 12px; -fx-border-radius: 4px;";

    /**
     * 初始化按钮样式和点击事件
     */
    public static void initButton(Button button, Runnable clickAction) {
        button.setStyle(UNIFIED_BUTTON_STYLE);
        button.setOnAction(e -> clickAction.run());
    }
}