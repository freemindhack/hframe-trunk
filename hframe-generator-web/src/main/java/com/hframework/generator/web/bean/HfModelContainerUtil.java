package com.hframework.generator.web.bean;

import com.hframework.common.util.CommonUtils;
import com.hframework.common.util.StringUtils;
import com.hframe.domain.model.*;

import java.util.*;

/**
 * Created by zhangqh6 on 2015/10/25.
 */
public class HfModelContainerUtil {


    public static HfModelContainer[] mergerModelContainer(
            HfModelContainer dbModelContainer, HfModelContainer targetModelContainer){
        HfModelContainer[] result = new HfModelContainer[2];
        HfModelContainer  addModelContainer = new HfModelContainer();
        addModelContainer.setContainerType(HfModelContainer.TYPE_ADD);
        result[0] = addModelContainer;
        HfModelContainer  modModelContainer = new HfModelContainer();
        modModelContainer.setContainerType(HfModelContainer.TYPE_MOD);
        result[1] = modModelContainer;

        long oldProgramId = -1;
        long oldModelId = -1;

        HfpmProgram dbModelContainerProgram = dbModelContainer.getProgram();
        HfpmProgram targetModelContainerProgram = targetModelContainer.getProgram();
        if(dbModelContainerProgram == null) {
            addModelContainer.setProgram(targetModelContainerProgram);
        }else {
            oldProgramId = dbModelContainerProgram.getHfpmProgramId();
            if(!targetModelContainerProgram.getHfpmProgramName().equals(dbModelContainerProgram.getHfpmProgramName())){
                dbModelContainerProgram.setHfpmProgramName(targetModelContainerProgram.getHfpmProgramName());
                modModelContainer.setProgram(dbModelContainerProgram);
            }
        }

        Map<String ,HfpmModule> dbModuleMap = new HashMap<String, HfpmModule>();
        for (HfpmModule module : dbModelContainer.getModuleMap().values()) {
            dbModuleMap.put(module.getHfpmModuleCode(),module);
        }

        for (HfpmModule targetModule : targetModelContainer.getModuleMap().values()) {
            HfpmModule dbModule = dbModuleMap.get(targetModule.getHfpmModuleCode());
            if(dbModule == null) {
                addModelContainer.getModuleMap().put(targetModule.getHfpmModuleId(),targetModule);
            }else {
                oldModelId = dbModule.getHfpmModuleId();
                if(!dbModule.getHfpmModuleName().equals(targetModule.getHfpmModuleName())) {
                    dbModule.setHfpmModuleName(targetModule.getHfpmModuleName());
                    //这里需要关系是新的值，还是老的值
                    modModelContainer.getModuleMap().put(targetModule.getHfpmModuleId(),dbModule);
                }
            }
            if(oldProgramId != -1) {
                targetModule.setHfpmProgramId(oldProgramId);
            }

        }

        Map<String, HfmdEntity> dbEntityMap = dbModelContainer.getEntityMap();
        Map<String, HfmdEntity> targetEntityMap = targetModelContainer.getEntityMap();

        Map<String, HfmdEntity> addEntityMap = new HashMap<String, HfmdEntity>();
        addModelContainer.setEntityMap(addEntityMap);
        Map<String, HfmdEntity> modEntityMap = new HashMap<String, HfmdEntity>();
        modModelContainer.setEntityMap(modEntityMap);
        for (String entityCode : targetEntityMap.keySet()) {
            HfmdEntity targetEntity = targetEntityMap.get(entityCode);
            if(dbEntityMap == null) {
                addModelContainer.getEntityMap().put(entityCode, targetEntity);
                continue;
            }
            HfmdEntity dbEntity = dbEntityMap.get(entityCode);
            if(dbEntity == null) {
                addModelContainer.getEntityMap().put(entityCode,targetEntity);
            }else if (targetEntity.getHfmdEntityName() != null &&
                    !targetEntity.getHfmdEntityName().equals(dbEntity.getHfmdEntityName())) {
                dbEntity.setHfmdEntityName(targetEntity.getHfmdEntityName());
                modModelContainer.getEntityMap().put(entityCode,dbEntity);
            }

            if(oldProgramId != -1) {
                targetEntity.setHfpmProgramId(oldProgramId);
                targetEntity.setHfpmModuleId(oldModelId);

            }
        }

        Map<String,HfmdEntityAttr> dbEntityAttrMap = dbModelContainer.getEntityAttrMap();
        Map<String,HfmdEntityAttr> targetEntityAttrMap = targetModelContainer.getEntityAttrMap();

        Map<String,HfmdEntityAttr> addEntityAttrMap = new HashMap<String, HfmdEntityAttr>();
        addModelContainer.setEntityAttrMap(addEntityAttrMap);
        Map<String,HfmdEntityAttr> modEntityAttrMap = new HashMap<String, HfmdEntityAttr>();
        modModelContainer.setEntityAttrMap(modEntityAttrMap);

        for (String emtityAttrCode : targetEntityAttrMap.keySet()) {
            HfmdEntityAttr targetEntityAttr = targetEntityAttrMap.get(emtityAttrCode);
            if(dbEntityAttrMap == null) {
                addModelContainer.getEntityAttrMap().put(emtityAttrCode, targetEntityAttr);
                continue;
            }
            HfmdEntityAttr dbEntityAttr = dbEntityAttrMap.get(emtityAttrCode);
            if(dbEntityAttr == null) {
                addModelContainer.getEntityAttrMap().put(emtityAttrCode,targetEntityAttr);
            }else if(dbEntityAttr.getAttrType() == null ) {
                System.out.println();
            }else if (dbEntityAttr.getHfmdEntityAttrName() != null
                    && !dbEntityAttr.getHfmdEntityAttrName().equals(targetEntityAttr.getHfmdEntityAttrName())) {
                dbEntityAttr.setHfmdEntityAttrName(targetEntityAttr.getHfmdEntityAttrName());
                dbEntityAttr.setIspk(targetEntityAttr.getIspk());
                dbEntityAttr.setAttrType(targetEntityAttr.getAttrType());
                dbEntityAttr.setPri(targetEntityAttr.getPri());
                dbEntityAttr.setSize(targetEntityAttr.getSize());
                dbEntityAttr.setNullable(targetEntityAttr.getNullable());
                modModelContainer.getEntityAttrMap().put(emtityAttrCode,dbEntityAttr);
            }else if (!dbEntityAttr.getIspk().equals(targetEntityAttr.getIspk())) {
                dbEntityAttr.setHfmdEntityAttrName(targetEntityAttr.getHfmdEntityAttrName());
                dbEntityAttr.setIspk(targetEntityAttr.getIspk());
                dbEntityAttr.setAttrType(targetEntityAttr.getAttrType());
                dbEntityAttr.setPri(targetEntityAttr.getPri());
                dbEntityAttr.setSize(targetEntityAttr.getSize());
                dbEntityAttr.setNullable(targetEntityAttr.getNullable());
                modModelContainer.getEntityAttrMap().put(emtityAttrCode,dbEntityAttr);
            }else if (!dbEntityAttr.getAttrType().equals(targetEntityAttr.getAttrType())) {
                dbEntityAttr.setHfmdEntityAttrName(targetEntityAttr.getHfmdEntityAttrName());
                dbEntityAttr.setIspk(targetEntityAttr.getIspk());
                dbEntityAttr.setAttrType(targetEntityAttr.getAttrType());
                dbEntityAttr.setPri(targetEntityAttr.getPri());
                dbEntityAttr.setSize(targetEntityAttr.getSize());
                dbEntityAttr.setNullable(targetEntityAttr.getNullable());
                modModelContainer.getEntityAttrMap().put(emtityAttrCode,dbEntityAttr);
            }else if (!(dbEntityAttr.getPri().compareTo(targetEntityAttr.getPri()) == 0)) {
                dbEntityAttr.setHfmdEntityAttrName(targetEntityAttr.getHfmdEntityAttrName());
                dbEntityAttr.setIspk(targetEntityAttr.getIspk());
                dbEntityAttr.setAttrType(targetEntityAttr.getAttrType());
                dbEntityAttr.setPri(targetEntityAttr.getPri());
                dbEntityAttr.setSize(targetEntityAttr.getSize());
                dbEntityAttr.setNullable(targetEntityAttr.getNullable());
                modModelContainer.getEntityAttrMap().put(emtityAttrCode,dbEntityAttr);
            }else if ((dbEntityAttr.getSize() != null && !dbEntityAttr.getSize().equals(targetEntityAttr.getSize()))) {
                dbEntityAttr.setHfmdEntityAttrName(targetEntityAttr.getHfmdEntityAttrName());
                dbEntityAttr.setIspk(targetEntityAttr.getIspk());
                dbEntityAttr.setAttrType(targetEntityAttr.getAttrType());
                dbEntityAttr.setPri(targetEntityAttr.getPri());
                dbEntityAttr.setSize(targetEntityAttr.getSize());
                dbEntityAttr.setNullable(targetEntityAttr.getNullable());
                modModelContainer.getEntityAttrMap().put(emtityAttrCode,dbEntityAttr);
            }else if (!dbEntityAttr.getNullable().equals(targetEntityAttr.getNullable())) {
                dbEntityAttr.setHfmdEntityAttrName(targetEntityAttr.getHfmdEntityAttrName());
                dbEntityAttr.setIspk(targetEntityAttr.getIspk());
                dbEntityAttr.setAttrType(targetEntityAttr.getAttrType());
                dbEntityAttr.setPri(targetEntityAttr.getPri());
                dbEntityAttr.setSize(targetEntityAttr.getSize());
                dbEntityAttr.setNullable(targetEntityAttr.getNullable());
                modModelContainer.getEntityAttrMap().put(emtityAttrCode,dbEntityAttr);
            }

            HfmdEntity hfmdEntity = dbEntityMap.get(emtityAttrCode.split("\\.")[0].trim());
            if(oldProgramId != -1 && hfmdEntity != null) {
                targetEntityAttr.setHfpmProgramId(hfmdEntity.getHfpmProgramId());
                targetEntityAttr.setHfpmModuleId(hfmdEntity.getHfpmModuleId());
                targetEntityAttr.setHfmdEntityId(hfmdEntity.getHfmdEntityId());
            }
            if(StringUtils.isBlank(targetEntityAttr.getHfmdEntityAttrName())) {
                targetEntityAttr.setHfmdEntityAttrName("");
            }
        }
        return result;
    }

    public static HfModelContainer[] mergerEntityToDataSet(HfModelContainer[] resultModelContainers, HfModelContainer dbModelContainer) {

        if(resultModelContainers == null) {
            return null;
        }

        HfModelContainer addModelContainer = resultModelContainers[0];
        mergerModelContainerSelf(addModelContainer, dbModelContainer);

        HfModelContainer modModelContainer = resultModelContainers[1];
        mergerModelContainerSelf(modModelContainer, dbModelContainer);


        return resultModelContainers;
    }

    private static void mergerModelContainerSelf(HfModelContainer modelContainer, HfModelContainer dbModelContainer) {
        if (modelContainer == null) {
            return;
        }

        Map<String, HfmdEntity> entityMap = modelContainer.getEntityMap();
        Map<String, HfmdEntityAttr> entityAttrMap = modelContainer.getEntityAttrMap();

        Map<String, HfpmDataSet> dataSetMap = new HashMap<String, HfpmDataSet>();
        modelContainer.setDataSetMap(dataSetMap);
        if (entityMap != null) {
            for (String entityCode : entityMap.keySet()) {
                HfmdEntity hfmdEntity = entityMap.get(entityCode);
                dataSetMap.put(entityCode,getDataSetFromEntity(hfmdEntity));
                dataSetMap.put(entityCode+"_QDS",getQryDataSetFromEntity(hfmdEntity));

            }
        }

        Map<String, List<HfpmDataField>> dataFieldListMap = new HashMap<String, List<HfpmDataField>>();
        modelContainer.setDataFieldListMap(dataFieldListMap);
        if (entityAttrMap != null) {
            for (String entityAttrEntityCode : entityAttrMap.keySet()) {
                String entityCode = entityAttrEntityCode.substring(0,entityAttrEntityCode.indexOf("."));
                HfmdEntityAttr hfmdEntityAttr = entityAttrMap.get(entityAttrEntityCode);
                if(!dataFieldListMap.containsKey(entityCode)) {
                    dataFieldListMap.put(entityCode,new ArrayList<HfpmDataField>());
                }

                HfpmDataSet dataSet;
                if(dataSetMap.containsKey(entityCode)) {
                    dataSet = dataSetMap.get(entityCode);
                }else {
                    dataSet =  dbModelContainer.getDataSetMap().get(entityCode);
                }

                dataFieldListMap.get(entityCode).add(getDataFieldFromEntityAttr(hfmdEntityAttr,dataSet));

                if(!dataFieldListMap.containsKey(entityCode+"_QDS")) {
                    dataFieldListMap.put(entityCode+"_QDS",new ArrayList<HfpmDataField>());
                }

                if(dataSetMap.containsKey(entityCode+"_QDS")) {
                    dataSet = dataSetMap.get(entityCode+"_QDS");
                }else {
                    dataSet =  dbModelContainer.getDataSetMap().get(entityCode+"_QDS");
                }

                dataFieldListMap.get(entityCode+"_QDS").addAll(getDS4QryDataFieldFromEntityAttr(hfmdEntityAttr, dataSet));
            }
        }
    }

    private static HfpmDataSet getDataSetFromEntity(HfmdEntity hfmdEntity) {
        HfpmDataSet hfpmDataSet = new HfpmDataSet( CommonUtils.uuidL(),
                hfmdEntity.getHfmdEntityName()+"【默认】",
                hfmdEntity.getHfmdEntityCode(),
                hfmdEntity.getHfmdEntityId(),
                hfmdEntity.getHfpmProgramId(),
                hfmdEntity.getOpId(),
                hfmdEntity.getCreateTime(),
                hfmdEntity.getModifyOpId(),
                hfmdEntity.getModifyTime(),
                hfmdEntity.getDelFlag());
        return hfpmDataSet;
    }

    private static HfpmDataSet getQryDataSetFromEntity(HfmdEntity hfmdEntity) {
        HfpmDataSet hfpmDataSet = new HfpmDataSet( CommonUtils.uuidL(),
                hfmdEntity.getHfmdEntityName()+"【查询】",
                hfmdEntity.getHfmdEntityCode()+"_DS4Q",
                hfmdEntity.getHfmdEntityId(),
                hfmdEntity.getHfpmProgramId(),
                hfmdEntity.getOpId(),
                hfmdEntity.getCreateTime(),
                hfmdEntity.getModifyOpId(),
                hfmdEntity.getModifyTime(),
                hfmdEntity.getDelFlag());
        return hfpmDataSet;
    }

    private static HfpmDataField getDataFieldFromEntityAttr(HfmdEntityAttr hfmdEntityAttr, HfpmDataSet dataSet) {
        HfpmDataField hfpmDataField= new HfpmDataField();
        hfpmDataField.setHfpmDataFieldId(CommonUtils.uuidL());
        hfpmDataField.setHfpmDataFieldCode(hfmdEntityAttr.getHfmdEntityAttrCode());
        hfpmDataField.setHfpmFieldShowTypeId(getFieldShowTypeIdByEntityAttr(hfmdEntityAttr));
        hfpmDataField.setFieldShowCode(getFieldShowCodeByEntityAttr(hfmdEntityAttr));
        hfpmDataField.setHfmdEntityId(hfmdEntityAttr.getHfmdEntityId());
        hfpmDataField.setHfmdEntityAttrId(hfmdEntityAttr.getHfmdEntityAttrId());
        hfpmDataField.setDataGetMethod(0);
        hfpmDataField.setHfpmDataFieldName(hfmdEntityAttr.getHfmdEntityAttrName());
        hfpmDataField.setHfpmDataSetId(dataSet.getHfpmDataSetId());
        hfpmDataField.setPri(hfmdEntityAttr.getPri());
        hfpmDataField.setOpId(hfmdEntityAttr.getOpId());
        hfpmDataField.setCreateTime(hfmdEntityAttr.getCreateTime());
        hfpmDataField.setModifyOpId(hfmdEntityAttr.getModifyOpId());
        hfpmDataField.setModifyTime(hfmdEntityAttr.getModifyTime());
        hfpmDataField.setDelFlag(hfmdEntityAttr.getDelFlag());

        return hfpmDataField;
    }

    private static List<HfpmDataField> getDS4QryDataFieldFromEntityAttr(HfmdEntityAttr hfmdEntityAttr, HfpmDataSet dataSet) {

        List<HfpmDataField> result = new ArrayList<HfpmDataField>();

        HfpmDataField dataFieldFromEntityAttr = null;
        if("pri".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())) {
            return result;
        }else if("create_time".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())
                || "modify_time".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())) {
            dataFieldFromEntityAttr = getDataFieldFromEntityAttr(hfmdEntityAttr, dataSet);
            dataFieldFromEntityAttr.setHfpmDataFieldName(
                    dataFieldFromEntityAttr.getHfpmDataFieldName().replaceAll("时间","开始时间"));
            dataFieldFromEntityAttr.setHfpmDataFieldCode(
                    dataFieldFromEntityAttr.getHfpmDataFieldCode() + "_GEQ");
            dataFieldFromEntityAttr.setFieldShowCode("222");
            result.add(dataFieldFromEntityAttr);
            dataFieldFromEntityAttr = getDataFieldFromEntityAttr(hfmdEntityAttr, dataSet);
            dataFieldFromEntityAttr.setHfpmDataFieldName(
                    dataFieldFromEntityAttr.getHfpmDataFieldName().replaceAll("时间","结束时间"));
            dataFieldFromEntityAttr.setHfpmDataFieldCode(
                    dataFieldFromEntityAttr.getHfpmDataFieldCode() + "_LEQ");
            dataFieldFromEntityAttr.setFieldShowCode("222");
            result.add(dataFieldFromEntityAttr);
        }else {
            dataFieldFromEntityAttr = getDataFieldFromEntityAttr(hfmdEntityAttr, dataSet);
            dataFieldFromEntityAttr.setFieldShowCode("222");
            result.add(dataFieldFromEntityAttr);
        }

        return result;
    }

    /**
     *  新建/修改：固定枚举值个数大于2个为下拉框,
        新建/修改：固定枚举值个数不大于2个为单选框
        新建/修改：关联配置表下拉框选择
        新建/修改：关联业务表弹出框选择
        新建/修改：默认都为input框
     * @param hfmdEntityAttr
     * @return
     */
    private static String getFieldShowTypeIdByEntityAttr(HfmdEntityAttr hfmdEntityAttr) {

        if(hfmdEntityAttr.getHfmdEnumClassId()!= null && hfmdEntityAttr.getHfmdEnumClassId() > 0) {//枚举值对象
            return "3";//TODO
        }else if(hfmdEntityAttr.getRelHfmdEntityAttrId()!= null && hfmdEntityAttr.getRelHfmdEntityAttrId() > 0){ //外键对象
            return "2";//TODO
        }else {
            return "1";//TODO
        }
    }

    private static String getFieldShowCodeByEntityAttr(HfmdEntityAttr hfmdEntityAttr) {

        if(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase().endsWith("_id") && hfmdEntityAttr.getIspk() == 1) {
            return "011";
        }else if("create_time".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())
                || "op_id".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())) {
            return "011";
        }else if("modify_op_id".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())
                || "modify_time".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())) {
            return "001";
        }else if("del_flag".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())) {
            return "021";
        }else if("pri".equals(hfmdEntityAttr.getHfmdEntityAttrCode().toLowerCase())) {
            return "000";
        }else {
            return "221";
        }
    }
}

