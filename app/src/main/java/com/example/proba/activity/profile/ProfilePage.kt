package com.example.proba.activity.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
fun ProfilePage(
    navController: NavController,
    onBackClick: () -> Unit,
    profileName: String = "Petra Petrovic Pr",
    profileEmail: String = "petra.petrovic@gmail.com",
    fullName: String = "Petar Petrovic",
    email: String = "petar@gmail.com",
    city: String = "Niš",
    phoneNumber: String = "(+381) 60 1234 456",
    description: String = "lorem ipsum srnajne neko aaaaa aaaaaaaa aaaaaaaaaaaa lorem ipsum srnajne neko aaaaaaaaaaa"
) {
    Scaffold(
        bottomBar = { bottomBarView(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgorund),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.25f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(180f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                val surfaceColor = colorResource(R.color.greenBackground)
                val cardMinHeight = maxOf(screenHeight - 140.dp, 560.dp)
                val actionButtonModifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .heightIn(min = cardMinHeight),
                        shape = RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = surfaceColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(78.dp))

                            Text(
                                text = profileName,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.black)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = profileEmail,
                                fontSize = 18.sp,
                                color = colorResource(R.color.grey)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileField(label = "Full Name", value = fullName)
                                ProfileField(label = "Email", value = email)
                                ProfileField(label = "City", value = city)
                                ProfileField(label = "Phone Number", value = phoneNumber)
                                ProfileField(label = "Description", value = description)
                            }

                            Spacer(modifier = Modifier.height(15.dp))

                            ProfileActionButton(
                                text = "My products",
                                containerColor = colorResource(R.color.greenFilter),
                                contentColor = colorResource(R.color.darkGreenTxt),
                                onClick = { navController.navigate(MainRoutes.EditProduct) },
                                modifier = actionButtonModifier
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            ProfileActionButton(
                                text = "Edit profile",
                                containerColor = colorResource(R.color.darkGreenTxt),
                                contentColor = colorResource(R.color.white),
                                onClick = { navController.navigate(MainRoutes.ProfileCreate) },
                                modifier = actionButtonModifier
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                    Card(
                        modifier = Modifier
                            .size(164.dp)
                            .offset(y = (-48).dp),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Profile image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            color = colorResource(R.color.black)
        )
    }
}

@Composable
private fun ProfileActionButton(
    text: String,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(13.dp),
        color = containerColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfilePage(
        navController = rememberNavController(),
        onBackClick = {}
    )
}
