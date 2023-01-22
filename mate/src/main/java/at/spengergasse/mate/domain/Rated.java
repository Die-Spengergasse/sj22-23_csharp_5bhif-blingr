package at.spengergasse.mate.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class Rated extends ApplicantStatus{
    @NotNull
    @NotBlank
    @Column(name = "rated_date")
    private LocalDate ratedDate;

    @Column(name = "passed")
    private Boolean passed;
    public Rated(LocalDate ratedDate, Boolean passed) {
        super("UploadDone");
        this.ratedDate = ratedDate;
        this.passed = passed;
    }
    @Override
    public String toString() {
        return "Rated{" +
                "status=" + super.getStatus() +
                ", ratedDate=" + ratedDate +
                ", passed=" + passed +
                '}';
    }
}
