package com.example.asgardiabutler.repository;

import com.example.asgardiabutler.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
