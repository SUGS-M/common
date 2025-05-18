package com.myy.common.usercenter.user.controller;

import com.myy.common.common.base.PageData;
import com.myy.common.usercenter.user.service.userService;
import com.myy.common.usercenter.user.vo.userSearchVo;
import com.myy.common.usercenter.user.vo.userVo;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表接口
 *
 * @author myy
 * @date 2025-05-18
 */
@Validated
@RestController
@RequestMapping("user")
public class userController {

    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }

    /**
     * 通过ID查询用户表VO
     */
    @GetMapping("get")
    public ResponseEntity<userVo> get(@RequestParam("id") String id) {
        return ResponseEntity.ok(userService.findById(id, userVo.class));
    }

    /**
     * 通过ID逻辑删除用户表
     */
    @GetMapping("delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    /**
     * 保存或者更新用户表数据
     */
    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody @Valid userVo vo) {
        return ResponseEntity.ok(userService.saveOrUpdate(vo));
    }

    /**
     * 查询用户表分页数据
     */
    @PostMapping("page")
    public ResponseEntity<PageData<userVo>> page(@RequestBody userSearchVo vo) {
        return ResponseEntity.ok(userService.findByPage(vo));
    }

}
