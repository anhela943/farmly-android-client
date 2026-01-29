package com.example.proba.activity.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.SearchView
import com.example.proba.activity.bottomBarView

@Composable
fun FavoriteScreenView() {
    val favorites = remember {
        mutableStateListOf(
            FavoriteItemUi("Plum", "4.5", "Petra Petrovic", R.drawable.basket),
            FavoriteItemUi("Potatoes", "4.5", "Petra Petrovic", R.drawable.basket),
            FavoriteItemUi("Tomatoes", "4.5", "Milena Ratkovic", R.drawable.basket)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        // TODO: Navigate back
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "Back",
                        modifier = Modifier.rotate(180f)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    SearchView(
                        onMenuClick = { /* TODO */ },
                        onSearchClick = { /* TODO */ },
                        onSearchChange = { query -> /* TODO */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Favorites",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.darkGreenTxt),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                favorites.forEach { item ->
                    FavoriteItem(
                        title = item.title,
                        rating = item.rating,
                        owner = item.owner,
                        image = item.image,
                        onCardClick = {
                            // TODO: Navigate to product details
                        },
                        onHeartClick = {
                            favorites.remove(item)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            bottomBarView()
        }
    }
}

@Composable
private fun FavoriteItem(
    title: String,
    rating: String,
    owner: String,
    image: Int,
    onCardClick: () -> Unit,
    onHeartClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.5.dp, colorResource(R.color.greenBackground)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            colorResource(R.color.greenFilter),
                            colorResource(R.color.lightGreen)
                        )
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = title,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black)
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = rating,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = owner,
                        fontSize = 12.sp,
                        color = colorResource(R.color.black)
                    )
                }

                IconButton(
                    onClick = onHeartClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.heart),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(22.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.darkGreenTxt))
                    )
                }
            }
        }
    }
}

private data class FavoriteItemUi(
    val title: String,
    val rating: String,
    val owner: String,
    val image: Int
)

@Preview(showBackground = true)
@Composable
fun FavoriteScreenViewPreview() {
    FavoriteScreenView()
}
