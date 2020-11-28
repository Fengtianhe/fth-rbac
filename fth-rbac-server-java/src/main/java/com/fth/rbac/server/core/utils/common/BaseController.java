package com.fth.rbac.server.core.utils.common;

import com.fth.rbac.server.core.exception.CommonException;
import com.fth.rbac.server.core.exception.ExceptionCode;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author fengtianhe
 */
public class BaseController {
    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    protected void validateData(Object req) throws CommonException {
        String validateMsg = validate(req);
        if (!stringIsEmpty(validateMsg)) {
            logger.error("请求参数验证失败,详情：{}", validateMsg);
            throw new CommonException(new ExceptionCode(401, "请求参数验证失败,详情：" + validateMsg));
        }
        logger.info("参数校验成功");
    }

    /**
     * 验证数据
     * <p>
     * 对数据的合法性进行校验 ,如果返回空，则校验通过，否则输出错误描述
     *
     * @param object
     * @return
     */
    private static String validate(Object object) {
        Validator validator = new Validator();
        List<ConstraintViolation> validateList = validator.validate(object);
        if ((null == validateList) || validateList.isEmpty()) {
            return null;
        }
        return buildValidateResult(validateList);
    }

    /**
     * 构建校验结果
     */
    private static String buildValidateResult(List<ConstraintViolation> validateList) {
        StringBuffer sbBuffer = new StringBuffer();
        for (ConstraintViolation val : validateList) {
            sbBuffer.append(val.getMessage());
            sbBuffer.append("，");
        }
        sbBuffer.deleteCharAt(sbBuffer.length() - 1);
        String ValidatorMsg = sbBuffer.toString();
        return ValidatorMsg;
    }

    /**
     * 判断是否为空字符串最优代码
     *
     * @param str
     * @return 如果为空，则返回true
     */
    private static boolean stringIsEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}