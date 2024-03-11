package com.example.paymentgateway;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PaymentGatewayPaymentActivity extends AppCompatActivity {
    ProgressBar pb;
    WebView webview;

    boolean doubleBackToExitPressedOnce = false;

    String api_key;

    String tran_id;

    String return_url;

    Boolean is_upi_intent_payment = Boolean.valueOf(false);

    Intent upiIntent;

    ActivityResultLauncher<Intent> upiIntentLauncher = registerForActivityResult((ActivityResultContract)new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        public void onActivityResult(ActivityResult result) {
            try {
                String upiPSPResponseString;
                JSONObject pgResponse;
                Intent pspUpiCallbackIntent;
                int resultCode = result.getResultCode();
                Intent data = result.getData();
                switch (resultCode) {
                    case -1:
                        upiPSPResponseString = data.getStringExtra("response");
                        if (upiPSPResponseString != null) {
                            HashMap<String, String> upiResponse = PaymentHelper.convertToQueryStringToHashMap(upiPSPResponseString);
                            if (upiResponse.containsKey("txnRef")) {
                                String upiParamsString = PaymentHelper.buildParamsForUpiResponse(upiResponse, PaymentGatewayPaymentActivity.this.api_key, PaymentGatewayPaymentActivity.this.tran_id);
                                PaymentGatewayPaymentActivity.GetPgUPIResponseTask pgUpiResponseTask = new PaymentGatewayPaymentActivity.GetPgUPIResponseTask();
                                pgUpiResponseTask.execute(new String[] { upiParamsString });
                                break;
                            }
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("status", "failed");
                            jSONObject.put("error_message", upiPSPResponseString);
                            Intent intent = new Intent();
                            intent.putExtra(PGConstants.PAYMENT_RESPONSE, jSONObject.toString());
                            PaymentGatewayPaymentActivity.this.setResult(-1, intent);
                            PaymentGatewayPaymentActivity.this.finish();
                            break;
                        }
                        pgResponse = new JSONObject();
                        pgResponse.put("status", "failed");
                        pgResponse.put("error_message", "No payment response received from PSP!");
                        pspUpiCallbackIntent = new Intent();
                        pspUpiCallbackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                        PaymentGatewayPaymentActivity.this.setResult(-1, pspUpiCallbackIntent);
                        PaymentGatewayPaymentActivity.this.finish();
                        break;
                }
            } catch (Exception ep) {
                ep.printStackTrace();
                try {
                    JSONObject pgResponse = new JSONObject();
                    pgResponse.put("status", "failed");
                    pgResponse.put("error_message", ep.getMessage());
                    Intent pspUpiCallbackIntent = new Intent();
                    pspUpiCallbackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                    PaymentGatewayPaymentActivity.this.setResult(-1, pspUpiCallbackIntent);
                    PaymentGatewayPaymentActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public PaymentGatewayPaymentActivity() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_payment);
        this.webview = (WebView)this.findViewById(R.id.pgPaymentGatewayWebview);
        this.pb = (ProgressBar)this.findViewById(R.id.progressBar);
        this.pb.setVisibility(0);
        String postPaymentRequestParams = this.getIntent().getStringExtra(PGConstants.POST_PARAMS);
        HashMap<String,String> hashParamsMap = (HashMap<String,String>)this.getIntent().getSerializableExtra(PGConstants.HASH_MAP);
        this.api_key = (String)hashParamsMap.get(PGConstants.API_KEY);
        this.return_url = (String)hashParamsMap.get(PGConstants.RETURN_URL);

        StringBuilder hashDict = new StringBuilder(hashParamsMap.get(PGConstants.SALT_KEY));
        TreeMap<String, String> sorted = new TreeMap<>();
        // Copy all data from hashMap into TreeMap
        sorted.putAll(hashParamsMap);
        // Display the TreeMap which is naturally sorted
        for (Map.Entry<String, String> entry : sorted.entrySet()) {
            if (!entry.getKey().equals("salt_key")) {
                hashDict.append("|").append(entry.getValue());
            }
        }
        String hashValue = get_SHA_512_SecurePassword(hashDict.toString());

        try {
            this.webview.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    pb.setVisibility(8);
//                    Log.i("log", "onPageFinished : " + url);
                    //TODO NEW CHANGES 15/07/22
                    try {
                        Log.i("log", "onPageFinished : " + url);
                        super.onPageFinished(view, url);
                        PaymentGatewayPaymentActivity.this.pb.setVisibility(8);
                        if (url.equals(URLDecoder.decode(PaymentGatewayPaymentActivity.this.return_url, "utf-8"))) {
                            PaymentGatewayPaymentActivity.this.pb.setVisibility(0);
                            view.setVisibility(8);
                        }
                    } catch (Exception var4) {
                        var4.printStackTrace();
                    }
                }

                public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                    super.onPageStarted(view, url, facIcon);
                    pb.setVisibility(0);
                    Log.i("log", "onPageStarted : " + url);
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    Log.i("log", "onReceivedError : " + error.toString());
                    Log.i("log", "onReceivedError : " + error);
                    super.onReceivedError(view, request, error);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentGatewayPaymentActivity.this);
                    String message;
                    switch (error.getPrimaryError()) {
                        case SslError.SSL_EXPIRED:
                            message = "The certificate has expired.";
                            break;
                        case SslError.SSL_IDMISMATCH:
                            message = "The certificate Hostname mismatch.";
                            break;
                        case SslError.SSL_UNTRUSTED:
                            message = "The certificate authority is not trusted.";
                            break;
                        case SslError.SSL_DATE_INVALID:
                            message = "The certificate date is invalid.";
                            break;
                        case SslError.SSL_NOTYETVALID:
                            message = "The certificate is not yet valid.";
                            break;
                        default:
                            message = "Unknown SSL error.";
                            break;
                    }

                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            handler.proceed();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            handler.cancel();
                        }
                    });
                    message += " Do you want to continue anyway?";
                    builder.setTitle("SSL Certificate Error");
                    builder.setMessage(message);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }

            });
            WebSettings webSettings = this.webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            this.webview.addJavascriptInterface(new PaymentGatewayPaymentActivity.MyJavaScriptInterface(this), "Android");
//            this.webview.addJavascriptInterface(new MyJavaScriptInterface((Activity)this), "AndroidInterface");
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            this.webview.clearHistory();
            this.webview.clearCache(true);
            this.webview.setWebChromeClient(new WebChromeClient() {
                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                    return super.onJsAlert(view, url, message, result);
                }
            });

            String postUrl = "https://pay.basispay.in/v2/paymentrequest?" + postPaymentRequestParams + "&hash=" + hashValue.toUpperCase();
            String postParamValues =  postPaymentRequestParams + "&hash=" + hashValue.toUpperCase();

            this.webview.postUrl(postUrl, (postParamValues).getBytes());
        } catch (Exception var7) {
            StringWriter sw = new StringWriter();
            var7.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Toast.makeText(this.getBaseContext(), exceptionAsString, 0).show();
        }

    }


    public String get_SHA_512_SecurePassword(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == 0) {
            switch(keyCode) {
                case 4:
                    /*if (this.webview.canGoBack()) {
                        this.webview.goBack();
                    } else {
                        this.finish();
                    }

                    return true;*/
                    //TODO NEW CHANGES 15/07/22
                    if (!this.doubleBackToExitPressedOnce) {
                        this.doubleBackToExitPressedOnce = true;
                        Toast.makeText((Context)this, "Clicking back button again will cancel this transaction !", 1).show();
                        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                            public void run() {
                                PaymentGatewayPaymentActivity.this.doubleBackToExitPressedOnce = false;
                            }
                        },  2000L);
                        return true;
                    }
                    try {
                        JSONObject pgResponse = new JSONObject();
                        pgResponse.put("status", "failed");
                        pgResponse.put("error_message", "TRANSACTION INCOMPLETE!");
                        Intent paymentResponseCallBackIntent = new Intent();
                        paymentResponseCallBackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                        setResult(-1, paymentResponseCallBackIntent);
                        finish();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        if (this.webview.getVisibility() == 0) {
            if (this.webview.canGoBack()) {
                this.webview.goBack();
            }

        } else {
            super.onBackPressed();
        }
    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            this.mContext = c;
        }

        @JavascriptInterface
        public void showHTML(String html, String url) {
            Log.i("log", "showHTML : " + url + " : " + html);
        }

        @JavascriptInterface
        public void paymentResponse(String jsonStringResponse) {
            if (!PaymentGatewayPaymentActivity.this.is_upi_intent_payment) {
                try {
                    Log.d("", "ResponseJson: " + jsonStringResponse);
                    JSONObject pgResponse = new JSONObject();
                    /*if (!jsonStringResponse.equals("null") && !jsonStringResponse.isEmpty() && jsonStringResponse.contains("transaction_id")) {
                        Intent data = new Intent();
                        data.putExtra(PGConstants.PAYMENT_RESPONSE, jsonStringResponse);
                        PaymentGatewayPaymentActivity.this.setResult(-1, data);
                        PaymentGatewayPaymentActivity.this.finish();
                    }*/
                    if (!jsonStringResponse.equals("null") && !jsonStringResponse.isEmpty() && jsonStringResponse.contains("transaction_id")) {
                        pgResponse.put("status", "success");
                        pgResponse.put("payment_response", jsonStringResponse);
                    } else {
                        pgResponse.put("status", "failed");
                        pgResponse.put("error_message", "No payment response received !");
                    }

                    Intent paymentResponseCallBackIntent = new Intent();
                    paymentResponseCallBackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                    PaymentGatewayPaymentActivity.this.setResult(-1, paymentResponseCallBackIntent);
                    PaymentGatewayPaymentActivity.this.finish();
                } catch (Exception var3) {
                    var3.printStackTrace();
                }
            }

        }

        @JavascriptInterface
        public void paymentUpiRequest(String jsonUpiIntentUri, String pg_tran_id) {
            PaymentGatewayPaymentActivity.this.is_upi_intent_payment = Boolean.valueOf(true);
            PaymentGatewayPaymentActivity.this.tran_id = pg_tran_id;
            try {
                Uri uri = Uri.parse(jsonUpiIntentUri.trim());
                PaymentGatewayPaymentActivity.this.upiIntent = new Intent("android.intent.action.VIEW", uri);
                PaymentGatewayPaymentActivity.this.upiIntentLauncher.launch(Intent.createChooser(PaymentGatewayPaymentActivity.this.upiIntent, "Pay with..."));
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
                try {
                    JSONObject pgResponse = new JSONObject();
                    pgResponse.put("status", "failed");
                    pgResponse.put("error_message", "No supported UPI app is installed in this phone !");
                    Intent upiResponseCallBackIntent = new Intent();
                    upiResponseCallBackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                    PaymentGatewayPaymentActivity.this.setResult(-1, upiResponseCallBackIntent);
                    PaymentGatewayPaymentActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    JSONObject pgResponse = new JSONObject();
                    pgResponse.put("status", "failed");
                    pgResponse.put("error_message", ex.getMessage());
                    Intent pspUpiCallbackIntent = new Intent();
                    pspUpiCallbackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                    PaymentGatewayPaymentActivity.this.setResult(-1, pspUpiCallbackIntent);
                    PaymentGatewayPaymentActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @JavascriptInterface
        public void cancelUpiPayment() {
            try {
                JSONObject pgResponse = new JSONObject();
                pgResponse.put("status", "failed");
                pgResponse.put("error_message", "TRANSACTION INCOMPLETE!");
                Intent pspUpiCallbackIntent = new Intent();
                pspUpiCallbackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                PaymentGatewayPaymentActivity.this.setResult(-1, pspUpiCallbackIntent);
                PaymentGatewayPaymentActivity.this.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void continueUpiPayment() {
            if (PaymentGatewayPaymentActivity.this.upiIntent != null)
                PaymentGatewayPaymentActivity.this.upiIntentLauncher.launch(Intent.createChooser(PaymentGatewayPaymentActivity.this.upiIntent, "Pay with..."));
        }
    }

    private class GetPgUPIResponseTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        private GetPgUPIResponseTask() {}

        protected void onPreExecute() {
            super.onPreExecute();
            PaymentGatewayPaymentActivity.this.pb.setVisibility(0);
        }

        protected String doInBackground(String... postParams) {
            String pgUpiPaymentResponseString = "";
            try {
                URL url = new URL(PGConstants.UPI_RES_URL);
                String postParam = postParams[0];
                byte[] postParamsByte = postParam.getBytes("UTF-8");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);
                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                int i;
                while ((i = responseInputStream.read(byteContainer)) != -1)
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                pgUpiPaymentResponseString = responseStringBuffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    JSONObject pgResponse = new JSONObject();
                    pgResponse.put("status", "failed");
                    pgResponse.put("error_message", e.getMessage());
                    Intent pspUpiCallbackIntent = new Intent();
                    pspUpiCallbackIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgResponse.toString());
                    PaymentGatewayPaymentActivity.this.setResult(-1, pspUpiCallbackIntent);
                    PaymentGatewayPaymentActivity.this.finish();
                } catch (JSONException ep) {
                    ep.printStackTrace();
                }
            }
            return pgUpiPaymentResponseString;
        }

        protected void onPostExecute(String pgUpiPaymentResponseString) {
            super.onPostExecute(pgUpiPaymentResponseString);
            try {
                JSONObject pgResponse = new JSONObject();
                if (pgUpiPaymentResponseString != null && !pgUpiPaymentResponseString.isEmpty()) {
                    pgResponse.put("status", "success");
                    pgResponse.put("payment_response", pgUpiPaymentResponseString);
                } else {
                    pgResponse.put("status", "failed");
                    pgResponse.put("error_message", "Error fetching PG UPI payment response");
                }
                Intent pgUpiPaymentResponseIntent = new Intent();
                pgUpiPaymentResponseIntent.putExtra(PGConstants.PAYMENT_RESPONSE, pgUpiPaymentResponseString);
                PaymentGatewayPaymentActivity.this.setResult(-1, pgUpiPaymentResponseIntent);
                PaymentGatewayPaymentActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
