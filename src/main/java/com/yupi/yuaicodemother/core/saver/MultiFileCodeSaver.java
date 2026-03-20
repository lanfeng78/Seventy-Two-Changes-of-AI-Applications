package com.yupi.yuaicodemother.core.saver;

import cn.hutool.core.util.StrUtil;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

/**
 * @author Lanfeng
 * @version 1.0
 */
public class MultiFileCodeSaver extends CodeFileSaver<MultiFileCodeResult> {

    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
        writeToFile(baseDirPath, "style.css", result.getCssCode());
        writeToFile(baseDirPath, "script.js", result.getJsCode());
    }

    @Override
    protected CodeGenTypeEnum getCodeGenType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }

    @Override
    protected void validateInput(MultiFileCodeResult result) {
        super.validateInput(result);
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "生成的Html代码不能为空");
        } else if (StrUtil.isBlank(result.getCssCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "生成的Css代码不能为空");
        } else if (StrUtil.isBlank(result.getJsCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "生成的Js代码不能为空");
        }
    }
}
