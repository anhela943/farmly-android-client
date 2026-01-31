package com.example.proba.activity.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import com.example.proba.data.model.response.ChatResponse
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.Resource
import com.example.proba.viewmodel.ChatsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


@Composable
fun MessagePage(
    navController: NavController,
    onBackClick: () -> Unit,
    chatsViewModel: ChatsViewModel
) {
    LaunchedEffect(Unit) {
        chatsViewModel.loadChats()
    }

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
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(30.dp)
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

                when (val state = chatsViewModel.chatsState) {
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
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
                                .fillMaxSize(),
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
                                Surface(
                                    onClick = { chatsViewModel.loadChats() },
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(13.dp),
                                    color = colorResource(R.color.darkGreenTxt)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
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
                        val chats = state.data.chats

                        if (chats.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No messages yet",
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.grey)
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 24.dp)
                            ) {
                                items(chats) { chat ->
                                    messageView(
                                        chatName = chat.name,
                                        lastMssg = chat.lastMessage ?: "",
                                        imageUrl = chat.imageUrl,
                                        onClick = { navController.navigate(MainRoutes.MessageChat) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatRelativeTime(isoDate: String?): String {
    if (isoDate == null) return ""
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = format.parse(isoDate) ?: return ""
        val now = Date()
        val diffMs = now.time - date.time

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMs)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMs)
        val days = TimeUnit.MILLISECONDS.toDays(diffMs)

        when {
            minutes < 1 -> "now"
            minutes < 60 -> "${minutes}m ago"
            hours < 24 -> "${hours}h ago"
            days < 7 -> "${days}d ago"
            else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
        }
    } catch (e: Exception) {
        ""
    }
}
