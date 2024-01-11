package com.wreckingballsoftware.design

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.framework.Framework
import com.wreckingballsoftware.design.ui.theme.DeSignTheme
import com.wreckingballsoftware.design.utils.NetworkConnection
import org.koin.android.ext.android.inject
import org.koin.core.parameter.ParametersHolder

class MainActivity : ComponentActivity() {
    private val userRepo: UserRepo by inject()
    private val networkConnection: NetworkConnection by inject(
        parameters = { ParametersHolder(mutableListOf(lifecycleScope)) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val connectionState by networkConnection.connection.collectAsStateWithLifecycle()
            DeSignTheme(
//                darkTheme = true,
            ) {
                Framework(lifecycleScope, userRepo, connectionState)
            }
        }
    }
}
