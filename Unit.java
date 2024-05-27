import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Unit {

    Scanner scanner = new Scanner(System.in);
    String unitname;
    List<Student> students = new ArrayList<>(10);
    List<String> notifications = new ArrayList<>(10);
    List<Task> tasks = new ArrayList<>(20);
    private int currentquize = 0;
    private Teacher teacher;

    public Unit(String unitname, Teacher teacher) {
        setUnitname(unitname);
        setTeacher(teacher);
    }
    public Unit(){}

    // Methods
    public void AddQuestion(String deadline, String taskname, String questiontext, int answer) {
        if (!deadline.isEmpty() && !taskname.isEmpty() && !questiontext.isEmpty() && answer>0) {
            tasks.add(new Question(deadline, questiontext, answer,taskname, this,this.teacher));
            System.out.println("question added , do you want to add another question?y/n");
            if (scanner.next().charAt(0) == 'y') {
                System.out.println("enter date, then question name ,text and answer");
                AddQuestion(scanner.next(), scanner.next(), scanner.next(), scanner.nextInt());
            }
        } else {
            System.out.println("deadline and question name can't be empty , do you want try again?y/n");
            if (scanner.next().charAt(0) == 'y') {
                System.out.println("enter date, then question name ,text and answer");
                AddQuestion(scanner.next(), scanner.next(), scanner.next(), scanner.nextInt());
            }
        }
    }

    public void AddQuiz(String startdate, String quiztime, String deadline, String quizname , int score) {
        if (startdate.isEmpty() || quiztime.isEmpty() || deadline.isEmpty() || quizname.isEmpty()) {
            System.out.println("start/deadline date and quiz date/name can't be empty! try again");
            AddQuiz(scanner.next(), scanner.next(), scanner.next(), scanner.next() ,scanner.nextInt());
        } else {
            tasks.add(new Quiz(startdate, quiztime, deadline, quizname, this, this.teacher ,score));
            currentquize++;
            boolean repeat;
            do {
                repeat = false;
                System.out.println("enter your questions and answers :");
                if(tasks.get(currentquize) instanceof Quiz quizInstance){
                    quizInstance.setProblem_answers(scanner.next(), scanner.next());
                    System.out.println("question and answer add successfully do you want to add another problem?(y/n))");
                    if (scanner.next().charAt(0) == 'y') {
                        repeat = true;
                    }
                }
            } while (repeat);

        }
    }

    public boolean getTimeState(String deadline) {
        // Use a date-time formatter to parse the deadline string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime finishtime = LocalDateTime.parse(deadline, formatter);
        Duration duration = Duration.between(LocalDateTime.now(), finishtime);
        if (finishtime.isAfter(LocalDateTime.now())) {
            System.out.println("You have " + duration.toHours() + " hours and " + duration.toMinutes() % 60 + " minutes time");
            return true;
        } else {
            System.out.println(duration.toHours() + " hours and " + duration.toMinutes() % 60 + " minutes have passed the deadline!");
            return false;
        }
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        if (!unitname.isEmpty()) {
            this.unitname = unitname;
        } else {
            System.out.println("Unit name cant be empty!");
            setUnitname(scanner.next());
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(Student student) {
        students.add(student);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    private void setTeacher(Teacher teacher) {
        if (teacher != null) {
            this.teacher = teacher;
        }
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public String getName(){
        return this.unitname;
    }

}
