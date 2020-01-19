package cn.edu.cqu.controller;

import cn.edu.cqu.controller.viewobject.UserVO;
import cn.edu.cqu.error.BusinessException;
import cn.edu.cqu.error.EmBusinessError;
import cn.edu.cqu.response.CommonReturnType;
import cn.edu.cqu.service.UserService;
import cn.edu.cqu.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    //用户获取otp短信接口
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
        //需要按照一定的规则生活生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);


        //将OTP验证码同对应用户的手机号关联
        //使用htppsession的方式绑定他的手机号与otpcode
        httpServletRequest.getSession().setAttribute(telephone, otpCode);


        //将OTP验证码通过短信通道发送给用户，省略
        // (可以买第三方的短信通道，
        // 用最简单的HTTP POST方法将短信模板POST给用户)
        // 实际项目中使用log4j检测用户信息
        System.out.println("telephone = " + telephone + " & otpCode = " + otpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {


        UserModel userModel = userService.getUserById(id);

        //若获取的用户信息不存在
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        UserVO userVO = convertFromModel(userModel);

        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
