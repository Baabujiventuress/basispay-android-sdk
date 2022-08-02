package com.test.pg.secure.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paymentgateway.PGConstants;
import com.example.paymentgateway.PaymentGatewayPaymentInitializer;
import com.example.paymentgateway.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentAction();
            }
        });

    }

    private void paymentAction() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        SampleAppConstants.PG_ORDER_ID=Integer.toString(n);

        PaymentParams pgPaymentParams = new PaymentParams();
        pgPaymentParams.setAPiKey(SampleAppConstants.PG_API_KEY);//*required
        pgPaymentParams.setSaltKey(SampleAppConstants.PG_SALT_KEY);//*required
        pgPaymentParams.setAmount(SampleAppConstants.PG_AMOUNT);//*required
        pgPaymentParams.setEmail(SampleAppConstants.PG_EMAIL);//*required
        pgPaymentParams.setName(SampleAppConstants.PG_NAME);//*required
        pgPaymentParams.setPhone(SampleAppConstants.PG_PHONE);//*required
        pgPaymentParams.setOrderId(SampleAppConstants.PG_ORDER_ID);//*required
        pgPaymentParams.setCurrency(SampleAppConstants.PG_CURRENCY);//*required
        pgPaymentParams.setDescription(SampleAppConstants.PG_DESCRIPTION);//*required
        pgPaymentParams.setCity(SampleAppConstants.PG_CITY);//*required
        pgPaymentParams.setState(SampleAppConstants.PG_STATE);//*required
        pgPaymentParams.setAddressLine1(SampleAppConstants.PG_ADD_1);
        pgPaymentParams.setAddressLine2(SampleAppConstants.PG_ADD_2);
        pgPaymentParams.setZipCode(SampleAppConstants.PG_ZIPCODE);//*required
        pgPaymentParams.setCountry(SampleAppConstants.PG_COUNTRY);//*required
        pgPaymentParams.setReturnUrl(SampleAppConstants.PG_RETURN_URL);//*required
        pgPaymentParams.setMode(SampleAppConstants.PG_MODE);//*required
        pgPaymentParams.setUdf1(SampleAppConstants.PG_UDF1);
        pgPaymentParams.setUdf2(SampleAppConstants.PG_UDF2);
        pgPaymentParams.setUdf3(SampleAppConstants.PG_UDF3);
        pgPaymentParams.setUdf4(SampleAppConstants.PG_UDF4);
        pgPaymentParams.setUdf5(SampleAppConstants.PG_UDF5);
        pgPaymentParams.setEnableAutoRefund("n");
        pgPaymentParams.setOfferCode("testcoupon");
        pgPaymentParams.setSplitEnforceStrict("y");
        pgPaymentParams.setInterface_type("android_sdk");//*required

        PaymentGatewayPaymentInitializer pgPaymentInitializer = new PaymentGatewayPaymentInitializer(pgPaymentParams,MainActivity.this);
        pgPaymentInitializer.initiatePaymentProcess();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PGConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String sdkResponse = data.getStringExtra(PGConstants.PAYMENT_RESPONSE);
                    System.out.println("paymentResponse: " + sdkResponse);
                    if (sdkResponse.equals("null")) {
                        System.out.println("Transaction Error!");
                        Toast.makeText(this,"Transaction Error!", Toast.LENGTH_SHORT).show();
                    } else {

                        JSONObject response = new JSONObject(sdkResponse);
                        if (response.has("payment_response") && response.getString("status").equals("success")) {
                            JSONObject paymentResponse = new JSONObject(response.getString("payment_response"));
                            Log.d("payment_response", paymentResponse.toString());
                            String transaction_id = paymentResponse.getString("transaction_id");
                            String response_code = paymentResponse.getString("response_code");
                            String response_message = paymentResponse.getString("response_message");
                            String amount = paymentResponse.getString("amount");
                            String payment_mode = paymentResponse.getString("payment_mode");
                            String payment_channel = paymentResponse.getString("payment_channel");
                            String payment_datetime = paymentResponse.getString("payment_datetime");
                            if (response_code.equals("0")) {
                                //Transaction Success
                                Toast.makeText(this, response_message, Toast.LENGTH_SHORT).show();
                            } else if (response_code.equals("1000")) {
                                //Transaction Failed
                                Toast.makeText(this, response_message, Toast.LENGTH_SHORT).show();
                            } else {
                                //Transaction Cancel
                                Toast.makeText(this, response_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println("Transaction Status: " + response.getString("status"));
                            System.out.println("Transaction Message: " + response.getString("error_message"));
                            Toast.makeText(this,response.getString("error_message"), Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
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
