package cn.edu.cqu.service.Impl;

import cn.edu.cqu.DAO.UserDOMapper;
import cn.edu.cqu.DAO.UserPwdDOMapper;
import cn.edu.cqu.dataobject.UserDO;
import cn.edu.cqu.dataobject.UserPwdDO;
import cn.edu.cqu.service.UserService;
import cn.edu.cqu.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPwdDOMapper userPwdDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if (userDO == null) {
            return null;
        }

        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO, userPwdDO);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPwdDO userPwdDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);

        if (userPwdDO != null) {
            userModel.setEncrptPassword(userPwdDO.getEncrptPassword());
        }

        return userModel;
    }
}
