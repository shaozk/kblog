package org.example.blog.utils;

public interface Constants {
    interface User {
        String ROLE_ADMIN = "role_admin";
        String ROLE_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://cdn.sunofbeaches.com/images/default_avatar.png";
        String DEFAULT_STATE = "1";
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";

    }

    interface Setting {
        String HAS_MANAGER_ACCOUNT_STATE = "has_manager_account_state";

    }
}
