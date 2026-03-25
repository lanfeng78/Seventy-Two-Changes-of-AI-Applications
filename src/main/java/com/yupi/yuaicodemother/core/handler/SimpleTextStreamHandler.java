package com.yupi.yuaicodemother.core.handler;

import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.model.entity.User;
import com.yupi.yuaicodemother.model.enums.ChatHistoryMessageTypeEnum;
import com.yupi.yuaicodemother.service.ChatHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author Lanfeng
 * @version 1.0
 */
@Component
public class SimpleTextStreamHandler {



    public Flux<String> handle(Flux<String> originFlux,
                               ChatHistoryService chatHistoryService,
                               long appId, User loginUser) {

        StringBuilder codeBuilder = new StringBuilder();

        return originFlux.map(chunk -> {
            // 实时收集代码片段
            codeBuilder.append(chunk);
            return chunk;
        }).doOnComplete(() -> {
            // 流式返回完成后保存代码
            String aiMessage = codeBuilder.toString();
            if (StrUtil.isNotBlank(aiMessage)) {
                chatHistoryService.addChatMessage(appId, aiMessage, ChatHistoryMessageTypeEnum.AI.getValue(),
                        loginUser);
            }
        }).doOnError(error -> {
            String errorMessage = "AI回复失败：" + error.getMessage();
            chatHistoryService.addChatMessage(appId, errorMessage, ChatHistoryMessageTypeEnum.AI.getValue(), loginUser);
        });
    }

}
