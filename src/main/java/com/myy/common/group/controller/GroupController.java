package com.myy.common.group.controller;

import com.myy.common.base.PageData;
import com.myy.common.group.service.GroupService;
import com.myy.common.group.vo.GroupSearchVo;
import com.myy.common.group.vo.GroupVo;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 小组表接口
 *
 * @author myy
 * @date 2025-04-22
 */
@Validated
@RestController
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 通过ID查询小组表VO
     */
    @GetMapping("get")
    public ResponseEntity<GroupVo> get(@RequestParam("id") String id) {
        return ResponseEntity.ok(groupService.findById(id, GroupVo.class));
    }

    /**
     * 通过ID逻辑删除小组表
     */
    @GetMapping("delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        return ResponseEntity.ok(groupService.deleteById(id));
    }

    /**
     * 保存或者更新小组表数据
     */
    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody @Valid GroupVo vo) {
        return ResponseEntity.ok(groupService.saveOrUpdate(vo));
    }

    /**
     * 查询小组表分页数据
     */
    @PostMapping("page")
    public ResponseEntity<PageData<GroupVo>> page(@RequestBody GroupSearchVo vo) {
        return ResponseEntity.ok(groupService.findByPage(vo));
    }

}
