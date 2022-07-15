package com.example.asgardiabutler.constraints;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum BotState {

    GREETING,
    BIRTHDAY,
    NOTHING,
    ADD_ASGARDETS,
    GIVE_FULL_NAME,
    GIVE_BIRTHDAY;
}
