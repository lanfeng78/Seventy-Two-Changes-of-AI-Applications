package com.yupi.yuaicodemother.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.yupi.yuaicodemother.model.dto.app.AppQueryRequest;
import com.yupi.yuaicodemother.model.entity.App;
import com.yupi.yuaicodemother.model.entity.User;
import com.yupi.yuaicodemother.model.vo.AppVO;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public interface AppService extends IService<App> {

    /**
     * 获得脱敏后的App对象
     * @param app   脱敏前的App对象
     * @return  脱敏后的App对象
     */
    AppVO getAppVO(App app);

    /**
     * 构造分页查询应用列表的条件
     * @param appQueryRequest  封装好的应用查询对象
     * @return  分页查询应用列表的条件
     */
    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    /**
     * 得到脱敏后的应用列表
     * @param appList  应用列表
     * @return  脱敏后的应用列表
     */
    List<AppVO> getAppVOList(List<App> appList);


    /**
     * 用户与AI对话的接口
     *
     * @param appId     应用的id
     * @param message   消息内容
     * @param loginUser 当前登录用户
     * @return ai生成的代码流
     */
    Flux<ServerSentEvent<String>> chatToGenCode(Long appId, String message, User loginUser);

}
