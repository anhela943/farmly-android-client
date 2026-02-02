package com.example.proba.activity.product

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import com.example.proba.data.model.response.CategoryItem
import com.example.proba.util.Resource
import com.example.proba.viewmodel.ProductAddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddView(
    navController: NavController,
    onBackClick: () -> Unit,
    productAddViewModel: ProductAddViewModel
) {
    val context = LocalContext.current
    var productName by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<String?>(null) }

    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedCategoryName by rememberSaveable { mutableStateOf("") }

    val categoriesState by productAddViewModel.categories.collectAsState()
    val createState by productAddViewModel.createProductState.collectAsState()

    val isLoading = createState is Resource.Loading

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri.toString()
        }
    }

    LaunchedEffect(createState) {
        when (val state = createState) {
            is Resource.Success -> {
                Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
                onBackClick()
            }
            is Resource.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
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
                val cardMinHeight = maxOf(screenHeight - 140.dp, 560.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = cardMinHeight),
                        shape = RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.greenBackground)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(start = 20.dp, end = 20.dp, top = 38.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))

                            ProductAddField(
                                label = "Product Name",
                                value = productName,
                                onValueChange = { productName = it },
                                placeholder = "Enter product name"
                            )

                            ProductPriceField(
                                value = price,
                                onValueChange = { price = it }
                            )

                            ProductCategoryField(
                                categoriesState = categoriesState,
                                selectedCategoryName = selectedCategoryName,
                                onCategorySelected = { category ->
                                    selectedCategory = category.id
                                    selectedCategoryName = category.name
                                }
                            )

                            ProductAddField(
                                label = "Description",
                                value = description,
                                onValueChange = { description = it },
                                placeholder = "Describe the product",
                                singleLine = false,
                                minLines = 4
                            )

                            ProductPictureField(
                                imageUri = selectedImageUri?.let { Uri.parse(it) },
                                onPickImage = { imagePickerLauncher.launch("image/*") }
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            ProductPrimaryButton(
                                text = if (isLoading) "" else "Add product",
                                containerColor = colorResource(R.color.darkGreenTxt),
                                contentColor = colorResource(R.color.white),
                                enabled = !isLoading,
                                onClick = {
                                    val priceValue = price.toDoubleOrNull()
                                    val categoryIdValue = selectedCategory?.toIntOrNull()
                                    val imageUri = selectedImageUri?.let { Uri.parse(it) }

                                    if (productName.isBlank()) {
                                        Toast.makeText(context, "Please enter a product name", Toast.LENGTH_SHORT).show()
                                        return@ProductPrimaryButton
                                    }
                                    if (priceValue == null || priceValue <= 0) {
                                        Toast.makeText(context, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                                        return@ProductPrimaryButton
                                    }
                                    if (categoryIdValue == null) {
                                        Toast.makeText(context, "Please select a category", Toast.LENGTH_SHORT).show()
                                        return@ProductPrimaryButton
                                    }
                                    if (imageUri == null) {
                                        Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                                        return@ProductPrimaryButton
                                    }

                                    productAddViewModel.createProduct(
                                        name = productName,
                                        description = description,
                                        price = priceValue,
                                        categoryId = categoryIdValue,
                                        imageUri = imageUri,
                                        context = context
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                isLoading = isLoading
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCategoryField(
    categoriesState: Resource<List<CategoryItem>>,
    selectedCategoryName: String,
    onCategorySelected: (CategoryItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = when (categoriesState) {
        is Resource.Success -> categoriesState.data
        else -> emptyList()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Category",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
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
                    value = selectedCategoryName,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = if (categoriesState is Resource.Loading) "Loading categories..." else "Select a category",
                            fontSize = 13.sp,
                            color = colorResource(R.color.grey)
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
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
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductAddField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
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
                placeholder = {
                    if (placeholder != null) {
                        Text(text = placeholder, fontSize = 13.sp, color = colorResource(R.color.grey))
                    }
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
private fun ProductPriceField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Price",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
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
                    singleLine = true,
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

            Box(
                modifier = Modifier
                    .height(54.dp)
                    .width(64.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "RSD",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.darkGreenTxt)
                )
            }
        }
    }
}

@Composable
private fun ProductPictureField(
    imageUri: Uri?,
    onPickImage: () -> Unit
) {
    val context = LocalContext.current
    val imageBitmap = remember(imageUri) {
        imageUri?.let { uri ->
            val resolver = context.contentResolver
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(resolver, uri)
                ImageDecoder.decodeBitmap(source).asImageBitmap()
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(resolver, uri).asImageBitmap()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Picture",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onPickImage() },
            contentAlignment = Alignment.Center
        ) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Selected picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "Add Picture",
                    fontSize = 14.sp,
                    color = colorResource(R.color.grey)
                )
            }
        }
    }
}

@Composable
private fun ProductPrimaryButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(13.dp),
        color = if (enabled) containerColor else containerColor.copy(alpha = 0.6f),
        enabled = enabled
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = contentColor,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductAddPreview() {
    ProductAddView(
        navController = rememberNavController(),
        onBackClick = {},
        productAddViewModel = ProductAddViewModel()
    )
}
