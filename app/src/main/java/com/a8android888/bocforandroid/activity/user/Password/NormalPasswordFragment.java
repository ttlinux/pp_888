package com.a8android888.bocforandroid.activity.user.Password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.ReplacementTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/3.
 */
public class NormalPasswordFragment extends BaseFragment{

    private EditText confirmpassword,newpassword,oldpassword;
    private TextView confirmmodify;
    private PublicDialog dialog;
    private int errcount=0;
    char[] word1=new char[26+26+10];
    char[] word2=new char[26+26+10];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.activity_password, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oldpassword=FindView(R.id.oldpassword);
        newpassword=FindView(R.id.newpassword);
        confirmpassword=FindView(R.id.confirmpassword);

        confirmmodify=FindView(R.id.confirmmodify);

        oldpassword.setSingleLine(true);
        oldpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        oldpassword.setTransformationMethod(new AllCapTransformationMethod());//设置显示为*号

        newpassword.setSingleLine(true);
        newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newpassword.setTransformationMethod(new AllCapTransformationMethod());//设置显示为*号

        confirmpassword.setSingleLine(true);
        confirmpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmpassword.setTransformationMethod(new AllCapTransformationMethod());//设置显示为*号
        confirmmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPassword();
            }
        });


        for (int i = 48; i < 58; i++) {
            word1[i-48]=(char)i;
        }//1-10
        for (int i = 97; i < 123; i++) {
            word1[10+i-97]=(char)i;
        }//a-z
        for (int i = 65; i < 91; i++) {
            word1[36+i-65]=(char)i;
        }//A-Z

        for (int i = 0; i <word2.length ; i++) {
            word2[i]='*';
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public class AllCapTransformationMethod extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {

            return word1;
        }

        @Override
        protected char[] getReplacement() {
            return word2;
        }

    }
    public void ModifyPassword()
    {
        if(!confirmpassword.getText().toString().trim().equalsIgnoreCase(newpassword.getText().toString().trim()))
        {
            ToastUtil.showMessage(getActivity(), getString(R.string.inputerror));
            return;
        }
        if(oldpassword.getText().toString().trim().length()<4){
            ToastUtil.showMessage(getActivity(), "请输入6-12位数字/字母的旧密码");
            return;
        }
        if(confirmpassword.getText().toString().trim().length()<4||
                newpassword.getText().toString().trim().length()<4){
            ToastUtil.showMessage(getActivity(), "请输入6-12位数字/字母的新密码");
            return;
        }


        if(dialog==null)
        {
            dialog=new PublicDialog(getActivity());
        }
        dialog.show();

        BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
        String name=baseApplication.getBaseapplicationUsername();
        if(name==null || name.equalsIgnoreCase(""))return;
        RequestParams params=new RequestParams();
        params.put("userName",name);
        params.put("newPwd",newpassword.getText().toString().trim());
        params.put("oldPwd", oldpassword.getText().toString().trim());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("|");
        stringBuilder.append(newpassword.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(oldpassword.getText().toString().trim());
        params.put("signature",MD5Util.sign(stringBuilder.toString(),Httputils.androidsecret));
        confirmmodify.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.ChangePassword,params,new MyJsonHttpResponseHandler(getActivity(),true){

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirmmodify.setEnabled(true);
                if(getView()!=null && dialog!=null)
                {
                    dialog.dismiss();
                }
                ToastUtil.showMessage(getActivity(), getString(R.string.modifyfail));
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirmmodify.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                if(getView()!=null && dialog!=null)
                {
                    dialog.dismiss();
                }
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    ToastUtil.showMessage(getActivity(), getString(R.string.modifysuccess));
                 getActivity().finish();
                }
                else {
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                }
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000005"))
                {
                    errcount++;
                    if(errcount==5)
                    {
                        Logout();//输错5次登出
                    }
                }
            }
        });
    }



    private void Logout()
    {

        RequestParams params=new RequestParams();
        params.put("userName",((BaseApplication)getActivity().getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.logout, params, new MyJsonHttpResponseHandler(getActivity(), true) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                BaseApplication baseApplication = (BaseApplication) context.getApplication();
                baseApplication.setUsername("");
                ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
                getActivity().finish();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(context, MainActivity.class);
                intent.putExtra(BundleTag.IntentTag, 0);
                context.startActivity(intent);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }
}
