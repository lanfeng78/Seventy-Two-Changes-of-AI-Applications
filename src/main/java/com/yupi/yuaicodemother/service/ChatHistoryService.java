package com.yupi.yuaicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.yupi.yuaicodemother.model.dto.chathistory.ChatHistoryQueryRequest;
import com.yupi.yuaicodemother.model.entity.ChatHistory;
import com.yupi.yuaicodemother.model.entity.User;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 将消息新增进数据库中
     * @param appId 应用id
     * @param message   消息内容
     * @param messageType   消息类型
     * @param loginUser 登录用户
     */
    boolean addChatMessage(Long appId, String message, String messageType, User loginUser);

    /**
     * 根据应用id删除对应的对话历史
     * @param appId 应用id
     * @return  是否删除成功
     */
    boolean deleteByAppId(Long appId);

    /**
     * 构建对话历史查询条件
     * @param chatHistoryQueryRequest   对话历史dto
     * @return  对话历史查询条件
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);

    /**
     * 分页查询对话历史
     * @param appId 应用Id
     * @param pageSize  每页展示数量
     * @param lastCreateTime    上次创建时间
     * @param loginUser 当前登录用户
     * @return  对话历史分页对象
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 根据对话id删除对应的对话历史
     * @param chatHistoryId 对话id
     * @return  是否删除成功
     */
    boolean deleteByChatHistoryId(Long chatHistoryId);
}
