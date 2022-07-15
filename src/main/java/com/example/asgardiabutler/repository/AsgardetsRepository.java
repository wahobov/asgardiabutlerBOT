package com.example.asgardiabutler.repository;

import com.example.asgardiabutler.entity.Asgardets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AsgardetsRepository extends JpaRepository<Asgardets, Long> {

    @Query("select a from asgardets a where a.userId = ?1")
    Asgardets findByUserId(String userId);

    @Query("select a from asgardets a where a.birthDate = ?1")
    List<Asgardets> findAllByBirthDate(String birthDate);

    @Query("select a from asgardets a where a.firstName = ?1 and a.lastName = ?2")
    Asgardets findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, String birthDate);
}
