import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.proba.activity.domain.OnboardingPage
import com.example.proba.activity.splash.OnboardingScreen
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(onGetStartedClick: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            R.drawable.onboarding1,
            "Welcome to FARMLY",
            "Best fruits, vegetables and products directly to your door"
        ),
        OnboardingPage(
            R.drawable.onboarding2,
            "Where Quality Begins",
            "Handpicked goods from trusted local producers"
        ),
        OnboardingPage(R.drawable.onboarding3,
            "Fresh Made Simple",
            "Your everyday market, always one tap away"
        )
    )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val currentPage = pages[page]

            OnboardingScreen(
                imageRes = currentPage.imageRes,
                title = currentPage.title,
                description = currentPage.description,
                showPrev = page > 0,
                showNext = page < pages.size - 1,
                onPrevClick = { scope.launch { pagerState.animateScrollToPage(page - 1) } },
                onNextClick = { scope.launch { pagerState.animateScrollToPage(page + 1) } },
                onGetStartedClick = if (page == pages.size - 1) onGetStartedClick else null
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            activeColor = colorResource(R.color.darkGreenTxt),
            inactiveColor = colorResource(R.color.grey)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPagerPreview() {
    // Preview ne podržava swipe animacije, ali prikazuje prvi ekran
    MaterialTheme {
        OnboardingPager(
            onGetStartedClick = { /* Preview ne radi ništa */ }
        )
    }
}

