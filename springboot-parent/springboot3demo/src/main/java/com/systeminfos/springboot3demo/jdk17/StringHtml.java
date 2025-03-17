package com.systeminfos.springboot3demo.jdk17;

/**
 * 文本块（Text Blocks）在 JDK 13 中引入，但在 JDK 17 中得到了优化，支持在多行字符串中插入占位符
 */
public class StringHtml {

    public static void main(String[] args) {
        String html = """
                <html>
                    <body>
                        <p>Hello, %s!</p>
                    </body>
                </html>
                """.formatted("World");
        System.out.println(html);
    }
}
