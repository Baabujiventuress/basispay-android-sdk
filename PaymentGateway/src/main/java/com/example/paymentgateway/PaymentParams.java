package com.example.paymentgateway;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PaymentParams {
    private String api_key;
    private String salt_key;
    private String amount = "0.00";
    private String email;
    private String name;
    private String phone;
    private String order_id;
    private String currency;
    private String description;
    private String city;
    private String state;
    private String zip_code;
    private String country;
    private String return_url;
    private String mode;
    private String udf1 = "";
    private String udf2 = "";
    private String udf3 = "";
    private String udf4 = "";
    private String udf5 = "";
    private String hash;
    private String address_line_1;
    private String address_line_2;
    private String timeout_duration;
    private String return_url_failure;
    private String return_url_cancel;
    private String percent_tdr_by_user;
    private String flatfee_tdr_by_user;
    private String show_convenience_fee;
    private String split_enforce_strict;
    private String payment_options;
    private String payment_page_display_text;
    private String enable_auto_refund;
    private String offer_code;
    private String allowed_bank_codes;
    private String allowed_bins;
    private String split_info;
    private String pg_payment_hostname;
    private String interface_type;

    public PaymentParams() {
    }

    public String getAPiKey() {
        return this.api_key;
    }

    public String getSaltKey() {
        return this.salt_key;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getOrderId() {
        return this.order_id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getZipCode() {
        return this.zip_code;
    }

    public String getCountry() {
        return this.country;
    }

    public String getReturnURL() {
        return this.return_url;
    }

    public String getMode() {
        return this.mode;
    }

    public String getUDF1() {
        return this.udf1;
    }

    public String getUDF2() {
        return this.udf2;
    }

    public String getUDF3() {
        return this.udf3;
    }

    public String getUDF4() {
        return this.udf4;
    }

    public String getUDF5() {
        return this.udf5;
    }

    public String getHash() {
        return this.hash;
    }

    public String getAddressLine1() {
        return this.address_line_1;
    }

    public String getAddressLine2() {
        return this.address_line_2;
    }

    public String getTimeoutDuration() {
        return this.timeout_duration;
    }

    public String getReturnUrlFailure() {
        return this.return_url_failure;
    }

    public String getReturnUrlCancel() {
        return this.return_url_cancel;
    }

    public String getPercentageTdrByUser() {
        return this.percent_tdr_by_user;
    }

    public String getFlatFeeTdrByUser() {
        return this.flatfee_tdr_by_user;
    }

    public String getShowConvenienceFee() {
        return this.show_convenience_fee;
    }

    public String getSplitEnforceStrict() {
        return this.split_enforce_strict;
    }

    public String getPaymentOptions() {
        return this.payment_options;
    }

    public String getPaymentPageDisplayText() {
        return this.payment_page_display_text;
    }

    public String getEnableAutoRefund() {
        return this.enable_auto_refund;
    }

    public String getOfferCode() {
        return this.offer_code;
    }

    public String getAllowedBankCodes() {
        return this.allowed_bank_codes;
    }

    public String getAllowedBins() {
        return this.allowed_bins;
    }

    public String getSplitInfo() {
        return this.split_info;
    }

    public String getPaymentHostname() {
        return this.pg_payment_hostname;
    }

    public String getInterface_type() {
        return interface_type;
    }

    public void setAPiKey(String api_key) {
        this.api_key = api_key;
    }

    public void setSaltKey(String salt_key) {
        this.salt_key = salt_key;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setReturnUrl(String return_url) {
        this.return_url = return_url;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setAddressLine1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public void setAddressLine2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public void setTimeDuration(String timeout_duration) {
        this.timeout_duration = timeout_duration;
    }

    public void setReturnUrlFailure(String return_url_failure) {
//        try {
//            this.return_url_failure = URLEncoder.encode(return_url_failure, "UTF-8");
//        } catch (UnsupportedEncodingException var3) {
//            var3.printStackTrace();
//        }
        this.return_url_failure = return_url_failure;
    }

    public void setReturnUrlCancel(String return_url_cancel) {
//        try {
//            this.return_url_cancel = URLEncoder.encode(return_url_cancel, "UTF-8");
//        } catch (UnsupportedEncodingException var3) {
//            var3.printStackTrace();
//        }
        this.return_url_cancel = return_url_cancel;
    }

    public void setPercentageTdrByUser(String percent_tdr_by_user) {
        this.percent_tdr_by_user = percent_tdr_by_user;
    }

    public void setFlatFeeTdrByUser(String flatfee_tdr_by_user) {
        this.flatfee_tdr_by_user = flatfee_tdr_by_user;
    }

    public void setShowConvienceFee(String show_convenience_fee) {
        this.show_convenience_fee = show_convenience_fee;
    }

    public void setSplitEnforceStrict(String split_enforce_strict) {
        this.split_enforce_strict = split_enforce_strict;
    }

    public void setPaymentOptions(String payment_options) {
        this.payment_options = payment_options;
    }

    public void setPaymentPageDisplayText(String payment_page_display_text) {
        this.payment_page_display_text = payment_page_display_text;
    }

    public void setEnableAutoRefund(String enable_auto_refund) {
        this.enable_auto_refund = enable_auto_refund;
    }

    public void setOfferCode(String offer_code) {
        this.offer_code = offer_code;
    }

    public void setAllowedBankCodes(String allowed_bank_codes) {
        this.allowed_bank_codes = allowed_bank_codes;
    }

    public void setAllowedBins(String allowed_bins) {
        this.allowed_bins = allowed_bins;
    }

    public void setSplitInfo(String split_info) {
        this.split_info = split_info;
    }

    public void setPaymentHostname(String pg_payment_hostname) {
        this.pg_payment_hostname = pg_payment_hostname;
    }

    public void setInterface_type(String interface_type) {
        this.interface_type = interface_type;
    }
}

