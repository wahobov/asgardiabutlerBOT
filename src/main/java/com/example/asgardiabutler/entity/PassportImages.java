package com.example.asgardiabutler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "passport_images")
public class PassportImages {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "image")
    private byte[] width;

}
