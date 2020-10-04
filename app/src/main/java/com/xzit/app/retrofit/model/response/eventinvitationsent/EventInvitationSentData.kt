package com.xzit.app.retrofit.model.response.eventinvitationsent

data class EventInvitationSentData(
 var fromName:String,
var userId:String,
var eventId:String,
 val eventName:String?,
var inviReceivedOn:String,
var eventLocation:String,
var eventVenueName:String,
var  guestList:List<GuestList>
) 