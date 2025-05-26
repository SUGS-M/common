package com.myy.common.usercenter.controller;

import com.myy.common.common.base.PageData;
import com.myy.common.usercenter.service.UserService;
import com.myy.common.usercenter.vo.LoginUser;
import com.myy.common.usercenter.vo.SessionUser;
import com.myy.common.usercenter.vo.UserSearchVo;
import com.myy.common.usercenter.vo.UserVo;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表
 * @author myy
 * @date 2025-05-25
 */
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public ResponseEntity<SessionUser> login(@RequestBody @Valid LoginUser user) {
        return ResponseEntity.ok(userService.login(user));
    }

//    /**
//     * 通过ID查询用户表VO
//     */
//    @GetMapping("get")
//    public ResponseEntity<UserVo> get(@RequestParam("id") String id) {
//        return ResponseEntity.ok(userService.findById(id, UserVo.class));
//    }
//
//    /**
//     * 通过ID逻辑删除用户表
//     */
//    @GetMapping("delete")
//    public ResponseEntity<String> delete(@RequestParam("id") String id) {
//        return ResponseEntity.ok(userService.deleteById(id));
//    }
//
//    /**
//     * 保存或者更新用户表数据
//     */
//    @PostMapping("save")
//    public ResponseEntity<String> save(@RequestBody @Valid UserVo vo) {
//        return ResponseEntity.ok(userService.saveOrUpdate(vo));
//    }
//
//    /**
//     * 查询用户表分页数据
//     */
//    @PostMapping("page")
//    public ResponseEntity<PageData<UserVo>> page(@RequestBody UserSearchVo vo) {
//        return ResponseEntity.ok(userService.findByPage(vo));
//    }

}
