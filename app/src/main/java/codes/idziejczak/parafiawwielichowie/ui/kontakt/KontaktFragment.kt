package codes.idziejczak.parafiawwielichowie.ui.kontakt

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.databinding.FragmentKontaktBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class KontaktFragment : Fragment() {

    private lateinit var viewModel: KontaktViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireActivity().application
        val viewModelFactory = KontaktViewModel.Factory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(KontaktViewModel::class.java)
        val binding: FragmentKontaktBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_kontakt, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.kontaktTekst.movementMethod = LinkMovementMethod.getInstance()

        val req = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("6BAFF71222ABE5046B2841CF75F38B42")).build()
        MobileAds.setRequestConfiguration(req)

        MobileAds.initialize(context) {}

        val extras = Bundle()
        extras.putString("npa", "1")
        val adRequest =
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
        binding.adView.loadAd(adRequest)

        viewModel.eventNetworkError.observe(viewLifecycleOwner, {
            if (it == true && !viewModel.isErrorNetworkShown.value!!) {
                Toast.makeText(activity, getString(R.string.network_error), Toast.LENGTH_LONG)
                    .show()
                viewModel.onNetworkErrorShown()
            }
        })

        return binding.root
    }

}