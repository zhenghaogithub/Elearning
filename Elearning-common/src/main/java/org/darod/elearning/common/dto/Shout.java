package org.darod.elearning.common.dto;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/8/2 0002 22:31
 */
public class Shout {
    private String message;

    public Shout() {
    }

    public Shout(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
