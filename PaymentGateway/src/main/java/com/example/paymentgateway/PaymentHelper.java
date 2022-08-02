package com.example.paymentgateway;

import java.util.HashMap;

public class PaymentHelper {
    public static String buildParamsForUpiResponse(HashMap<String, String> paymentParams, String apiKey, String tran_id) {
        StringBuffer postParamsBuffer = new StringBuffer();
        for (String key : paymentParams.keySet())
            postParamsBuffer.append(concatParams(key, paymentParams.get(key)));
        postParamsBuffer.append(concatParams("api_key", apiKey));
        postParamsBuffer.append(concatParams("tran_id", tran_id));
        String postParams = (postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&') ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
        return postParams;
    }

    protected static String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    public static HashMap<String, String> convertToQueryStringToHashMap(String source) throws Exception {
        HashMap<String, String> data = new HashMap<>();
        String[] arrParameters = source.split("&");
        for (String tempParameterString : arrParameters) {
            String[] arrTempParameter = tempParameterString.split("=");
            if (arrTempParameter.length >= 2) {
                String parameterKey = arrTempParameter[0];
                String parameterValue = arrTempParameter[1];
                data.put(parameterKey, parameterValue);
            } else {
                String parameterKey = arrTempParameter[0];
                data.put(parameterKey, "");
            }
        }
        return data;
    }
}
