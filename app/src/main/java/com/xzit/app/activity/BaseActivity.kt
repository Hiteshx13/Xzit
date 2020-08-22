package com.xzit.app.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.xzit.app.utils.AppPreference


open class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var preference: AppPreference


    open var gso: GoogleSignInOptions? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        preference = AppPreference()
        if (gso == null) {
            gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
        }

        if (mGoogleSignInClient == null) {
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso!!)
        }

    }
}