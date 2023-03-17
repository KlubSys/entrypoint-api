package com.klub.entrypoint.api.repository;

import com.klub.entrypoint.api.model.KlubFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlubFileRepositoryInterface extends JpaRepository<KlubFileEntity, String> {
}
