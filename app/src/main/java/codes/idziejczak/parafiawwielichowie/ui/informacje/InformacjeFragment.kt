package codes.idziejczak.parafiawwielichowie.ui.informacje

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
import codes.idziejczak.parafiawwielichowie.databinding.FragmentInformacjeBinding

class InformacjeFragment : Fragment() {

    private lateinit var viewModel: InformacjeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireActivity().application
        val viewModelFactory = InformacjeViewModel.Factory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(InformacjeViewModel::class.java)
        val binding: FragmentInformacjeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_informacje, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.informacjeTekst.movementMethod = LinkMovementMethod.getInstance()

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