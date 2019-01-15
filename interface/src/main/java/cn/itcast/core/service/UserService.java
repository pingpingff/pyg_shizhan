package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public void sendCode(String phone);

    public Boolean checkSmsCode(String phone, String smsCode);

    public void add(User user);

    void userFreeze(Long[] ids);

    List<User> find(String username);

    void addLiveness(String username);

    Map findLiveness();

}
