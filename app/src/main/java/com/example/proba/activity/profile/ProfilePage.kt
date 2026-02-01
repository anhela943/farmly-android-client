package com.example.proba.activity.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.Resource
import com.example.proba.viewmodel.ProfileViewModel

@Composable
fun ProfilePage(
    navController: NavController,
    onBackClick: () -> Unit,
    profileViewModel: ProfileViewModel,
    onLogout: () -> Unit = {}
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                when (val state = profileViewModel.profileState) {
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(R.color.darkGreenTxt)
                            )
                        }
                    }

                    is Resource.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = state.message,
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.grey),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ProfileActionButton(
                                    text = "Retry",
                                    containerColor = colorResource(R.color.darkGreenTxt),
                                    contentColor = colorResource(R.color.white),
                                    onClick = { profileViewModel.loadProfile() },
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(48.dp)
                                )
                            }
                        }
                    }

                    is Resource.Success -> {
                        val profile = state.data
                        ProfileContent(
                            navController = navController,
                            profileName = profile.fullName,
                            profileStar = profile.overallReview,
                            fullName = profile.fullName,
                            email = profile.email,
                            city = profile.city ?: "",
                            phoneNumber = profile.phoneNumber ?: "",
                            description = profile.description ?: "",
                            imageUrl = profile.imageUrl,
                            onLogout = { profileViewModel.logout { onLogout() } }
                        )
                    }
                }

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(30.dp)
                        .align(Alignment.TopStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(180f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    navController: NavController,
    profileName: String,
    profileStar: Double?,
    fullName: String,
    email: String,
    city: String,
    phoneNumber: String,
    description: String,
    imageUrl: String?,
    onLogout: () -> Unit
) {
    val surfaceColor = colorResource(R.color.greenBackground)
    val actionButtonModifier = Modifier
        .fillMaxWidth()
        .height(48.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(top = 75.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Rating",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = profileStar.toString(),
                        fontSize = 20.sp,
                        color = colorResource(R.color.black)
                    )
                }

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

                Spacer(modifier = Modifier.height(12.dp))

                ProfileActionButton(
                    text = "Log out",
                    containerColor = androidx.compose.ui.graphics.Color(0xFFD32F2F),
                    contentColor = colorResource(R.color.white),
                    onClick = onLogout,
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
            if (!imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
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
    ProfileContent(
        navController = rememberNavController(),
        profileName = "Ana Markovic",
        profileStar = 2.0,
        fullName = "Ana Markovic",
        email = "ana.markovic@example.com",
        city = "Belgrade",
        phoneNumber = "+381 64 123 4567",
        description = "Enthusiastic home chef and small-batch seller.",
        imageUrl = null,
        onLogout = {}
    )
}
