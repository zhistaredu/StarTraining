package com.edu.common.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtil {
    //校验对象（线程安全）
    static Validator validator;

    //初始化
    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

//
//    //校验方法
//    public static List<String> valid(List<EasySysUser> value){
//
//        //校验错误信息
//        List<String>  errorResult = new ArrayList<>();
//        //初始行数
//        int row = 2;
//
//        for (EasySysUser user : value){
//
//            //如果没有校验通过，就会有校验信息
//            Set<ConstraintViolation<Object>> validate = validator.validate(user);// 进行校验
//
//            if (!validate.isEmpty()){
//                int finalRow = row;
//                List<String> collect = validate.stream().map(v ->
//                                "第"+ finalRow +"行--"+
//                                        "属性：" + v.getPropertyPath() +
//                                        "，属性的值：" + v.getInvalidValue() +
//                                        "，校验不通过的提示信息:" + v.getMessage())
//                        .collect(Collectors.toList());
//
//                errorResult.add(collect.toString());
//            }
//            ++row;
//        }
//        return errorResult;
//
//    }
}
