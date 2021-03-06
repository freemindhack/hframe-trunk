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
import com.hframe.domain.model.HfpmPageComponent;
import com.hframe.domain.model.HfpmPageComponent_Example;
import com.hframe.service.interfaces.IHfpmPageComponentSV;

@Controller
@RequestMapping(value = "/hframe/hfpmPageComponent")
public class HfpmPageComponentController   {
    private static final Logger logger = LoggerFactory.getLogger(HfpmPageComponentController.class);

	@Resource
	private IHfpmPageComponentSV iHfpmPageComponentSV;
  




    /**
     * 查询展示页面组件列表
     * @param hfpmPageComponent
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.html")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfpmPageComponent") HfpmPageComponent hfpmPageComponent,
                                      @ModelAttribute("example") HfpmPageComponent_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfpmPageComponent, example, pagination);
        try{
            ExampleUtils.parseExample(hfpmPageComponent, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfpmPageComponent> list = iHfpmPageComponentSV.getHfpmPageComponentListByExample(example);
            pagination.setTotalCount(iHfpmPageComponentSV.getHfpmPageComponentCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 查询展示页面组件明细
     * @param hfpmPageComponent
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.html")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfpmPageComponent") HfpmPageComponent hfpmPageComponent){
        logger.debug("request : {},{}", hfpmPageComponent.getHfpmPageComponentId(), hfpmPageComponent);
        try{
            HfpmPageComponent result = iHfpmPageComponentSV.getHfpmPageComponentByPK(hfpmPageComponent.getHfpmPageComponentId());
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
    * 创建页面组件
    * @param hfpmPageComponent
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.html")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfpmPageComponent") HfpmPageComponent hfpmPageComponent) {
        logger.debug("request : {}", hfpmPageComponent);
        try {
            int result = iHfpmPageComponentSV.create(hfpmPageComponent);
            if(result > 0) {
                return ResultData.success(hfpmPageComponent);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新页面组件
    * @param hfpmPageComponent
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.html")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfpmPageComponent") HfpmPageComponent hfpmPageComponent) {
        logger.debug("request : {}", hfpmPageComponent);
        try {
            int result = iHfpmPageComponentSV.update(hfpmPageComponent);
            if(result > 0) {
                return ResultData.success(hfpmPageComponent);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除页面组件
    * @param hfpmPageComponent
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.html")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfpmPageComponent") HfpmPageComponent hfpmPageComponent) {
        logger.debug("request : {}", hfpmPageComponent);

        try {
            int result = iHfpmPageComponentSV.delete(hfpmPageComponent);
            if(result > 0) {
                return ResultData.success(hfpmPageComponent);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public IHfpmPageComponentSV getIHfpmPageComponentSV(){
		return iHfpmPageComponentSV;
	}
	//setter
	public void setIHfpmPageComponentSV(IHfpmPageComponentSV iHfpmPageComponentSV){
    	this.iHfpmPageComponentSV = iHfpmPageComponentSV;
    }
}
