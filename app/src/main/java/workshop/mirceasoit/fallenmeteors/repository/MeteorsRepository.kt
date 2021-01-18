package workshop.mirceasoit.fallenmeteors.repository

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import workshop.mirceasoit.fallenmeteors.api.MeteorsApi
import workshop.mirceasoit.fallenmeteors.model.Meteor
import workshop.mirceasoit.fallenmeteors.viewmodel.MeteorsViewModel

class MeteorsRepository {

    interface DataResponseCallback {
        fun onSuccess(meteors: List<Meteor>)
        fun onError(error: String)
    }

    private var dataResponseCallback: DataResponseCallback? = null
    private var disposable: Disposable? = null

   fun getMeteors(dataResponseCallback: DataResponseCallback) {
        MeteorsApi.retrofitService.getMeteors().enqueue(object :
            retrofit2.Callback<List<Meteor>> {
            override fun onResponse(call: Call<List<Meteor>>, response: Response<List<Meteor>>) {
                val data: List<Meteor> = response.body()!!
                dataResponseCallback.onSuccess(data)
            }

            override fun onFailure(call: Call<List<Meteor>>, t: Throwable) {
                dataResponseCallback.onError(t.message!!)
            }
        })
    }

    fun getMeteorsWithRxJava(dataResponseCallback: DataResponseCallback) {
        this.dataResponseCallback = dataResponseCallback
        val observable = MeteorsApi.retrofitService.getMeteorsWithRxJava()
        disposable = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (this::handleResults, this::handleError)
    }

    private fun handleResults(list: List<Meteor>) {
        dataResponseCallback!!.onSuccess(list)
        disposable!!.dispose()
    }

    private fun handleError(t: Throwable) {
        dataResponseCallback!!.onError(t.message!!)
        disposable!!.dispose()
    }

    suspend fun getMeteorsWithSuspendFunction(): MeteorsViewModel.State {
        return try {
            val response = MeteorsApi.retrofitService.getMeteorsWithSuspendFunction()
            MeteorsViewModel.State.Success(response)
        } catch (throwable: Throwable) {
            MeteorsViewModel.State.Error(throwable.message!!)
        }
    }

}