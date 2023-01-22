package at.spengergasse.mate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicant_status")
@Getter
@Setter
public class ApplicantStatus extends AbstractPersistable<Long> {
    @NotNull
    @NotBlank
    @Column(name = "status")
    private String status;
}
