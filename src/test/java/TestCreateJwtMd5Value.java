import org.springframework.util.DigestUtils;

public class TestCreateJwtMd5Value {
    public static void main(String[] args) {
        String jwtKeyMd5Str = DigestUtils.md5DigestAsHex("laoping123".getBytes());
        System.out.println(jwtKeyMd5Str);
    }
}
