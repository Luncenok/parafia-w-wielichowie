package codes.idziejczak.parafiawwielichowie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import codes.idziejczak.parafiawwielichowie.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = when (destination.id) {
                R.id.ogloszeniaFragment -> getString(R.string.home_intencje_i_ogloszenia)
                R.id.grupyFragment -> getString(R.string.home_grupy)
                R.id.kalendariumFragment -> getString(R.string.home_kalendarium)
                R.id.niezbednikiFragment -> getString(R.string.home_niezbedniki)
                R.id.informacjeFragment -> getString(R.string.home_informacje_ogolne)
                else -> getString(R.string.app_name)
            }
            destination.label = title
        }
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}