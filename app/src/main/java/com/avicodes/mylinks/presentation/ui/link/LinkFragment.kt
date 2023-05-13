package com.avicodes.mylinks.presentation.ui.link

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.avicodes.mylinks.R
import com.avicodes.mylinks.data.models.Analytics
import com.avicodes.mylinks.data.utils.Constants
import com.avicodes.mylinks.data.utils.DateUtil.getChartDateRange
import com.avicodes.mylinks.data.utils.Result
import com.avicodes.mylinks.databinding.FragmentLinkBinding
import com.avicodes.mylinks.presentation.ui.MainActivity
import com.avicodes.mylinks.presentation.ui.MainActivityViewModel
import com.avicodes.mylinks.presentation.ui.link.adapters.AnalyticsAdapter
import com.avicodes.mylinks.presentation.ui.link.adapters.LinkPagerAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LinkFragment : Fragment() {

    private var _binding: FragmentLinkBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var analyticsAdapter: AnalyticsAdapter
    private lateinit var linkPagerAdapter: LinkPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLinkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        viewModel.getLinkData()

        binding.run {
            tvGreeting.text = viewModel.getGreeting()
        }

        observeAnalyticsData()

        initLinkTabLayout()

        initChart()

        observeChartData()
    }

    private fun initChart() {
        val chart = binding.chart
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setDrawGridBackground(true)
        chart.setScaleEnabled(true)
        chart.isDragEnabled = true
        chart.axisRight.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.setGridBackgroundColor(Color.WHITE)
        chart.legend.isEnabled = false
    }

    private fun observeChartData() {
        viewModel.getChartData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    it.data?.let {
                        populateChartData(it)
                    }
                }

                else -> {}
            }
        })
    }

    private fun populateChartData(overallURLChart: Map<String, Long>) {
        val chartData = ArrayList<Entry>()
        var first = "0"
        var sec = "0"
        overallURLChart.keys.forEachIndexed { index, key ->
            if(index == 0) {
                first = key
            }
            if(index == overallURLChart.size - 1) {
                sec = key
            }
            chartData.add(
                Entry(
                    index.toFloat(),
                    overallURLChart[key]?.toFloat() ?: 0f,
                    key
                )
            )

        }

        binding.tvRange.text = getChartDateRange(first, sec)

        val lineDataset = LineDataSet(chartData, "Overview")
        lineDataset.apply {
            setDrawIcons(false)
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            //formLineWidth = 1f
            lineWidth = 3f
            setDrawFilled(true)
            fillDrawable = activity?.getDrawable(R.drawable.gradient_blue)
        }

        lineDataset.color = binding.root.context.getColor(R.color.mainBlue)
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataset)
        binding.apply {
            chart.data = LineData(dataSets)
            val dates = overallURLChart.keys.mapIndexed { index, key ->
                val date = Constants.remoteDateFormatter.parse(key)
                if (date != null) {
                    Constants.localDateFormatter.format(date)
                } else {
                    index.toString()
                }
            }

            chart.xAxis.valueFormatter = object : IndexAxisValueFormatter(dates) {}
            lineDataset.notifyDataSetChanged()
            chart.notifyDataSetChanged()
            chart.invalidate();

        }
    }

    private fun observeAnalyticsData() {

        viewModel.getAnalyticsList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    it.data?.let { list ->
                        initAnalyticsAdapter(list)
                    }
                }

                else -> {}
            }
        })
    }

    private fun initLinkTabLayout() {
        binding.apply {
            tlLinks.addTab(
                tlLinks.newTab().setText("Top Links")
            )

            tlLinks.addTab(
                tlLinks.newTab().setText("Recent Links")
            )

            linkPagerAdapter = LinkPagerAdapter(childFragmentManager, lifecycle)
            vpLinks.adapter = linkPagerAdapter

            tlLinks.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        vpLinks.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            vpLinks.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    tlLinks.selectTab(tlLinks.getTabAt(position))
                }
            })
        }
    }

    private fun initAnalyticsAdapter(data: List<Analytics>) {
        binding.apply {
            analyticsAdapter = AnalyticsAdapter(data)
            rvAnalytics.adapter = analyticsAdapter
            rvAnalytics.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }
}