package com.yupi.yuaicodemother.core.handler;

import com.yupi.yuaicodemother.model.entity.User;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;
import com.yupi.yuaicodemother.service.ChatHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author Lanfeng
 * @version 1.0
 */
@Component
public class StreamHandlerExecutor {

    @Resource
    private JsonMessageStreamHandler jsonMessageStreamHandler;

    @Resource
    private SimpleTextStreamHandler simpleTextStreamHandler;


    public Flux<String> executeHandler(Flux<String> originFlux,
                                       ChatHistoryService chatHistoryService,
                                       long appId, User loginUser, CodeGenTypeEnum genTypeEnum) {

        return switch (genTypeEnum) {
            case HTML, MULTI_FILE -> simpleTextStreamHandler.handle(originFlux, chatHistoryService, appId, loginUser);

            case VUE_PROJECT -> jsonMessageStreamHandler.handle(originFlux, chatHistoryService, appId, loginUser);
        };
    }


}
