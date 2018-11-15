package com.mwaltman.devops.framework.externalapi;

import lombok.Getter;
import org.apache.http.Header;

import java.util.Arrays;

@Getter
public class HttpResponse {

    private String statusCode;

    private String reason;

    private Header[] headers;

    private String content;

    public HttpResponse(String statusCode, String reason, Header[] headers, String content) {
        this.statusCode = statusCode;
        this.reason = reason;
        this.headers = headers;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpResponse that = (HttpResponse) o;

        if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null)
            return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null)
            return false;
        // deepEquals because Header has nested arrays
        return Arrays.deepEquals(headers, that.headers) &&
                (content != null ?
                        content.equals(that.content) :
                        that.content == null);
    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(headers);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", reason='" + reason + '\'' +
                ", headers=" + Arrays.toString(headers) +
                ", content='" + content + '\'' +
                '}';
    }
}
