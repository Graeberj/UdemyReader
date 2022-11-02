package com.jig.UdemyReader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jig.UdemyReader.R
import com.jig.UdemyReader.components.EmailInput
import com.jig.UdemyReader.components.PasswordInput
import com.jig.UdemyReader.components.ReaderLogo

@Composable
fun Login(navController: NavController) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()
            if (showLoginForm.value) UserForm(isLoading = false, isCreateAccount = false)
            { email, password -> //todo add login
            }
            else {
                UserForm(isLoading = false, isCreateAccount = true){ email, password ->
                    //todo create account
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ){
                val loginText = if(showLoginForm.value) "Sign up" else "Login"
                val newUserText = if(showLoginForm.value) "New User?" else "Already have an account?"
                Text(text = newUserText)
                Text(text = loginText,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }

    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    isLoading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if(isCreateAccount) {
            Text(
                text = stringResource(id = R.string.create_acct),
                modifier = Modifier.padding(4.dp)
            )
        } else {
            Text(text = stringResource(R.string.sign_in))
        }
    EmailInput(emailState = email, enabled = !isLoading, onAction = KeyboardActions {
        passwordFocusRequest.requestFocus()
    })
    PasswordInput(
        modifier = Modifier.focusRequester(passwordFocusRequest),
        passwordState = password,
        labelId = "password",
        enabled = !isLoading,
        passwordVisibility = passwordVisibility,
        onAction = KeyboardActions{
            if (!valid) return@KeyboardActions
            onDone(email.value.trim(), password.value.trim())
        }
    ) }
    SubmitButton(
        textId = if (isCreateAccount) "Create Account" else "Login",
        isLoading = isLoading,
        validInputs = valid
    ){
        onDone(email.value.trim(), password.value.trim())
        keyboardController?.hide()
    }

}

@Composable
fun SubmitButton(
    textId: String,
    isLoading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
Button(onClick = onClick,
    modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
    enabled = !isLoading &&validInputs,
    shape = CircleShape
    ){
    if (isLoading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
    else Text(text = textId, Modifier.padding(5.dp))
}
}

