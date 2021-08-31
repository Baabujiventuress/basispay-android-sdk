package com.example.paymentgateway;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class PaymentGatewayPaymentInitializer {
    private Activity context = null;
    private HashMap<String, String> params = new LinkedHashMap();

    public PaymentGatewayPaymentInitializer(PaymentParams paymentParams, Activity context) {
        this.context = context;
        if (TextUtils.isEmpty(paymentParams.getAPiKey())) {
            throw new RuntimeException("Merchant API Key missing");
        } else {
            this.params.put("api_key", paymentParams.getAPiKey());
            if (TextUtils.isEmpty(paymentParams.getSaltKey())) {
                throw new RuntimeException("Merchant Salt Key missing");
            } else {
                this.params.put("salt_key", paymentParams.getSaltKey());
                if (Double.parseDouble(paymentParams.getAmount()) > 0.0D && Double.parseDouble(paymentParams.getAmount()) <= 1000000.0D) {
                    this.params.put("amount", paymentParams.getAmount() + "");
                    if (TextUtils.isEmpty(paymentParams.getEmail())) {
                        throw new RuntimeException("Email ID missing");
                    } else {
                        this.params.put("email", paymentParams.getEmail());
                        if (TextUtils.isEmpty(paymentParams.getName())) {
                            throw new RuntimeException("First Name is missing");
                        } else {
                            this.params.put("name", paymentParams.getName());
                            if (TextUtils.isEmpty(paymentParams.getPhone())) {
                                throw new RuntimeException("phone is missing");
                            } else {
                                this.params.put("phone", paymentParams.getPhone());
                                if (TextUtils.isEmpty(paymentParams.getOrderId())) {
                                    throw new RuntimeException("Order Id missing");
                                } else {
                                    this.params.put("order_id", paymentParams.getOrderId());
                                    if (TextUtils.isEmpty(paymentParams.getCurrency())) {
                                        throw new RuntimeException("Currency missing");
                                    } else {
                                        this.params.put("currency", paymentParams.getCurrency());
                                        if (TextUtils.isEmpty(paymentParams.getDescription())) {
                                            throw new RuntimeException("Description missing");
                                        } else {
                                            this.params.put("description", paymentParams.getDescription());
                                            if (TextUtils.isEmpty(paymentParams.getCity())) {
                                                throw new RuntimeException("City missing");
                                            } else {
                                                this.params.put("city", paymentParams.getCity());
                                                if (TextUtils.isEmpty(paymentParams.getState())) {
                                                    throw new RuntimeException("State missing");
                                                } else {
                                                    this.params.put("state", paymentParams.getState());
                                                    if (TextUtils.isEmpty(paymentParams.getZipCode())) {
                                                        throw new RuntimeException("Zip Code missing");
                                                    } else {
                                                        this.params.put("zip_code", paymentParams.getZipCode());
                                                        if (TextUtils.isEmpty(paymentParams.getCountry())) {
                                                            throw new RuntimeException("Country missing");
                                                        } else {
                                                            this.params.put("country", paymentParams.getCountry());
                                                            if (TextUtils.isEmpty(paymentParams.getReturnURL())) {
                                                                throw new RuntimeException("Return URL missing");
                                                            } else {
                                                                this.params.put("return_url", paymentParams.getReturnURL());
                                                                if (TextUtils.isEmpty(paymentParams.getMode())) {
                                                                    throw new RuntimeException("Mode missing");
                                                                } else {
                                                                    this.params.put("mode", paymentParams.getMode());
                                                                    if (paymentParams.getUDF1() != null) {
                                                                        this.params.put("udf1", paymentParams.getUDF1());
                                                                    }

                                                                    if (paymentParams.getUDF2() != null) {
                                                                        this.params.put("udf2", paymentParams.getUDF2());
                                                                    }

                                                                    if (paymentParams.getUDF3() != null) {
                                                                        this.params.put("udf3", paymentParams.getUDF3());
                                                                    }

                                                                    if (paymentParams.getUDF4() != null) {
                                                                        this.params.put("udf4", paymentParams.getUDF4());
                                                                    }

                                                                    if (paymentParams.getUDF5() != null) {
                                                                        this.params.put("udf5", paymentParams.getUDF5());
                                                                    }

                                                                    if (paymentParams.getAddressLine1() != null) {
                                                                        this.params.put("address_line_1", paymentParams.getAddressLine1());
                                                                    }

                                                                    if (paymentParams.getAddressLine2() != null) {
                                                                        this.params.put("address_line_2", paymentParams.getAddressLine2());
                                                                    }

                                                                    if (paymentParams.getTimeoutDuration() != null) {
                                                                        this.params.put("timeout_duration", paymentParams.getTimeoutDuration());
                                                                    }

                                                                    if (paymentParams.getReturnUrlFailure() != null) {
                                                                        this.params.put("return_url_failure", paymentParams.getReturnUrlFailure());
                                                                    }

                                                                    if (paymentParams.getReturnUrlCancel() != null) {
                                                                        this.params.put("return_url_cancel", paymentParams.getReturnUrlCancel());
                                                                    }

                                                                    if (paymentParams.getPercentageTdrByUser() != null) {
                                                                        this.params.put("percent_tdr_by_user", paymentParams.getPercentageTdrByUser());
                                                                    }

                                                                    if (paymentParams.getFlatFeeTdrByUser() != null) {
                                                                        this.params.put("flatfee_tdr_by_user", paymentParams.getFlatFeeTdrByUser());
                                                                    }

                                                                    if (paymentParams.getShowConvenienceFee() != null) {
                                                                        this.params.put("show_convenience_fee", paymentParams.getShowConvenienceFee());
                                                                    }

                                                                    if (paymentParams.getSplitEnforceStrict() != null) {
                                                                        this.params.put("split_enforce_strict", paymentParams.getSplitEnforceStrict());
                                                                    }

                                                                    if (paymentParams.getPaymentOptions() != null) {
                                                                        this.params.put("payment_options", paymentParams.getPaymentOptions());
                                                                    }

                                                                    if (paymentParams.getPaymentPageDisplayText() != null) {
                                                                        this.params.put("payment_page_display_text", paymentParams.getPaymentPageDisplayText());
                                                                    }

                                                                    if (paymentParams.getEnableAutoRefund() != null) {
                                                                        this.params.put("enable_auto_refund", paymentParams.getEnableAutoRefund());
                                                                    }

                                                                    if (paymentParams.getOfferCode() != null) {
                                                                        this.params.put("offer_code", paymentParams.getOfferCode());
                                                                    }

                                                                    if (paymentParams.getAllowedBankCodes() != null) {
                                                                        this.params.put("allowed_bank_codes", paymentParams.getAllowedBankCodes());
                                                                    }

                                                                    if (paymentParams.getAllowedBins() != null) {
                                                                        this.params.put("allowed_bins", paymentParams.getAllowedBins());
                                                                    }

                                                                    if (paymentParams.getSplitInfo() != null) {
                                                                        this.params.put("split_info", paymentParams.getSplitInfo());
                                                                    }

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("Amount should be greater 0 and  less than 1000000.00");
                }
            }

        }
    }

    public void initiatePaymentProcess() {
        Intent startActivity = new Intent(this.context, PaymentGatewayPaymentActivity.class);
        startActivity.putExtra(PGConstants.POST_PARAMS, this.buildParamsForPayment());
        startActivity.putExtra(PGConstants.HASH_MAP, this.params);
        startActivity.setFlags(8388608);
        this.context.startActivityForResult(startActivity, PGConstants.REQUEST_CODE);
    }

    private String buildParamsForPayment() {
        HashMap<String, String> params = this.params;
        StringBuilder hashPostParamsBuilder = new StringBuilder();
        String parameterEntry;
        TreeMap<String, String> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(params);

        // Display the TreeMap which is naturally sorted
        try {
            for(Iterator var3 = sorted.keySet().iterator(); var3.hasNext(); hashPostParamsBuilder = hashPostParamsBuilder.append(parameterEntry)) {
                String key = (String)var3.next();
                parameterEntry = key + "=" + (String)sorted.get(key) + "&";
            }
        } catch (Exception var6) {
            StringWriter sw = new StringWriter();
            var6.printStackTrace(new PrintWriter(sw));
            parameterEntry = sw.toString();
            Toast.makeText(this.context, parameterEntry, 0).show();
        }

        String postParams = hashPostParamsBuilder.toString();
        return postParams.charAt(postParams.length() - 1) == '&' ? postParams.substring(0, postParams.length() - 1) : postParams;
    }
}
