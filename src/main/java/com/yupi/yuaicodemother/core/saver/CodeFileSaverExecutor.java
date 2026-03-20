package com.yupi.yuaicodemother.core.saver;

import com.yupi.yuaicodemother.ai.model.HtmlCodeResult;
import com.yupi.yuaicodemother.ai.model.MultiFileCodeResult;
import com.yupi.yuaicodemother.exception.BusinessException;
import com.yupi.yuaicodemother.exception.ErrorCode;
import com.yupi.yuaicodemother.model.enums.CodeGenTypeEnum;

import java.io.File;

/**
 * @author Lanfeng
 * @version 1.0
 */
public class CodeFileSaverExecutor {
    private static final HtmlCodeSaver htmlCodeSaver = new HtmlCodeSaver();
    private static final MultiFileCodeSaver multiFileCodeSaver = new MultiFileCodeSaver();

    public static File executeSaver(Object codeResult, CodeGenTypeEnum codeGenType) {
        return switch (codeGenType) {
            case HTML -> htmlCodeSaver.saveCodeResult((HtmlCodeResult)codeResult);
            case MULTI_FILE -> multiFileCodeSaver.saveCodeResult((MultiFileCodeResult)codeResult);
            default -> throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的代码生成类型：" + codeGenType + " 仅支持html和多文件格式");
        };
    }
}
