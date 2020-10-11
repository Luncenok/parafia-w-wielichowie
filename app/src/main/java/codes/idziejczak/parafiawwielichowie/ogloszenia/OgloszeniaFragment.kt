package codes.idziejczak.parafiawwielichowie.ogloszenia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import codes.idziejczak.parafiawwielichowie.R
import codes.idziejczak.parafiawwielichowie.databinding.FragmentOgloszeniaBinding

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

        val adapter = OgloszeniaAdapter()
        adapter.ogloszeniaList = viewModel.ogloszeniaList
        binding.ogloszeniaList.adapter = adapter
        binding.ogloszeniaList.layoutManager = LinearLayoutManager(context)


        return binding.root
    }

}