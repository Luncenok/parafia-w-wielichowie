package codes.idziejczak.parafiawwielichowie.ui.ogloszenia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.databinding.FragmentOgloszeniaBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class OgloszeniaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentOgloszeniaBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ogloszenia, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = OgloszeniaViewModel.Factory(application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(OgloszeniaViewModel::class.java)

        val adapter = OgloszeniaAdapter()
        viewModel.listAllOgloszenia.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        binding.viewModel = viewModel
        binding.ogloszeniaList.adapter = adapter
        binding.ogloszeniaList.layoutManager = LinearLayoutManager(context)

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