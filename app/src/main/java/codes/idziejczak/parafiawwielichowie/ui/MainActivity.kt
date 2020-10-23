package codes.idziejczak.parafiawwielichowie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import codes.idziejczak.parafiawwielichowie.R
import com.google.ads.consent.*
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {
    lateinit var form: ConsentForm
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
                R.id.kontaktFragment -> getString(R.string.home_kontakt)
                else -> getString(R.string.app_name)
            }
            destination.label = title
        }
        NavigationUI.setupActionBarWithNavController(this, navController)

        googleFormLoaded()

        ConsentInformation.getInstance(this)
            .addTestDevice("6BAFF71222ABE5046B2841CF75F38B42")

        val consentInformation = ConsentInformation.getInstance(this)
        val publisherIds = arrayOf("pub-7457190986528845")
        consentInformation.requestConsentInfoUpdate(
            publisherIds,
            object : ConsentInfoUpdateListener {
                override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {
                    if (consentStatus == ConsentStatus.UNKNOWN)
                        googleFormLoaded()
                }

                override fun onFailedToUpdateConsentInfo(errorDescription: String) {

                    Timber.tag("loggo").d(errorDescription)
                }
            })
    }

    fun googleFormLoaded() {
        var privacyUrl: URL? = null
        try {
            privacyUrl =
                URL("http://idziejczak.codes/parafia-w-wielichowie/polityka-prywatnosci.html")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            // Handle error.
        }
        form = ConsentForm.Builder(this, privacyUrl)
            .withPersonalizedAdsOption()
            .withNonPersonalizedAdsOption()
            .withListener(object : ConsentFormListener() {
                override fun onConsentFormLoaded() {
                    show()
                }

                override fun onConsentFormOpened() {
                    // Consent form was displayed.
                }

                override fun onConsentFormClosed(
                    consentStatus: ConsentStatus, userPrefersAdFree: Boolean
                ) {
                    ConsentInformation.getInstance(this@MainActivity).consentStatus =
                        consentStatus
                }

                override fun onConsentFormError(errorDescription: String) {
                    Timber.tag("loggo").d(errorDescription)
                }
            })
            .build()
        form.load()
    }

    fun show() {
        form.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}