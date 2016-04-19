package com.hframe.generator.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.hframe.common.util.*;
import com.hframe.common.util.message.Dom4jUtils;
import com.hframe.common.util.message.JacksonObjectMapper;
import com.hframe.common.util.message.VelocityUtil;
import com.hframe.generator.bean.*;
import com.hframe.generator.bean.Class;
import com.hframework.generator.thirdplatform.bean.GeneratorConfig;
import com.hframework.generator.thirdplatform.bean.descriptor.Node;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.IOException;
import java.util.*;

/**
 * User: zhangqh6
 * Date: 2016/1/20 16:33:33
 */
public class BeanGeneratorUtil {

    private static final Map<String,String> KEYWORDS= new HashMap<String, String>() {{
        put("interface","interface1");
        put("class","clazz");
    }};

    /**
     * 通过Json数据生成Bean对象
     * @param packagePath
     * @param rootClassName
     * @param jsonString
     */
    public static void generateByJson(String packagePath, String rootClassName, String jsonString, List<Node> ruleNodeList) throws IOException {
        JsonNode jsonNode = JacksonObjectMapper.getInstance().readTree(jsonString);
        Map<String, Object> mergeMap = new HashMap<String, Object>();
        mergeMap.put(rootClassName, parseJsonNode(jsonNode));
        Map<String,Integer> nameRepeatCache = new HashMap<String, Integer>();
        generateClassByJson(packagePath, rootClassName, mergeMap.get(rootClassName), true, nameRepeatCache, ruleNodeList);
    }



//    /**
//     * 通过Xml数据生成Bean对象
//     * @param packagePath
//     * @param rootClassName
//     * @param xmlString
//     */
//    public static void generateByXml(String packagePath, String rootClassName,String rootXmlName, String xmlString) throws IOException {
//        Document document = Dom4jUtils.getDocumentByContent(xmlString);
//        Element root = document.getRootElement();
//        Map<String, Object> mergeMap = new HashMap<String, Object>();
//        mergeMap.put(rootClassName, parseXmlNode(root));
//        generateClassByXml(packagePath, rootClassName, rootXmlName, mergeMap.get(rootClassName), true);
//
//    }


    /**
     * 通过Xml数据获取XmlNode对象
     * @param xmlString
     */
    public static XmlNode getXmlNodeByXml(String xmlString) throws IOException {
        Document document = Dom4jUtils.getDocumentByContent(xmlString);
        Element root = document.getRootElement();

        XmlNode rootXmlNode = parseXmlNodeNew(root);

        rootXmlNode.settingNodeCode();

        Map<String,List<XmlNode>> sameNameNodeMap = rootXmlNode.fetchSameNameNode(new LinkedHashMap<String, List<XmlNode>>());

        XmlNode.XmlNodeHelper.filterSingletonNode(sameNameNodeMap);

        //获取合并规则 TODO
        for (String nodeName : sameNameNodeMap.keySet()) {
            List<XmlNode> xmlNodes = sameNameNodeMap.get(nodeName);
            XmlNode baseNode = xmlNodes.get(0);
            for (int i = 1; i < xmlNodes.size(); i++) {
                baseNode.mergeOutSide(xmlNodes.get(i));
                xmlNodes.get(i).getParentXmlNode().getChildrenXmlNode().set(
                        xmlNodes.get(i).getParentXmlNode().getChildrenXmlNode().indexOf(xmlNodes.get(i)),baseNode);
            }
        }

        return rootXmlNode;
    }

    /**
     * 通过Xml数据生成Bean对象
     * @param descriptor 类生成描述器
     * @param rootClassName
     * @param xmlString
     */
    public static void generateByXml(GenerateDescriptor descriptor, String xmlString, String rootClassName)  throws IOException {
        generateClassByXmlNode(descriptor, rootClassName, getXmlNodeByXml(xmlString), true);
    }

//    /**
//     * 生成类文件
//     * @param packagePath
//     * @param rootClassName
//     * @param rootXmlName
//     * @param data
//     * @param isRoot
//     */
//    private static void generateClassByXml(String packagePath, String rootClassName,String rootXmlName, Object data, boolean isRoot) {
//        com.hframe.generator.bean.Class beanClass = new com.hframe.generator.bean.Class();
//        beanClass.setSrcFilePath("D:\\my_workspace\\hframe-trunk\\hframe-target\\src\\main\\java\\");
//        beanClass.setClassPackage(packagePath);
//        beanClass.setClassName(rootClassName);
//        beanClass.addConstructor();
//        beanClass.addAnnotation("@XStreamAlias(\"" + rootXmlName + "\")");
//
//        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
//        if(data instanceof Map) {
//            dataMap = (Map<String, Object>) data;
//        }else if(data instanceof List){
//            dataMap = (Map<String, Object>) ((List) data).get(0);
//        }else {
//            return ;
//        }
//        beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamAlias");
//        beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamAsAttribute");
//
//        for (String fieldName : dataMap.keySet()) {
//            String subElementName = getSubElementName(dataMap.get(fieldName));
//            Field field = getField(fieldName, dataMap.get(fieldName), subElementName);
//            field.addFieldAnno("@XStreamAlias(\"" + fieldName + "\")");
//
//            beanClass.addField(field);
//            if(!"String".equals(field.getType())) {
//                if(field.getType().startsWith("List<") && !beanClass.getImportClassList().contains("java.util.List")) {
//                    beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamImplicit");
//                    beanClass.addImportClass("java.util.List");
//                    field.addFieldAnno("@XStreamImplicit");
//                }
//                if(isRoot) {
//                    beanClass.addImportClass(packagePath + "." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase() + ".*");
//                }
//
//                if(subElementName != null) {
//                    generateClassByXml(packagePath + (isRoot ? ("." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase()) : ""),
//                            CreatorUtil.getJavaClassName(subElementName), subElementName, dataMap.get(fieldName), false);
//                }else {
//                    generateClassByXml(packagePath + (isRoot ? ("." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase()) : ""),
//                            CreatorUtil.getJavaClassName(fieldName), fieldName, dataMap.get(fieldName), false);
//                }
//
//            }
//        }
//
//        Map map = new HashMap();
//        map.put("CLASS", beanClass);
//        String content = VelocityUtil.produceTemplateContent("com/hframe/generator/vm/poByTemplate.vm", map);
//        System.out.println(content);
//        FileUtils.writeFile(beanClass.getFilePath(), content);
//    }

    /**
     * 生成类文件
     * @param rootClassName
     * @param rootXmlNode
     * @param isRoot
     */
    private static void generateClassByXmlNode(GenerateDescriptor descriptor, String rootClassName, XmlNode rootXmlNode, boolean isRoot) {

        com.hframe.generator.bean.Class beanClass = generateDefaultClassByXmlNode(descriptor,rootClassName,rootXmlNode,isRoot);
        //类扩展处理
        descriptor.execute(beanClass, rootXmlNode);






        List<XmlNode> childrenXmlNode = rootXmlNode.getChildrenXmlNode();
        if(childrenXmlNode != null) {
            for (XmlNode childXmlNode : childrenXmlNode) {
                boolean cascadeFlag = true;
                if(childXmlNode.getChildrenXmlNode().size() == 0 &&
                        (childXmlNode.getAttrMap() == null || childXmlNode.getAttrMap().size() == 0 )) {
                    cascadeFlag = false;
                }
                if(cascadeFlag) {
                    if(isRoot) {
//                        FileUtils.createDir(beanClass.getFilePath().substring(0, beanClass.getFilePath().lastIndexOf("/"))
//                                + "/" + CreatorUtil.getJavaClassName(rootClassName).toLowerCase());
                        beanClass.addImportClass(descriptor.getJavaPackage() + "." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase() + ".*");
                    }
                    GenerateDescriptor newDescriptor = descriptor;
                    try {
                        newDescriptor = (GenerateDescriptor) BeanUtils.cloneBean(descriptor);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    newDescriptor.setJavaPackage(descriptor.getJavaPackage() +
                            (isRoot ? ("." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase()) : ""));
                    generateClassByXmlNode(newDescriptor,
                            CreatorUtil.getJavaClassName(childXmlNode.getNodeName()), childXmlNode, false);
                }
            }
        }

        //注意：这里迭代调用是否存在先后关系 TODO
        Map map = new HashMap();
        map.put("CLASS", beanClass);
        String content = VelocityUtil.produceTemplateContent(descriptor.getTemplatePath(), map);
        System.out.println(content);
        FileUtils.writeFile(beanClass.getFilePath(), content);
    }

    private static Class generateDefaultClassByXmlNode(GenerateDescriptor descriptor, String rootClassName, XmlNode rootXmlNode, boolean isRoot) {
        com.hframe.generator.bean.Class beanClass = new com.hframe.generator.bean.Class();
        beanClass.setSrcFilePath(descriptor.getJavaRootPath());
        beanClass.setClassPackage(descriptor.getJavaPackage());
        beanClass.setClassName(rootClassName);
        beanClass.addConstructor();
        beanClass.addAnnotation("@XStreamAlias(\"" + rootXmlNode.getNodeName() + "\")");

        beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamAlias");

        List<XmlNode> childrenXmlNode = rootXmlNode.getChildrenXmlNode();
        if(childrenXmlNode != null) {
            for (XmlNode childXmlNode : childrenXmlNode) {
                Field field ;
                if(childXmlNode.getChildrenXmlNode().size() == 0 &&
                        (childXmlNode.getAttrMap() == null || childXmlNode.getAttrMap().size() == 0 )) {
                    if(childXmlNode.isSingleton()) {
                        field = new Field("String",
                                CreatorUtil.getJavaVarName(childXmlNode.getNodeName()));
                    }else {
                        field = new Field("List<String>",
                                CreatorUtil.getJavaVarName(childXmlNode.getNodeName()) + "List");
                        beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamImplicit");
                        beanClass.addImportClass("java.util.List");
                        field.addFieldAnno("@XStreamImplicit");
                    }

                }else if(childXmlNode.isSingleton()) {
                    field = new Field(CreatorUtil.getJavaClassName(childXmlNode.getNodeName()),
                            CreatorUtil.getJavaVarName(childXmlNode.getNodeName()));
                }else {
                    field = new Field("List<" + CreatorUtil.getJavaClassName(childXmlNode.getNodeName()) + ">",
                            CreatorUtil.getJavaVarName(childXmlNode.getNodeName()) + "List");
                    beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamImplicit");
                    beanClass.addImportClass("java.util.List");
                    field.addFieldAnno("@XStreamImplicit");
                }
                field.addFieldAnno("@XStreamAlias(\"" + childXmlNode.getNodeName() + "\")");
                beanClass.addField(field);

                if(childXmlNode.isGenerated()) {
                    continue;
                }
                childXmlNode.setGenerated(true);
            }
        }

        Map<String, String> attrMap = rootXmlNode.getAttrMap();
        for (Map.Entry<String, String> entry : attrMap.entrySet()) {
            Field field = new Field("String",
                    CreatorUtil.getJavaVarName(entry.getKey()));
            field.addFieldAnno("@XStreamAsAttribute");
            field.addFieldAnno("@XStreamAlias(\"" + entry.getKey()+ "\")");
            beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamAsAttribute");
            beanClass.addField(field);
        }


        String nodeText = rootXmlNode.getNodeText();
        if(StringUtils.isNotBlank(nodeText)) {
            Field field = new Field("String","text");
            field.setFieldComment("标签内内容");
            beanClass.addAnnotation("@XStreamConverter(value=ToAttributedValueConverter.class, strings={\"text\"})");
            beanClass.addImportClass("com.thoughtworks.xstream.annotations.XStreamConverter");
            beanClass.addImportClass("com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter");
            beanClass.addField(field);
        }
        return beanClass;
    }

    private static Node matchNode(XmlNode childXmlNode, List<Node> ruleNodeList) {
        for (Node node : ruleNodeList) {
            System.out.println(node.getPath() + "--" + childXmlNode.getNodeCode());
            if(PathMatcherUtils.matches(node.getPath(), childXmlNode.getNodeCode())) {
                return node;
            }
        }
        return null;
    }


    /**
     * 获取子元素
     * @param data
     * @return
     */
    private static String getSubElementName(Object data) {
        if(data instanceof List) {
            return (String) ((List) data).get(1);
        }

        return null;
    }


    /**
     * 生成类文件
     * @param packagePath
     * @param rootClassName
     * @param data
     * @param isRoot
     */
    private static void generateClassByJson(String packagePath, String rootClassName, Object data, boolean isRoot, Map<String,Integer> nameRepeatCache,List<Node> ruleNodeList) {
        com.hframe.generator.bean.Class beanClass = new com.hframe.generator.bean.Class();
        beanClass.setSrcFilePath(GeneratorConfig.getInstance().getJavaRootPath());
        beanClass.setClassPackage(packagePath);
        Integer integer = nameRepeatCache.get(rootClassName);
        beanClass.setClassName(rootClassName + (integer==null ? "" : integer));
        nameRepeatCache.put(rootClassName, (integer == null ? 1 : ++integer));
        beanClass.addConstructor();

        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        if(data instanceof Map) {
            dataMap = (Map<String, Object>) data;
        }else if(data instanceof List){
            dataMap = (Map<String, Object>) ((List) data).get(0);
        }else {
            return ;
        }
        beanClass.addImportClass("com.fasterxml.jackson.annotation.JsonProperty");
        for (String fieldName : dataMap.keySet()) {
            Integer integer2 = nameRepeatCache.get(CreatorUtil.getJavaClassName(fieldName));
            Field field = getField(fieldName + (integer2 == null ? "" : integer2), dataMap.get(fieldName));
            field.addFieldAnno("@JsonProperty(\"" + fieldName + "\")");

            beanClass.addField(field);
            if(!"String".equals(field.getType())) {
                if(field.getType().startsWith("List<") && !beanClass.getImportClassList().contains("java.util.List")) {
                       beanClass.addImportClass("java.util.List");
                }
                if(isRoot) {
                    beanClass.addImportClass(packagePath + "." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase() + ".*");
                }

                generateClassByJson(packagePath + (isRoot ? ("." + CreatorUtil.getJavaClassName(rootClassName).toLowerCase()) : ""),
                        CreatorUtil.getJavaClassName(fieldName), dataMap.get(fieldName), false,nameRepeatCache,ruleNodeList);
            }
        }

        Map map = new HashMap();
        map.put("CLASS", beanClass);
        String content = VelocityUtil.produceTemplateContent("com/hframe/generator/vm/poByTemplate.vm", map);
        System.out.println(content);
        FileUtils.writeFile(beanClass.getFilePath(), content);
    }

    /**
     * 获取字段定义
     * @param fieldName
     * @param data
     * @param subElementName
     * @return
     */
    private static Field getField(String fieldName, Object data, String subElementName) {
        if(data instanceof Map) {
            return new Field(CreatorUtil.getJavaClassName(fieldName),CreatorUtil.getJavaVarName(fieldName));
        }else if(data instanceof List){
            if(subElementName != null) {
                return new Field("List<" + CreatorUtil.getJavaClassName(subElementName) + ">", CreatorUtil.getJavaVarName(subElementName) + "List");
            }else {
                return new Field("List<" + CreatorUtil.getJavaClassName(fieldName) + ">", CreatorUtil.getJavaVarName(fieldName) + "List");
            }

        }else {
            return new Field("String",CreatorUtil.getJavaVarName(fieldName));
        }
    }

    /**
     * 获取字段定义
     * @param fieldName
     * @param data
     * @return
     */
    private static Field getField(String fieldName, Object data) {
        if(data instanceof Map) {
            return new Field(CreatorUtil.getJavaClassName(fieldName),CreatorUtil.getJavaVarName(fieldName));
        }else if(data instanceof List){
            return new Field("List<" + CreatorUtil.getJavaClassName(fieldName) + ">", CreatorUtil.getJavaVarName(fieldName) + "List");
        }else {
            return new Field("String",CreatorUtil.getJavaVarName(fieldName));
        }
    }

    private static void generateClass(String packagePath, Map<String, Object> mergeMap) {


    }

    private static boolean checkElementIsArray(Element element) {
        List elements = element.elements();
        if(elements.size() > 1) {
            Element element1 = (Element) elements.get(0);
            Element element2 = (Element) elements.get(1);
            if(element1.getName().equals(element2.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析XML节点信息
     * @param element
     * @return
     */
    @Deprecated
    private static Object parseXmlNode(Element element) {
        if(checkElementIsArray(element)) {
            List result = new ArrayList();
            String xmlElementName = null;
            for (Object o : element.elements()) {
                Element subElement = (Element) o;
                xmlElementName = subElement.getName();//子元素名称
                Map<String, Object> fieldMap = (Map<String, Object>)parseXmlNode(subElement);
                //result列表第一个元素存放子元素信息
                mergeField(result, fieldMap);
            }
            //result列表第二个元素存放子元素节点名称
            result.add(xmlElementName);
            //result列表第二个元素存放元素节点属性
            result.add(getAttrMap(element));
            return result;
        }

        Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
        for (Object o : element.elements()) {
            Element subElement = (Element) o;
            fieldMap.put(subElement.getName(), parseXmlNode(subElement));
        }

        if(fieldMap.size() == 0) {
            return element.getTextTrim();
        }

        return fieldMap;
    }

    /**
     * 解析XML节点信息
     * @param element
     * @return
     */
    private static XmlNode parseXmlNodeNew(Element element) {

        XmlNode xmlNode = new XmlNode();
        xmlNode.setNodeName(element.getName());
        xmlNode.setAttrMap(getAttrMap(element));

        //添加节点属性
        for (Object attr : element.attributes()) {
            Attribute attribute= (Attribute) attr;
            xmlNode.addNodeAttr(attribute.getName(), attribute.getValue());
        }

        //添加子节点信息
        for (Object o : element.elements()) {
            XmlNode subXmlNode = parseXmlNodeNew((Element) o);
            xmlNode.addOrMergeChildNode(subXmlNode);
            subXmlNode.setParentXmlNode(xmlNode);
        }

        if(element.elements().size() == 0) {
            xmlNode.setNodeText(element.getTextTrim());
        }
        return xmlNode;
    }


    private static Map<String,String> getAttrMap(Element element) {
        Map attrMap = new LinkedHashMap();
        for (Object attr : element.attributes()) {
            Attribute attribute= (Attribute) attr;
            attrMap.put(attribute.getName(),attribute.getValue());
        }
        return attrMap;
    }


    /**
     * 解析Json节点信息
     * @param jsonNode
     * @return
     */
    public static Object parseJsonNode(JsonNode jsonNode) {

        if(jsonNode.isArray()) {
            List result = new ArrayList();
            for (JsonNode subNode : jsonNode) {
                Map<String, Object> fieldMap = (Map<String, Object>)parseJsonNode(subNode);
                mergeField(result, fieldMap);
            }
            return result;
        }

        Map<String, Object> fieldMap = new LinkedHashMap<String, Object>();
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            fieldMap.put(field.getKey(), parseJsonNode(field.getValue()));
        }

        if(fieldMap.size() == 0) {
            return jsonNode.asText();
        }
        return fieldMap;
    }

    /**
     * 属性合并
     * @param result
     * @param fieldMap
     */
    private static void mergeField(List result, Map<String, Object> fieldMap) {
        if(result == null || result.size() == 0) {
            result.add(fieldMap);
        }else {
            ((Map<String, Object>) result.get(0)).putAll(fieldMap);
        }
    }


    public static void main(String[] args) throws IOException {
        String rootClassPath = Thread.currentThread().getContextClassLoader ().getResource("").getPath();
//        String jsonString = FileUtils.readFile(rootClassPath + "test.json");
//        generateByJson("com.wechat.bean.request","Menu",jsonString);

        String xmlString = FileUtils.readFile(rootClassPath + "test.xml");

        GenerateDescriptor descriptor = new DefaultGenerateDescriptor();

        generateByXml(descriptor, xmlString, "Descriptor");

    }

    public static class CreatorUtil {
        public static String getJavaClassName(String name) {

            String returnName = "";

            String[] parts = name.split("[_\\-\\.]+");
            for (String part : parts) {
                if (!"".equals(part)) {
                    returnName += part.substring(0, 1).toUpperCase()
                            + part.substring(1);
                }
            }

            return returnName;
        }

        public static String getJavaVarName(String name) {

            String returnName="";

            String[] parts=name.split("[__\\-\\.]+");
            for (String part : parts) {
                if(!"".equals(part)){
                    returnName+=part.substring(0,1).toUpperCase()+part.substring(1);
                }
            }

            String javaVarName = returnName.substring(0, 1).toLowerCase() + returnName.substring(1);

            if(KEYWORDS.containsKey(javaVarName)) {
                return KEYWORDS.get(javaVarName);
            }

            return javaVarName;
        }
    }
}