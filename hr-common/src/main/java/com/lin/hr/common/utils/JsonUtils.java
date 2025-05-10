package com.lin.hr.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/17 22:02
 **/
@Slf4j
public class JsonUtils {
    public static SerializerFeature[] FEATURES = new SerializerFeature[]{SerializerFeature.WriteMapNullValue};

    public static String convertObj2Json(Object obj) {
        return JSON.toJSONString(obj, FEATURES);
    }

    public static <T> T convertJson2Obj(String json, Class<T> clazz) {
        try {
            return JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("convertJson2Obj异常, json --> {}", json);
            throw new BusinessException(ResponseCodeEnum.CODE_601);
        }
    }

    public static <T> List<T> convertJsonArray2List(String json, Class<T> clazz) {
        try {
            return JSONArray.parseArray(json, clazz);
        } catch (Exception e) {
            log.error("convertJsonArray2List异常, json --> {}", json, e);
            throw new BusinessException(ResponseCodeEnum.CODE_601);
        }
    }
}
