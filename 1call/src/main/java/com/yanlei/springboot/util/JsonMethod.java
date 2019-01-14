package com.yanlei.springboot.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * 生成Json的公共静态方法 
 * @author x
 * **/
public class JsonMethod {
	/**
	 * 自定义 公共把对象转换成字符串类型的json方法
	 * @param object 需要转换的对象
	 * @param include 是否系列化 
	 * Include.Include.ALWAYS 
	 * 默认 Include.NON_DEFAULT 属性为默认值不序列化
	 *	Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
	 *	Include.NON_NULL 属性为NULL 不序列化 
	 * @return 字符串类型的json
	 * @throws JsonProcessingException
	 */
	public static String toJson(Object object, Include include) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(include);
		return objectMapper.writeValueAsString(object);
	}
	public static ObjectMapper objectMapper;
	  
    /** 
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。 
     * (1)转换为普通JavaBean：readValue(json,Student.class) 
     * (2)转换为List,如List<Student>,将第二个参数传递为Student 
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List 
     *  
     * @param jsonStr 
     * @param valueType 
     * @return 
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        if (objectMapper == null) {  
            objectMapper = new ObjectMapper();
        }  
  
        try {  
            return objectMapper.readValue(jsonStr, valueType);  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
  
        return null;  
    }  
      
    /** 
     * 使用泛型方法，把json字符串转换为相应的Map对象。 
     * (1)转换为普通JavaBean：readValue(json,Student.class) 
     * (2)转换为List,如List<Student>,将第二个参数传递为Student 
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List 
     *  
     * @param jsonStr 
     * @param
     * @return 
     */  
    public static Map<String, Object> readValue(String jsonStr) {
        if (objectMapper == null) {  
            objectMapper = new ObjectMapper();
        }  
  
        try {  
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (Exception e) {
            e.printStackTrace();  
        }  
  
        return null;  
    }  
    /** 
     * json数组转List 
     * @param jsonStr 
     * @param valueTypeRef 
     * @return 
     */  
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
        if (objectMapper == null) {  
            objectMapper = new ObjectMapper();
        }  
  
        try {  
            return objectMapper.readValue(jsonStr, valueTypeRef);  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
  
        return null;  
    }  
  
    /** 
     * 把JavaBean转换为json字符串 
     *  
     * @param object 
     * @return 
     */  
    public static String toJSon(Object object) {
        if (objectMapper == null) {  
            objectMapper = new ObjectMapper();
        }  
  
        try {  
            return objectMapper.writeValueAsString(object);  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
  
        return null;  
    }  
}

