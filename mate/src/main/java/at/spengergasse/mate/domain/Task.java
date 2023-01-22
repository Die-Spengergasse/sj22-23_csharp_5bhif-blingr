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
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "task")
public class Task extends AbstractPersistable<Long> {

    @NotNull
    @NotBlank
    private String text;
    private LocalDate dateTo;
    private LocalDate dateFrom;

    @ManyToOne
    private Department departments;
}
