package com.jariba.portfolio.Service;


import com.jariba.portfolio.Model.Candidate;
import com.jariba.portfolio.Model.ProfileImage;
import com.jariba.portfolio.Repo.ProfileImageRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ProfileImageService {

    @Autowired
    private ProfileImageRepo profileImageRepo;

    @Autowired
    private CandidateService candidateService;

    public ProfileImage getProfileImage(int id) {

        if(!candidateService.isCandidateActive(id))
            return null;

        return profileImageRepo.findById(id).orElse(null);
    }


    public String addProfileImage(ProfileImage profileImage) throws Exception {
        try {

            if(!candidateService.isCandidateActive(profileImage.getId()))
                throw new IllegalArgumentException("Invalid Candidate Id");

//            Candidate candidate = candidateService.getCandidateById(profileImage.getId());
//            candidate.setProfileImageObject(profileImage);
//
//            candidateService.updateCandidate(candidate);
            profileImageRepo.addProfileImage(profileImage.getId(),profileImage.getImage(),profileImage.getImageType(),profileImage.getImageName());
            return "Profile image added successfully";
        }
        catch (IllegalArgumentException e) {
            throw  e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("Failed to Added the image");
        }
    }


    public String deleteProfileImage(int id) throws Exception {
        try{
            profileImageRepo.deleteById(id);
            return "Profile image deleted successfully";
        }
        catch(Exception e)
        {
            throw new Exception("Failed to delete the image");
        }
    }
}
