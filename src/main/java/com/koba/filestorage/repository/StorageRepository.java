package com.koba.filestorage.repository;

import com.koba.filestorage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage,Long> {

    List<Storage> findAll();

    Optional<Storage> findByFilename(String filename);

}
