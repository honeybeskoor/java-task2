output:<img width="521" height="198" alt="image" src="https://github.com/user-attachments/assets/3a8bed34-ea7d-4bc8-bdb2-eef173e8c5ab" />
<img width="1211" height="214" alt="image" src="https://github.com/user-attachments/assets/41dca0cd-7576-4307-ac2c-3ebb3d57c4c3" />


package studentrecords;

public class Student {
	private String name;
    private int rollNumber;
    private int marks;

    public Student(String name, int rollNumber, int marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    public String toCSV() {
        return rollNumber + "," + name + "," + marks;
    }

}
package studentrecords;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreWriter implements Runnable {
    private Student student;
    private String fileName;
    private static final Object lock = new Object();

    public ScoreWriter(Student student, String fileName) {
        this.student = student;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true)))
{
                writer.write(student.toCSV());
                writer.newLine();
                System.out.println("Written: " + student.toCSV());
            } catch (IOException e) {
         e.printStackTrace();
            }
}
   }
 }
package studentrecords;

public class ExamScoreLogger {
    public static void main(String[] args) {
        String fileName = "exam_scores.csv";

       Student s1 = new Student("Alice", 201, 92);
        Student s2 = new Student("Bob", 202, 85);
      Student s3 = new Student("Charlie", 203, 78);
        Student s4 = new Student("Diana", 204, 89);

       Thread t1 = new Thread(new ScoreWriter(s1, fileName));
       Thread t2 = new Thread(new ScoreWriter(s2, fileName));
       Thread t3 = new Thread(new ScoreWriter(s3, fileName));
      Thread t4 = new Thread(new ScoreWriter(s4, fileName));

        
        t1.start();
        t2.start();
      t3.start();
        t4.start();

        
        try
        {
            t1.join();
          t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All student scores written to " + fileName);
               }
}
package studentrecords;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Test;

public class ExamScoreLoggerTest {

    @Test
    public void testFileWrite() throws IOException {
        String testFile = "test_exam_scores.csv";

        Student student = new Student("TestStudent", 999, 100);
        Thread t = new Thread(new ScoreWriter(student, testFile));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File file = new File(testFile);
        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());
        assertTrue(lines.contains("999,TestStudent,100"));

        file.delete();
    }
}
