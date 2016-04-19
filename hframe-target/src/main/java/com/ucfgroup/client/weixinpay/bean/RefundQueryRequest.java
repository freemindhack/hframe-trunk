package com.ucfgroup.client.weixinpay.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.hframe.common.util.message.*;
import com.ucfgroup.client.weixinpay.*;

@XStreamAlias("xml")
public class RefundQueryRequest   {

	@XStreamAlias("appid")
	private String appid;
	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("device_info")
	private String deviceInfo;
	@XStreamAlias("out_refund_no")
	private String outRefundNo;
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	@XStreamAlias("refund_id")
	private String refundId;
	@XStreamAlias("transaction_id")
	private String transactionId;
	@XStreamAlias("sign")
	private String sign;
	@XStreamOmitField
	private boolean converted;

    public RefundQueryRequest() {
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

	public String getDeviceInfo() {
			return deviceInfo;
	}

	private void setDeviceInfo(String deviceInfo) {
			this.deviceInfo = deviceInfo;
	}

	public String getSign() {
			return sign;
	}

	private void setSign(String sign) {
			this.sign = sign;
	}

	public RefundQueryRequest convert()  throws Exception{
			if(!converted) {
			   String beforeInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(beforeInfo);
			   converted = true;
			   appid=WeixinpayConfig.getInstance().getAppid();
			   mchId=WeixinpayConfig.getInstance().getMchId();
			   nonceStr=WeixinpayHelper.md5RandomNumber();
			   deviceInfo=WeixinpayConfig.getInstance().getDeviceInfo();
			   sign=WeixinpayHelper.encryptSign(this);
			   String afterInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(afterInfo);
			}
			return this;
	}

  
 	 	 	 	 	 	 
     public String getOutRefundNo(){
     	return outRefundNo;
     }

     public void setOutRefundNo(String outRefundNo){
     	this.outRefundNo = outRefundNo;
     }
	 	 	 
     public String getOutTradeNo(){
     	return outTradeNo;
     }

     public void setOutTradeNo(String outTradeNo){
     	this.outTradeNo = outTradeNo;
     }
	 	 	 
     public String getRefundId(){
     	return refundId;
     }

     public void setRefundId(String refundId){
     	this.refundId = refundId;
     }
	 	 	 
     public String getTransactionId(){
     	return transactionId;
     }

     public void setTransactionId(String transactionId){
     	this.transactionId = transactionId;
     }
	 	 	 
}