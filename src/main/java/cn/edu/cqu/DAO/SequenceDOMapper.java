package cn.edu.cqu.DAO;

import cn.edu.cqu.dataobject.SequenceDO;

public interface SequenceDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Feb 12 17:39:01 GMT+08:00 2020
     */
    int insert(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Feb 12 17:39:01 GMT+08:00 2020
     */
    int insertSelective(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Feb 12 17:39:01 GMT+08:00 2020
     */
    SequenceDO getSequenceByName(String name);
    SequenceDO selectByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Feb 12 17:39:01 GMT+08:00 2020
     */
    int updateByPrimaryKeySelective(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Feb 12 17:39:01 GMT+08:00 2020
     */
    int updateByPrimaryKey(SequenceDO record);
}