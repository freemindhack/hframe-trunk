package com.hframe.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.*;
import com.hframe.domain.model.HfusProgramEntityAttr;
import com.hframe.domain.model.HfusProgramEntityAttr_Example;
import com.hframe.service.interfaces.IHfusProgramEntityAttrSV;

@Controller
@RequestMapping(value = "/hframe/hfusProgramEntityAttr")
public class HfusProgramEntityAttrController   {
    private static final Logger logger = LoggerFactory.getLogger(HfusProgramEntityAttrController.class);

	@Resource
	private IHfusProgramEntityAttrSV iHfusProgramEntityAttrSV;
  




    /**
     * 查询展示常用项目实体属性列表
     * @param hfusProgramEntityAttr
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.html")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfusProgramEntityAttr") HfusProgramEntityAttr hfusProgramEntityAttr,
                                      @ModelAttribute("example") HfusProgramEntityAttr_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfusProgramEntityAttr, example, pagination);
        try{
            ExampleUtils.parseExample(hfusProgramEntityAttr, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfusProgramEntityAttr> list = iHfusProgramEntityAttrSV.getHfusProgramEntityAttrListByExample(example);
            pagination.setTotalCount(iHfusProgramEntityAttrSV.getHfusProgramEntityAttrCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 查询展示常用项目实体属性明细
     * @param hfusProgramEntityAttr
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.html")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfusProgramEntityAttr") HfusProgramEntityAttr hfusProgramEntityAttr){
        logger.debug("request : {},{}", hfusProgramEntityAttr.getHfusProgramEntityAttrId(), hfusProgramEntityAttr);
        try{
            HfusProgramEntityAttr result = iHfusProgramEntityAttrSV.getHfusProgramEntityAttrByPK(hfusProgramEntityAttr.getHfusProgramEntityAttrId());
            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 创建常用项目实体属性
    * @param hfusProgramEntityAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.html")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfusProgramEntityAttr") HfusProgramEntityAttr hfusProgramEntityAttr) {
        logger.debug("request : {}", hfusProgramEntityAttr);
        try {
            int result = iHfusProgramEntityAttrSV.create(hfusProgramEntityAttr);
            if(result > 0) {
                return ResultData.success(hfusProgramEntityAttr);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新常用项目实体属性
    * @param hfusProgramEntityAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.html")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfusProgramEntityAttr") HfusProgramEntityAttr hfusProgramEntityAttr) {
        logger.debug("request : {}", hfusProgramEntityAttr);
        try {
            int result = iHfusProgramEntityAttrSV.update(hfusProgramEntityAttr);
            if(result > 0) {
                return ResultData.success(hfusProgramEntityAttr);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除常用项目实体属性
    * @param hfusProgramEntityAttr
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.html")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfusProgramEntityAttr") HfusProgramEntityAttr hfusProgramEntityAttr) {
        logger.debug("request : {}", hfusProgramEntityAttr);

        try {
            int result = iHfusProgramEntityAttrSV.delete(hfusProgramEntityAttr);
            if(result > 0) {
                return ResultData.success(hfusProgramEntityAttr);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public IHfusProgramEntityAttrSV getIHfusProgramEntityAttrSV(){
		return iHfusProgramEntityAttrSV;
	}
	//setter
	public void setIHfusProgramEntityAttrSV(IHfusProgramEntityAttrSV iHfusProgramEntityAttrSV){
    	this.iHfusProgramEntityAttrSV = iHfusProgramEntityAttrSV;
    }
}
