package io.fulu.userservice.user.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

//    @Query("SELECT t.title FROM Todo t where t.id = :id")
//    Optional<String> findTitleById(@Param("id") Long id);
}
