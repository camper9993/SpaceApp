package ru.danilov.spaceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import ru.danilov.spaceapp.R
import ru.danilov.spaceapp.adapter.SpaceAdapter
import ru.danilov.spaceapp.di.component.DaggerApiComponent
import ru.danilov.spaceapp.viewmodel.SpaceViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var spaceAdapter: SpaceAdapter

    private val spaceViewModel: SpaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerApiComponent.create().inject(this)

        main_swipe_refresh_layout.setOnRefreshListener {
            main_swipe_refresh_layout.isRefreshing = false
            spaceViewModel.refresh()
        }

        main_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = spaceAdapter
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        observeInProgress()
        observeIsError()
        observeItemList()
    }

    private fun observeItemList() {
        spaceViewModel.itemsListLD.observe(this, Observer { allVehicles ->
            allVehicles.let {
                main_recycler_view.visibility = View.VISIBLE
                spaceAdapter.setUpItems(it)
            }
        })
    }

    private fun observeInProgress() {
        spaceViewModel.inProgressLD.observe(this, Observer { isLoading ->
            isLoading.let {
                if (it) {
                    space_fetch_error.visibility = View.GONE
                    main_recycler_view.visibility = View.GONE
                    space_fetch_progress.visibility = View.VISIBLE
                } else {
                    space_fetch_progress.visibility = View.GONE
                }
            }
        })
    }

    private fun observeIsError() {
        spaceViewModel.isErrorLD.observe(this, Observer { isError ->
            isError.let { space_fetch_error.visibility = if (it) View.VISIBLE else View.GONE }
        })
    }

}