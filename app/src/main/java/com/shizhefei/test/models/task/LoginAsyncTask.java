package com.shizhefei.test.models.task;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.shizhefei.task.IAsyncTask;
import com.shizhefei.test.models.datasource.VolleyRequestHandle;
import com.shizhefei.test.models.enties.User;
import com.shizhefei.test.models.exception.BizException;
import com.shizhefei.utils.MyVolley;

public class LoginAsyncTask implements IAsyncTask<User> {
    private String name;
    private String password;

    public LoginAsyncTask(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    @Override
    public RequestHandle execute(final ResponseSender<User> sender) throws Exception {
        String url = "http://www.baidu.com";
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("userName", name);
        builder.appendQueryParameter("password", password);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                builder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(name)) {
                    sender.sendError(new BizException("请输入用户名"));
                }
                if (TextUtils.isEmpty(password)) {
                    sender.sendError(new BizException("请输入密码"));
                }
                if (name.equals("LuckyJayce") && password.equals("111")) {
                    sender.sendData(new User("1", "LuckyJayce", 23,
                            "中国人"));
                } else {
                    sender.sendError(new BizException("用户名或者密码不正确"));
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                sender.sendError(error);
            }
        });
        MyVolley.getRequestQueue().add(jsonObjRequest);
        return new VolleyRequestHandle(jsonObjRequest);
    }

}
