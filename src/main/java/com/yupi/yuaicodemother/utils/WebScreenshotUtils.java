package com.yupi.yuaicodemother.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.exception.ThrowUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

/**
 * @author LanFeng
 * @version 1.0
 */
@Slf4j
public class WebScreenshotUtils {

    private static final WebDriver webDriver;

    static {
        final int DEFAULT_WIDTH = 1600;
        final int DEFAULT_HEIGHT = 900;
        webDriver = initChromeDriver(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @PreDestroy
    public void destroy() {
        webDriver.quit();
    }

    /**
     * 生成网页截图并返回路径
     * @param webPagePath   网页URL
     * @return  生成的网页截图路径
     */
    public static String saveWebPageScreenshot(String webPagePath) {
        try {
            // 1. 校验网页是否为空
            if (StrUtil.isBlank(webPagePath)) {
                log.error("网页URL不能为空！");
                return null;
            }
            // 2. 创建临时目录
            String imageDir = System.getProperty("user.dir") + "/tmp/" +
                    "screenshots/" + UUID.randomUUID().toString().substring(0, 8);
            FileUtil.mkdir(imageDir);
            // 3. 构建原始图片保存路径
            final String IMAGE_SUFFIX = ".png";
            String sourceImagePath = imageDir + File.separator + RandomUtil.randomNumbers(5) + IMAGE_SUFFIX;
            // 4. 获取网页并等待网页加载完成
            webDriver.get(webPagePath);
            waitForPageLoad(webDriver);
            // 5. 截图
            byte[] screenshotBytes = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            // 6. 保存截图到指定路径
            saveImage(screenshotBytes, sourceImagePath);
            // 7. 构建压缩路径压缩图片
            final String COMPRESSED_IMAGE_SUFFIX = "_compressed.jpg";
            String compressedImagePath = imageDir + File.separator + RandomUtil.randomNumbers(5) + COMPRESSED_IMAGE_SUFFIX;
            compressImage(sourceImagePath, compressedImagePath);
            // 8. 删除原始图片
            FileUtil.del(sourceImagePath);

            return compressedImagePath;
        } catch (Exception e) {
            log.error("网页截图生成失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 等待页面加载完成
     */
    private static void waitForPageLoad(WebDriver driver) {
        try {
            // 创建等待页面加载对象
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // 等待 document.readyState 为complete
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                            .equals("complete")
            );
            // 额外等待一段时间，确保动态内容加载完成
            Thread.sleep(2000);
            log.info("页面加载完成");
        } catch (Exception e) {
            log.error("等待页面加载时出现异常，继续执行截图", e);
        }
    }

    /**
     * 压缩图片
     * @param sourceImagePath   原始图片路径
     * @param compressedImagePath   压缩后的图片路径
     */
    private static void compressImage(String sourceImagePath, String compressedImagePath) {
        float COMPRESSED_QUALITY = 0.3f;
        try {
            ImgUtil.compress(FileUtil.file(sourceImagePath),
                    FileUtil.file(compressedImagePath), COMPRESSED_QUALITY);
            log.info("图片压缩成功！");
        } catch (IORuntimeException e) {
            log.error("图片压缩失败：{}", e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "压缩图片失败");
        }
    }

    /**
     * 保存图片
     * @param imageBytes    byte数组形式的图片
     * @param imagePath 要保存的图片路径
     */
    private static void saveImage(byte[] imageBytes, String imagePath) {
        try {
            FileUtil.writeBytes(imageBytes, imagePath);
            log.info("图片保存成功！");
        } catch (IORuntimeException e) {
            log.error("图片保存失败：{}", e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存图片失败");
        }
    }


    /**
     * 初始化 Chrome 浏览器驱动
     */
    private static WebDriver initChromeDriver(int width, int height) {
        try {
            // 使用本地 ChromeDriver，避免从外网下载
            String chromeDriverPath = "D:\\gamehhh\\chromedriver-win64\\chromedriver.exe"; // 本地驱动路径
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            
            // 配置 Chrome 选项
            ChromeOptions options = new ChromeOptions();
            // 无头模式
            options.addArguments("--headless");
            // 禁用GPU（在某些环境下避免问题）
            options.addArguments("--disable-gpu");
            // 禁用沙盒模式（Docker环境需要）
            options.addArguments("--no-sandbox");
            // 禁用开发者shm使用
            options.addArguments("--disable-dev-shm-usage");
            // 设置窗口大小
            options.addArguments(String.format("--window-size=%d,%d", width, height));
            // 禁用扩展
            options.addArguments("--disable-extensions");
            // 设置用户代理
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            // 创建驱动
            WebDriver driver = new ChromeDriver(options);
            // 设置页面加载超时
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            // 设置隐式等待
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            return driver;
        } catch (Exception e) {
            log.error("初始化 Chrome 浏览器失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "初始化 Chrome 浏览器失败");
        }
    }
}

