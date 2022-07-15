package com.example.asgardiabutler.entity;

import com.example.asgardiabutler.constraints.BotState;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "asgardets")
public class Asgardets {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "user_role")
    private String role;

    @Column(name = "username")
    private String username;

    @Column(name = "bot_state")
    private BotState botState;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_day")
    private String birthDay;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "birth_month")
    private String birthMonth;

    @Column(name = "birth_year")
    private String birthYear;

}
