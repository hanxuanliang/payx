package com.hxl.payx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 类目 返回对象
 * @Author: hanxuanliang
 * @Date: 2020/1/29 22:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVo> subCategories;
}
