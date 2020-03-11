package cn.edu.cqu.service.Impl;

import cn.edu.cqu.DAO.UserDOMapper;
import cn.edu.cqu.DAO.UserPwdDOMapper;
import cn.edu.cqu.dataobject.UserDO;
import cn.edu.cqu.dataobject.UserPwdDO;
import cn.edu.cqu.error.BusinessException;
import cn.edu.cqu.error.EmBusinessError;
import cn.edu.cqu.service.UserService;
import cn.edu.cqu.service.model.UserModel;
import cn.edu.cqu.validator.ValidationResult;
import cn.edu.cqu.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPwdDOMapper userPwdDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if (userDO == null) {
            return null;
        }

        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO, userPwdDO);
    }

    @Override
    public UserModel getUserByIdInCache(Integer id) {
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get("user_validate_"+id);
        if (userModel == null) {
            userModel = this.getUserById(id);
            redisTemplate.opsForValue().set("user_validate_"+id,userModel);
            redisTemplate.expire("user_validate_"+id,10, TimeUnit.MINUTES);
        }
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult validationResult = validator.validate(userModel);
        if (validationResult.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }

        UserDO userDO = convertFromModdel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该手机号已被注册");
        }

        //mapper插入之后才生成id，需要取出之后再传回给Model
        userModel.setId(userDO.getId());

        UserPwdDO userPwdDO = convertPwdDOFromModel(userModel);
        userPwdDOMapper.insertSelective(userPwdDO);
        return;
    }

    @Override
    public UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException {
        //通过用户手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPwdDO);

        //比对加密密码是否和传输进来的密码匹配
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        return userModel;
    }

    private UserDO convertFromModdel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);

        return userDO;
    }

    private UserPwdDO convertPwdDOFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPwdDO userPwdDO = new UserPwdDO();
        userPwdDO.setUserId(userModel.getId());
        userPwdDO.setEncrptPassword(userModel.getEncrptPassword());
        return userPwdDO;
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
