package workshop.mirceasoit.fallenmeteors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import workshop.mirceasoit.fallenmeteors.model.GeoLocation
import workshop.mirceasoit.fallenmeteors.model.Meteor
import workshop.mirceasoit.fallenmeteors.repository.MeteorsRepository
import workshop.mirceasoit.fallenmeteors.viewmodel.MeteorsViewModel


@RunWith(MockitoJUnitRunner::class)
internal class MeteorsViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
        const val ERROR_MESSAGE = "This is an error message"
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

    @MockK(relaxUnitFun = true)
    lateinit var meteorsRepository: MeteorsRepository

    private val meteorsObserver: Observer<MeteorsViewModel.State> = mock()

    private lateinit var viewModel: MeteorsViewModel

    private val coordinates: List<Double> = listOf(0.0, 0.0)
    private val geoLocation = GeoLocation("typeTest", coordinates)
    private val meteor = Meteor(
        1,
        "nameTest",
        "nameTypeTest",
        "recClassTest",
        "massTest",
        "fallTest",
        "yearTest",
        0.0,
        0.0,
        geoLocation
    )
    private val meteors: List<Meteor> = listOf(meteor)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        MockKAnnotations.init(this)
        viewModel = MeteorsViewModel(meteorsRepository)
        viewModel.meteors.observeForever(meteorsObserver)
    }

    @Test
    fun getMeteors_ResultSuccess_Test() {
        val success = MeteorsViewModel.State.Success(meteors)

        val loading = MeteorsViewModel.State.Loading

        val slot = slot<MeteorsRepository.DataResponseCallback>()

        every { meteorsRepository.getMeteors(capture(slot)) } answers {
            slot.captured.onSuccess(meteors)
        }

        viewModel.getMeteors()

        Mockito.verify(meteorsObserver).onChanged(loading)

        Mockito.verify(meteorsObserver).onChanged(success)
    }

    @Test
    fun getMeteors_ResultError_Test() {
        val error = MeteorsViewModel.State.Error(ERROR_MESSAGE)

        val loading = MeteorsViewModel.State.Loading

        val slot = slot<MeteorsRepository.DataResponseCallback>()

        every { meteorsRepository.getMeteors(capture(slot)) } answers {
            slot.captured.onError(error.error)
        }

        viewModel.getMeteors()

        Mockito.verify(meteorsObserver).onChanged(loading)

        Mockito.verify(meteorsObserver).onChanged(error)
    }

    @Test
    fun getMeteorsWithRxJava_ResultSuccess_Test() {
        val success = MeteorsViewModel.State.Success(meteors)

        val loading = MeteorsViewModel.State.Loading

        val slot = slot<MeteorsRepository.DataResponseCallback>()

        every { meteorsRepository.getMeteorsWithRxJava(capture(slot)) } answers {
            slot.captured.onSuccess(meteors)
        }

        viewModel.getMeteorsWithRxJava()

        Mockito.verify(meteorsObserver).onChanged(loading)

        Mockito.verify(meteorsObserver).onChanged(success)
    }

    @Test
    fun getMeteorsWithRxJava_ResultError_Test() {
        val error = MeteorsViewModel.State.Error(ERROR_MESSAGE)

        val loading = MeteorsViewModel.State.Loading

        val slot = slot<MeteorsRepository.DataResponseCallback>()

        every { meteorsRepository.getMeteorsWithRxJava(capture(slot)) } answers {
            slot.captured.onError(error.error)
        }

        viewModel.getMeteorsWithRxJava()

        Mockito.verify(meteorsObserver).onChanged(loading)

        Mockito.verify(meteorsObserver).onChanged(error)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMeteorsWithSuspendFunction_ResultSuccess_Test() {
        TestCoroutineScope().launch {
            val result = MeteorsViewModel.State.Success(meteors)

            Mockito.`when`(meteorsRepository.getMeteorsWithSuspendFunction()).thenReturn(result)

            viewModel.getMeteorsWithSuspendFunction()

            Mockito.verify(meteorsObserver).onChanged(result)

            Mockito.verifyNoMoreInteractions(meteorsObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMeteorsWithSuspendFunction_ResultError_Test() {
        TestCoroutineScope().launch {
            val result = MeteorsViewModel.State.Error(ERROR_MESSAGE)

            Mockito.`when`(meteorsRepository.getMeteorsWithSuspendFunction()).thenReturn(result)

            viewModel.getMeteorsWithSuspendFunction()

            Mockito.verify(meteorsObserver).onChanged(result)

            Mockito.verifyNoMoreInteractions(meteorsObserver)
        }
    }
}

