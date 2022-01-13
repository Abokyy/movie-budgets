package hu.benefanlabs.moviebudgets.android.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.benefanlabs.moviebudgets.android.styling.colorPrimary
import hu.benefanlabs.moviebudgets.android.styling.colorSecondary

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String,
    searchValue: String,
    leadingIcon: ImageVector = Icons.Filled.Search,
    onSearchValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    OutlinedTextField(
        modifier = modifier,
        value = searchValue,
        onValueChange = onSearchValueChange,
        placeholder = { Text(text = hint) },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.secondary,
            leadingIconColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
            placeholderColor = MaterialTheme.colors.secondary,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(hint = "Keress valamire", searchValue = "")
}