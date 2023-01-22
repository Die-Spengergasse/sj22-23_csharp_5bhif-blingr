package at.spengergasse.mate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "upload")
public class Upload extends AbstractPersistable<Long>{

    @NotNull
    @NotBlank
    @Column(name = "time_Stamp")
    private LocalDate timeStamp;
    @NotNull
    @NotBlank
    @Column(name = "file_name")
    private String fileName;
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
