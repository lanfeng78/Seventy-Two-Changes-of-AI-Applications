package com.yupi.yuaicodemother.service;

import org.springframework.stereotype.Service;

/**
 * @author LanFeng
 * @version 1.0
 */
public interface ScreenshotService {

    /**
     * 生成并上传截图至COS
     * @param webUrl    截图的初始访问路径（部署路径）
     * @return  上传成功后的截图访问地址
     */
    String generateAndUploadScreenshot(String webUrl);
}
