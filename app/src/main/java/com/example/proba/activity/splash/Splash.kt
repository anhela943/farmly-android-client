package com.example.proba.activity.splash

import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.proba.R

@Composable
fun OnboardingScreen(
    imageRes: Int,
    title: String,
    description: String,
    showNext: Boolean = true,
    showPrev: Boolean = false,
    onNextClick: () -> Unit = {},
    onPrevClick: () -> Unit = {},
    onGetStartedClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .offset(y = (-220).dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.darkGreenTxt),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.grey),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(52.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (showPrev) {
                    IconButton(
                        onClick = onPrevClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = "Previous",
                            modifier = Modifier.size(32.dp).rotate(180f)
                        )
                    }
                }
                if (showNext) {
                    IconButton(
                        onClick = onNextClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = "Next",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        onGetStartedClick?.let { click ->
            Button(
                onClick = click,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.darkGreenTxt),
                    contentColor = colorResource(R.color.white)
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(text = "Get Started")
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun SplashScreenPreview() {
    OnboardingScreen(
        imageRes = R.drawable.onboarding3,
        title = "Start Your Journey",
        description = "Order your favorite products today",
        showNext = false,
        showPrev = true,
        onPrevClick = { /* idi nazad na ekran 2 */ },
        onGetStartedClick = { /* idi u aplikaciju */ }
    )
}
