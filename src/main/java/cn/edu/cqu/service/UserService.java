package cn.edu.cqu.service;

import cn.edu.cqu.error.BusinessException;
import cn.edu.cqu.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException;
}
