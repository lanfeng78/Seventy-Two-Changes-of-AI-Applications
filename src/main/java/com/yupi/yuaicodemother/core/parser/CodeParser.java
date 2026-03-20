package com.yupi.yuaicodemother.core.parser;

public interface CodeParser<T> {
    /**
     * 解析代码
     * @param codeContent 代码
     * @return 解析结果
     */
    T parseCode(String codeContent);
}
