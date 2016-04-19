package com.ucfgroup.client.weixinpay.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.hframe.common.util.message.*;
import com.ucfgroup.client.weixinpay.*;

@XStreamAlias("xml")
public class UnifiedOrderRequest   {

	@XStreamAlias("appid")
	private String appid;
	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("device_info")
	private String deviceInfo;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("trade_type")
	private String tradeType;
	@XStreamAlias("notify_url")
	private String notifyUrl;
	@XStreamAlias("spbill_create_ip")
	private String spbillCreateIp;
	@XStreamAlias("sign")
	private String sign;
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	@XStreamAlias("body")
	private String body;
	@XStreamAlias("detail")
	private String detail;
	@XStreamAlias("attach")
	private String attach;
	@XStreamAlias("goods_tag")
	private String goodsTag;
	@XStreamAlias("limit_pay")
	private String limitPay;
	@XStreamAlias("fee_type")
	private String feeType;
	@XStreamAlias("total_fee")
	private String totalFee;
	@XStreamAlias("time_start")
	private String timeStart;
	@XStreamAlias("time_expire")
	private String timeExpire;
	@XStreamOmitField
	private boolean converted;

    public UnifiedOrderRequest() {
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

	public String getDeviceInfo() {
			return deviceInfo;
	}

	private void setDeviceInfo(String deviceInfo) {
			this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
			return nonceStr;
	}

	private void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
	}

	public String getTradeType() {
			return tradeType;
	}

	private void setTradeType(String tradeType) {
			this.tradeType = tradeType;
	}

	public String getNotifyUrl() {
			return notifyUrl;
	}

	private void setNotifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
	}

	public String getSign() {
			return sign;
	}

	private void setSign(String sign) {
			this.sign = sign;
	}

	public String getGoodsTag() {
			return goodsTag;
	}

	private void setGoodsTag(String goodsTag) {
			this.goodsTag = goodsTag;
	}

	public String getLimitPay() {
			return limitPay;
	}

	private void setLimitPay(String limitPay) {
			this.limitPay = limitPay;
	}

	public String getFeeType() {
			return feeType;
	}

	private void setFeeType(String feeType) {
			this.feeType = feeType;
	}

	public UnifiedOrderRequest convert()  throws Exception{
			if(!converted) {
			   String beforeInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(beforeInfo);
			   converted = true;
			   appid=WeixinpayConfig.getInstance().getAppid();
			   mchId=WeixinpayConfig.getInstance().getMchId();
			   deviceInfo=WeixinpayConfig.getInstance().getDeviceInfo();
			   nonceStr=WeixinpayHelper.md5RandomNumber();
			   tradeType=WeixinpayConfig.getInstance().getTradeType();
			   notifyUrl=WeixinpayConfig.getInstance().getUnifiedorderCallback();
			   sign=WeixinpayHelper.encryptSign(this);
			   goodsTag=WeixinpayConfig.getInstance().getGoodsTag();
			   limitPay=WeixinpayConfig.getInstance().getLimitPay();
			   feeType=WeixinpayConfig.getInstance().getFeeType();
			   String afterInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(afterInfo);
			}
			return this;
	}

  
 	 	 	 	 	 	 	 	 
     public String getSpbillCreateIp(){
     	return spbillCreateIp;
     }

     public void setSpbillCreateIp(String spbillCreateIp){
     	this.spbillCreateIp = spbillCreateIp;
     }
	 	 	 	 
     public String getOutTradeNo(){
     	return outTradeNo;
     }

     public void setOutTradeNo(String outTradeNo){
     	this.outTradeNo = outTradeNo;
     }
	 	 	 
     public String getBody(){
     	return body;
     }

     public void setBody(String body){
     	this.body = body;
     }
	 	 	 
     public String getDetail(){
     	return detail;
     }

     public void setDetail(String detail){
     	this.detail = detail;
     }
	 	 	 
     public String getAttach(){
     	return attach;
     }

     public void setAttach(String attach){
     	this.attach = attach;
     }
	 	 	 	 	 	 
     public String getTotalFee(){
     	return totalFee;
     }

     public void setTotalFee(String totalFee){
     	this.totalFee = totalFee;
     }
	 	 	 
     public String getTimeStart(){
     	return timeStart;
     }

     public void setTimeStart(String timeStart){
     	this.timeStart = timeStart;
     }
	 	 	 
     public String getTimeExpire(){
     	return timeExpire;
     }

     public void setTimeExpire(String timeExpire){
     	this.timeExpire = timeExpire;
     }
	 	 
}