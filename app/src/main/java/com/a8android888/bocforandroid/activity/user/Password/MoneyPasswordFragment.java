package com.a8android888.bocforandroid.activity.user.Password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.ReplacementTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/3. 修改资金密码
 */
public class MoneyPasswordFragment extends BaseFragment{

    private EditText confirmpassword,newpassword,oldpassword;
    private TextView confirmmodify;
    private PublicDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.activity_password,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldpassword=FindView(R.id.oldpassword);
        oldpassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        oldpassword.setHint("请输入旧密码");

        newpassword=FindView(R.id.newpassword);
        newpassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        newpassword.setHint("4位数字");

        confirmpassword=FindView(R.id.confirmpassword);
        confirmpassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        confirmpassword.setHint("确认新密码");

        confirmmodify=FindView(R.id.confirmmodify);


        oldpassword.setSingleLine(true);
        oldpassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        oldpassword.setTransformationMethod(new AllCapTransformationMethod());//设置显示为*号
        newpassword.setSingleLine(true);
        newpassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        newpassword.setTransformationMethod(new AllCapTransformationMethod());//设置显示为*号
        confirmpassword.setSingleLine(true);
        confirmpassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        confirmpassword.setTransformationMethod(new AllCapTransformationMethod());//设置显示为*号

        confirmmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPassword();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public class AllCapTransformationMethod extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {
            char[] aa = {'0','1','2','3','4','5','6','7','8','9'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'*','*','*','*','*','*','*','*','*','*'};
            return cc;
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
              ToastUtil.showMessage(getActivity(), "请输入4位数字的旧密码");
              return;
          }
        if(confirmpassword.getText().toString().trim().length()<4||
                newpassword.getText().toString().trim().length()<4){
            ToastUtil.showMessage(getActivity(), "请输入4位数字的新密码");
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
        params.put("oldPwd",oldpassword.getText().toString().trim());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("|");
        stringBuilder.append(newpassword.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(oldpassword.getText().toString().trim());
        params.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));
        confirmmodify.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.ChangeWithdrawPassword, params, new MyJsonHttpResponseHandler(getActivity(),true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirmmodify.setEnabled(true);
                if (getView() != null && dialog != null) {
                    dialog.dismiss();
                }
                ToastUtil.showMessage(getActivity(), getString(R.string.modifyfail));
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                confirmmodify.setEnabled(true);
                LogTools.e("jsonObject",jsonObject.toString());
                if (getView() != null && dialog != null) {
                    dialog.dismiss();
                }
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(getActivity(), getString(R.string.modifysuccess));
                    getActivity().finish();
                }
                else {
                    ToastUtil.showMessage(getActivity(),jsonObject.optString("msg", ""));
                }
            }
        });
    }

}
