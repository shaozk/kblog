package org.example.blog.utils;

public interface Constants {

    int DEFAULT_SIZE = 30;

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
        String COOKIE_KEY_TOKEN = "k_blog_token";
        // redis的key
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_TOKEN = "key_token_";
    }

    /**
     * 图片类型
     */
    interface ImageType {
        String PREFIX = "image/";
        String TYPE_JPG = "jpg";
        String TYPE_PNG = "png";
        String TYPE_GIF = "gif";
        String TYPE_JPG_WITH_PREFIX = PREFIX + "jpg";
        String TYPE_PNG_WITH_PREFIX = PREFIX + "png";
        String TYPE_GIF_WITH_PREFIX = PREFIX + "gif";
    }

    interface Setting {
        String HAS_MANAGER_ACCOUNT_STATE = "has_manager_account_state";
        String WEB_SIZE_TITLE = "web_size_title";
        String WEB_SIZE_DESCRIPTION = "web_size_description";
        String WEB_SIZE_KEYWORDS = "web_size_keywords";
        String WEB_SIZE_VIEW_COUNT = "web_size_view_count";
    }

    interface Page {
        int DEFAULT_PAGE = 1;
        int DEFAULT_SIZE = 10;
        int MIN_SIZE = 10;
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

    /**
     * 文章
     */
    interface Article {
        int TITLE_MAX_LENGTH = 128;
        int SUMMARY_MAX_LENGTH = 256;

    }


}
