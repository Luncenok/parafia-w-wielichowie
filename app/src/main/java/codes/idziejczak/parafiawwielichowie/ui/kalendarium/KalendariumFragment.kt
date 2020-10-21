package codes.idziejczak.parafiawwielichowie.ui.kalendarium

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
import codes.idziejczak.parafiawwielichowie.databinding.FragmentKalendariumBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class KalendariumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = KalendariumViewModel.Factory(requireActivity().application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(KalendariumViewModel::class.java)

        val binding: FragmentKalendariumBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_kalendarium, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.kalendariumSmierciTekst.movementMethod = LinkMovementMethod.getInstance()
        binding.kalendariumUrodzinTekst.movementMethod = LinkMovementMethod.getInstance()

        val req = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("6BAFF71222ABE5046B2841CF75F38B42")).build()
        MobileAds.setRequestConfiguration(req)

        MobileAds.initialize(context) {}

        val adRequest = AdRequest.Builder().build()
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