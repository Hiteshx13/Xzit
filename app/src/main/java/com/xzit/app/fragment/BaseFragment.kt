package com.xzit.app.fragment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xzit.app.activity.BaseActivity
import com.xzit.app.viewmodel.BaseViewModel

open class BaseFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as AppCompatActivity
    }

    fun getParentActivity():BaseActivity{
        return mActivity as BaseActivity
    }

    fun replaceFragment(){
        var baseActivity=mActivity as BaseActivity
       // baseActivity.add
    }

}