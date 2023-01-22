package at.spengergasse.mate.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "applicant")
@Builder
public class Applicant extends AbstractPersistable<Long> {

    @NotNull
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @NotBlank
    @Column(name = "email")
    private String email;
    @NotNull
    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;
    @NotNull
    @NotBlank
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "gender")
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Upload> uploads = new ArrayList<>(5); // list of files

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne
    private ApplicantStatus applicantStatus; // enum


}
