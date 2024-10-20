package org.example.apirest.demo.proj.utill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonErrorResponce {




    private String message;
    private long timestamp;



    public PersonErrorResponce(String message, Long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
