package com.avicodes.mylinks.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.avicodes.mylinks.R
import com.avicodes.mylinks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        observeLoader()
    }

    fun observeLoader(){
        viewModel.isLoading().observe(this, Observer {
            if(it) {
                showLoader()
            }
            else {
                hideLoader()
            }
        })
    }


    fun hideLoader() {
        binding.apply {
            progressCircular.visibility = View.GONE
            fragmentContainerView.visibility = View.VISIBLE
        }
    }

    fun showLoader() {
        binding.apply {
            progressCircular.visibility = View.VISIBLE
            fragmentContainerView.visibility = View.INVISIBLE
        }
    }
}