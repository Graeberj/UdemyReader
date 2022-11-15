package com.jig.UdemyReader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.jig.UdemyReader.model.MUser
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {

//    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FB", "signInWithEmailAndPassword: yaaay! ${task.result.toString()}")
                        //TODO("take them home")
                        home()
                    } else {
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result.toString()}")

                    }
                }

        } catch (ex: Exception){
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }

    }


    fun createUserWithEmailAndPassword(email: String, password: String, home:() -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        home()
                    } else{
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "", quote = "life awesoem",
            profession = "Android Dev").toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}