package com.emi;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EMIDao {

	public static void getEMIList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		EMICalculationRequest requestBody = getRequestBody(request);
		String responseBody = "{ \"statusMessage\":\"Emi Details Fetched\",\"paymentsList\":"
				+ CalculateEMI(requestBody) + "}";
		response.getWriter().write(responseBody);
	}

	public static EMICalculationRequest getRequestBody(HttpServletRequest request) {
		EMICalculationRequest requestBody = new EMICalculationRequest();
		requestBody.totalPrincipal = Float.parseFloat(request.getParameter("totalPrincipal"));
		requestBody.timePeriod = Float.parseFloat(request.getParameter("timePeriod"));
		requestBody.rateOfInterest = ((Float.parseFloat(request.getParameter("rateOfInterest")) / 100)) / 12;
		System.out.println(requestBody.timePeriod + " timePeriod");
		
		return requestBody;
	}

	public static String CalculateEMI(EMICalculationRequest requestBody) {
		String stringResponse = "[";
		for (int index = 1; index <= requestBody.timePeriod; index++) {
			Payment payment = new Payment();
			payment.paymentAmount = (float) ((requestBody.rateOfInterest * requestBody.totalPrincipal)
					* Math.pow((1 + requestBody.rateOfInterest), requestBody.timePeriod)
					/ ((Math.pow((1 + requestBody.rateOfInterest), requestBody.timePeriod)) - 1));
			payment.principalAmountPaid = (float) (payment.paymentAmount
					* Math.pow((1 + requestBody.rateOfInterest), -(1 + requestBody.timePeriod - index)));
			payment.intrestAmountPaid = payment.paymentAmount - payment.principalAmountPaid;
			payment.loanOutstanding = (payment.intrestAmountPaid / requestBody.rateOfInterest)
					- payment.principalAmountPaid;
			stringResponse += (index == 1) ? "" : ",";
			stringResponse += Util.JSONStringfyPayment(payment);
		}
		stringResponse += "]";
		return stringResponse;
	}

}
