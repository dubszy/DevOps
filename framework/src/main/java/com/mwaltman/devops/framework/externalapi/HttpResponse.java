package com.mwaltman.devops.framework.externalapi;

import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Arrays;

import static com.mwaltman.devops.framework.util.StringUtils.WS_2_SPACES;
import static com.mwaltman.devops.framework.util.StringUtils.WS_4_SPACES;

/**
 * POJO (kind of) for storing responses received when making API
 * {@link ApiClient#call(HttpRequestBase) calls}. Apache's HTTP response class
 * is used to fill instances of this class, but this is used instead to provide
 * a layer of abstraction so other parts of this application don't need to worry
 * about properly closing entity streams.
 *
 * @see org.apache.http.HttpResponse Apache's HTTP response interface
 */
@Getter
public class HttpResponse {

    /**
     * The status code of this response.
     *
     * @see StatusLine#getStatusCode() How APIClient populates this field
     */
    private Integer statusCode;

    /**
     * The reason for this response.
     *
     * @see StatusLine#getReasonPhrase() How APIClient populates this field
     */
    private String reason;

    /**
     * The headers in this response.
     *
     * @see org.apache.http.HttpResponse#getAllHeaders() How APIClient populates this field
     */
    private Header[] headers;

    /**
     * The content in this response.
     *
     * @see org.apache.http.HttpResponse#getEntity() How APIClient populates this field
     */
    private String content;

    public HttpResponse(Integer statusCode, String reason, Header[] headers, String content) {
        this.statusCode = statusCode;
        this.reason = reason;
        this.headers = headers;
        this.content = content;
    }

    /**
     * Creates a pretty-printed string of this instance.
     *
     * @param perLinePrefix Prefix to use immediately after every newline
     *
     * @return A pretty-printed string of this instance
     */
    public String prettyToString(String perLinePrefix) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(perLinePrefix)
                .append(this.getClass().getSimpleName())
                .append(" {\n")
                .append(perLinePrefix)
                .append(WS_2_SPACES)
                .append("statusCode: '")
                .append(statusCode)
                .append("',\n")
                .append(perLinePrefix)
                .append(WS_2_SPACES)
                .append("reason: '")
                .append(reason)
                .append("',\n")
                .append(perLinePrefix)
                .append(WS_2_SPACES)
                .append("headers: [\n");

        for (Header aHeader : headers) {
            stringBuilder.append(perLinePrefix)
                    .append(WS_4_SPACES)
                    .append(aHeader.getName())
                    .append(": ")
                    .append(aHeader.getValue())
                    .append(",\n");
        }

        stringBuilder.append(perLinePrefix)
                .append(WS_2_SPACES)
                .append("],\n")
                .append(perLinePrefix)
                .append(WS_2_SPACES)
                .append("content: (displayed below)\n")
                .append(perLinePrefix)
                .append("}\n")
                .append(perLinePrefix)
                .append("Content:\n");

        if (content.length() > 512) {
            stringBuilder
                    .append(perLinePrefix)
                    .append(content.substring(0, 256))
                    .append("\n")
                    .append(perLinePrefix)
                    .append("...\n")
                    .append(perLinePrefix)
                    .append(content.substring(content.length() - 256, content.length()));
        } else {
            stringBuilder.append(content);
        }
        return stringBuilder.append("\n").toString();
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
