package com.nishbs.repository;

import com.nishbs.entities.CustomerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<CustomerFile, Long> {
}