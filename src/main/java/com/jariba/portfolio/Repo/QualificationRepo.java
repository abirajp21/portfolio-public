package com.jariba.portfolio.Repo;


import com.jariba.portfolio.Model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepo extends JpaRepository<Qualification, Integer> {
}
