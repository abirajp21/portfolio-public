package com.jariba.portfolio.Service;


import com.jariba.portfolio.Model.Visitors;
import com.jariba.portfolio.Repo.VisitorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorsService {

    @Autowired
    VisitorsRepo visitorsRepo;

    public void increaseVisitors(){
        Visitors visitors = visitorsRepo.findById(1).orElse(null);
        if (visitors != null) {
            visitors.setCount(visitors.getCount()+1);
            visitorsRepo.save(visitors);
        }
    }

}
