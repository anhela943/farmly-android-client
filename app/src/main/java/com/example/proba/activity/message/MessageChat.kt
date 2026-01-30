package com.example.proba.activity.message

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.math.log

@Composable
fun MessageChatPage(
    navController: NavController,
    onBackClick: () -> Unit,
    producerName: String = "Petra Petrovic Pr",
    productName: String = "Tomatoes",
    productPrice: String = "200 din",
    productRating: String = "4.5",
    @DrawableRes producerAvatar: Int = R.drawable.user,
    @DrawableRes productImage: Int = R.drawable.basket
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isReviewVisible by remember { mutableStateOf(false)}
    var messageText by remember { mutableStateOf("") }
    val isPreview = LocalInspectionMode.current
    val logMessageChat: (String) -> Unit = { message ->
        if (isPreview) {
            println("MessageChat: $message")
        } else {
            Log.d("MessageChat", message)
        }
    }
    val arrowRotation by animateFloatAsState(
        targetValue = if (isDropdownExpanded) -90f else 90f,
        label = "dropdownArrowRotation"
    )

    Scaffold(
        bottomBar = { bottomBarView(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colorResource(R.color.greenBackground),
                            colorResource(R.color.greenSrDark),
                            colorResource(R.color.greenStrokeDark)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.greenBackground)
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Image(
                                painter = painterResource(R.drawable.arrow),
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(22.dp)
                                    .rotate(180f)
                            )
                        }

                        Image(
                            painter = painterResource(producerAvatar),
                            contentDescription = "Producer avatar",
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = producerName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                isDropdownExpanded = !isDropdownExpanded
                                logMessageChat("Leave review clicked")
                            }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.arrow),
                                contentDescription = "Toggle product menu",
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(arrowRotation)
                            )
                        }
                    }

                    AnimatedVisibility(visible = isDropdownExpanded) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .shadow(
                                        elevation = 6.dp,
                                        shape = RoundedCornerShape(2.dp),
                                        clip = false
                                    )
                                    .background(colorResource(R.color.greenStrokeDark).copy(alpha = 0.7f))
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.92f)
                                    .widthIn(max = 340.dp)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(colorResource(R.color.greenSrLight))
                                    .padding(horizontal = 10.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(productImage),
                                        contentDescription = "Product image",
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = productName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorResource(R.color.black)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = productPrice,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = colorResource(R.color.grey)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Row{
                                            Image(
                                                painter = painterResource(R.drawable.star),
                                                contentDescription = "Favorite",
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = productRating,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = colorResource(R.color.grey)
                                            )
                                        }
                                    }

                                    Button(
                                        onClick = {
                                            isReviewVisible = true
                                            logMessageChat("Is stanje : " + isReviewVisible)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent,
                                            contentColor = colorResource(R.color.darkGreenTxt)
                                        ),
                                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                                    ) {
                                        Text(
                                            text = "Leave review",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorResource(R.color.darkGreenTxt)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                MessageBubble(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    isOwnMessage = true
                )

                MessageBubble(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    isOwnMessage = false
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                placeholder = { Text("Message...", color = colorResource(R.color.grey)) },
                trailingIcon = {
                    IconButton(onClick = { /* TODO: send message */ }) {
                        Image(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = "Send",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colorResource(R.color.black),
                    unfocusedTextColor = colorResource(R.color.black),
                    cursorColor = colorResource(R.color.black)
                )
            )
        }

        logMessageChat("Is stanje posle : " + isReviewVisible)

        if (isReviewVisible) {
            logMessageChat("Is stanje unutar IF-a : " + isReviewVisible)
            ReviewOverlay(
                reviewerName = producerName,
                reviewerAvatar = producerAvatar,
                onDismiss = { isReviewVisible = false },
                onPostReview = { isReviewVisible = false }
            )
        }
    }
}

@Composable
private fun ReviewOverlay(
    reviewerName: String,
    @DrawableRes reviewerAvatar: Int,
    onDismiss: () -> Unit,
    onPostReview: (String) -> Unit
) {
    var reviewText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.35f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onDismiss()
                }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(26.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.greenSrLight))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(reviewerAvatar),
                        contentDescription = "Reviewer avatar",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = reviewerName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.black),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "*****",
                    fontSize = 28.sp,
                    letterSpacing = 6.sp,
                    color = colorResource(R.color.grey),
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    placeholder = { Text("Add a review...", color = colorResource(R.color.grey)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colorResource(R.color.black),
                        unfocusedTextColor = colorResource(R.color.black),
                        cursorColor = colorResource(R.color.black)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onPostReview(reviewText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.darkGreenTxt)
                    ),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text(
                        text = "Post the review",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.grey)
                    ),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text(
                        text = "Close",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(
    text: String,
    isOwnMessage: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 260.dp),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageChatPagePreview() {
    MessageChatPage(
        navController = rememberNavController(),
        onBackClick = {}
    )
}
