package at.spengergasse.mate;

import at.spengergasse.mate.domain.*;
import at.spengergasse.mate.repository.ApplicantRepository;
import at.spengergasse.mate.repository.DepartmentRepository;
import at.spengergasse.mate.repository.TaskRepository;
import at.spengergasse.mate.repository.UploadRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class MateApplicationTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private UploadRepository uploadRepository;

    @Test
    public void addDepartmentSuccessTest() {
        Department department = new Department("Abteilung für Medien - Animation", null);
        departmentRepository.save(department);

        assertThat(department.getId()).isNotNull();
        assertThat(departmentRepository.findById(department.getId())).isPresent();
    }

    @Test
    public void addTaskSuccessTest() {
        Task task = new Task("Aufgabenstellung 1", LocalDate.now(), LocalDate.now(), null);
        taskRepository.save(task);

        assertThat(task.getId()).isNotNull();
        assertThat(taskRepository.findById(task.getId())).isPresent();
    }

    @Test
    public void addApplicantSuccessTest() {
        Applicant applicant = new Applicant("Jane", "Smith", "email", "0987654321", "email", Gender.MALE, null, null, null);
        applicantRepository.save(applicant);

        assertThat(applicant.getId()).isNotNull();
        assertThat(applicantRepository.findById(applicant.getId())).isPresent();
    }
    @Test
    public void addUploadSuccessTest() {
        Upload upload = new Upload(LocalDate.now(),"fdwafd", null,null);
        uploadRepository.save(upload);

        assertThat(upload.getId()).isNotNull();
        assertThat(uploadRepository.findById(upload.getId())).isPresent();
    }

    @Test
    public void addApplicantWithApplicantStatusSuccessTest() {
        Applicant applicant = new Applicant("Jane", "Smith", "email", "0987654321", "email", Gender.MALE, null, null, null);
        applicantRepository.save(applicant);

        assertThat(applicant.getId()).isNotNull();
        assertThat(applicantRepository.findById(applicant.getId())).isPresent();
    }
}
