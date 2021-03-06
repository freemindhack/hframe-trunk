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
import com.hframe.domain.model.HfmdEnum;
import com.hframe.domain.model.HfmdEnum_Example;
import com.hframe.service.interfaces.IHfmdEnumSV;

@Controller
@RequestMapping(value = "/hframe/hfmdEnum")
public class HfmdEnumController   {
    private static final Logger logger = LoggerFactory.getLogger(HfmdEnumController.class);

	@Resource
	private IHfmdEnumSV iHfmdEnumSV;
  




    /**
     * 查询展示枚举列表
     * @param hfmdEnum
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.html")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum,
                                      @ModelAttribute("example") HfmdEnum_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfmdEnum, example, pagination);
        try{
            ExampleUtils.parseExample(hfmdEnum, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfmdEnum> list = iHfmdEnumSV.getHfmdEnumListByExample(example);
            pagination.setTotalCount(iHfmdEnumSV.getHfmdEnumCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 查询展示枚举明细
     * @param hfmdEnum
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.html")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum){
        logger.debug("request : {},{}", hfmdEnum.getHfmdEnumId(), hfmdEnum);
        try{
            HfmdEnum result = iHfmdEnumSV.getHfmdEnumByPK(hfmdEnum.getHfmdEnumId());
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
    * 创建枚举
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.html")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);
        try {
            int result = iHfmdEnumSV.create(hfmdEnum);
            if(result > 0) {
                return ResultData.success(hfmdEnum);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新枚举
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.html")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);
        try {
            int result = iHfmdEnumSV.update(hfmdEnum);
            if(result > 0) {
                return ResultData.success(hfmdEnum);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除枚举
    * @param hfmdEnum
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.html")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfmdEnum") HfmdEnum hfmdEnum) {
        logger.debug("request : {}", hfmdEnum);

        try {
            int result = iHfmdEnumSV.delete(hfmdEnum);
            if(result > 0) {
                return ResultData.success(hfmdEnum);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public IHfmdEnumSV getIHfmdEnumSV(){
		return iHfmdEnumSV;
	}
	//setter
	public void setIHfmdEnumSV(IHfmdEnumSV iHfmdEnumSV){
    	this.iHfmdEnumSV = iHfmdEnumSV;
    }
}
