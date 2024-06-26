package com.example.demo.controller;

import com.example.demo.entity.ChatMessage;
import com.example.demo.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chatBLM")
public class ChatController {

    private final WebClient webClient;
    private final SignatureUtil signatureUtil;

    @Autowired
    public ChatController(WebClient webClient, SignatureUtil signatureUtil) {
        this.webClient = webClient;
        this.signatureUtil = signatureUtil;
    }

    @PostMapping("/send")
    public Mono<ResponseEntity<String>> sendMessage(@RequestBody ChatMessage message) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("requestId", UUID.randomUUID().toString());  // 生成唯一的requestId
        //queryParams.put("requestId", "1e344557-8e8b-43e3-a36e-94e7f36616e0");
        // 创建一个MultiValueMap来存储转换后的参数
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        queryParams.forEach(multiValueMap::add);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/vivogpt/completions")
                        .queryParams(multiValueMap)  // 使用MultiValueMap作为查询参数
                        .build())
                .headers(headers -> headers.setAll(signatureUtil.generateAuthHeaders("POST", "/vivogpt/completions", queryParams)))
                .bodyValue(buildRequestBody(message))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> ResponseEntity.ok().body(body))
                .onErrorResume(e -> {
                    e.printStackTrace();  // 在实际部署中应考虑更合适的错误处理方式
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request: " + e.getMessage()));
                });
    }

    // 确保当构建JSON字符串时包括sessionId
    private String buildRequestBody(ChatMessage message) {
        return String.format("{\"prompt\": \"%s\", \"model\": \"vivo-BlueLM-TB\", \"sessionId\": \"%s\"}",
                message.getContent(),
                message.getSessionId()); // 使用传递的sessionId
    }

}
