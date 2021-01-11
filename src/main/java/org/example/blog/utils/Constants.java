package org.example.blog.utils;

public interface Constants {


    interface TimeValueInMillions {
        int HOUR_1 = 60 * 1000; // 1小时
        int HOUR_2 = 2 * 60 * 1000; // 2小时
        int HOUR_3 = 3 * 60 * 1000; // 3小时（默认）
    }

    interface User {
        String ROLE_ADMIN = "role_admin";
        String ROLE_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://cdn.sunofbeaches.com/images/default_avatar.png";
        String DEFAULT_STATE = "1";
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_TOKEN = "key_token_";
        String COOKIE_KEY_TOKEN = "k_blog_token";
    }

    interface Setting {
        String HAS_MANAGER_ACCOUNT_STATE = "has_manager_account_state";

    }

    /**
     * 单位是秒
     */
    interface TimeValue {
        int MIN = 60;
        int HOUR = 60 * MIN;
        int HOUR_2 = 2 * HOUR;
        int DAY = 24 * HOUR;
        int WEEK = 7 * DAY;
        int MONTH = 30 * DAY;
        int YEAR = 12 * MONTH;
    }


}
