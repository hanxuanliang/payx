package com.hxl.payx.dao;

import com.hxl.payx.entity.PayInfo;
import org.springframework.stereotype.Repository;

/**
 * @Description: 订单详情 Mapper
 * @Author: hanxuanliang
 * @Date: 2020/1/16 10:13
 */
@Repository
public interface PayInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

    PayInfo selectByOrderNo(Long orderNo);
}
