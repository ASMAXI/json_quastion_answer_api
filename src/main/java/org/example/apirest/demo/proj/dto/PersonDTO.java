package org.example.apirest.demo.proj.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter

public class PersonDTO {

    @NotEmpty(message = "name not null")
    @Size(min = 2,max = 30,message = "name min 2 symbols - max 30")
    private String name;

    @NotEmpty
    @Min(value = 0,message = "age > 0")
    private int age;

    @NotEmpty(message = "email not null")
    @Email
    private String email;



}
