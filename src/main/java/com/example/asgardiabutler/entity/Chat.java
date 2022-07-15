package com.example.asgardiabutler.entity;

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
@Entity(name = "chat")
public class Chat {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "chat_link")
    private String chatLink;

    @Column(name = "chat_id")
    private String chatId;


}
