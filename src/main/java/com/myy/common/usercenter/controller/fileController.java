package com.myy.common.usercenter.controller;

import com.myy.common.common.base.PageData;
import com.myy.common.common.reponse.Result;
import com.myy.common.usercenter.service.fileService;
import com.myy.common.usercenter.vo.fileSearchVo;
import com.myy.common.usercenter.vo.fileVo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 附件表接口
 *
 * @author myy
 * @date 2025-05-25
 */
@Validated
@RestController
@RequestMapping("file")
public class fileController {

    private final fileService fileService;

    public fileController(fileService fileService) {
        this.fileService = fileService;
    }


    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result<fileVo> uploadFile(@RequestParam("file") MultipartFile file) {
        return Result.ok(fileService.uploadFile(file));
    }

    /**
     * 文件下载
     */
    @GetMapping("download")
    public void downloadFile(HttpServletResponse response, @RequestParam("id") String fileId) {
        fileService.downloadFile(response, fileId);
    }

//    /**
//     * 通过ID查询附件表VO
//     */
//    @GetMapping("get")
//    public ResponseEntity<fileVo> get(@RequestParam("id") String id) {
//        return ResponseEntity.ok(fileService.findById(id, fileVo.class));
//    }
//
//    /**
//     * 通过ID逻辑删除附件表
//     */
//    @GetMapping("delete")
//    public ResponseEntity<String> delete(@RequestParam("id") String id) {
//        return ResponseEntity.ok(fileService.deleteById(id));
//    }
//
//    /**
//     * 保存或者更新附件表数据
//     */
//    @PostMapping("save")
//    public ResponseEntity<String> save(@RequestBody @Valid fileVo vo) {
//        return ResponseEntity.ok(fileService.saveOrUpdate(vo));
//    }
//
//    /**
//     * 查询附件表分页数据
//     */
//    @PostMapping("page")
//    public ResponseEntity<PageData<fileVo>> page(@RequestBody fileSearchVo vo) {
//        return ResponseEntity.ok(fileService.findByPage(vo));
//    }

}
