package com.example.helper;

import javafx.scene.control.Button;
import javafx.concurrent.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ButtonHelper {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 临时禁用按钮（3秒后自动启用）
     */
    public void disableButtonsTemporarily(Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(true);
        }

        // 3秒后自动启用按钮
        scheduler.schedule(() -> {
            for (Button button : buttons) {
                button.setDisable(false);
            }
        }, 3, TimeUnit.SECONDS);
    }

    /**
     * 立即启用按钮
     */
    public void enableButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }

    /**
     * 设置按钮加载状态
     */
    public void setButtonLoading(Button button, boolean isLoading) {
        button.setDisable(isLoading);
        button.setText(isLoading ? "处理中..." : button.getId());
    }

    /**
     * 清理资源
     */
    public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}