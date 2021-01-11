import io.jsonwebtoken.Claims;
import org.example.blog.utils.JwtUtil;

public class TestParesToken {
    public static void main(String[] args) {
        Claims claims = JwtUtil.parseJWT("");
        System.out.println(claims.getId() + " " + claims.getSubject());
    }
}
