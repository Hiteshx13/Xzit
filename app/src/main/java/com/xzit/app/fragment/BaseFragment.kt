package com.xzit.app.fragment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xzit.app.activity.BaseActivity

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
}