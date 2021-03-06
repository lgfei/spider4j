package com.lgfei.tool.spider.common.validation.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.lgfei.tool.spider.common.enums.ResultCode;
import com.lgfei.tool.spider.common.exception.InnerException;
import com.lgfei.tool.spider.common.exception.ValidateException;
import com.lgfei.tool.spider.common.message.request.IRequestData;
import com.lgfei.tool.spider.common.util.ReflectUtil;
import com.lgfei.tool.spider.common.util.StringUtil;
import com.lgfei.tool.spider.common.validation.IValidatable;
import com.lgfei.tool.spider.common.validation.Validator;
import com.lgfei.tool.spider.common.validation.annotations.Param;

/**
 * 注解Param对应的处理类
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年10月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ParamHandler implements Handler
{
    private static final Logger LOG = LoggerFactory.getLogger(ParamHandler.class);
    
    /**
     * 校验异常内容的日志格式  ： param=所有参数的值|具体异常消息
     */
    private static final String LOG_PARAM = "param=";
    
    /**
     * 校验异常内容的日志格式  ： param=所有参数的值|具体异常消息
     */
    private static final String LOG_SEPERATE = "|";
    
    @Override
    public void execute(Annotation anno, String fieldName, Object fieldValue, IValidatable bean, String param)
        throws ValidateException, InnerException
    {
        Field field = ReflectUtil.getField(bean, fieldName);
        if (null == field)
        {
            LOG.error("反射field异常");
            throw new InnerException(ResultCode.INNER_ERROR.getRetCode(), ResultCode.INNER_ERROR.getRetMsg());
        }
        // 属性类型
        Class<?> fieldClazz = field.getType();
        
        Param paramAnno = (Param)anno;
        // 为空校验
        boolean canBlank = paramAnno.canBlank();
        validateCanBlank(canBlank, fieldName, fieldValue, fieldClazz, param);
        
        // 默认值处理
        String defValue = paramAnno.defValue();
        fieldValue = handleDefValue(defValue, bean, fieldName, fieldValue, fieldClazz);
        
        if (IRequestData.class.isAssignableFrom(fieldClazz))
        {
            // 业务封装类，递归校验封装类对象
            IRequestData fieldObj = (IRequestData)JSON.parseObject(JSON.toJSONString(fieldValue), fieldClazz);
            // 如果允许为空，且刚好为空，则跳过其他校验
            if (null == fieldObj)
            {
                return;
            }
            Validator.getInstance().validate(fieldClazz, fieldObj);
            return;
        }
        else if (Integer.class.isAssignableFrom(fieldClazz))
        {
            check4Integer(fieldName, fieldValue, paramAnno, param);
            return;
        }
        else if (String.class.isAssignableFrom(fieldClazz))
        {
            check4String(fieldName, fieldValue, paramAnno, param);
            return;
        }
        else
        {
            LOG.info("暂不支持的数据类型");
            return;
        }
    }
    
    /**
     * 为空校验
     * <功能详细描述>
     * @param canBlank 是否可以为空
     * @param fieldName 属性名称
     * @param fieldValue 属性值
     * @param fieldClazz 属性类型
     * @throws ValidateException 校验异常
     * @see [类、类#方法、类#成员]
     */
    private void validateCanBlank(boolean canBlank, String fieldName, Object fieldValue, Class<?> fieldClazz,
        String param)
        throws ValidateException
    {
        if (canBlank)
        {
            return;
        }
        boolean isPass = true;
        if ((Integer.class.isAssignableFrom(fieldClazz) || IRequestData.class.isAssignableFrom(fieldClazz))
            && null == fieldValue)
        {
            isPass = false;
        }
        else if (String.class.isAssignableFrom(fieldClazz) && StringUtils.isEmpty(fieldValue))
        {
            isPass = false;
        }
        // 校验不通过
        if (!isPass)
        {
            throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                LOG_PARAM + param + LOG_SEPERATE + fieldName + "不能为空");
        }
    }
    
    /**
     * 默认值处理逻辑
     * <功能详细描述>
     * @param defValue 默认值
     * @param bean 对象
     * @param fieldName 属性名
     * @param fieldValue 属性值
     * @param fieldClazz 属性类型
     * @return Object
     * @throws InnerException 内部异常
     * @see [类、类#方法、类#成员]
     */
    private Object handleDefValue(String defValue, IValidatable bean, String fieldName, Object fieldValue,
        Class<?> fieldClazz)
        throws InnerException
    {
        if (Integer.class.isAssignableFrom(fieldClazz) && null == fieldValue && StringUtil.isInteger(defValue))
        {
            //反射赋值
            ReflectUtil.setValue(bean, fieldName, defValue);
            return defValue;
        }
        else if (String.class.isAssignableFrom(fieldClazz) && StringUtils.isEmpty(fieldValue)
            && !StringUtils.isEmpty(defValue))
        {
            //反射赋值
            ReflectUtil.setValue(bean, fieldName, defValue);
            return defValue;
        }
        return fieldValue;
    }
    
    /**
     * integer类型的属性校验
     * <功能详细描述>
     * @param fieldName 属性名
     * @param fieldValue 属性值
     * @param paramAnno 注解配置
     * @throws ValidateException 校验异常
     * @see [类、类#方法、类#成员]
     */
    private void check4Integer(String fieldName, Object fieldValue, Param paramAnno, String param)
        throws ValidateException
    {
        // 如果允许为空，且没有赋值默认值，则跳过其他校验
        if (null == fieldValue)
        {
            return;
        }
        
        int fieldIntValue = Integer.parseInt(fieldValue.toString());
        // 最大值校验
        int maxLen = paramAnno.maxLength();
        if (maxLen != -1 && fieldIntValue > maxLen)
        {
            throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                LOG_PARAM + param + LOG_SEPERATE + fieldName + "最大值不能大于" + maxLen);
        }
        // 最小值校验
        int minLen = paramAnno.minLength();
        if (minLen != -1 && fieldIntValue < minLen)
        {
            throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                LOG_PARAM + param + LOG_SEPERATE + fieldName + "最小值不能小于" + minLen);
        }
        
        // 正则匹配校验
        String regex = paramAnno.regex();
        if (!StringUtils.isEmpty(regex))
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(String.valueOf(fieldValue));
            if (!matcher.matches())
            {
                throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                    LOG_PARAM + param + LOG_SEPERATE + fieldName + "格式不正确");
            }
        }
    }
    
    /**
     * string类型的属性校验
     * <功能详细描述>
     * @param fieldName 属性名
     * @param fieldValue 属性值
     * @param paramAnno 注解配置 
     * @throws ValidateException 校验异常
     * @see [类、类#方法、类#成员]
     */
    private void check4String(String fieldName, Object fieldValue, Param paramAnno, String param)
        throws ValidateException
    {
        // 如果允许为空，且没有赋值默认值，则跳过其他校验
        if (StringUtils.isEmpty(fieldValue))
        {
            return;
        }
        
        // 最大长度校验
        int maxLen = paramAnno.maxLength();
        if (maxLen != -1 && String.valueOf(fieldValue).length() > maxLen)
        {
            throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                LOG_PARAM + param + LOG_SEPERATE + fieldName + "超过最大长度");
        }
        // 最小长度校验
        int minLen = paramAnno.minLength();
        if (minLen != -1 && String.valueOf(fieldValue).length() < minLen)
        {
            throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                LOG_PARAM + param + LOG_SEPERATE + fieldName + "长度不足");
        }
        // 正则匹配校验
        String regex = paramAnno.regex();
        if (!StringUtils.isEmpty(regex))
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(String.valueOf(fieldValue));
            if (!matcher.matches())
            {
                throw new ValidateException(ResultCode.INVALID_PARAM.getRetCode(), ResultCode.INVALID_PARAM.getRetMsg(),
                    LOG_PARAM + param + LOG_SEPERATE + fieldName + "格式不正确");
            }
        }
    }
    
}
