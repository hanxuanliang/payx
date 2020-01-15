package com.hxl.payx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 商品分类表
 * @Author: hanxuanliang
 * @Date: 2020/1/15 8:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;
}
