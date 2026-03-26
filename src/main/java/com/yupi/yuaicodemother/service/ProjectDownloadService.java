package com.yupi.yuaicodemother.service;

import jakarta.servlet.http.HttpServletResponse;

public interface ProjectDownloadService {

    /**
     * 下载项目源码
     * @param projectPath   项目根路径
     * @param downloadFileName  下载文件名
     * @param httpServletResponse   http响应
     */
    void downloadProject(String projectPath, String downloadFileName, HttpServletResponse httpServletResponse);
}
