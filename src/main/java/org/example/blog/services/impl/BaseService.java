package org.example.blog.services.impl;

import org.example.blog.utils.Constants;

public class BaseService {
    /**
     * 页面数
     * @param page
     * @return
     */
    int checkPage(int page) {
        if(page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        return page;
    }

    /**
     * 页面大小
     * @param size
     * @return
     */
    int checkSize(int size) {
        if (size < Constants.Page.DEFAULT_SIZE) {
            size = Constants.Page.DEFAULT_SIZE;
        }
        return size;
    }
}
