package com.lth.hw11_4;

import java.util.ArrayDeque;
import java.util.Deque;

class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}

class LL1_Deque {
    // 预测分析表
    private String[][] analysisTable = new String[][]{
            {"TE'", "", "", "TE'", "", ""},  // E
            {"", "+TE'", "", "", "ε", "ε"},  // E'
            {"FT'", "", "", "FT'", "", ""},  // T
            {"", "ε", "*FT'", "", "ε", "ε"}, // T'
            {"i", "", "", "(E)", "", ""}     // F
    };
    // 终结符
    private String[] VT = new String[]{"i", "+", "*", "(", ")", "#"};
    // 非终结符
    private String[] VN = new String[]{"E", "E'", "T", "T'", "F"};
    // 输入串 strToken
    private static StringBuilder strToken = new StringBuilder("a+i");
    // 分析栈 stack
    private Deque<String> stack = new ArrayDeque<>();
    // shuru1 保存从输入串中读取的一个输入符号，当前符号
    private String shuru1 = null;
    // X 中保存 stack 栈顶符号
    private String X = null;
    // flag 标志预测分析是否成功
    private boolean flag = true;
    // 记录输入串中当前字符的位置
    private int cur = 0;
    // 记录步数
    private int count = 0;

    public static void main(String[] args) {
        System.out.println("所输入的表达式为：" + strToken);
        LL1_Deque ll1 = new LL1_Deque();
        ll1.init();
        ll1.totalControlProgram();
        ll1.printf();
    }

    // 初始化
    private void init() {
        strToken.append("#");
        stack.push("#");
        System.out.printf("%-10s %-30s %-35s %-1s\n", "步骤", "符号栈", "输入串", "所用产生式");
        stack.push("E");
        curCharacter();
        System.out.printf("%-10d %-30s %-40s %-1s\n", count, stack.toString(), strToken.substring(cur, strToken.length()), "");
    }

    // 读取当前栈顶符号
    private void stackPeek() {
        X = stack.peekFirst();
    }

    // 返回输入串中当前位置的字母
    private String curCharacter() {
        if (cur < strToken.length()) {
            shuru1 = String.valueOf(strToken.charAt(cur));
        } else {
            shuru1 = "#"; // 如果 cur 超出范围，则返回结束符
        }
        return shuru1;
    }

    // 判断 X 是否是终结符
    private boolean XisVT() {
        for (int i = 0; i < VT.length; i++) {
            if (VT[i].equals(X)) {
                return true;
            }
        }
        return false;
    }

    // 查找 X 在非终结符中分析表中的横坐标
    private String VNTI() {
        int Ni = 0, Tj = 0;
        for (int i = 0; i < VN.length; i++) {
            if (VN[i].equals(X)) {
                Ni = i;
            }
        }
        for (int j = 0; j < VT.length; j++) {
            if (VT[j].equals(shuru1)) {
                Tj = j;
            }
        }
        return analysisTable[Ni][Tj];
    }

    // 判断 M[A, a] = {X -> X1X2...Xk}
    private boolean productionType() {
        return VNTI() != "";
    }

    // 推进 stack 栈
    private void pushStack() {
        stack.pop();
        String M = VNTI();
        String ch;
        switch (M) {
            case "TE'":
                stack.push("E'");
                stack.push("T");
                break;
            case "+TE'":
                stack.push("E'");
                stack.push("T");
                stack.push("+");
                break;
            case "FT'":
                stack.push("T'");
                stack.push("F");
                break;
            case "*FT'":
                stack.push("T'");
                stack.push("F");
                stack.push("*");
                break;
            case "(E)":
                stack.push(")");
                stack.push("E");
                stack.push("(");
                break;
            default:
                for (int i = M.length() - 1; i >= 0; i--) {
                    ch = String.valueOf(M.charAt(i));
                    stack.push(ch);
                }
                break;
        }
        // 增加宽度设置，确保各列对齐
        System.out.printf("%-10d %-30s %-40s %-1s->%s\n", (++count), stack.toString(), strToken.substring(cur, strToken.length()), X, M);
    }

    // 总控程序
    private void totalControlProgram() {
        try {
            while (flag) {
                stackPeek();
                // 读取当前栈顶符号 令 X = 栈顶符号
                if (XisVT()) {
                    if (X.equals(shuru1)) {
                        cur++;
                        shuru1 = curCharacter();
                        stack.pop();
                        System.out.printf("%-10d %-30s %-40s \n", (++count), stack.toString(), strToken.substring(cur, strToken.length()));
                    } else {
                        ERROR();
                    }
                } else if (X.equals("#")) {
                    if (X.equals(shuru1)) {
                        flag = false;
                    } else {
                        ERROR();
                    }
                } else if (productionType()) {
                    if (VNTI().equals("")) {
                        ERROR();
                    } else if (VNTI().equals("ε")) {
                        stack.pop();
                        System.out.printf("%-10d %-30s %-40s %-1s->ε\n", (++count), stack.toString(), strToken.substring(cur, strToken.length()), X);
                    } else {
                        pushStack();
                    }
                } else {
                    ERROR();
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    // 出现错误时抛出异常并提供详细的错误信息
    private void ERROR() throws ParseException {
        String errorMessage = "语法错误：栈顶符号 " + X + " 与当前输入符号 " + shuru1 + " 不匹配";
        throw new ParseException(errorMessage);
    }

    // 打印存储分析表
    private void printf() {
        if (!flag) {
            System.out.println("分析成功");
        } else {
            System.out.println("分析失败");
        }
    }
}
