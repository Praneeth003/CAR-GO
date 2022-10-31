package com.cargobackend.pojo.response.common;

import java.io.Serializable;

import com.cargobackend.utils.CommonConstants;

public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;
    private String errorCode;
    private int errorId;
    private String errorDescription;

    public BaseResponse() {
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorId() {
        return this.errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseResponse [status=");
        builder.append(this.status);
        builder.append(", errorCode=");
        builder.append(this.errorCode);
        builder.append(", errorId=");
        builder.append(this.errorId);
        builder.append(", errorDescription=");
        builder.append(this.errorDescription);
        builder.append("]");
        return builder.toString();
    }

    public void setFailedResponse() {
        this.setStatus(CommonConstants.Status.FAILURE.name().toString());
    }

    public void setSuccessResponse() {
        this.setStatus(CommonConstants.Status.SUCCESS.name().toString());
    }

    public boolean isSuccess() {
        return CommonConstants.Status.SUCCESS.name().toString().equals(this.status);
    }
}
