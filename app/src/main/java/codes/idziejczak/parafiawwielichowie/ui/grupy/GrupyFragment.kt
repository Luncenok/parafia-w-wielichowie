package codes.idziejczak.parafiawwielichowie.ui.grupy

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
import codes.idziejczak.parafiawwielichowie.databinding.FragmentGrupyBinding

class GrupyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentGrupyBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_grupy, container, false)
        val viewModelFactory = GrupyViewModel.Factory(requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(GrupyViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.grupyTekst.movementMethod = LinkMovementMethod.getInstance()

        viewModel.eventNetworkError.observe(viewLifecycleOwner) {
            if (it == true && !viewModel.isErrorNetworkShown.value!!) {
                Toast.makeText(activity, getString(R.string.network_error), Toast.LENGTH_LONG)
                    .show()
                viewModel.onNetworkErrorShown()
            }
        }

        return binding.root
    }

}