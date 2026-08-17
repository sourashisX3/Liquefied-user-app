package com.lecomapp.liquefied.features.profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.common.AppHeaderContainer
import com.lecomapp.liquefied.core.ui.components.inputs.AppPickerField
import com.lecomapp.liquefied.core.ui.components.inputs.AppTextField
import com.lecomapp.liquefied.core.ui.components.feedback.PickerOption
import com.lecomapp.liquefied.core.ui.components.feedback.SelectionPickerSheet
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UpdateProfileRequest
import com.lecomapp.liquefied.features.auth.presentation.components.CountryCodePhoneField
import com.lecomapp.liquefied.features.profile.presentation.components.LocationOptions
import com.lecomapp.liquefied.features.profile.presentation.viewmodels.EditProfileViewModel
import kotlinx.coroutines.launch

@OptIn(RestrictedApi::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val cachedProfile by viewModel.cachedProfile.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val saveError by viewModel.saveError.collectAsStateWithLifecycle()
    val saveSuccess by viewModel.saveSuccess.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackBarHostState.current
    val scope = rememberCoroutineScope()

    val pickerState = rememberKomposeCountryCodePickerState(defaultCountryCode = "IN")

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var addressLine1 by remember { mutableStateOf("") }
    var addressLine2 by remember { mutableStateOf("") }
    var streetAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var stateName by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    var initialized by remember { mutableStateOf(false) }
    var activePicker by remember { mutableStateOf<LocationPicker?>(null) }

    val countryOptions = remember(pickerState.countryList) {
        pickerState.countryList.map { it.name }.distinct()
    }

    LaunchedEffect(cachedProfile) {
        val profile = cachedProfile ?: return@LaunchedEffect
        if (initialized) return@LaunchedEffect
        firstName = profile.firstName
        lastName = profile.lastName.orEmpty()
        phoneNumber = profile.phoneNumber.orEmpty()
        addressLine1 = profile.addressLine1.orEmpty()
        addressLine2 = profile.addressLine2.orEmpty()
        streetAddress = profile.streetAddress.orEmpty()
        city = profile.city.orEmpty()
        stateName = profile.state.orEmpty()
        country = profile.country.orEmpty()
        zipCode = profile.zipCode?.toString().orEmpty()
        profile.dialCode?.let { dialCode ->
            pickerState.countryList.find { it.phoneNoCode == dialCode }?.let { country ->
                pickerState.setCode(country.code)
            }
        }
        initialized = true
    }

    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            viewModel.consumeSuccess()
            scope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.edit_profile_saved))
            }
            onBack()
        }
    }

    LaunchedEffect(saveError) {
        saveError?.let { snackbarHostState.showSnackbar(it.asString(context)) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AppHeaderContainer {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = AppSpacing.xs),
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.edit_profile_back),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Spacer(modifier = Modifier.width(AppSpacing.xs))
                Text(
                    text = stringResource(R.string.edit_profile_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.lg),
        ) {
            FormCard(title = stringResource(R.string.edit_profile_personal_section)) {
                AppTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = stringResource(R.string.register_first_name),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = stringResource(R.string.register_last_name),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                CountryCodePhoneField(
                    pickerState = pickerState,
                    text = phoneNumber,
                    onValueChange = { phoneNumber = it },
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppTextField(
                    value = cachedProfile?.email.orEmpty(),
                    onValueChange = {},
                    label = stringResource(R.string.register_email),
                    enabled = false,
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.lg))

            FormCard(title = stringResource(R.string.edit_profile_address_section)) {
                AppTextField(
                    value = addressLine1,
                    onValueChange = { addressLine1 = it },
                    label = stringResource(R.string.edit_profile_address_line1),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppTextField(
                    value = addressLine2,
                    onValueChange = { addressLine2 = it },
                    label = stringResource(R.string.edit_profile_address_line2),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppTextField(
                    value = streetAddress,
                    onValueChange = { streetAddress = it },
                    label = stringResource(R.string.edit_profile_street),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppPickerField(
                    value = city,
                    onClick = { activePicker = LocationPicker.CITY },
                    label = stringResource(R.string.edit_profile_city),
                    placeholder = stringResource(R.string.edit_profile_select_city),
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppPickerField(
                    value = stateName,
                    onClick = { activePicker = LocationPicker.STATE },
                    label = stringResource(R.string.edit_profile_state),
                    placeholder = stringResource(R.string.edit_profile_select_state),
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppPickerField(
                    value = country,
                    onClick = { activePicker = LocationPicker.COUNTRY },
                    label = stringResource(R.string.edit_profile_country),
                    placeholder = stringResource(R.string.edit_profile_select_country),
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))
                AppTextField(
                    value = zipCode,
                    onValueChange = { newValue ->
                        if (newValue.length <= 10 && newValue.all { it.isDigit() }) {
                            zipCode = newValue
                        }
                    },
                    label = stringResource(R.string.edit_profile_zip),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.xl))

            AppButton(
                onClick = {
                    viewModel.save(
                        UpdateProfileRequest(
                            firstName = firstName.trim().ifBlank { null },
                            lastName = lastName.trim().ifBlank { null },
                            dialCode = pickerState.getCountryPhoneCode(),
                            phoneNumber = phoneNumber.trim().ifBlank { null },
                            addressLine1 = addressLine1.trim().ifBlank { null },
                            addressLine2 = addressLine2.trim().ifBlank { null },
                            streetAddress = streetAddress.trim().ifBlank { null },
                            city = city.trim().ifBlank { null },
                            state = stateName.trim().ifBlank { null },
                            country = country.trim().ifBlank { null },
                            zipCode = zipCode.toLongOrNull(),
                        ),
                    )
                },
                text = stringResource(R.string.edit_profile_save),
                isLoading = isSaving,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AppSpacing.xxl))
        }
    }

    when (activePicker) {
        LocationPicker.CITY -> SelectionPickerSheet(
            title = stringResource(R.string.edit_profile_city),
            options = LocationOptions.cities.map { option ->
                PickerOption(label = option, selected = option == city)
            },
            onSelect = { index ->
                city = LocationOptions.cities[index]
                activePicker = null
            },
            onDismissRequest = { activePicker = null },
        )
        LocationPicker.STATE -> SelectionPickerSheet(
            title = stringResource(R.string.edit_profile_state),
            options = LocationOptions.states.map { option ->
                PickerOption(label = option, selected = option == stateName)
            },
            onSelect = { index ->
                stateName = LocationOptions.states[index]
                activePicker = null
            },
            onDismissRequest = { activePicker = null },
        )
        LocationPicker.COUNTRY -> SelectionPickerSheet(
            title = stringResource(R.string.edit_profile_country),
            options = countryOptions.map { option ->
                PickerOption(label = option, selected = option == country)
            },
            onSelect = { index ->
                country = countryOptions[index]
                activePicker = null
            },
            onDismissRequest = { activePicker = null },
        )
        null -> Unit
    }
}

private enum class LocationPicker { CITY, STATE, COUNTRY }

@Composable
private fun FormCard(
    title: String,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppCornerRadius.large),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = AppSpacing.xs, vertical = AppSpacing.lg),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(AppSpacing.md))
            content()
        }
    }
}