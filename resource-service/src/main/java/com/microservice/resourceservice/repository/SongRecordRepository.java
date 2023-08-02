package com.microservice.resourceservice.repository;

import com.microservice.resourceservice.domain.SongRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRecordRepository extends JpaRepository<SongRecord, Integer> {
}
