package com.example.proba.activity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.R
import com.example.proba.data.model.response.CategoryItem
import com.example.proba.data.repository.ProductFilters
import com.example.proba.util.Resource
import com.example.proba.viewmodel.CategoryViewModel

@Composable
fun FilterView(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel = viewModel(),
    initialCategoryId: String? = null,
    onFilterApply: (ProductFilters) -> Unit = {}
) {
    val outsideInteraction = remember { MutableInteractionSource() }

    Box(modifier = modifier.fillMaxSize()) {
        if (isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = outsideInteraction,
                        indication = null
                    ) { onDismiss() }
            )
        }

        AnimatedVisibility(
            visible = isOpen,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            FilterPanel(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 12.dp, top = 36.dp, bottom = 90.dp),
                categoryViewModel = categoryViewModel,
                initialCategoryId = initialCategoryId,
                onFilterApply = onFilterApply
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterPanel(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    initialCategoryId: String?,
    onFilterApply: (ProductFilters) -> Unit
) {
    val scrollState = rememberScrollState()
    val categoriesState by categoryViewModel.categories.collectAsState()

    var priceRange by remember { mutableStateOf(0f..5000f) }
    var selectedCategoryId by remember { mutableStateOf<String?>(initialCategoryId) }
    var city by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(0.82f)
            .shadow(14.dp, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomEnd = 30.dp, bottomStart = 30.dp ))
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(colorResource(R.color.greenBackground))
    ) {
        Column(
            modifier = Modifier
                .heightIn(max = maxHeight)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 18.dp, vertical = 20.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.darkGreenTxt)
                )

                IconButton(onClick = {
                    onFilterApply(
                        ProductFilters(
                            city = city.ifBlank { null },
                            priceFrom = priceRange.start,
                            priceTo = priceRange.endInclusive,
                            value = null,
                            categoryId = selectedCategoryId
                        )
                    )
                }) {
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.greenStrokeLight)),
                        border = BorderStroke(1.dp, colorResource(R.color.greenStrokeDark))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.search),
                                contentDescription = "Apply Filter",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Text(
                text = "Price range",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.darkGreenTxt)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ChipLabel(text = "From ${priceRange.start.toInt()}")
                ChipLabel(text = "To ${priceRange.endInclusive.toInt()}")
            }

            RangeSlider(
                value = priceRange,
                onValueChange = { priceRange = it },
                valueRange = 0f..5000f,
                steps = 4999,
                colors = SliderDefaults.colors(
                    activeTrackColor = colorResource(R.color.darkGreenTxt),
                    inactiveTrackColor = colorResource(R.color.greenStrokeDark),
                    activeTickColor = colorResource(R.color.darkGreenTxt),
                    inactiveTickColor = colorResource(R.color.greenStrokeDark),
                    thumbColor = colorResource(R.color.darkGreenTxt)
                )
            )

            Text(
                text = "Categories",
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.darkGreenTxt)
            )

            HorizontalDivider(color = colorResource(R.color.greenStrokeDark), thickness = 1.dp)

            // Categories from API - Single selection
            when (categoriesState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = "Failed to load categories",
                        fontSize = 14.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                }
                is Resource.Success -> {
                    val categories = (categoriesState as Resource.Success<List<CategoryItem>>).data

                    // "All Categories" option to clear selection
                    CategoryRadioItem(
                        name = "All Categories",
                        isSelected = selectedCategoryId == null,
                        onClick = { selectedCategoryId = null }
                    )

                    categories.forEach { category ->
                        CategoryRadioItem(
                            name = category.name,
                            isSelected = selectedCategoryId == category.id,
                            onClick = { selectedCategoryId = category.id }
                        )
                    }
                }
            }

            HorizontalDivider(color = colorResource(R.color.greenStrokeDark), thickness = 1.dp)

            Text(
                text = "City",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.darkGreenTxt)
            )

            CityField(
                value = city,
                onValueChange = { city = it }
            )

            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
private fun CategoryRadioItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(R.color.darkGreenTxt),
                unselectedColor = colorResource(R.color.greenStrokeDark)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            fontSize = 16.sp,
            color = colorResource(R.color.darkGreenTxt)
        )
    }
}

@Composable
private fun ChipLabel(text: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.greenStrokeLight)),
        border = BorderStroke(1.dp, colorResource(R.color.greenStrokeDark))
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.darkGreenTxt)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Chose a city...",
                fontSize = 15.sp
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorResource(R.color.greenStrokeDark),
            unfocusedIndicatorColor = colorResource(R.color.greenStrokeDark),
            focusedContainerColor = colorResource(R.color.greenStrokeLight),
            unfocusedContainerColor = colorResource(R.color.greenStrokeLight),
            cursorColor = colorResource(R.color.darkGreenTxt)
        ),
        shape = RoundedCornerShape(20.dp),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
private fun FilterViewPreview() {
    FilterView(
        isOpen = true,
        onDismiss = {},
        onFilterApply = {}
    )
}
