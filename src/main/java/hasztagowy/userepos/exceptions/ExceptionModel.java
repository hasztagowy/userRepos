package hasztagowy.userepos.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ExceptionModel {
    private  int status;
    private  String message;
    private  ZonedDateTime zonedDateTime;

    public ExceptionModel(int status, String message) {
        this.status = status;
        this.message = message;
        this.zonedDateTime =ZonedDateTime.now();
    }
}
