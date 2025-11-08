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
