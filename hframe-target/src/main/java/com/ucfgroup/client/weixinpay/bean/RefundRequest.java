package com.ucfgroup.client.weixinpay.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.hframe.common.util.message.*;
import com.ucfgroup.client.weixinpay.*;

@XStreamAlias("xml")
public class RefundRequest   {

	@XStreamAlias("appid")
	private String appid;
	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("device_info")
	private String deviceInfo;
	@XStreamAlias("refund_fee_type")
	private String refundFeeType;
	@XStreamAlias("op_user_id")
	private String opUserId;
	@XStreamAlias("out_refund_no")
	private String outRefundNo;
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	@XStreamAlias("refund_fee")
	private String refundFee;
	@XStreamAlias("total_fee")
	private String totalFee;
	@XStreamAlias("transaction_id")
	private String transactionId;
	@XStreamAlias("sign")
	private String sign;
	@XStreamOmitField
	private boolean converted;

    public RefundRequest() {
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

	public String getRefundFeeType() {
			return refundFeeType;
	}

	private void setRefundFeeType(String refundFeeType) {
			this.refundFeeType = refundFeeType;
	}

	public String getOpUserId() {
			return opUserId;
	}

	private void setOpUserId(String opUserId) {
			this.opUserId = opUserId;
	}

	public String getSign() {
			return sign;
	}

	private void setSign(String sign) {
			this.sign = sign;
	}

	public RefundRequest convert()  throws Exception{
			if(!converted) {
			   String beforeInfo = XmlUtils.writeValueAsString(this);
			   System.out.println(beforeInfo);
			   converted = true;
			   appid=WeixinpayConfig.getInstance().getAppid();
			   mchId=WeixinpayConfig.getInstance().getMchId();
			   nonceStr=WeixinpayHelper.md5RandomNumber();
			   deviceInfo=WeixinpayConfig.getInstance().getDeviceInfo();
			   refundFeeType=WeixinpayConfig.getInstance().getRefundFeeType();
			   opUserId=WeixinpayConfig.getInstance().getMchId();
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
	 	 	 
     public String getRefundFee(){
     	return refundFee;
     }

     public void setRefundFee(String refundFee){
     	this.refundFee = refundFee;
     }
	 	 	 
     public String getTotalFee(){
     	return totalFee;
     }

     public void setTotalFee(String totalFee){
     	this.totalFee = totalFee;
     }
	 	 	 
     public String getTransactionId(){
     	return transactionId;
     }

     public void setTransactionId(String transactionId){
     	this.transactionId = transactionId;
     }
	 	 	 
}