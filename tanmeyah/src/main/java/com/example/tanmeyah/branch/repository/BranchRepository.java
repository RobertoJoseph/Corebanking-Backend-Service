package com.example.tanmeyah.branch.repository;

import com.example.tanmeyah.branch.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findBranchById(Long id);

    @Transactional
    @Modifying
    @Query(
        "UPDATE Branch b SET b.branchName =?1, b.branchAddress=?2 WHERE b.id=?3"
    )
    int updateBranch(String branchName, String branchLocation, Long id);






}
