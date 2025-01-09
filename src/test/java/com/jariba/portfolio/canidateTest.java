package com.jariba.portfolio;


import com.jariba.portfolio.Model.Candidate;
import com.jariba.portfolio.Model.ProfileImage;
import com.jariba.portfolio.Repo.CandidateRepo;
import com.jariba.portfolio.Repo.ProfileImageRepo;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class canidateTest {


    @Autowired
    CandidateRepo candidateRepo;
    @Autowired
    ProfileImageRepo profileImageRepo;


    @Test
    public  void candidateTest()
    {
        Candidate candidate = Candidate.builder()
                .fullName("Kavin P")
                .title("Electrical Engineer")
                .location("Tiruppur")
                .about("Loves Farming and gardening")
                .build();

        ProfileImage profileImage = ProfileImage.builder()
                .image(new byte[10])
                .imageName("test")
                .imageType("img")
                .candidate(candidate)
                .build();

        profileImageRepo.save(profileImage);
    }

    @Test
    public  void getCandidate()
    {
        Candidate candidate = candidateRepo.findById(1).orElse(null);
        System.out.println("candidate = " + candidate);
    }
}
