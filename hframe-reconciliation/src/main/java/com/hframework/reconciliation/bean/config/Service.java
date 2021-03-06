package com.hframework.reconciliation.bean.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * generated by hframework on 2016.
 */@XStreamAlias("service")
public class Service   {

	@XStreamAlias("trigger")
	private Trigger trigger;
	@XStreamAlias("importers")
	private Importers importers;
	@XStreamAlias("ruleCenter")
	private RuleCenter ruleCenter;
	@XStreamAlias("exporters")
	private Exporters exporters;
	@XStreamAsAttribute
    @XStreamAlias("id")
	private String id;

    public Service() {
    }
   
 	 	 
     public Trigger getTrigger(){
     	return trigger;
     }

     public void setTrigger(Trigger trigger){
     	this.trigger = trigger;
     }
	 	 	 
     public Importers getImporters(){
     	return importers;
     }

     public void setImporters(Importers importers){
     	this.importers = importers;
     }
	 	 	 
     public RuleCenter getRuleCenter(){
     	return ruleCenter;
     }

     public void setRuleCenter(RuleCenter ruleCenter){
     	this.ruleCenter = ruleCenter;
     }
	 	 	 
     public Exporters getExporters(){
     	return exporters;
     }

     public void setExporters(Exporters exporters){
     	this.exporters = exporters;
     }
	 	 	 
     public String getId(){
     	return id;
     }

     public void setId(String id){
     	this.id = id;
     }
	 
}
