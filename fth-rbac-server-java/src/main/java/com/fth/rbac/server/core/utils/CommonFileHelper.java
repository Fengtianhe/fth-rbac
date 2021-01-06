package com.fth.rbac.server.core.utils;

import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author 54570
 * @version 1.0
 * @date 2020/9/2
 * content:
 */
@Component
public class CommonFileHelper {
    @Value("${nas.localStorage:nas}")
    public String localStorage;
    private static CommonFileHelper staticUtils;

    public static void downloadByPath(String filepath, HttpServletResponse response) {
        try {
            if (filepath.isEmpty()) {
                response.sendError(40411, "url不能为空");
            }
            String fileName = filepath.substring(filepath.lastIndexOf(File.separator) + 1);
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //创建不同的文件目录
            File file = new File(fileName);
            //判断文件夹是否存在
            if (file.exists()) {
                response.sendError(40422, "File not found!");
            }
            //设置文件名，并指定编码格式
            response.setCharacterEncoding("UTF-8");
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            // 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            FileInputStream inputStream = null;
            BufferedInputStream bis = null;
            OutputStream out = null;

            try {
                inputStream = new FileInputStream(filepath);
                bis = new BufferedInputStream(inputStream);
                //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
                out = new BufferedOutputStream(response.getOutputStream());
                byte[] buf = new byte[1024 * 8];
                int len = 0;
                //保存文件
                while ((len = bis.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(bis);
                IOUtils.closeQuietly(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostConstruct
    public void init() {
        staticUtils = this;
        staticUtils.localStorage = this.localStorage;
    }

    public static MultipartFile toMultipartFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        return multipartFile;
    }

    public static String localStoragePath() {
        String dir = staticUtils.localStorage;
        File d = new File(dir);
        if (!d.exists()) {
            // 如果目录不存在，尝试创建
            if (!d.mkdirs()) {
                throw new CommonException(new ExceptionCode(1001, "创建目录" + dir + "失败！"));
            }
        }
        return staticUtils.localStorage;
    }

    /**
     * 生成.json格式文件
     *
     * @return File Path
     */
    public static String createJsonFile(String jsonString, String fileName) {
        // 标记文件生成是否成功
        boolean flag = true;


        // 拼接文件完整路径
        String fullPath = localStoragePath() + File.separator + fileName + ".json";


        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            file.createNewFile();

            // 格式化json字符串
            jsonString = JsonFormatTools.formatJson(jsonString);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag ? fullPath : null;
    }

}
