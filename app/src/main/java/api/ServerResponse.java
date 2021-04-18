package api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ServerResponse {
    // variable name should be same as in the json response from php
    @SerializedName("success")
    boolean success;
    @SerializedName("url")
    String url;
    String getUrl() {
        return url;
    }
    boolean getSuccess() {
        return success;
    }
}
