package org.example.apirest.demo.proj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "name not null")
    @Size(min = 2,max = 30,message = "name min 2 symbols - max 30")
    @Column (name = "name")
    private String name;

    @NotEmpty
    @Min(value = 0,message = "age > 0")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "email not null")
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @NotEmpty
    @Column(name = "created_who")
    private String created_who;

}
