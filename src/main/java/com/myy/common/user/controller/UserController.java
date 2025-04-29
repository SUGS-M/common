package com.myy.common.user.controller;

import com.myy.common.user.entity.SysUser;
import com.myy.common.user.mapper.SysUserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final SysUserMapper sysUserMapper;

    public UserController(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 测试接口
     */
    @GetMapping("/test")
    public ResponseEntity<SysUser> test(@RequestParam String id) {
        return ResponseEntity.ok(sysUserMapper.selectById(id));
    }
}
