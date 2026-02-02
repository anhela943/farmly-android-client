package com.example.proba.activity.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import com.example.proba.util.Resource
import com.example.proba.viewmodel.ProfileViewModel
import android.webkit.MimeTypeMap
import java.io.File

@Composable
fun ProfileCreateView(
    navController: NavController,
    onBackClick: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val profileState = profileViewModel.profileState
    val updateState = profileViewModel.updateProfileState

    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<String?>(null) }
    var fieldsInitialized by rememberSaveable { mutableStateOf(false) }

    var originalFullName by rememberSaveable { mutableStateOf("") }
    var originalEmail by rememberSaveable { mutableStateOf("") }
    var originalCity by rememberSaveable { mutableStateOf("") }
    var originalPhoneNumber by rememberSaveable { mutableStateOf("") }
    var originalDescription by rememberSaveable { mutableStateOf("") }

    var profileName by rememberSaveable { mutableStateOf("") }
    var currentImageUrl by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(profileState) {
        if (profileState is Resource.Success && !fieldsInitialized) {
            val profile = profileState.data
            fullName = profile.fullName
            email = profile.email
            city = profile.city ?: ""
            phoneNumber = profile.phoneNumber ?: ""
            description = profile.description ?: ""
            profileName = profile.fullName
            currentImageUrl = profile.imageUrl
            originalFullName = profile.fullName
            originalEmail = profile.email
            originalCity = profile.city ?: ""
            originalPhoneNumber = profile.phoneNumber ?: ""
            originalDescription = profile.description ?: ""
            fieldsInitialized = true
        }
    }

    LaunchedEffect(updateState) {
        if (updateState is Resource.Success) {
            profileViewModel.clearUpdateState()
            onBackClick()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri.toString()
        }
    }

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

            when (profileState) {
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
                        Text(
                            text = profileState.message,
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }

                is Resource.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
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
                                        .padding(
                                            start = 20.dp,
                                            end = 20.dp,
                                            bottom = 15.dp
                                        ),
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

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        ProfileCreateField(
                                            label = "Full Name",
                                            value = fullName,
                                            onValueChange = { fullName = it }
                                        )
                                        ProfileCreateField(
                                            label = "Email",
                                            value = email,
                                            onValueChange = { email = it }
                                        )
                                        ProfileCreateField(
                                            label = "City",
                                            value = city,
                                            onValueChange = { city = it }
                                        )
                                        ProfileCreateField(
                                            label = "Phone Number",
                                            value = phoneNumber,
                                            onValueChange = { phoneNumber = it },
                                            leadingIcon = {
                                                Box(
                                                    modifier = Modifier
                                                        .size(12.dp)
                                                        .background(
                                                            color = colorResource(R.color.darkGreenTxt),
                                                            shape = CircleShape
                                                        )
                                                )
                                            }
                                        )
                                        ProfileCreateField(
                                            label = "Description",
                                            value = description,
                                            onValueChange = { description = it },
                                            singleLine = false,
                                            minLines = 3
                                        )
                                        ProfileCreateField(
                                            label = "Set Password",
                                            value = password,
                                            onValueChange = { password = it },
                                            isPassword = true
                                        )
                                    }

                                    if (updateState is Resource.Error) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = updateState.message,
                                            color = Color.Red,
                                            fontSize = 14.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    ProfilePrimaryButton(
                                        text = if (updateState is Resource.Loading) "Saving..." else "Save changes",
                                        containerColor = colorResource(R.color.darkGreenTxt),
                                        contentColor = colorResource(R.color.white),
                                        onClick = {
                                            if (updateState !is Resource.Loading) {
                                                var imageMimeType: String? = null
                                                val imageFile = selectedImageUri?.let { uriStr ->
                                                    val uri = Uri.parse(uriStr)
                                                    imageMimeType = context.contentResolver.getType(uri)
                                                    val extension = MimeTypeMap.getSingleton()
                                                        .getExtensionFromMimeType(imageMimeType) ?: "jpg"
                                                    val inputStream = context.contentResolver.openInputStream(uri)
                                                    inputStream?.let { stream ->
                                                        val tempFile = File.createTempFile("profile_img", ".$extension", context.cacheDir)
                                                        tempFile.outputStream().use { output ->
                                                            stream.copyTo(output)
                                                        }
                                                        stream.close()
                                                        tempFile
                                                    }
                                                }
                                                profileViewModel.updateProfile(
                                                    fullName = if (fullName != originalFullName) fullName else null,
                                                    email = if (email != originalEmail) email else null,
                                                    password = if (password.isNotBlank()) password else null,
                                                    phoneNumber = if (phoneNumber != originalPhoneNumber) phoneNumber else null,
                                                    city = if (city != originalCity) city else null,
                                                    description = if (description != originalDescription) description else null,
                                                    imageFile = imageFile,
                                                    imageMimeType = imageMimeType
                                                )
                                            }
                                        },
                                        modifier = actionButtonModifier
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .size(132.dp)
                                    .offset(y = (-48).dp)
                                    .clickable { imagePickerLauncher.launch("image/*") },
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(6.dp),
                                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
                            ) {
                                if (selectedImageUri != null) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data(Uri.parse(selectedImageUri))
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Profile image",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else if (currentImageUrl != null) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data(currentImageUrl)
                                            .crossfade(true)
                                            .build(),
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
                }
            }
        }
    }
}

@Composable
private fun ProfileCreateField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    leadingIcon: (@Composable (() -> Unit))? = null
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                minLines = minLines,
                leadingIcon = leadingIcon,
                visualTransformation = if (isPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = colorResource(R.color.black),
                    unfocusedTextColor = colorResource(R.color.black),
                    cursorColor = colorResource(R.color.darkGreenTxt)
                )
            )
        }
    }
}

@Composable
private fun ProfilePrimaryButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
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
                color = contentColor
            )
        }
    }
}
