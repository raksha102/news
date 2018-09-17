
package com.application.data.net;

import android.annotation.SuppressLint;


import com.application.domain.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "News_API";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();
        String requestLog = String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers());
        if (request.method().compareToIgnoreCase("post") == 0) {
            requestLog = "\n" + requestLog + "\n" + bodyToString(request);
        }
        requestLog = requestLog + "\n" + request.headers().toString();
        Logger.d(TAG, "request" + ":" + requestLog);

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Logger.d(TAG, "response Header" + "\n" + response.headers());
        @SuppressLint("DefaultLocale")
        String responseLog = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());

        String bodyString = response.body().string();

        Logger.d(TAG, "response" + "\n" + responseLog + "\n" + bodyString);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            String msg = "Could not convert body to string";
            Logger.e(TAG, msg);
            return msg;
        }
    }
}