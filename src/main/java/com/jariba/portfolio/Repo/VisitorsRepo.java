package com.jariba.portfolio.Repo;


import com.jariba.portfolio.Model.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorsRepo extends JpaRepository<Visitors, Integer> {
}
