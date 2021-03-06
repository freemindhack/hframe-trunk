package com.ucfgroup.client.weixinpay.bean;

import com.hframework.common.util.message.XmlUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.ucfgroup.client.weixinpay.*;

/**
 * generated by hframework on 2016-04-22.
 */@XStreamAlias("xml")
public class OrderQueryRequest   {

	@XStreamAlias("appid")
	private String appid;
	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("transaction_id")
	private String transactionId;
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	@XStreamAlias("sign")
	private String sign;
	@XStreamOmitField
	private boolean converted;

    public OrderQueryRequest() {
    }
 	
	public String getAppid() {
			return appid;
	}

	
	private void setAppid(String appid) {
			this.appid = appid;
	}

	
	public String getMchId() {
			return mchId;
	}

	
	private void setMchId(String mchId) {
			this.mchId = mchId;
	}

	
	public String getNonceStr() {
			return nonceStr;
	}

	
	private void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
	}

	
	public String getSign() {
			return sign;
	}

	
	private void setSign(String sign) {
			this.sign = sign;
	}

	
	public OrderQueryRequest convert()  throws Exception{
			if(!converted) {
			   String beforeInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(beforeInfo);
			   converted = true;
			   appid=WeixinpayConfig.getInstance().getAppid();
			   mchId=WeixinpayConfig.getInstance().getMchId();
			   nonceStr=WeixinpayHelper.md5RandomNumber();
			   sign=WeixinpayHelper.encryptSign(this);
			   String afterInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(afterInfo);
			}
			return this;
	}

  
 	 	 	 	 	 
     public String getTransactionId(){
     	return transactionId;
     }

     public void setTransactionId(String transactionId){
     	this.transactionId = transactionId;
     }
	 	 	 
     public String getOutTradeNo(){
     	return outTradeNo;
     }

     public void setOutTradeNo(String outTradeNo){
     	this.outTradeNo = outTradeNo;
     }
	 	 	 
}
