package com.xzit.app.listener

import com.xzit.app.retrofit.model.response.eventinvitationreceived.EventInvitationReceivedData

interface OnAcceptRejectClicked {
    fun onClick(position: Int,isAccept:Boolean,model: EventInvitationReceivedData)
}