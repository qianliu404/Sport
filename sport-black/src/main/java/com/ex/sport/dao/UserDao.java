package com.ex.sport.dao;

import com.ex.sport.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    public User getUserByMessage(@Param("username") String username, @Param("password") String password);
    public List<User> getAllUser(@Param("username") String username,@Param("pageStart") int PageStart,@Param("pageSize")int PageSize);
    public int getUserCounts(@Param("username") String username);
    public int updateState(@Param("id") Integer id,@Param("state") Boolean state);
    public int addUser(User user);
    public int deleteUser(int id);
    public User getUpdateUser(int id);
    public int editUser(User user);
}
