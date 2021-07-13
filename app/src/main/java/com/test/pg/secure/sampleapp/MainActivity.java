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
        pgPaymentParams.setAPiKey("79e111fb-098d-4730-8c3a-17fe0c30738a");
        pgPaymentParams.setAmount("2300");
        pgPaymentParams.setEmail("nvnkumar398@gmail.com");
        pgPaymentParams.setName("Dragon Booster");
        pgPaymentParams.setPhone("8248350384");
        pgPaymentParams.setOrderId("Nav2848");
        pgPaymentParams.setCurrency("INR");
        pgPaymentParams.setDescription("Dragon city's protector");
        pgPaymentParams.setCity("Chennai");
        pgPaymentParams.setState("TamilNadu");
        pgPaymentParams.setAddressLine1("No 28/39 Muthiah 2nd Street");
        pgPaymentParams.setAddressLine2("No 28/39 Muthiah 2nd Street");
        pgPaymentParams.setZipCode("600014");
        pgPaymentParams.setCountry("India");
        pgPaymentParams.setReturnUrl("http://159.65.148.139/rest/mcpayment/pgdetails");
        pgPaymentParams.setReturnUrlFailure("http://159.65.148.139/rest/mcpayment/pgdetails");
        pgPaymentParams.setReturnUrlCancel("http://159.65.148.139/rest/mcpayment/pgdetails");
        pgPaymentParams.setMode("TEST");
        pgPaymentParams.setUdf1("udf1");
        pgPaymentParams.setUdf2("udf2");
        pgPaymentParams.setUdf3("udf3");
        pgPaymentParams.setUdf4("udf4");
        pgPaymentParams.setUdf5("udf5");
//        pgPaymentParams.setEnableAutoRefund(inputParameters.get(PaymentDefaults.enableAutoRefund));
//        pgPaymentParams.setOfferCode(inputParameters.get(PaymentDefaults.offerCode));
//        pgPaymentParams.setSplitInfo(inputParameters.get(PaymentDefaults.splitInfo));

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
               // paymentListener.OnPaymentCancelled("Transaction failed");
            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
