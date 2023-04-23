package com.example.chatapp.presentation.user_profile

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
            getCurrentUserData(currentUserUID)
        }
    }

    fun onEvent(event: UserProfileEvent) {
        when(event) {
            is UserProfileEvent.EditSectionVisibilityChange -> {
                _userProfileState.value = userProfileState.value.copy(
                    isEditSectionVisible = !_userProfileState.value.isEditSectionVisible
                )
            }
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
            is UserProfileEvent.SelectedProfilePicture -> {
                _userProfileState.value = userProfileState.value.copy(
                    profilePictureUri = event.value!!,
                    profilePictureUrl = event.value.path.toString(),
                    wasProfilePictureChanged = true
                )
            }
            is UserProfileEvent.Save -> {
                val firstName = _userProfileState.value.firstName
                val lastName = _userProfileState.value.lastName

                if (isValidationSuccessful(firstName, lastName)) {
                    val wasProfilePictureChanged = _userProfileState.value.wasProfilePictureChanged
                    if(wasProfilePictureChanged) {

                    }
                    else {
                        val user = User(
                            userUID = _userProfileState.value.currentUserUID,
                            firstName = _userProfileState.value.firstName,
                            lastName = _userProfileState.value.lastName,
                            profilePictureUrl = _userProfileState.value.profilePictureUrl
                        )
                        updateUserInfo(user)
                    }
                    Log.i("TAG","Profile Updated")
                }
                else {
                    Log.i("TAG", "Error while updating user profile")
                }
            }
            is UserProfileEvent.SaveNewProfilePicture -> {
                viewModelScope.launch {
                    _eventFlow.emit(UserProfileUiEvent.SaveNewProfilePicture)
                }
                Log.i("TAG","Profile Picture Updated")
            }
        }
    }

    fun getCurrentUserData(currentUserUID: String) {
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
                        Log.i("TAG",response.message)
                    }
                }
            }
        }
    }

    fun updateUserInfo(user: User) {
        viewModelScope.launch {
            _userProfileState.value = userProfileState.value.copy(
                updateUserInfoResponse = Resource.Loading
            )

            _userProfileState.value = userProfileState.value.copy(
                updateUserInfoResponse = chatUseCases.updateUserInfoUseCase(user)
            )

            when(val updateResponse = _userProfileState.value.updateUserInfoResponse) {
                is Resource.Success -> {
                    Log.i("TAG", "Update Successful")
                    _eventFlow.emit(UserProfileUiEvent.Save)
                }
                is Resource.Error -> {
                    val errorMessage = updateResponse.message
                    Log.i("TAG", "Update Error")
                    _eventFlow.emit(UserProfileUiEvent.ShowErrorMessage(errorMessage))
                }
                else -> {
                    Log.i("TAG", "Loading...")
                }
            }

        }
    }

    fun isValidationSuccessful(
        firstName: String,
        lastName: String
    ): Boolean {
        val firstNameValidationResult = chatUseCases.validateNameUseCase(firstName)
        val lastNameValidationResult = chatUseCases.validateNameUseCase(lastName)

        val hasError = listOf(
            firstNameValidationResult,
            lastNameValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _userProfileState.value = userProfileState.value.copy(
                firstNameError = firstNameValidationResult.errorMessage,
                lastNameError = lastNameValidationResult.errorMessage
            )
            return false
        }
        return true
    }
}