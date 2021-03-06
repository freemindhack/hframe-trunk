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
import com.hframe.domain.model.HfpmFieldShowType;
import com.hframe.domain.model.HfpmFieldShowType_Example;
import com.hframe.service.interfaces.IHfpmFieldShowTypeSV;

@Controller
@RequestMapping(value = "/hframe/hfpmFieldShowType")
public class HfpmFieldShowTypeController   {
    private static final Logger logger = LoggerFactory.getLogger(HfpmFieldShowTypeController.class);

	@Resource
	private IHfpmFieldShowTypeSV iHfpmFieldShowTypeSV;
  




    /**
     * 查询展示列展示类型列表
     * @param hfpmFieldShowType
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.html")
    @ResponseBody
    public ResultData list(@ModelAttribute("hfpmFieldShowType") HfpmFieldShowType hfpmFieldShowType,
                                      @ModelAttribute("example") HfpmFieldShowType_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", hfpmFieldShowType, example, pagination);
        try{
            ExampleUtils.parseExample(hfpmFieldShowType, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< HfpmFieldShowType> list = iHfpmFieldShowTypeSV.getHfpmFieldShowTypeListByExample(example);
            pagination.setTotalCount(iHfpmFieldShowTypeSV.getHfpmFieldShowTypeCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 查询展示列展示类型明细
     * @param hfpmFieldShowType
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.html")
    @ResponseBody
    public ResultData detail(@ModelAttribute("hfpmFieldShowType") HfpmFieldShowType hfpmFieldShowType){
        logger.debug("request : {},{}", hfpmFieldShowType.getHfpmFieldShowTypeId(), hfpmFieldShowType);
        try{
            HfpmFieldShowType result = iHfpmFieldShowTypeSV.getHfpmFieldShowTypeByPK(hfpmFieldShowType.getHfpmFieldShowTypeId());
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
    * 创建列展示类型
    * @param hfpmFieldShowType
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.html")
    @ResponseBody
    public ResultData create(@ModelAttribute("hfpmFieldShowType") HfpmFieldShowType hfpmFieldShowType) {
        logger.debug("request : {}", hfpmFieldShowType);
        try {
            int result = iHfpmFieldShowTypeSV.create(hfpmFieldShowType);
            if(result > 0) {
                return ResultData.success(hfpmFieldShowType);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新列展示类型
    * @param hfpmFieldShowType
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.html")
    @ResponseBody
    public ResultData update(@ModelAttribute("hfpmFieldShowType") HfpmFieldShowType hfpmFieldShowType) {
        logger.debug("request : {}", hfpmFieldShowType);
        try {
            int result = iHfpmFieldShowTypeSV.update(hfpmFieldShowType);
            if(result > 0) {
                return ResultData.success(hfpmFieldShowType);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除列展示类型
    * @param hfpmFieldShowType
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.html")
    @ResponseBody
    public ResultData delete(@ModelAttribute("hfpmFieldShowType") HfpmFieldShowType hfpmFieldShowType) {
        logger.debug("request : {}", hfpmFieldShowType);

        try {
            int result = iHfpmFieldShowTypeSV.delete(hfpmFieldShowType);
            if(result > 0) {
                return ResultData.success(hfpmFieldShowType);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public IHfpmFieldShowTypeSV getIHfpmFieldShowTypeSV(){
		return iHfpmFieldShowTypeSV;
	}
	//setter
	public void setIHfpmFieldShowTypeSV(IHfpmFieldShowTypeSV iHfpmFieldShowTypeSV){
    	this.iHfpmFieldShowTypeSV = iHfpmFieldShowTypeSV;
    }
}
