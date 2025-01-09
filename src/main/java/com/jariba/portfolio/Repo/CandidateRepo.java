package com.jariba.portfolio.Repo;

import com.jariba.portfolio.Model.Candidate;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer> {


     Candidate findByUserName(String userName);

     @Modifying
     @Transactional
     @Query("update Candidate c set c.activeStatus = false where c.userName = :userName ")
     void setActiveStatusFalseByUserName(String userName);

     Boolean existsByUserName(String userName);

     @Query("select c.activeStatus from  Candidate c WHERE  c.id = :id")
     Boolean findActiveStatusById(int id);

     @Query("select c.id from Candidate c where c.userName = :userName")
     Integer findIdByUserName(String userName);
}
