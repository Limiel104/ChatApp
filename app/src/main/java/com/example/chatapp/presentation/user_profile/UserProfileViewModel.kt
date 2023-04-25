package com.example.chatapp.presentation.user_profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.use_case.ChatUseCases
import com.example.chatapp.util.Constants
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _userProfileState = mutableStateOf(UserProfileState())
    val userProfileState: State<UserProfileState> = _userProfileState

    private val _eventFlow = MutableSharedFlow<UserProfileUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i("TAG", "UserProfileViewModel")
        savedStateHandle.get<String>(Constants.USER_UID)?.let { currentUserUID ->
            _userProfileState.value = userProfileState.value.copy(
                currentUserUID = currentUserUID
            )
            getCurrentUserProfileInfoData(currentUserUID)
        }
        getCurrentUserLoginData()
    }

    fun onEvent(event: UserProfileEvent) {
        when(event) {
            is UserProfileEvent.EnteredFirstName -> {
                _userProfileState.value = userProfileState.value.copy(
                    firstName = event.value
                )
            }
            is UserProfileEvent.EnteredLastName -> {
                _userProfileState.value = userProfileState.value.copy(
                    lastName = event.value
                )
            }
            is UserProfileEvent.EnteredEmail -> {
                _userProfileState.value = userProfileState.value.copy(
                    email = event.value
                )
            }
            is UserProfileEvent.EnteredPassword -> {
                _userProfileState.value = userProfileState.value.copy(
                    password = event.value
                )
            }
            is UserProfileEvent.EnteredConfirmPassword -> {
                _userProfileState.value = userProfileState.value.copy(
                    confirmPassword = event.value
                )
            }
            is UserProfileEvent.SelectedProfilePicture -> {
                _userProfileState.value = userProfileState.value.copy(
                    profilePictureUri = event.value!!,
                    profilePictureUrl = event.value.path.toString(),
                    wasProfilePictureChanged = true
                )
            }
            is UserProfileEvent.EditProfileInfoVisibilityChange -> {
                _userProfileState.value = userProfileState.value.copy(
                    isEditProfileInfoVisible = !_userProfileState.value.isEditProfileInfoVisible
                )
            }
            is UserProfileEvent.EditEmailVisibilityChange -> {
                _userProfileState.value = userProfileState.value.copy(
                    isEditEmailVisible = !_userProfileState.value.isEditEmailVisible
                )
            }
            is UserProfileEvent.EditPasswordVisibilityChange -> {
                _userProfileState.value = userProfileState.value.copy(
                    isEditPasswordVisible = !_userProfileState.value.isEditPasswordVisible
                )
            }
            is UserProfileEvent.Save -> {
                if(isValidationSuccessful()) {
                    updateUser()
                }
                else {
                    Log.i("TAG", "Error while updating user profile")
                }
            }
        }
    }

    fun getCurrentUserProfileInfoData(currentUserUID: String) {
        viewModelScope.launch {
            chatUseCases.getUserUseCase(currentUserUID).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        val user = response.result[0]
                        _userProfileState.value = userProfileState.value.copy(
                            name = user.firstName + " " + user.lastName,
                            firstName = user.firstName,
                            lastName = user.lastName,
                            profilePictureUrl = user.profilePictureUrl
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Log.i("TAG", response.message)
                    }
                }
            }
        }
    }

    fun getCurrentUserLoginData() {
        _userProfileState.value = userProfileState.value.copy(
            email = chatUseCases.getCurrentUserUseCase()!!.email!!
        )
    }

    fun updateUser() {
        val wasProfilePictureChanged = _userProfileState.value.wasProfilePictureChanged
        val wasEmailChanged = _userProfileState.value.isEditEmailVisible
        val wasPasswordChanged = _userProfileState.value.isEditPasswordVisible

        viewModelScope.launch {
            if(wasProfilePictureChanged) {
                val imageUri = _userProfileState.value.profilePictureUri
                val userUID = _userProfileState.value.currentUserUID
                updateProfilePicture(userUID, imageUri)
            }

            if(wasEmailChanged) {
                val email = _userProfileState.value.email
                updateUserEmail(email)
            }
            else if(wasPasswordChanged) {
                val password = _userProfileState.value.password
                updateUserPassword(password)
            }
            else if(!wasProfilePictureChanged) {
                updateUserProfileInfo()
            }

            _eventFlow.emit(UserProfileUiEvent.Save)
        }
    }

    suspend fun updateProfilePicture(userUID: String, imageUri: Uri) {
        _userProfileState.value = userProfileState.value.copy(
            updateProfilePictureResponse = Resource.Loading
        )

        _userProfileState.value = userProfileState.value.copy(
            updateProfilePictureResponse = chatUseCases.addImageUseCase(userUID, imageUri)
        )

        when(val updateResponse = _userProfileState.value.updateProfilePictureResponse) {
            is Resource.Success -> {
                Log.i("TAG", "Update Photo Successful")
                val imageUrl = updateResponse.result
                _userProfileState.value = userProfileState.value.copy(
                    profilePictureUrl = imageUrl.toString()
                )
            }
            is Resource.Error -> {
                Log.i("TAG", "Update Photo Error")
                val errorMessage = updateResponse.message
                _eventFlow.emit(UserProfileUiEvent.ShowErrorMessage(errorMessage))
            }
            else -> {
                Log.i("TAG", "Loading...")
            }
        }
        updateUserProfileInfo()
    }

    suspend fun updateUserProfileInfo() {
        _userProfileState.value = userProfileState.value.copy(
            updateUserResponse = Resource.Loading
        )

        val user = User(
            userUID = _userProfileState.value.currentUserUID,
            firstName = _userProfileState.value.firstName,
            lastName = _userProfileState.value.lastName,
            profilePictureUrl = _userProfileState.value.profilePictureUrl
        )

        _userProfileState.value = userProfileState.value.copy(
            updateUserResponse = chatUseCases.updateUserInfoUseCase(user)
        )

        when(val updateResponse = _userProfileState.value.updateUserResponse) {
            is Resource.Success -> {
                Log.i("TAG", "Update Info Successful")
            }
            is Resource.Error -> {
                Log.i("TAG", "Update Info Error")
                val errorMessage = updateResponse.message
                _eventFlow.emit(UserProfileUiEvent.ShowErrorMessage(errorMessage))
            }
            else -> {
                Log.i("TAG", "Loading...")
            }
        }
    }

    suspend fun updateUserEmail(email: String) {
        _userProfileState.value = userProfileState.value.copy(
            updateUserResponse = Resource.Loading
        )

        _userProfileState.value = userProfileState.value.copy(
            updateUserResponse = chatUseCases.updateUserEmailUseCase(email)
        )

        when(val updateResponse = _userProfileState.value.updateUserResponse) {
            is Resource.Success -> {
                Log.i("TAG", "Update Email Successful")
            }
            is Resource.Error -> {
                Log.i("TAG", "Update Email Error")
                val errorMessage = updateResponse.message
                _eventFlow.emit(UserProfileUiEvent.ShowErrorMessage(errorMessage))
            }
            else -> {
                Log.i("TAG", "Loading...")
            }
        }
    }

    suspend fun updateUserPassword(password: String) {
        _userProfileState.value = userProfileState.value.copy(
            updateUserResponse = Resource.Loading
        )

        _userProfileState.value = userProfileState.value.copy(
            updateUserResponse = chatUseCases.updateUserPasswordUseCase(password)
        )

        when(val updateResponse = _userProfileState.value.updateUserResponse) {
            is Resource.Success -> {
                Log.i("TAG", "Update Password Successful")
            }
            is Resource.Error -> {
                Log.i("TAG", "Update Password Error")
                val errorMessage = updateResponse.message
                _eventFlow.emit(UserProfileUiEvent.ShowErrorMessage(errorMessage))
            }
            else -> {
                Log.i("TAG", "Loading...")
            }
        }
    }

    fun isValidationSuccessful(): Boolean {
        val firstName = _userProfileState.value.firstName
        val lastName = _userProfileState.value.lastName
        val email = _userProfileState.value.email
        val password = _userProfileState.value.password
        val confirmPassword = _userProfileState.value.confirmPassword
        val wasPasswordChanged = _userProfileState.value.isEditPasswordVisible

        return if(wasPasswordChanged) {
            isNameAndEmailValidationSuccessful(firstName, lastName, email) &&
                    isPasswordValidationSuccessful(password, confirmPassword)
        }
        else {
            isNameAndEmailValidationSuccessful(firstName, lastName, email)
        }
    }


    fun isNameAndEmailValidationSuccessful(firstName: String, lastName: String, email: String, ): Boolean {
        val firstNameValidationResult = chatUseCases.validateNameUseCase(firstName)
        val lastNameValidationResult = chatUseCases.validateNameUseCase(lastName)
        val emailValidationResult = chatUseCases.validateEmailUseCase(email)

        val hasError = listOf(
            firstNameValidationResult,
            lastNameValidationResult,
            emailValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _userProfileState.value = userProfileState.value.copy(
                firstNameError = firstNameValidationResult.errorMessage,
                lastNameError = lastNameValidationResult.errorMessage,
                emailError = emailValidationResult.errorMessage
            )
            return false
        }
        return true
    }

    fun isPasswordValidationSuccessful(password: String, confirmPassword: String): Boolean {
        val passwordValidationResult = chatUseCases.validateSignupPasswordUseCase(password)
        val confirmPasswordValidationResult = chatUseCases.validateConfirmPasswordUseCase(password, confirmPassword)

        val hasError = listOf(
            passwordValidationResult,
            confirmPasswordValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _userProfileState.value = userProfileState.value.copy(
                passwordError = passwordValidationResult.errorMessage,
                confirmPasswordError = confirmPasswordValidationResult.errorMessage
            )
            return false
        }
        return true
    }
}