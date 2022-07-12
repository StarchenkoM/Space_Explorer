package com.rprd.space_explorer.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rprd.space_explorer.NavGraphDirections
import com.rprd.space_explorer.R
import com.rprd.space_explorer.databinding.ActivityMainBinding
import com.rprd.space_explorer.utils.DAILY_PHOTO_NOTIFICATION_KEY
import com.rprd.space_explorer.utils.navigateSafe
import com.rprd.space_explorer.worker.SpaceWorkManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding by Delegates.notNull<ActivityMainBinding>()

    @Inject
    lateinit var spaceWorkManager: SpaceWorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spaceWorkManager.startNewDailyPhotoWorker()
        setupNavigation()
        handleNotificationIntent()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.navHostFragment)
        binding.navView.setupWithNavController(navController)
    }

    private fun handleNotificationIntent() {
        val notifyIntent = intent
        val extras = notifyIntent.extras
        if (extras != null && extras.equals(DAILY_PHOTO_NOTIFICATION_KEY)) {
            val action = NavGraphDirections.openDailyPhotoFragment()
            findNavController(R.id.navHostFragment).navigateSafe(action)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}