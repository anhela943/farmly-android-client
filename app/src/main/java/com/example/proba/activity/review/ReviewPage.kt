package com.example.proba.activity.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.proba.R
import com.example.proba.data.model.response.Review
import com.example.proba.util.Resource
import com.example.proba.viewmodel.UserReviewsViewModel

@Composable
fun ReviewPageView(
    navController: NavController,
    userReviewsViewModel: UserReviewsViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgorund),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.25f },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp)
                    .background(
                        colorResource(R.color.greenBackground),
                        RoundedCornerShape(topStart = 140.dp, topEnd = 140.dp)
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 32.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(30.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Reviews",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                when (val state = userReviewsViewModel.reviewsState) {
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(R.color.darkGreenTxt)
                            )
                        }
                    }

                    is Resource.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = state.message,
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.grey)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                androidx.compose.material3.Surface(
                                    onClick = { userReviewsViewModel.loadReviews() },
                                    shape = RoundedCornerShape(13.dp),
                                    color = colorResource(R.color.darkGreenTxt)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Retry",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorResource(R.color.white)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Resource.Success -> {
                        val reviews = state.data.reviews
                        if (reviews.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No reviews yet",
                                    fontSize = 18.sp,
                                    color = colorResource(R.color.grey)
                                )
                            }
                        } else {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                reviews.forEach { review ->
                                    ReviewItem(review)
                                }
                            }
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp)
                .size(30.dp)
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f)
            )
        }
    }
}

@Composable
private fun ReviewItem(
    review: Review
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!review.user.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = review.user.imageUrl,
                            contentDescription = review.user.fullName,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.user_1),
                            contentDescription = review.user.fullName,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = review.user.fullName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.star),
                                contentDescription = "Rating",
                                modifier = Modifier.size(11.dp),
                                colorFilter = ColorFilter.tint(Color(0xFFFFC107))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = review.user.rating.toString(),
                                fontSize = 11.sp,
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = review.content,
                    fontSize = 10.sp,
                    color = colorResource(R.color.black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewPagePreview() {
    // Preview requires a mock ViewModel, so this is kept simple
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Review Page Preview")
    }
}
