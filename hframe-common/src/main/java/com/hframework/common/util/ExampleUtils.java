package com.hframework.common.util;

import javafx.scene.control.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangqh6 on 2015/10/18.
 */
public class ExampleUtils {

    private static final Map<String, String> signKeyWordMap = new HashMap<String, String>();

    /*
    EQU - 等于:equal
    NEQ - 不等于:not equal
    LSS - 小于:less than
    LEQ - 小于或等于:equal or less than
    GTR - 大于:greater than
    GEQ - 大于或等于:equal or greater than
     */

    static {
        signKeyWordMap.put("N","IsNull");
        signKeyWordMap.put("NN","IsNotNull");
        signKeyWordMap.put("=","EqualTo");
        signKeyWordMap.put("!=","NotEqualTo");
        signKeyWordMap.put(">","GreaterThan");
        signKeyWordMap.put("<","LessThan");
        signKeyWordMap.put(">=","GreaterThanOrEqualTo");
        signKeyWordMap.put("<=","LessThanOrEqualTo");
        signKeyWordMap.put("IN","In");
        signKeyWordMap.put("NIN","NotIn");
        signKeyWordMap.put("BT","Between");
        signKeyWordMap.put("NBT","NotBetween");
    }


    /**
     * 将一个业务对象转换为Example查询对象
     * @param srcObj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public  static <T> T parseExample(Object srcObj,Class<T> exampleClass) throws InvocationTargetException,
            IllegalAccessException, InstantiationException {
        return (T) parseExample(srcObj,exampleClass.newInstance());
    }


    /**
     * 将一个业务对象转换为Example查询对象
     * @param srcObj
     * @param exampleObj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object parseExample(Object srcObj,Object exampleObj) throws InvocationTargetException, IllegalAccessException {

        // 方法名称及方法
        Map<String, Method> exampleMethods = BeanUtils.getMethods(exampleObj.getClass());

        Map<String, String> convertMap = BeanUtils.convertMap(srcObj, false);
        if(convertMap != null && !convertMap.isEmpty()) {
            for (String filed : convertMap.keySet()) {
                StringBuffer sb = new StringBuffer("and");
                sb.append(StringUtils.upperCaseFirstChar(filed));
                sb.append("EqualTo");

                // 获取对应方法
                Method method = exampleMethods.get(sb.toString());
                if (method != null) {
                    // 对exampleObj对象直接赋值
                    method.invoke(exampleObj, new Object[]{convertMap.get(filed)});
                }
            }
        }

        return exampleObj;
    }

    /**
     * 将参数字符串转换为MAP对象
     * @param params
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, List<String[]>> parseParams2Map(String params) throws InvocationTargetException, IllegalAccessException{
        Map<String, List<String[]>> convertMap = new HashMap<String, List<String[]>>();

        String[] splits = StringUtils.split(params, "&");
        if(splits !=null && splits.length > 0) {
            for (String split : splits) {
                Pattern pattern = Pattern.compile("(>=|<=|>|<|!=|=|NIN|IN|NN|N)");
                Matcher matches = pattern.matcher(split);

                if(matches.find()){
                    String sign = matches.group();
                    if(!convertMap.containsKey(sign)) {
                        convertMap.put(sign,new ArrayList<String[]>());
                    }
                    convertMap.get(sign).add(new String[]{
                            split.substring(0, split.indexOf(sign)).trim(),
                            split.substring(split.indexOf(sign) + sign.length()).trim()});
                }
            }
        }

        return convertMap;
    }

    /**
     * 将参数MAP转换为Example查询对象
     * @param map
     * @param exampleObj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object parseExample(Map<String, List<String[]>> map,Object exampleObj) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Method criteriaMethod = exampleObj.getClass().getMethod("or");
        Object criteriaObj = criteriaMethod.invoke(exampleObj);

        // 方法名称及方法
        Map<String, Method> criteriaMethods = BeanUtils.getMethods(criteriaObj.getClass());
        if (map != null && !map.isEmpty()) {
            for (String sign : map.keySet()) {
                List<String[]> conditions = map.get(sign);
                for (String[] condition : conditions) {
                    String filed = condition[0];
                    String value = condition[1];
                    String methodName = calcExampleMethodName(sign, filed, value);
                    // 获取对应方法
                    Method method = criteriaMethods.get(methodName);
                    if (method != null) {
                        if(method.getParameterTypes()[0] == Long.class) {
                            // 对exampleObj对象直接赋值
                            method.invoke(criteriaObj, new Object[]{Long.valueOf(value)});
                        }else if(method.getParameterTypes()[0] == Integer.class) {
                            // 对exampleObj对象直接赋值
                            method.invoke(criteriaObj, new Object[]{Integer.valueOf(value)});
                        }else if(method.getParameterTypes()[0] == Double.class) {
                            // 对exampleObj对象直接赋值
                            method.invoke(criteriaObj, new Object[]{Double.valueOf(value)});
                        }else {
                            // 对exampleObj对象直接赋值
                            method.invoke(criteriaObj, new Object[]{value});
                        }

                    }
                }
            }
        }

        return exampleObj;
    }

    /**
     * 将查询参数转换为Example查询对象
     * @param params
     * @param exampleObj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object parseExample(String params,Object exampleObj) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Map<String, List<String[]>> convertMap = parseParams2Map(params);

        return parseExample(convertMap,exampleObj);
    }

    private static String calcExampleMethodName(String sign, String filed, String value) {
        StringBuffer sb = new StringBuffer("and");
        sb.append(StringUtils.upperCaseFirstChar(filed));
        sb.append(signKeyWordMap.get(sign));
        return sb.toString();
    }
    /*

    public Criteria andHfpmProgramIdIsNull() {
        addCriterion("hfpm_program_id is null");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdIsNotNull() {
        addCriterion("hfpm_program_id is not null");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdEqualTo(Long value) {
        addCriterion("hfpm_program_id =", value, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdNotEqualTo(Long value) {
        addCriterion("hfpm_program_id <>", value, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdGreaterThan(Long value) {
        addCriterion("hfpm_program_id >", value, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdGreaterThanOrEqualTo(Long value) {
        addCriterion("hfpm_program_id >=", value, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdLessThan(Long value) {
        addCriterion("hfpm_program_id <", value, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdLessThanOrEqualTo(Long value) {
        addCriterion("hfpm_program_id <=", value, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdIn(List<Long> values) {
        addCriterion("hfpm_program_id in", values, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdNotIn(List<Long> values) {
        addCriterion("hfpm_program_id not in", values, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdBetween(Long value1, Long value2) {
        addCriterion("hfpm_program_id between", value1, value2, "hfpmProgramId");
        return (Criteria) this;
    }

    public Criteria andHfpmProgramIdNotBetween(Long value1, Long value2) {
        addCriterion("hfpm_program_id not between", value1, value2, "hfpmProgramId");
        return (Criteria) this;
    }
    */
}
