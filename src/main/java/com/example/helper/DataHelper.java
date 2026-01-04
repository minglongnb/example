package com.example.helper;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    // 模拟数据存储
    private List<String> dataStore = new ArrayList<>();

    /**
     * 加载数据（模拟耗时操作）
     */
    public List<String> loadData() {
        // 模拟网络延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 返回数据副本
        return new ArrayList<>(dataStore);
    }

    /**
     * 保存数据
     */
    public void saveData(String data) {
        // 模拟保存操作的延迟
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        dataStore.add(data);
    }

    /**
     * 删除选中的数据
     */
    public void deleteSelectedData() {
        // 模拟删除操作的延迟
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!dataStore.isEmpty()) {
            dataStore.remove(dataStore.size() - 1);
        }
    }

    /**
     * 获取数据数量
     */
    public int getDataCount() {
        return dataStore.size();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        dataStore.clear();
    }
}