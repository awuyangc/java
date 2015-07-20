package com.wy.dao;

import com.wy.model.AccessToken;
import java.util.List;

public interface AccessTokenMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_access_token
	 * @mbggenerated  Tue Jun 23 13:46:01 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_access_token
	 * @mbggenerated  Tue Jun 23 13:46:01 CST 2015
	 */
	int insert(AccessToken record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_access_token
	 * @mbggenerated  Tue Jun 23 13:46:01 CST 2015
	 */
	AccessToken selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_access_token
	 * @mbggenerated  Tue Jun 23 13:46:01 CST 2015
	 */
	List<AccessToken> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_access_token
	 * @mbggenerated  Tue Jun 23 13:46:01 CST 2015
	 */
	int updateByPrimaryKey(AccessToken record);
}