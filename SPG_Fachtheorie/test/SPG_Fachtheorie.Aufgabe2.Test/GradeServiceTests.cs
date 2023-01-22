using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;
using SPG_Fachtheorie.Aufgabe2;
using Microsoft.EntityFrameworkCore;
using SPG_Fachtheorie.Aufgabe2.Model;

namespace SPG_Fachtheorie.Aufgabe2.Test
{
    [Collection("Sequential")]
    public class GradeServiceTests
    {
        /// <summary>
        /// Legt die Datenbank an und befüllt sie mit Musterdaten. Die Datenbank ist
        /// nach Ausführen des Tests ServiceClassSuccessTest in
        /// C:\Scratch\Aufgabe2_Test\bin\Debug\net6.0\Grades.db
        /// und kann mit SQLite Manager, DBeaver, ... betrachtet werden.
        /// </summary>
        private GradeContext GetContext(bool deleteDb = true)
        {
            var options = new DbContextOptionsBuilder()
                .UseSqlite("Data Source=Grades.db")
                .Options;

            var db = new GradeContext(options);
            if (deleteDb)
            {
                db.Database.EnsureDeleted();
                db.Database.EnsureCreated();
                db.Seed();
            }
            return db;
        }
        /// <summary>
        /// Erzeugt die Datenbank in C:\Scratch\Aufgabe2_Test\Debug\net6.0
        /// </summary>
        [Fact]
        public void ServiceClassSuccessTest()
        {
            using var db = GetContext();
            Assert.True(db.Students.Count() > 0);
            Assert.True(db.Students.Include(s => s.Grades).First().Grades.Count() > 0);
            var service = new GradeService(db);
        }

        [Fact]
        public void TryAddRegistrationReturnsFalseIfSubjectDoesNotExist()
        {
            var _db = GetContext();
            var service = new GradeService(_db);

            // Arrange
            var student = _db.Students.First();
            var subjectShortname = "invalidSubject";
            var date = DateTime.Now;

            // Act
            var result = service.TryAddRegistration(student, subjectShortname, date);

            // Assert
            Assert.False(result);
        }
        [Fact]
        public void TryAddRegistrationReturnsFalseIfSubjectIsNotNegative()
        {
            var _db = GetContext();
            var service = new GradeService(_db);

            // Arrange
            var student = _db.Students.First();
            var subjectShortname = _db.Grades.Include(g => g.Lesson).First(g => g.StudentId == student.Id && g.GradeValue < 5).Lesson.Subject.Shortname;
            var date = DateTime.Now;

            // Act
            var result = service.TryAddRegistration(student, subjectShortname, date);

            // Assert
            Assert.False(result);
        }
        private GradeContext DbSetupTests()
        {
            var options = new DbContextOptionsBuilder()
               .UseSqlite("Data Source=Grades.db")
               .Options;

            var db = new GradeContext(options);

            db.Database.EnsureDeleted();
            db.Database.EnsureCreated();

            Class newClass = new Class()
            {
                Id = new Guid("3eef9689-3664-47f4-9378-bfdf0226c69b"),
                Name = "6XKIF"

            };

            Student newStudent = new Student()
            {
                Firstname = "Student_01",
                Lastname = "Studen_01",
                Email = "xy@gmx.at",
                Class = newClass,
                Id = new Guid("a96a13e5-029b-4419-8104-048204e0c408")
            };

            db.Students.Add(newStudent);
            db.SaveChanges();

            Teacher teacher = new Teacher()
            {
                Id = new Guid("69484312-fc27-4c6f-84bb-20dc9de8b42a"),
                Firstname = "Teacher_01",
                Lastname = "Teacher_01",
                Email = "xy@gmx.at"
            };

            db.Teachers.AddRange(teacher);
            db.SaveChanges();

            List<Subject> newSubjects = new List<Subject>()
            {
                new Subject(){Longmame="Programmieren",Shortname="POS"},
                new Subject(){Longmame="Datenbanken",Shortname="DBI"},
            };

            List<Lesson> newLessons = new List<Lesson>()
            {
                new Lesson() {Id=new Guid("8d413ab5-2c70-43e3-95b8-89636a4c60a8"), Class=newClass, Subject=newSubjects[0], Teacher=teacher },
                new Lesson() {Id=new Guid("733c8740-2364-40fc-8db6-95f5910dffea"), Class=newClass, Subject=newSubjects[1], Teacher=teacher },
            };

            db.Lessons.AddRange(newLessons);
            db.SaveChanges();

            List<Grade> newGrades = new List<Grade>()
            {
                new Grade() {Id=new Guid("4dde1f7d-31ad-46ff-8ed3-46e733411892"),GradeValue=1,Lesson=newLessons[0],Student=newStudent},
                new Grade() {Id=new Guid("7aeb2358-dc33-4ba5-a8c7-86e3485db3cc"),GradeValue=5,Lesson=newLessons[1],Student=newStudent},

            };

            db.Grades.AddRange(newGrades);
            db.SaveChanges();

            List<Exam> newExams = new List<Exam>()
            {
                new Exam() {Date=DateTime.Now, Grade=newGrades[0]},
            };

            db.Exams.AddRange(newExams);
            db.SaveChanges();

            return db;
        }
        [Fact]
        public void TryAddRegistrationReturnsFalseIfExamExists()
        {
            var _db = GetContext();
            var service = new GradeService(_db);

            // Arrange
            var student = _db.Students.First();
            var subjectShortname = "POS";
            var date = DateTime.Now;

            // Add an exam for the student and subject
            _db.Exams.Add(new Exam
            {
                Grade = new Grade()
                {
                    Student = student,
                    Lesson = _db.Lessons.First(l => l.Subject.Shortname == subjectShortname && l.ClassId == student.ClassId)
                },
                Date = date
            });
            _db.SaveChanges();

            // Act
            var result = service.TryAddRegistration(student, subjectShortname, date);

            // Assert
            Assert.False(result);
        }
        [Fact]
        public void TryAddRegistrationReturnsFalseOnDateConflict()
        {
            var _db = GetContext();
            var service = new GradeService(_db);

            // Arrange
            var student = _db.Students.First();
            var subjectShortname = "POS";
            var date = _db.Exams.First().Date;

            // Act
            var result = service.TryAddRegistration(student, subjectShortname, date);

            // Assert
            Assert.False(result);
        }
        [Fact]
        public void TryAddRegistrationReturnsSuccessTest()
        {
            GradeContext db = DbSetupTests();

            GradeService service = new GradeService(db);

            bool actual = service.TryAddRegistration(db.Students.SingleOrDefault(s => s.Id == Guid.Parse("a96a13e5-029b-4419-8104-048204e0c408"))
                , "DBI"
                , DateTime.Now.AddDays(14));

            Assert.True(actual);
        }
    }
}
