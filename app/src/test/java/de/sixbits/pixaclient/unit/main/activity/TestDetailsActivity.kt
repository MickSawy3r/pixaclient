package de.sixbits.pixaclient.unit.main.activity

import android.os.Build
import android.os.Looper
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import de.sixbits.pixaclient.ImageResponseFactory
import de.sixbits.pixaclient.R
import de.sixbits.pixaclient.main.ui.DetailsActivity
import de.sixbits.pixaclient.main.view_model.DetailsViewModel
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.junit.jupiter.MockitoExtension
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@LooperMode(LooperMode.Mode.PAUSED)
@ExtendWith(MockitoExtension::class)
class TestDetailsActivity {
    private lateinit var activity: DetailsActivity

    private lateinit var activityController: ActivityController<DetailsActivity>

    @Mock
    private lateinit var viewModel: DetailsViewModel

    @Mock
    private lateinit var detailsLiveData: MutableLiveData<ImageDetailsModel>

    @Captor
    private lateinit var detailsObservableCaptor: ArgumentCaptor<Observer<ImageDetailsModel>>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        Mockito.`when`(viewModel.detailsLiveData).thenReturn(detailsLiveData)

        activityController = Robolectric.buildActivity(DetailsActivity::class.java)
        activity = activityController.get()

        Shadows.shadowOf(Looper.getMainLooper()).idle()

        activityController.create()
        activity.setTestViewModel(viewModel)

        Shadows.shadowOf(Looper.getMainLooper()).idle()

        activityController.start()

        Mockito.verify(detailsLiveData).observe(
            ArgumentMatchers.any(LifecycleOwner::class.java),
            detailsObservableCaptor.capture()
        )
    }

    @LooperMode(LooperMode.Mode.PAUSED)
    @Test
    fun testWhenGetData() {
        val image = ImageResponseFactory.getImageDetails()
        detailsObservableCaptor.value.onChanged(image)

        Shadows.shadowOf(Looper.getMainLooper()).idle()

        assertEquals(
            activity.findViewById<TextView>(R.id.tv_details_username).text,
            image.username
        )
        assertEquals(
            activity.findViewById<TextView>(R.id.tv_details_comments).text,
            image.comments.toString()
        )
        assertEquals(
            activity.findViewById<TextView>(R.id.tv_details_likes).text,
            image.likes.toString()
        )
        assertEquals(
            activity.findViewById<TextView>(R.id.tv_details_love).text,
            image.favorites.toString()
        )
        assertEquals(
            activity.findViewById<TextView>(R.id.tv_details_tags).text,
            image.tags
        )
    }
}