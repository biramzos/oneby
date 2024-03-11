package com.web.oneby.commons.Repositories;

import com.web.oneby.commons.Models.OBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OBFileRepository extends JpaRepository<OBFile, Long> {
    Optional<OBFile> getOBFileById(Long id);
}
