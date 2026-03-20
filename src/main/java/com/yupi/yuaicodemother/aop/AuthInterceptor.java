package com.yupi.yuaicodemother.aop;

import com.yupi.yuaicodemother.annotation.AuthCheck;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.entity.User;
import com.yupi.yuaicodemother.model.enums.UserRoleEnum;
import com.yupi.yuaicodemother.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Lanfeng
 * @version 1.0
 */
@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

/**
 * 环绕拦截器，用于处理带有@AuthCheck注解的方法
 * 该方法会在目标方法执行前后进行拦截处理
 *
 * @param joinPoint 连接点，可以获取目标方法的执行信息
 * @param authCheck 注解对象，包含注解中的属性值
 * @throws Throwable 可能抛出的异常
 */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取注解中的mustRole属性值
        String mustRole = authCheck.mustRole();
        // 从请求中获取当前登录用户的信息
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User user = userService.getCurrentUser(request);
        // 如果必须拥有的权限为空，直接放行
        UserRoleEnum enumByValue = UserRoleEnum.getEnumByValue(mustRole);
        if (enumByValue == null) {
            return joinPoint.proceed();
        }
        // 获取当前登录用户的权限信息
        UserRoleEnum userRole = UserRoleEnum.getEnumByValue(user.getUserRole());
        // 如果当前用户没有必须的权限，抛出异常
        // 1. 用户无任何权限
        if (userRole == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        // 2. 用户权限不够
        if (enumByValue == UserRoleEnum.ADMIN && userRole != UserRoleEnum.ADMIN) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "用户权限不足");
        }
        // 3. 用户权限足够
        return joinPoint.proceed();


    }

}
