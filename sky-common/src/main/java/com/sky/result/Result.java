package com.sky.result;

import lombok.Data;

import java.io.Serializable;

/**
 * バックエンドは結果を均一に返す
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //エンコード: 1は成功、0はとその他の数値は失敗
    private String msg; //エラーメッセージ
    private T data; //データ

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
