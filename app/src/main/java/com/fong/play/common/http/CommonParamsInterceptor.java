package com.fong.play.common.http;

import android.content.Context;

import com.fong.play.common.Constant;
import com.fong.play.common.utils.DensityUtil;
import com.fong.play.common.utils.DeviceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by FANGDINGJIE
 * 2018/3/27.
 */

public class CommonParamsInterceptor implements Interceptor {

    private Gson mGson;
    private Context mContext;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public CommonParamsInterceptor(Context context, Gson gson) {
        this.mContext = context;
        this.mGson = gson;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            HashMap<String, Object> commomParamsMap = new HashMap<>();

            commomParamsMap.put(Constant.IMEI, "11111");//DeviceUtils.getIMEI(mContext)
            commomParamsMap.put(Constant.MODEL, DeviceUtils.getModel());
            commomParamsMap.put(Constant.LANGUAGE, DeviceUtils.getLanguage());
            commomParamsMap.put(Constant.os, DeviceUtils.getBuildVersionIncremental());
            commomParamsMap.put(Constant.RESOLUTION, DensityUtil.getScreenW(mContext) + "*" + DensityUtil.getScreenH(mContext));
            commomParamsMap.put(Constant.SDK, DeviceUtils.getBuildVersionSDK() + "");
            commomParamsMap.put(Constant.DENSITY_SCALE_FACTOR, mContext.getResources().getDisplayMetrics().density + "");

            String method = request.method();
            if (method.equals("GET")) {
                HttpUrl httpUrl = request.url();
                HashMap<String, Object> rootMap = new HashMap<>();

                Set<String> paramNames = httpUrl.queryParameterNames();
                for (String key : paramNames) {
                    if (Constant.PARAM.equals(key)) {
                        String oldParamjSON = httpUrl.queryParameter(Constant.PARAM);
                        if (oldParamjSON != null) {
                            HashMap<String, Object> p = mGson.fromJson(oldParamjSON, HashMap.class);
                            if (p != null) {
                                for (Map.Entry<String, Object> entry : p.entrySet()) {
                                    rootMap.put(entry.getKey(), entry.getValue());
                                }
                            }
                        }
                    } else {
                        rootMap.put(key, httpUrl.queryParameter(key));
                    }
                }

                rootMap.put("publicParams", commomParamsMap);
                String newJsonParams = mGson.toJson(rootMap);

                String url = httpUrl.toString();

                int index = url.indexOf("?");
                if (index > 0) {
                    url = url.substring(0, index);
                }
                url = url + "?" + Constant.PARAM + "=" + newJsonParams;

                request = request.newBuilder().url(url).build();


            } else if (method.equals("POST")) {
                RequestBody body = request.body();


                HashMap<String, Object> rootMap = new HashMap<>();
                if (body instanceof FormBody) { // form 表单

                    for (int i = 0; i < ((FormBody) body).size(); i++) {

                        rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                    }

                } else {

                    Buffer buffer = new Buffer();

                    body.writeTo(buffer);

                    String oldJsonParams = buffer.readUtf8();

                    rootMap = mGson.fromJson(oldJsonParams, HashMap.class); // 原始参数
                    rootMap.put("publicParams", commomParamsMap); // 重新组装
                    String newJsonParams = mGson.toJson(rootMap); // {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}

                    request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();


                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return chain.proceed(request);
    }
}
