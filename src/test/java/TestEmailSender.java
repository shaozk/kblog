import org.example.blog.utils.EmailSender;

import javax.mail.MessagingException;

public class TestEmailSender {
    public static void main(String[] args) throws MessagingException {
//        EmailSender.subject("测试邮件发送")
//                .from("阳光沙滩博客系统")
//                .text("这是发送的内容：ab12rf")
//                .to("913667678@qq.com")
//                .send();
        sendToPing();

    }

    public static void sendToZhao() throws MessagingException {
        EmailSender.subject("测试邮箱发送")
                .from("康神")
                .text("赵最帅")
                .to("907627553@qq.com")
                .send();
    }

    public static void sendToShen() throws MessagingException {
        EmailSender.subject("测试邮箱发送")
                .from("康神")
                .text("豪神，快回来吃饭")
                .to("2210889275@qq.com")
                .send();
    }

    public static void sendToWei() throws MessagingException {
        EmailSender.subject("测试邮箱发送")
                .from("康神")
                .text("薇神，恰恰饭莫")
                .to("2820208536@qq.com")
                .send();
    }

    public static void sendToHao() throws MessagingException {
        EmailSender.subject("测试邮箱发送")
                .from("康神")
                .text("禹豪，收到消息否？")
                .to("1596101416@qq.com")
                .send();
        System.out.println("toHao成功！！");
    }

    public static void sendToPing() throws MessagingException {
        EmailSender.subject("测试邮箱发送")
                .from("康神")
                .text("测试邮箱发送")
                .to("2914549550@qq.com")
                .send();
    }


}
