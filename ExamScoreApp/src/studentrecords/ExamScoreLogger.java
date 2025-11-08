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
