package com.test.pg.secure.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.paymentgateway.PGConstants;
import com.example.paymentgateway.PaymentGatewayPaymentInitializer;
import com.example.paymentgateway.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;





public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PaymentParams pgPaymentParams = new PaymentParams();
        pgPaymentParams.setAPiKey("");
        pgPaymentParams.setSaltKey("");
        pgPaymentParams.setAmount("2300");
        pgPaymentParams.setEmail("XXXXXXX");
        pgPaymentParams.setName("YYYYYYYY");
        pgPaymentParams.setPhone("ZZZZZZZZ");
        pgPaymentParams.setOrderId("Nav2848");
        pgPaymentParams.setCurrency("INR");
        pgPaymentParams.setDescription("Dragon city's protector");
        pgPaymentParams.setCity("XXXXXX");
        pgPaymentParams.setState("YYYYYY");
        pgPaymentParams.setAddressLine1("ZZZZZZZZ");
        pgPaymentParams.setAddressLine2("ZZZZZZZZ");
        pgPaymentParams.setZipCode("XXXXXX");
        pgPaymentParams.setCountry("YYYYY");
        pgPaymentParams.setReturnUrl("YOUR_RETURN_URL");
        pgPaymentParams.setReturnUrlFailure("YOUR_RETURN_URL");
        pgPaymentParams.setReturnUrlCancel("YOUR_RETURN_URL");
        pgPaymentParams.setMode("TEST");
        pgPaymentParams.setUdf1("udf1");
        pgPaymentParams.setUdf2("udf2");
        pgPaymentParams.setUdf3("udf3");
        pgPaymentParams.setUdf4("udf4");
        pgPaymentParams.setUdf5("udf5");
        pgPaymentParams.setEnableAutoRefund(inputParameters.get(PaymentDefaults.enableAutoRefund));
        pgPaymentParams.setOfferCode(inputParameters.get(PaymentDefaults.offerCode));
        pgPaymentParams.setSplitInfo(inputParameters.get(PaymentDefaults.splitInfo));

        PaymentGatewayPaymentInitializer pgPaymentInitialzer = new PaymentGatewayPaymentInitializer(pgPaymentParams,MainActivity.this);
        pgPaymentInitialzer.initiatePaymentProcess();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PGConstants.REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                try{
                    String paymentResponse=data.getStringExtra(PGConstants.PAYMENT_RESPONSE);
                    System.out.println("paymentResponse: "+paymentResponse);
                    if(paymentResponse.equals("null")){
                        System.out.println("Transaction Error!");
                    }else{

                        JSONObject response = new JSONObject(paymentResponse);
                        if (response.get("statusCode") == "0") {

                        }else {

                        }

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
