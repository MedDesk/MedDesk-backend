package com.mustapha.medDesk.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // optional metadata
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;
    private String path;

    // constructors, getters, setters ...

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.message = message;
        res.data = data;
        res.timestamp = LocalDateTime.now();
        res.status = 200;
        return res;
    }

    public static <T> ApiResponse<T> error(int status, String message, String path) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.message = message;
        res.data = null;
        res.timestamp = LocalDateTime.now();
        res.status = status;
        res.path = path;
        return res;
    }
}
