package com.myy.common.usercenter.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.myy.common.usercenter.config.LoginConfig;
import com.myy.common.usercenter.constants.Constants;
import com.myy.common.usercenter.entity.User;
import com.myy.common.usercenter.mapper.UserMapper;
import com.myy.common.usercenter.utils.PwdUtil;
import com.myy.common.usercenter.utils.RedisUtils;
import com.myy.common.usercenter.utils.UserUtil;
import com.myy.common.usercenter.vo.LoginUser;
import com.myy.common.usercenter.vo.SessionUser;
import com.myy.common.usercenter.vo.UserSearchVo;
import com.myy.common.usercenter.vo.UserVo;
import com.myy.common.common.base.PageData;
import com.myy.common.common.base.BaseService;
import com.myy.common.common.exception.ParameterException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 用户表  Service服务
 *
 * @author myy
 * @date 2025-05-25
 */
@Service
@Transactional(readOnly = false)
public class UserService extends BaseService<UserMapper,User> {

    private final LoginConfig loginConfig;
    private final StringRedisTemplate stringRedisTemplate;

    public UserService(LoginConfig loginConfig, StringRedisTemplate stringRedisTemplate) {
        this.loginConfig = loginConfig;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 登录
     */
    public SessionUser login(LoginUser loginUser) {
        if (loginUser == null) {
            throw new ParameterException("参数不能为空");
        }
        User user = findByUsername(loginUser.getUsername());
        if (user == null) {
            throw new ParameterException("账号或密码错误，请重新输入");
        }
        if (StrUtil.isBlank(user.getPassword())) {
            throw new ParameterException("用户数据密码为空，请联系管理员");
        }
        if (!Constants.YHZT_NO.equals(user.getStatus())) {
            throw new ParameterException("账号已被禁用，请联系管理员");
        }
        Integer loginErrorCount = null;
        if (loginConfig.isLock()) {
            //登录错误5次锁定10分钟
            loginErrorCount = RedisUtils.get(Constants.LOGIN_ERROR_COUNT + user.getId());
            if (loginErrorCount != null && loginErrorCount >= 5) {
                throw new ParameterException("错误次数太多，10分钟后再试");
            }
        }
        if (!PwdUtil.verify(loginUser.getPassword(), user.getPassword())) {
            if (loginConfig.isLock()) {
                RedisUtils.set(Constants.LOGIN_ERROR_COUNT + user.getId(), loginErrorCount == null ? 1 : loginErrorCount + 1, 10, TimeUnit.MINUTES);
            }
            throw new ParameterException("账号或密码错误，请重新输入");
        } else {
            if (loginConfig.isLock()) { //清理错误次数
                RedisUtils.clear(Constants.LOGIN_ERROR_COUNT + user.getId());
            }
        }
        SessionUser sessionUser = BeanUtil.toBean(user, SessionUser.class);
        String token = UUID.randomUUID().toString();
        sessionUser.setToken(token);
        sessionUser.setCreationTime(LocalDateTime.now());
        if (loginConfig.isOnly()) {
            //同一个用户只能在一个浏览器中登录
            String oldToken = RedisUtils.get(Constants.LOGIN_ONLY + user.getId());
            if (StrUtil.isNotBlank(oldToken)) {
                stringRedisTemplate.delete(oldToken);
            }
            RedisUtils.set(Constants.LOGIN_ONLY + user.getId(), token);
        }
        stringRedisTemplate.opsForValue().set(sessionUser.getToken(), JSONUtil.toJsonStr(sessionUser), 1, TimeUnit.HOURS);
        if (!PwdUtil.verifyPassword(loginUser.getPassword())) {
            sessionUser.setPasswordChange(true);
            sessionUser.setPasswordMessage("密码格式太简单");
        }
//        else if (PwdUtil.passwordExpire(user.getPasswordDate())) {
//            sessionUser.setPasswordChange(true);
//            sessionUser.setPasswordMessage("3个月未修改密码");
//        }
        UserUtil.clearCache("SystemPrefix", user.getId());  //ToDo
        return sessionUser;
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }



    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(UserVo vo) {
        validate(vo);
        User po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, User.class);
            po.setId(IdUtil.objectId());
            po.init();
            save(po);
            return "保存成功";
        } else {
            BeanUtil.copyProperties(vo, po);
            po.initByUpdate();
            updateById(po);
            return "更新成功";
        }
    }

    /**
     * 验证对象
     *
     * @param vo 提交参数
     */
    private void validate(UserVo vo) {
        if (vo == null) {
            throw new ParameterException("参数不能为空");
        }

    }

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return
     */
    public PageData<UserVo> findByPage(UserSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }



}
