package models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRespModel {

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    String name;
    String job;
}
