package com.example.proba.activity.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proba.navigation.MainRoutes


@Composable
fun MessagePage(
    navController: NavController,
    onBackClick: () -> Unit
) {
    Scaffold(
        bottomBar = { bottomBarView(navController) }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.backgorund),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 0.25f },
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(180f)
                        )
                    }

                    Text(
                        text = "Messages",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.darkGreenTxt),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    thickness = 2.dp,
                    color = colorResource(R.color.darkGreenTxt)
                )


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                ) {

                    item {
                        messageView(
                            producer = "Milena",
                            lastMssg = "Hvala na odgovoru",
                            productName = "Paradajz",
                            timeMssg = 6,
                            imageUser = R.drawable.user,
                            onClick = { navController.navigate(MainRoutes.MessageChat) }
                        )
                    }

                    item {
                        messageView(
                            producer = "Radomir",
                            lastMssg = "Odličan kvalitet",
                            productName = "Paradajz",
                            timeMssg = 1,
                            imageUser = R.drawable.user,
                            onClick = { navController.navigate(MainRoutes.MessageChat) }
                        )
                    }

                    item {
                        messageView(
                            producer = "Elena",
                            lastMssg = "Upravo sam preuzela",
                            productName = "Paradajz",
                            timeMssg = 16,
                            imageUser = R.drawable.user,
                            onClick = { navController.navigate(MainRoutes.MessageChat) }
                        )
                    }

                    item {
                        messageView(
                            producer = "Mile",
                            lastMssg = "Sveže i ukusno",
                            productName = "Paradajz",
                            timeMssg = 3,
                            imageUser = R.drawable.user,
                            onClick = { navController.navigate(MainRoutes.MessageChat) }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MessagePagePreview() {
    MessagePage(
        navController = rememberNavController(),
        onBackClick = {}
    )
}
