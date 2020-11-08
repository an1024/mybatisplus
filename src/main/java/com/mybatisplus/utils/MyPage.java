package com.mybatisplus.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 适应前端的包装类
 */
@Data
public class MyPage<T> implements Serializable {

    private List<T> list;

    private Pagination pagination;

    private MyPage() {}

    public MyPage(IPage<T> iPage) {
        this.list = iPage.getRecords();
        this.pagination = new Pagination(
                Integer.parseInt(String.valueOf(iPage.getCurrent())),
                Integer.parseInt(String.valueOf(iPage.getSize())),
                Integer.parseInt(String.valueOf(iPage.getTotal()))
        );
    }

}