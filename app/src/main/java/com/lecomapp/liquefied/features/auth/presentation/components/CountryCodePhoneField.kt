package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.component.CountryCodePicker
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.feedback.PickerOption
import com.lecomapp.liquefied.core.ui.components.feedback.SelectionPickerSheet
import com.lecomapp.liquefied.core.ui.components.inputs.AppTextField
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@OptIn(RestrictedApi::class)
@Composable
fun CountryCodePhoneField(
    pickerState: CountryCodePicker,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    var showCountrySheet by remember { mutableStateOf(false) }

    val selectedCountry = remember(pickerState.countryCode) {
        pickerState.countryList.find { it.code.equals(pickerState.countryCode, ignoreCase = true) }
            ?: pickerState.countryList.first()
    }

    if (showCountrySheet) {
        val countries = pickerState.countryList
        SelectionPickerSheet(
            title = stringResource(R.string.picker_select_country),
            options = countries.map { country ->
                PickerOption(
                    label = country.name,
                    searchKeys = listOf(country.phoneNoCode),
                    flagRes = country.flag,
                    selected = country.code.equals(pickerState.countryCode, ignoreCase = true),
                )
            },
            onSelect = { index ->
                pickerState.setCode(countries[index].code)
                showCountrySheet = false
            },
            onDismissRequest = { showCountrySheet = false },
        )
    }

    AppTextField(
        value = text,
        onValueChange = onValueChange,
        label = stringResource(R.string.register_phone),
        placeholder = stringResource(R.string.register_phone_hint),
        isError = isError,
        errorMessage = errorMessage,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next,
        ),
        leadingContent = {
            Row(
                modifier = Modifier
                    .clickable { showCountrySheet = true }
                    .padding(horizontal = AppSpacing.xs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(selectedCountry.flag),
                    contentDescription = null,
                    modifier = Modifier.size(width = 28.dp, height = 18.dp),
                )

                Spacer(modifier = Modifier.width(AppSpacing.xs))

                Text(
                    text = selectedCountry.phoneNoCode,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        modifier = modifier,
    )
}
