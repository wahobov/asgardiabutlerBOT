package com.example.asgardiabutler.service;

import com.example.asgardiabutler.repository.AsgardetsRepository;
import com.example.asgardiabutler.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl {

    private final AsgardetsRepository asgardetsRepository;
    private final ChatRepository chatRepository;

}
