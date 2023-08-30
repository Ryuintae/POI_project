package com.example.demo.mapper;

import com.example.demo.dto.UserVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM public.user")
    List<UserVo> findAll();

    UserVo getUserById(@Param("userId") Long userId);

    // 로그인
    @Select("SELECT * FROM public.user WHERE user_email = #{user_password}")
    UserVo getUserAccount(@Param("user_password") String user_password);

    // 회원가입
    @Insert("INSERT INTO public.user (user_email, user_password, user_name, user_auth) " +
            "VALUES (#{user_email}, #{user_password}, #{user_name}, #{user_auth})")
    void saveUser(UserVo userVo);

    // 이메일 중복 체크
    @Select("SELECT * FROM public.user WHERE user_email = #{user_email}")
    UserVo findByEmail(@Param("user_email") String email);

}