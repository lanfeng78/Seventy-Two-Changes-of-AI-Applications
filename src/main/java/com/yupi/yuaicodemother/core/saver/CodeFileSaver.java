package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static com.yupi.yuaicodemother.constant.AppConstant.CODE_OUTPUT_ROOT_DIR;

/**
 * 模板抽象类
 * @author Lanfeng
 * @version 1.0
 */
public abstract class CodeFileSaver<T> {

    // 文件保存根目录
    private static final String FILE_SAVE_ROOT_DIR = CODE_OUTPUT_ROOT_DIR;

    /**
     * 模板方法，定义了算法的骨架
     * @param result
     * @return
     */
    public File saveCodeResult(T result, Long appId)  {
        // 1. 校验输入
        validateInput(result);
        // 2. 构建唯一目录
        String baseDirPath = buildUniqueDir(appId);
        // 3. 保存文件，由子类实现
        saveFiles(result, baseDirPath);
        // 4. 返回File对象
        return new File(baseDirPath);
    }

    /**
     * 构建唯一目录路径：tmp/code_output/bizType_雪花ID
     */
    private String buildUniqueDir(Long appId) {
        CodeGenTypeEnum codeGenType = getCodeGenType();
        String bizType = codeGenType.getValue();
        //TODO 这里用雪花ID得到的目录，后续要将其与 appId 结合起来
        String uniqueDirName = StrUtil.format("{}_{}", bizType, appId);
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }


    /**
     * 校验解析后的代码
     * @param result    解析后的代码
     */
    protected void validateInput(T result) {
        if (result == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "生成和解析后的代码不能为空");
        }
    }

    /**
     * 写入单个文件
     */
    protected final void writeToFile(String dirPath, String filename, String content) {
        if (!StrUtil.isBlank(content)) {
            String filePath = dirPath + File.separator + filename;
            FileUtil.writeString(content, filePath, StandardCharsets.UTF_8);

        }
    }

    /**
     * 保存文件
     * @param result    解析后的结果
     * @param baseDirPath   根目录
     */
    protected abstract void saveFiles(T result, String baseDirPath);

    /**
     * @return 需要被子类重写，返回对应的枚举类型
     */
    protected abstract CodeGenTypeEnum getCodeGenType();

}
