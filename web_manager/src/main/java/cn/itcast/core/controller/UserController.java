package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/userFreeze")
    public Result userFreeze(Long[] ids) {
        try {
            userService.userFreeze(ids);
            return new Result(true, "冻结成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "冻结失败");
        }
    }

    @RequestMapping("/findLiveness")
    public Map findLiveness() {
        return userService.findLiveness();
    }



}
