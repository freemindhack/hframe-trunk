package com.hframe.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.hframe.domain.model.HfpmProgramCfg;
import com.hframe.domain.model.HfpmProgramCfg_Example;
import com.hframe.dao.HfpmProgramCfgMapper;
import com.hframe.service.interfaces.IHfpmProgramCfgSV;

@Service("iHfpmProgramCfgSV")
public class HfpmProgramCfgSVImpl  implements IHfpmProgramCfgSV {

	@Resource
	private HfpmProgramCfgMapper hfpmProgramCfgMapper;
  
    /**
    * 创建项目配置
    * @param hfpmProgramCfg
    * @return
    * @throws Exception
    */
    public int create(HfpmProgramCfg hfpmProgramCfg) throws Exception {
        return hfpmProgramCfgMapper.insertSelective(hfpmProgramCfg);
    }

    /**
    * 更新项目配置
    * @param hfpmProgramCfg
    * @return
    * @throws Exception
    */
    public int update(HfpmProgramCfg hfpmProgramCfg) throws  Exception {
        return hfpmProgramCfgMapper.updateByPrimaryKeySelective(hfpmProgramCfg);
    }

    /**
    * 通过查询对象更新项目配置
    * @param hfpmProgramCfg
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(HfpmProgramCfg hfpmProgramCfg, HfpmProgramCfg_Example example) throws  Exception {
        return hfpmProgramCfgMapper.updateByExampleSelective(hfpmProgramCfg, example);
    }

    /**
    * 删除项目配置
    * @param hfpmProgramCfg
    * @return
    * @throws Exception
    */
    public int delete(HfpmProgramCfg hfpmProgramCfg) throws  Exception {
        return hfpmProgramCfgMapper.deleteByPrimaryKey(hfpmProgramCfg.getHfpmProgramCfgId());
    }

    /**
    * 删除项目配置
    * @param hfpmProgramCfgId
    * @return
    * @throws Exception
    */
    public int delete(long hfpmProgramCfgId) throws  Exception {
        return hfpmProgramCfgMapper.deleteByPrimaryKey(hfpmProgramCfgId);
    }

    /**
    * 查找所有项目配置
    * @return
    */
    public List<HfpmProgramCfg> getHfpmProgramCfgAll()  throws  Exception {
        return hfpmProgramCfgMapper.selectByExample(new HfpmProgramCfg_Example());
    }

    /**
    * 通过项目配置ID查询项目配置
    * @param hfpmProgramCfgId
    * @return
    * @throws Exception
    */
    public HfpmProgramCfg getHfpmProgramCfgByPK(long hfpmProgramCfgId)  throws  Exception {
        return hfpmProgramCfgMapper.selectByPrimaryKey(hfpmProgramCfgId);
    }

    /**
    * 通过MAP参数查询项目配置
    * @param params
    * @return
    * @throws Exception
    */
    public List<HfpmProgramCfg> getHfpmProgramCfgListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询项目配置
    * @param example
    * @return
    * @throws Exception
    */
    public List<HfpmProgramCfg> getHfpmProgramCfgListByExample(HfpmProgramCfg_Example example) throws  Exception {
        return hfpmProgramCfgMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询项目配置数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getHfpmProgramCfgCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询项目配置数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getHfpmProgramCfgCountByExample(HfpmProgramCfg_Example example) throws  Exception {
        return hfpmProgramCfgMapper.countByExample(example);
    }


  	//getter
 	
	public HfpmProgramCfgMapper getHfpmProgramCfgMapper(){
		return hfpmProgramCfgMapper;
	}
	//setter
	public void setHfpmProgramCfgMapper(HfpmProgramCfgMapper hfpmProgramCfgMapper){
    	this.hfpmProgramCfgMapper = hfpmProgramCfgMapper;
    }
}
