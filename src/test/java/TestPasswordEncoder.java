import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPasswordEncoder {
    public static void main(String[] args) {
        // 加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println("encode ==> " + encode);
        // $2a$10$9aQ8nHDJyY1NvpoHEuM8T.8tn/dS6Y9uJicJo6SmduHOq/2L7Dl56

        // 验证密码
        String originalPassword = "123456";
        boolean matches = passwordEncoder.matches(originalPassword, "$2a$10$9aQ8nHDJyY1NvpoHEuM8T.8tn/dS6Y9uJicJo6SmduHOq/2L7Dl56");
        System.out.println(matches);
    }
}
