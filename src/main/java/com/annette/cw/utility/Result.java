package com.annette.cw.utility;

import lombok.Data;

@Data
public class Result<T> {
    private T result = null;
    private int code;
    private boolean isServerError = false;
    private boolean isObjectExist = true;
    private Exception exception = null;
    private String message;
}
