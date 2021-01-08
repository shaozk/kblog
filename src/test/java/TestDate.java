import java.util.Calendar;

public class TestDate {
    public static void main(String[] args) {
//        long currentTime = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.set(2999,11,1);
        long timeMills = instance.getTimeInMillis();
        System.out.println(String.valueOf(timeMills).length());
    }
}
