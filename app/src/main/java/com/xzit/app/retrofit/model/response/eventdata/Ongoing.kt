package com.xzit.app.retrofit.model.response.eventdata

data class Ongoing(

  var clientId:String,
var eventType:String,
var eventTitle:String,
var eventDetail:String,
var eventCategory:String,
var eventPreferance:String,
var eventStartDate:String,
var eventEndDate:String,
//var eventTime:String,
var evenLocation:String,
var eventLat:String,
var eventLong:String,
var eventVenueName:String,
var eventVenueId:String,
var eventPrice:String,
var eventDealPrice:String,
var eventDiscType:String,
var eventDiscAmt:String,
var eventSalesFlag:String,
var eventId:String,
var usetDetail :List<UsetDetail>,
var eventBanners :List<String?>)