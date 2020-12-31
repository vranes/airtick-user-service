package com.raf.airtickuserservice.repository;

import com.raf.airtickuserservice.domain.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRankRepository extends JpaRepository<UserRank, Long> {
}
