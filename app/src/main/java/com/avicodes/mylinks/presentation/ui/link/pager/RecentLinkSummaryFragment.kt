package com.avicodes.mylinks.presentation.ui.link.pager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.avicodes.mylinks.data.utils.Result
import com.avicodes.mylinks.databinding.FragmentRecentLinkSummaryBinding
import com.avicodes.mylinks.presentation.ui.MainActivity
import com.avicodes.mylinks.presentation.ui.MainActivityViewModel
import com.avicodes.mylinks.presentation.ui.link.adapters.LinkAdapter

class RecentLinkSummaryFragment(
) : Fragment() {

    private var _binding: FragmentRecentLinkSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var linkAdapter: LinkAdapter
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentLinkSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        linkAdapter = LinkAdapter { link ->
            context?.let { context ->
                val myClipboard: ClipboardManager = (activity as MainActivity).getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("link", link)
                myClipboard.setPrimaryClip(clipData)
                Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
            }
        }

        binding.apply {
            rvRecentLink.adapter = linkAdapter
            rvRecentLink.layoutManager = LinearLayoutManager(activity)
        }

        observeRecentLink()
    }

    private fun observeRecentLink() {
        viewModel.getRecentLinkList().observe(viewLifecycleOwner, Observer {
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