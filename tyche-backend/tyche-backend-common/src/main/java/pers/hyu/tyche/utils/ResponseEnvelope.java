package pers.hyu.tyche.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class is used for encapsulated the response with the code, the message and the data as JSON.
 *
 * @param <T> The type of the data need to encapsulated.
 * @author Heng Yu
 * @version 1.0
 */
//@ApiModel(description = "Encapsulated the response as JSON")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseEnvelope<T> {
    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public ResponseEnvelope(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Return the successful response.
     *
     * @param data The data needed to send back.
     * @param <T>  The type of the data.
     * @return Encapsulated the response with the 200 code, ok and the data.
     */
    public static <T> ResponseEnvelope<T> ok(T data) {
        return new ResponseEnvelope<T>(200, "ok", data);
    }

    /**
     * Return the error response.
     *
     * @param errorCode The code of the error.
     * @param message   The error message.
     * @return Encapsulated the response with the error code,and the error message.
     */
    public static ResponseEnvelope<String> error(int errorCode, String message) {
        return new ResponseEnvelope<String>(errorCode, message, null);
    }
}
