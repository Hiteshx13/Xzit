package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.activity.ChatActivity
import com.xzit.app.adapter.ChatAdapter
import com.xzit.app.databinding.FragmentChatBinding
import java.util.*

class ChatFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentChatBinding? = null
    private var mAdapter: ChatAdapter? = null

    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        friendSearchList()
    }

    fun initListener() {
//        binding?.imgbackscreen?.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.imgbackscreen -> {

            }


        }
    }

    private fun friendSearchList() {
        binding?.rrFriend?.setHasFixedSize(true)
        binding?.rrFriend?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val input: MutableList<String> = ArrayList()
        for (i in 0..20) {
            input.add("Test$i")
        }
        mAdapter = ChatAdapter(input, object : ChatAdapter.OnChatClickListener {
            override fun onClickChat(pos: Int) {
                startActivity(Intent(mContext, ChatActivity::class.java))
            }
        })
        binding?.rrFriend?.adapter = mAdapter
    }
}