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
