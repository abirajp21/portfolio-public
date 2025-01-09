package com.jariba.portfolio.Service;


import com.jariba.portfolio.Model.Candidate;
import com.jariba.portfolio.Repo.CandidateRepo;
import com.jariba.portfolio.Repo.VisitorsRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    final  String url = "https://portfolio-r2m2.onrender.com/";
    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private VisitorsService visitorsService;



    public Candidate getCandidate(String userName) {
        try
        {
            Candidate candidate = candidateRepo.findByUserName(userName);
            if(candidate != null && candidate.getActiveStatus())
            {
                candidate.setProfileImage(url+"image/"+candidate.getId());
                visitorsService.increaseVisitors();
                return candidate;
            }
            return null;

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Candidate getCandidateById(int id) {
        try
        {
            Candidate candidate = candidateRepo.findById(id).orElse(null);
            if(candidate != null && candidate.getActiveStatus())
            {
                candidate.setProfileImage(url+"image/"+candidate.getId());
                visitorsService.increaseVisitors();
                return candidate;
            }
            return null;

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Candidate addCandidate(@Valid Candidate candidate) throws Exception
    {
        try {
            if(candidateRepo.existsByUserName(candidate.getUserName()))
                throw new IllegalArgumentException("Username already exists");
           return candidateRepo.save(candidate);
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Candidate updateCandidate(@Valid Candidate candidate) throws Exception
    {
        try {
            //System.out.println(candidateRepo.findById(candidate.getId()).orElse(null));

            if(!isCandidateActive(candidate.getId()))
                throw new IllegalArgumentException("Candidate does not exist");

            Integer Id = candidateRepo.findIdByUserName(candidate.getUserName());

            if(Id !=null && Id != candidate.getId())
                throw new IllegalArgumentException("UserName Already exist");

            return candidateRepo.save(candidate);
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public Boolean deleteCandidate(String userName){
        try
        {
            candidateRepo.setActiveStatusFalseByUserName(userName);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public Boolean isCandidateActive(int id)
    {
        return candidateRepo.existsById(id) && candidateRepo.findActiveStatusById(id);
    }




}
