package hasztagowy.userepos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class Repository {

    @JsonProperty("name")
    private String name;
    @JsonProperty("language")
    private String languages;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    private Boolean  updatedRecently;


    /**
     * @param updatedAt - lastUpdateDate in format ISO 860
     * @return true- if last update< 90 days otherwise false
     */
    public static boolean checkLastUpdateDate(String updatedAt){
        LocalDateTime dateNow=LocalDateTime.now();
        try {
            LocalDateTime lastUpdateDate = LocalDateTime.parse(updatedAt, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX"));
            return Math.abs(ChronoUnit.DAYS.between(dateNow, lastUpdateDate)) < 90;
        }catch (DateTimeParseException e){
            return false;
        }
    }
}
