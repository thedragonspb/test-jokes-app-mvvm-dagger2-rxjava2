package thedragonspb.testjokesapp.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import thedragonspb.testjokesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}