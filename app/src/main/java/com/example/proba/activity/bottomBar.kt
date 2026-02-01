package com.example.proba.activity

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proba.R
import com.example.proba.navigation.MainRoutes

data class BottomBarDestination(
    val label: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: String
)

@Composable
fun bottomBarView(navController: NavController) {

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomBarDestination("Home", R.drawable.home, R.drawable.homef, MainRoutes.Home),
        BottomBarDestination("Explore", R.drawable.search, R.drawable.searchf, MainRoutes.Explore),
        BottomBarDestination("Favorite", R.drawable.favorite, R.drawable.heart, MainRoutes.Favorite),
        BottomBarDestination("Message", R.drawable.mssg, R.drawable.envelopef, MainRoutes.Message),
        BottomBarDestination("Profile", R.drawable.user, R.drawable.userf, MainRoutes.Profile)
    )

    Surface(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp
    ) {
        NavigationBar(
            containerColor = Color.White
        ) {
            items.forEach { item ->

                val selected = currentRoute == item.route

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },

                    icon = {
                        Icon(
                            painter = painterResource(
                                if (selected) item.selectedIcon else item.icon
                            ),
                            contentDescription = item.label,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                    },

                    label = {
                        Text(
                            item.label,
                            fontSize = 10.sp,
                            color = if (selected)
                                colorResource(R.color.darkGreenTxt)
                            else
                                colorResource(R.color.black)
                        )
                    },

                    alwaysShowLabel = true,

                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Unspecified,
                        unselectedIconColor = Color.Unspecified,
                        selectedTextColor = colorResource(R.color.darkGreenTxt),
                        unselectedTextColor = colorResource(R.color.black),
                        indicatorColor = Color.Transparent
                    ),

                    interactionSource = remember { MutableInteractionSource() }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun bottomBarPreview() {
    bottomBarView(rememberNavController())
}