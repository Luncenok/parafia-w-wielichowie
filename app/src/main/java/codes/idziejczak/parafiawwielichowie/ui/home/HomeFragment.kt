package codes.idziejczak.parafiawwielichowie.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, HomeViewModel.Factory()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToOgloszenia.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToOgloszeniaFragment())
                viewModel.doneNavigatingOgloszenia()
            }
        })

        viewModel.navigateToGrupy.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToGrupyFragment())
                viewModel.doneNavigatingGrupy()
            }
        })

        viewModel.navigateToKalendarium.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToKalendariumFragment())
                viewModel.doneNavigatingKalendarium()
            }
        })

        viewModel.navigateToNiezbedniki.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToNiezbednikiFragment())
                viewModel.doneNavigatingNiezbedniki()
            }
        })

        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}