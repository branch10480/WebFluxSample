package com.example.samplewebfluxapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.samplewebfluxapp.model.Person;

// Repositoryはデータアクセスのためのものであることを示す
// このクラス、インタフェースがどんなものかをアノテーションが示す
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
