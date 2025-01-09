package com.jariba.portfolio.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@ToString(exclude = "candidate")

public class Contact {

    @Id
    private int candidateId;
    private String phoneNo;

    @Email
    private String emailId;

    private String linkedin;
    private String github;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},optional = false)
    @JoinColumn(name= "candidate_id", referencedColumnName = "id" )
    @MapsId
    private Candidate candidate;
}
