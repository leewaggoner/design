package com.wreckingballsoftware.design

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wreckingballsoftware.design.ui.framework.Framework
import com.wreckingballsoftware.design.ui.theme.DeSignTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            DeSignTheme(
//                darkTheme = true,
            ) {
                Framework()
            }
        }
    }
}
