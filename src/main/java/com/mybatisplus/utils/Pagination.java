package com.mybatisplus.utils;

import lombok.Data;

/**
 * 前台页码包装类
 */
@Data
public class Pagination {

    public Pagination(Integer current,Integer pageSize,Integer total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    private Integer current;

    private Integer pageSize;

    private Integer total;
}
