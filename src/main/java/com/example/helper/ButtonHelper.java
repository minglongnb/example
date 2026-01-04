package com.example.helper;

import javafx.scene.control.Button;
//import java.util.function.Runnable;

/**
 * 按钮功能统一管理，实现样式复用、懒加载初始化
 */
public class ButtonHelper {
    // 统一样式（合并重复代码，减少冗余）
    public static final String UNIFIED_BUTTON_STYLE =
            "-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 6px 12px; -fx-border-radius: 4px;";

    /**
     * 初始化按钮（懒加载：调用时才初始化，避免启动时加载所有组件）
     * @param button 目标按钮
     * @param clickAction 点击事件逻辑
     */
    public static void initButton(Button button, Runnable clickAction) {
        button.setStyle(UNIFIED_BUTTON_STYLE);
        button.setOnAction(e -> clickAction.run());
    }
}