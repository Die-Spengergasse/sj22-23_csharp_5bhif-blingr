using System.Collections.Generic;

namespace SPG_Fachtheorie.Aufgabe1.Model
{
    public class Applicant
    {
        public int Id { get; set; }
        public List<Upload> Uploads { get; set; } = new();
        public Department DepartmentNavigation { get; set; } = default!;
        public int DepartmentId { get; set; }
        public ApplicantStatus ApplicantStatusNavigation { get; set; } = default!;
        public int ApplicantId { get; set;}

    }
}
