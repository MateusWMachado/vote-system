package com.mateuswmachado.votesystem.repository;

import com.mateuswmachado.votesystem.model.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {
}
