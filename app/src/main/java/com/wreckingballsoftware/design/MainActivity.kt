package com.wreckingballsoftware.design

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.framework.Framework
import com.wreckingballsoftware.design.ui.theme.DeSignTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val userRepo: UserRepo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            DeSignTheme(
//                darkTheme = true,
            ) {
                Framework(userRepo)
            }
        }
    }
}
