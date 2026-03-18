package com.yupi.yuaicodemother.controller;

import com.yupi.yuaicodemother.common.BaseResponse;
import com.yupi.yuaicodemother.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lanfeng
 * @version 1.0
 */
@RestController                 // 标记该类为RESTful控制器，所有方法默认返回JSON格式数据
@RequestMapping("/health")      // 设置该控制器的基路径为"/health"
public class HealthController {  // 定义健康检查控制器类，用于提供系统健康状态相关的API接口
/**
 * 检查接口
 * 该接口用于系统健康检查，返回简单的状态信息
 *
 * @return 返回"ok"字符串表示系统运行正常
 */
    @GetMapping("/check")    // HTTP GET请求映射到/check路径，完整路径为"/health/check"
    public BaseResponse<String> check(){   // 定义一个返回String类型的无参方法，用于处理健康检查请求
        return ResultUtils.success("ok");      // 返回"ok"字符串表示系统状态正常
    }
}
