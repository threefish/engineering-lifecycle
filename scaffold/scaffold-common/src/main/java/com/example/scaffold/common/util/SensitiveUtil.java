package com.example.scaffold.common.util;

import java.util.regex.Pattern;

/**
 * 对外文案脱敏：掩码敏感键值、去换行、截断，避免密钥/个人信息与内部细节外泄。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class SensitiveUtil {

    private static final int MAX_LEN = 200;

    /** 匹配 password/token/secret 等键值对，掩码其值部分 */
    private static final Pattern SENSITIVE = Pattern.compile(
            "(?i)(password|passwd|pwd|token|secret|authorization|accessKey|apiKey)\"?\\s*[:=]\\s*\"?([^\",&\\s}]+)");

    private SensitiveUtil() {
    }

    public static String clean(String text) {
        if (text == null) {
            return null;
        }
        String masked = SENSITIVE.matcher(text).replaceAll("$1=***");
        String oneLine = masked.replaceAll("[\\r\\n]+", " ");
        return oneLine.length() > MAX_LEN ? oneLine.substring(0, MAX_LEN) + "..." : oneLine;
    }
}