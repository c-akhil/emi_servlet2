package com.emi;

public class Util {
	public static String JSONStringfyPayment(Payment payment) {
		return "{ \"paymentAmount\":" + (payment.paymentAmount) + ",\"principalAmountPaid\":"
				+ (payment.principalAmountPaid) + ",\"intrestAmountPaid\":" + (payment.intrestAmountPaid)
				+ ", \"loanOutstanding\": " + (payment.loanOutstanding) + "}";
	}
}
