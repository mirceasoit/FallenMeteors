package workshop.mirceasoit.fallenmeteors.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import workshop.mirceasoit.fallenmeteors.R
import workshop.mirceasoit.fallenmeteors.databinding.ActivityMeteorsBinding
import workshop.mirceasoit.fallenmeteors.model.Meteor
import workshop.mirceasoit.fallenmeteors.repository.MeteorsRepository
import workshop.mirceasoit.fallenmeteors.view.MapActivity.Companion.LAT_KEY
import workshop.mirceasoit.fallenmeteors.view.MapActivity.Companion.LON_KEY
import workshop.mirceasoit.fallenmeteors.view.MapActivity.Companion.NAME_KEY
import workshop.mirceasoit.fallenmeteors.viewmodel.MeteorsViewModel

class MeteorsActivity : AppCompatActivity() {

    private lateinit var adapter: MeteorsAdapter
    private val _viewModel: MeteorsViewModel by viewModels {
        object :
            ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MeteorsViewModel(MeteorsRepository()) as T
            }
        }
    }
    private lateinit var binding: ActivityMeteorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MeteorsAdapter(clickListener)
        _viewModel.meteors.observe(this, { result ->
            when (result) {
                is MeteorsViewModel.State.Loading -> {}
                is MeteorsViewModel.State.Success -> {
                    adapter.submitData(result.meteors)
                    adapter.notifyDataSetChanged()
                }
                is MeteorsViewModel.State.Error -> {
                    binding.error.text = result.error
                }
            }
        })
        binding = DataBindingUtil.setContentView<ActivityMeteorsBinding>(this,
                R.layout.activity_meteors
        ).apply {
            viewmodel = _viewModel
            lifecycleOwner = this@MeteorsActivity
            content.layoutManager = GridLayoutManager(this@MeteorsActivity, 1)
            content.adapter = adapter
        }
        loadMeteors()
    }

    private fun loadMeteors() {
        //_viewModel.getMeteors()
        _viewModel.getMeteorsWithRxJava()
       // _viewModel.getMeteorsWithSuspendFunction()
    }

    private val clickListener = object : MeteorsAdapter.OnClickListener {
        override fun onMeteorClick(meteor: Meteor) {
            val intent = Intent(this@MeteorsActivity, MapActivity::class.java)
            intent.putExtra(NAME_KEY, meteor.name)
            meteor.geoLocation?.let {
                intent.putExtra(LAT_KEY, it.coordinates[0].toString())
                intent.putExtra(LON_KEY, it.coordinates[1].toString())
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                loadMeteors()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}