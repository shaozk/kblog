import org.springframework.util.DigestUtils;

public class TestCreateJwtMd5Value {
    public static void main(String[] args) {
        String jwtKeyMd5Str = DigestUtils.md5DigestAsHex("k_blog_token".getBytes());
        System.out.println(jwtKeyMd5Str);
    }
}
