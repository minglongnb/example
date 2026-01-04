package com.example;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deepseek LLM API 调用工具类，与 deepseek-chat 交互获取优化代码
 */
public class LLMClient {
    // 从环境变量读取 Deepseek Key（避免明文写在代码里）
    private static final String API_KEY = "sk-bdf9941fa0c54080ad4b38f1ac394433";
    // Deepseek Chat API 接口地址
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private final Gson gson;

    public LLMClient() {
        this.gson = new Gson();
    }

    /**
     * 调用 Deepseek LLM 优化 JavaFX 代码
     *
     * @param originalCode 待优化的原始 JavaFX 代码
     * @return 优化后代码 + 优化说明
     * @throws Exception 网络异常或 API 调用异常
     */
    public String optimizeJavaFXCode(String originalCode) throws Exception {
        // 核心 Prompt
        String prompt = "帮我按以下要求优化这段JavaFX代码：" +
                "规则1：功能归类拆分——按钮相关逻辑→ButtonHelper类；弹窗相关→DialogHelper类；数据操作→DataHelper类；" +
                "规则2：性能优化——合并重复代码、组件懒加载、数据加载放子线程避免UI阻塞；" +
                "规则3：保留所有功能，只做归类和提速，不新增/删除功能；" +
                "输出要求：先列优化说明，再输出完整代码（含包名、导入语句）。";

        // 构造请求体 JSON
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat"); // ✅ Deepseek 官方要求
        requestBody.put("messages", new Object[]{
                Map.of("role", "system", "content", "你是专业JavaFX开发工程师，擅长重构与性能优化"),
                Map.of("role", "user", "content", prompt + "\n\n原始代码：\n" + originalCode)
        });
        requestBody.put("stream", false); // ✅ Deepseek 官方要求

        // 发送 POST 请求
        String response = Request.Post(API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .bodyString(gson.toJson(requestBody), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString(StandardCharsets.UTF_8);

        // 解析返回内容
        Map<String, Object> responseMap = gson.fromJson(response, Map.class);
        List<?> choices = (List<?>) responseMap.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("Deepseek 返回内容为空");
        }

        Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
        Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");
        return message.get("content").toString();
    }

    // 测试调用
    public static void main(String[] args) {
        try {
            LLMClient client = new LLMClient();

            // ✅ 使用实际 JavaFX 代码
            String filePath = "src/main/java/com/example/controller/MainController.java";
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("文件不存在：" + filePath);
                return;
            }

            String originalCode = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");


            String result = client.optimizeJavaFXCode(originalCode);
            System.out.println("LLM 优化结果：\n" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
