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
import com.hframe.domain.model.HfpmModule;
import com.hframe.domain.model.HfpmModule_Example;
import com.hframe.service.interfaces.IHfpmModuleSV;

@Controller
@RequestMapping(value = "/hframe/hfpmModule")
public class HfpmModuleController   {
    private static final Logger logger = LoggerFactory.getLogger(HfpmModuleController.class);

	@Resource
	private IHfpmModuleSV iHfpmModuleSV;
  




    /**
     * 查询展示模块列表
     * @param hfpmModule
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.html")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfpmModule") HfpmModule hfpmModule,
                                      @ModelAttribute("example") HfpmModule_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfpmModule, example, pagination);
        try{
            ExampleUtils.parseExample(hfpmModule, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfpmModule> list = iHfpmModuleSV.getHfpmModuleListByExample(example);
            pagination.setTotalCount(iHfpmModuleSV.getHfpmModuleCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 查询展示模块明细
     * @param hfpmModule
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.html")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfpmModule") HfpmModule hfpmModule){
        logger.debug("request : {},{}", hfpmModule.getHfpmModuleId(), hfpmModule);
        try{
            HfpmModule result = iHfpmModuleSV.getHfpmModuleByPK(hfpmModule.getHfpmModuleId());
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
    * 创建模块
    * @param hfpmModule
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.html")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfpmModule") HfpmModule hfpmModule) {
        logger.debug("request : {}", hfpmModule);
        try {
            int result = iHfpmModuleSV.create(hfpmModule);
            if(result > 0) {
                return ResultData.success(hfpmModule);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新模块
    * @param hfpmModule
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.html")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfpmModule") HfpmModule hfpmModule) {
        logger.debug("request : {}", hfpmModule);
        try {
            int result = iHfpmModuleSV.update(hfpmModule);
            if(result > 0) {
                return ResultData.success(hfpmModule);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除模块
    * @param hfpmModule
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.html")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfpmModule") HfpmModule hfpmModule) {
        logger.debug("request : {}", hfpmModule);

        try {
            int result = iHfpmModuleSV.delete(hfpmModule);
            if(result > 0) {
                return ResultData.success(hfpmModule);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public IHfpmModuleSV getIHfpmModuleSV(){
		return iHfpmModuleSV;
	}
	//setter
	public void setIHfpmModuleSV(IHfpmModuleSV iHfpmModuleSV){
    	this.iHfpmModuleSV = iHfpmModuleSV;
    }
}
