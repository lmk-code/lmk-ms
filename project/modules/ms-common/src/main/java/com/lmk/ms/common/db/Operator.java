package com.lmk.ms.common.db;

/**
 * 查询条件运算符
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public enum Operator {
    eq("="),
    notEq("!="),
    like("like"),
    gt(">"),
    lt("<"),
    between("between"),
    in("in");

    private String text;

    Operator(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
