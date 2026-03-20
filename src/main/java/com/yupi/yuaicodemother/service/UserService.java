package com.yupi.yuaicodemother.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.yupi.yuaicodemother.model.dto.user.UserQueryRequest;
import com.yupi.yuaicodemother.model.entity.User;
import com.yupi.yuaicodemother.model.vo.LoginUserVO;
import com.yupi.yuaicodemother.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册接口

 * 该接口用于处理用户的注册请求，验证用户输入的信息并创建新用户账号
 *
     * @param userAccount   用户账户名，用于唯一标识用户，不能为空且需符合账号规则
     * @param userPassword  用户密码，用于用户登录验证，需要符合密码强度要求
     * @param checkPassword 确认密码，用于二次确认用户输入的密码是否一致
     * @return 返回新注册用户的ID，如果注册失败则返回-1或其他错误码
     */
    long UserRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 对密码进行加密
     * @param userPassword  原始密码
     * @return
     */
    String encryptedPassword(String userPassword);

    /**
     * 对用户信息进行脱敏
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVo(User user);

    /**
     * 用户登录逻辑
     * @param userAccount  用户账户名
     * @param userPassword  用户密码
     * @param httpServletRequest    http请求
     * @return  用户登录成功后脱敏后的用户数据
     */

    LoginUserVO UserLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);

    /**
     * 获取当前登录用户
     * @param httpServletRequest    http请求
     * @return  当前登录用户
     */

    User getCurrentUser(HttpServletRequest httpServletRequest);

    /**
     * 用户注销
     * @param httpServletRequest http请求
     */
    boolean UserLogout(HttpServletRequest httpServletRequest);

    /**
     * 将User对象转换为UserVO对象
     * @param user 用户实体对象
     * @return 转换后的用户视图对象，如果输入为null则返回null
     */
    UserVO getUserVO(User user);

    /**
     * 将User对象列表转换为UserVO对象列表
     * @param userList 用户实体对象列表
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据用户查询请求条件构造查询包装器
     *
     * @param userQueryRequest 用户查询请求对象，包含查询条件
     * @return 返回一个QueryWrapper对象，用于构建数据库查询条件
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}
