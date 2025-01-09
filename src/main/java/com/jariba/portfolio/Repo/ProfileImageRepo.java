package com.jariba.portfolio.Repo;

import com.jariba.portfolio.Model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ProfileImageRepo extends JpaRepository<ProfileImage, Integer> {

    @Modifying
    @Transactional
    @Query("delete from ProfileImage pi where pi.id = :id")
    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("insert into ProfileImage pi(pi.id, pi.image, pi.imageType, pi.imageName) VALUES(:id, :image, :imageType, :imageName)")
    void addProfileImage(int id, byte[] image, String imageType, String imageName);
}

