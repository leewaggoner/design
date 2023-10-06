package com.wreckingballsoftware.design

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wreckingballsoftware.design.Destinations.CampaignsScreen
import com.wreckingballsoftware.design.Destinations.MapScreen
import com.wreckingballsoftware.design.Destinations.SignsScreen
import com.wreckingballsoftware.design.ui.compose.TopBar
import com.wreckingballsoftware.design.ui.theme.DeSignTheme
import com.wreckingballsoftware.design.ui.theme.customColorsPalette

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            DeSignTheme(
//                darkTheme = true,
            ) {
                Scaffold(
                    topBar = {
                        TopBar(
                            title = stringResource(id = R.string.app_name)
                        )
                    },
                    bottomBar = {
                        NavigationBar(

                        ) {
                            listOf(
                                CampaignsScreen,
                                MapScreen,
                                SignsScreen
                            ).forEach { screenName ->
                                NavigationBarItem(
                                    label = { Text(text = screenName) },
                                    selected = false,
                                    onClick = { },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Home,
                                            contentDescription = screenName,
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(MaterialTheme.customColorsPalette.background)
                    ) {
                        DeSignHost()
                    }
                }
            }
        }
    }
}
