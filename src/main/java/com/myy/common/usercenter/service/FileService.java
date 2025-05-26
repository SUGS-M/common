package com.myy.common.usercenter.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.myy.common.usercenter.entity.File;
import com.myy.common.usercenter.mapper.FileMapper;
import com.myy.common.usercenter.vo.FileSearchVo;
import com.myy.common.usercenter.vo.FileVo;
import com.myy.common.common.base.PageData;
import com.myy.common.common.base.BaseService;
import com.myy.common.common.exception.ParameterException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 附件表  Service服务
 * @author myy
 * @date 2025-05-25
 */
@Slf4j
@Service
public class FileService extends BaseService<FileMapper, File> {

    @Value("${uploadFile.dir}")
    private String uploadDir;

    @Transactional
    public FileVo uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ParameterException("上传的文件不能为空");
        }
        try {
            String originalFilename = file.getOriginalFilename();// 获取文件原始名称和扩展名
            if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
                throw new ParameterException("文件格式不正确，必须包含扩展名");
            }
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 生成当前时间戳作为文件名
            String timestamp = dateFormat.format(new Date());
            String newFileName = timestamp + fileExtension;
            Path uploadPath = Paths.get(uploadDir);// 如果目录不存在，则创建目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            file.getName();
            Path filePath = uploadPath.resolve(newFileName);//构建文件保存路径
            file.transferTo(filePath.toFile());// 保存文件
            //附件表
            FileVo fjbVo = new FileVo();
            fjbVo.setFilepath(filePath.toString());//文件路径
            fjbVo.setFilename(originalFilename);//文件名
            fjbVo.setFiletype(fileExtension);//文件类型
            saveOrUpdate(fjbVo);
            return fjbVo;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new ParameterException("文件上传失败：" + e.getMessage());
        }
    }

    public void downloadFile(HttpServletResponse response, String fileId) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            File fileVo = baseMapper.selectById(fileId);
            if (fileVo == null || StrUtil.isBlank(fileVo.getFilepath())) {
                throw new ParameterException("不存在的文件");
            }
            Path path = Paths.get(fileVo.getFilepath());//构建文件路径
            Resource resource = new FileSystemResource(path.toFile());
            if (!resource.exists()) {//检查文件是否存在
                throw new ParameterException("文件已不存在");
            }
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(fileVo.getFilename(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");// 确保文件名使用UTF-8编码
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            fileInputStream = new FileInputStream(resource.getFile());// 写入文件流到响应输出流
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            //执行写出操作
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
            throw new ParameterException("文件下载失败：" + e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    //关闭输入流
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    //关闭输出流
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(FileVo vo) {
        validate(vo);
        File po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, File.class);
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
    private void validate(FileVo vo) {
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
    public PageData<FileVo> findByPage(FileSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }
}
