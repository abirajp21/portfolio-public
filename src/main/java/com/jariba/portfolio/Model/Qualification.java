package com.jariba.portfolio.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Fetch;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@ToString(exclude = "candidate")
public class Qualification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Institution name is required")
    private String institution;

    @NotBlank(message = "Qualification is required" )
    private String qualification;

    private String fieldOfStudy;

    @PositiveOrZero(message = "percantage can't be negative")
    private double percentage;
    @Min(value = 1900, message = "Graduation Year must be greater than 1900")
    private int graduationYear;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

}
