package workshop.mirceasoit.fallenmeteors.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import workshop.mirceasoit.fallenmeteors.model.Meteor
import workshop.mirceasoit.fallenmeteors.repository.MeteorsRepository

class MeteorsViewModel (private val meteorsRepository: MeteorsRepository) : ViewModel() {

    private var _meteors = MutableLiveData<State>()
    var meteors: LiveData<State> = _meteors


    val getMeteorsCallback: MeteorsRepository.DataResponseCallback =
        object : MeteorsRepository.DataResponseCallback {
            override fun onSuccess(meteors: List<Meteor>) {
                _meteors.postValue(State.Success(meteors))
            }

            override fun onError(error: String) {
                _meteors.postValue(State.Error(error))
            }

        }

    fun getMeteors() {
        _meteors.value = State.Loading
        meteorsRepository.getMeteors(getMeteorsCallback)
    }

    fun getMeteorsWithRxJava() {
        _meteors.value = State.Loading
        meteorsRepository.getMeteorsWithRxJava(getMeteorsCallback)
    }

    fun getMeteorsWithSuspendFunction() {
        _meteors.value = State.Loading
        viewModelScope.launch {
            _meteors.value = meteorsRepository.getMeteorsWithSuspendFunction()
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val meteors: List<Meteor>) : State()
        data class Error(val error: String) : State()

        fun isLoading() = this is Loading
        fun isSuccess() = this is Success
        fun isError() = this is Error
    }

}