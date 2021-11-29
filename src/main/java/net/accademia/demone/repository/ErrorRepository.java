package net.accademia.demone.repository;

import net.accademia.demone.domain.Error;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Error entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {}
