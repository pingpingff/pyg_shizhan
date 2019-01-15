package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证类:
 * springSecurity和cas集成后, 用户名, 密码的判断认证工作交给cas来完成
 * springSecurity只负责cas验证完后, 给用户赋权工作
 * 如果能进入到这个实现类, 说明cas已经认证通过, 这里只做赋权操作
 */
public class UserDetailServiceImpl implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名得到用户所有信息
        List<User> userList = userService.find(username);
        String statu = "";
        for (User user1 : userList) {
            statu = user1.getStatus();
        }
        //判断用户的状态
        if (statu == Constants.User_Statu) {
            //冻结状态不给予权限,使其无法登陆
            return new org.springframework.security.core.userdetails.User(username, "", null);
        } else {
            //调用业务层添加登陆次数,修改最后登陆时间
            userService.addLiveness(username);

            //正常状态给予登陆权限
            List<GrantedAuthority> autoList = new ArrayList<GrantedAuthority>();
            autoList.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(username, "", autoList);
        }
    }
}
