package com.web.basic.project.mapper;

import com.web.basic.project.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.basic.project.form.user.ListUserForm;
import com.web.basic.project.vo.UserVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date Created in 2020/3/7 2:21 下午
 * Utils: Intellij Idea
 * Description: 数据库资源操作类
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户列表
     * @param listUserForm 表单数据
     * @return 用户列表
     */
    @Select("SELECT id,nickname,username,birthday FROM `user` WHERE `status`= #{status} LIMIT #{current},#{size}")
    List<UserVo> listUser(ListUserForm listUserForm);

    @Select("SELECT password FROM user WHERE username= #{username}")
    String getPassword(String username);

    @Select("SELECT role FROM user WHERE username= #{username}")
    String getRole(String username);

}
