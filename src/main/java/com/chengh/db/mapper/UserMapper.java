package com.chengh.db.mapper;

import com.chengh.db.entity.User;
import com.chengh.db.entity.crmchance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 保存
     */
    void save(User user);

    /**
     * 查询
     * @param id
     * @return
     */
    List<User> getById(@Param("id")Long id);

    List<crmchance> getCreateDate(@Param("mobiles")List<String> mobiles);
}

