package dev.osmocode.codehub.repository;

import dev.osmocode.codehub.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByAuthority(String authority);
}
