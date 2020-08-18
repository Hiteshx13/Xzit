package com.xzit.app.repository

import com.xzit.app.retrofit.ApiClient

open class BaseRepository {
    public var apiInterface = ApiClient.getApiInterface()
}