package com.mateuswmachado.votesystem.repository;

import com.mateuswmachado.votesystem.model.VoteSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Interface representing a Vote Session repository
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Repository
public interface VoteSessionRepository extends JpaRepository<VoteSession, Long> {
}
