package com.avicodes.mylinks.presentation.ui.link.pager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.avicodes.mylinks.data.utils.Result
import com.avicodes.mylinks.databinding.FragmentTopLinkSummaryBinding
import com.avicodes.mylinks.presentation.ui.MainActivity
import com.avicodes.mylinks.presentation.ui.MainActivityViewModel
import com.avicodes.mylinks.presentation.ui.link.adapters.LinkAdapter

class TopLinkSummaryFragment(
) : Fragment() {

    private var _binding: FragmentTopLinkSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var linkAdapter: LinkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopLinkSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        linkAdapter = LinkAdapter { link ->
            context?.let { context ->
                val myClipboard: ClipboardManager = (activity as MainActivity).getSystemService(
                    Context.CLIPBOARD_SERVICE
                ) as ClipboardManager
                val clipData = ClipData.newPlainText("link", link)
                myClipboard.setPrimaryClip(clipData)
                Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
            }
        }

        binding.apply {
            rvTopLink.adapter = linkAdapter
            rvTopLink.layoutManager = LinearLayoutManager(activity)
        }

        observeTopLink()

    }

    private fun observeTopLink() {
        viewModel.getTopLinkList().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    it.data?.let {data ->
                        val endSize = if(data.size >= 4) 4 else data.size
                        linkAdapter.differ.submitList(data.subList(0, endSize))
                    }
                }

                else -> {}
            }
        })
    }

}