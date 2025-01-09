package com.jariba.portfolio.Controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jariba.portfolio.Model.*;
import com.jariba.portfolio.Service.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ProfileImageService profileImageService;

    private final Set<String> imgType = new HashSet<String>(Arrays.asList("image/jpeg", "image/png", "image/jpg"));

    @GetMapping("/")
    public ResponseEntity<?> getCandidate()
    {

        try{
            Candidate candidate = candidateService.getCandidate("abirajp04");

            if(candidate != null)
                return  new ResponseEntity<>(candidate, HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getCandidateByUserName(@PathVariable String userName)
    {
        try{
            Candidate candidate = candidateService.getCandidate(userName);
            if(candidate != null)
                return  new ResponseEntity<>(candidate, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addCandidate(@RequestPart("candidate") @NotNull String candidateData, @RequestPart(value = "img", required = false)  MultipartFile img){
        try {

            ObjectMapper mapper = new ObjectMapper();
            Candidate candidate = mapper.readValue(candidateData, Candidate.class);


            if(img != null)
            {
                try {
                    ProfileImage profileImage = extractImage(img);
                    candidate.setProfileImageObject(profileImage);
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }

            candidateService.addCandidate(candidate);
            return new ResponseEntity<>("Candidate added successfully", HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>("UserName Already Exists", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Failed to Upload Candidate Details ", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping(value = "/{userName}")
    public ResponseEntity<String> deleteCandidateByUserName(@PathVariable String userName)
    {
        if(candidateService.deleteCandidate(userName))
            return new ResponseEntity<>("Candidate deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to delete Candidate", HttpStatus.NOT_FOUND);
    }


    @PutMapping("/")
    public ResponseEntity<String> updateCandidate(@RequestBody Candidate candidate) {
        try {
            candidateService.updateCandidate(candidate);
            return new ResponseEntity<>("Candidate Updated successfully", HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Failed to Update Candidate Details ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte []> getProfileImage(@PathVariable int id)
    {
        try{
            ProfileImage profileImage = profileImageService.getProfileImage(id);
            if(profileImageService.getProfileImage(id) != null)
                return  ResponseEntity.ok().contentType(MediaType.valueOf(profileImage.getImageType())).body(profileImage.getImage());
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/image/{id}")
    public ResponseEntity<String> addProfileImage(@NotNull MultipartFile img, @PathVariable int id)
    {

        try{
             ProfileImage profileImage = extractImage(img);
             profileImage.setId(id);
             String res = profileImageService.addProfileImage(profileImage);
             return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Image Upload failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/image/{id}")
    public ResponseEntity<String> updateProfileImage(@NotNull MultipartFile img, @PathVariable int id)
    {
        return addProfileImage(img, id);
    }

    @DeleteMapping(value = "/image/{id}" )
    public ResponseEntity<String> deleteProfileImage(@PathVariable int id)
    {
        try {
            String res = profileImageService.deleteProfileImage(id);
            System.out.println("in delete mapping");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



    private ProfileImage extractImage(MultipartFile imgFile) throws IOException {

        if(!imgType.contains(imgFile.getContentType()))
            throw  new IllegalArgumentException("Invalid image type");


        if(imgFile.getSize() > 512*1024)
            throw  new IllegalArgumentException("Invalid image size");

        ProfileImage profileImage =
                ProfileImage.builder()
                        .imageName(imgFile.getOriginalFilename())
                        .imageType(imgFile.getContentType())
                        .image(imgFile.getBytes())
                        .build();

        return profileImage;
    }



}
