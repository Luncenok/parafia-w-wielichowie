package codes.idziejczak.parafiawwielichowie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.databinding.FragmentOgloszeniaBinding
import codes.idziejczak.parafiawwielichowie.viewmodels.OgloszeniaViewModel

class OgloszeniaFragment : Fragment() {

    private val viewModel: OgloszeniaViewModel by lazy {
        ViewModelProvider(this).get(OgloszeniaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOgloszeniaBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ogloszenia, container, false)
        binding.lifecycleOwner = this
        activity?.actionBar?.title = getString(R.string.home_intencje_i_ogloszenia)

        return binding.root
    }

}