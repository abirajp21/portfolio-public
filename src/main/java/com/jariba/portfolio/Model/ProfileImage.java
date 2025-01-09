package com.jariba.portfolio.Model;


import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
//@ToString(exclude = "candidate" )
public class ProfileImage {

    @Id
    int id;

    @Lob
    private byte[] image;

    @NotBlank(message = "Image name Cannot be empty")
    private String imageName;
    @NotBlank(message = "Image Type Can't be empty")
    private String imageType;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH})
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Candidate candidate;
}
