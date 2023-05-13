package com.avicodes.mylinks.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.mylinks.R
import com.avicodes.mylinks.data.models.Analytics
import com.avicodes.mylinks.data.models.ApiResponse
import com.avicodes.mylinks.data.models.Link
import com.avicodes.mylinks.data.utils.Result
import com.avicodes.mylinks.domain.repository.LinkRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


class MainActivityViewModel(
    private val linkRepository: LinkRepository
) : ViewModel() {

    private val linkData: MutableLiveData<Result<ApiResponse>> =
        MutableLiveData(Result.NotInitialized)

    private val _analyticsList: MutableLiveData<Result<List<Analytics>>> =
        MutableLiveData(Result.NotInitialized)

    private val _topLinkList: MutableLiveData<Result<List<Link>>> =
        MutableLiveData(Result.NotInitialized)

    private val _recentLinkList: MutableLiveData<Result<List<Link>>> =
        MutableLiveData(Result.NotInitialized)

    private val _chartData: MutableLiveData<Result<Map<String, Long>>> =
        MutableLiveData(Result.NotInitialized)

    private val isLoading: MutableLiveData<Boolean> =
        MutableLiveData(false)

    fun getAnalyticsList() = _analyticsList
    fun getTopLinkList() = _topLinkList
    fun getRecentLinkList() = _recentLinkList
    fun getChartData() = _chartData

    fun getLinkData() {
        viewModelScope.launch {
            linkRepository.getLinkData().collectLatest {
                when (it) {
                    is Result.Loading -> {
                        isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        it.data?.let { data ->
                            extractAnalyticsList(
                                data.today_clicks,
                                data.top_location,
                                data.top_source
                            )
                            extractRecentLinks(data.data.recent_links)
                            extractTopLinks(data.data.top_links)
                            extractChartData(data.data.overall_url_chart)

                        }
                        isLoading.postValue(false)
                    }

                    is Result.Error -> {
                        isLoading.postValue(false)

                    }

                    is Result.NotInitialized -> {
                        isLoading.postValue(true)
                    }
                }
            }
        }
    }

    private fun extractChartData(overallUrlChart: Map<String, Long>) {

        _chartData.postValue(Result.Success(overallUrlChart))
    }

    private fun extractTopLinks(links: List<Link>) {
        _topLinkList.postValue(Result.Success(links))
    }

    private fun extractRecentLinks(recentLinks: List<Link>) {
        _recentLinkList.postValue(Result.Success(recentLinks))
    }


    private fun extractAnalyticsList(todayClicks: Int, topLocation: String, topSource: String) {
        val tempList = mutableListOf<Analytics>()

        tempList.add(
            Analytics(
                R.drawable.click,
                todayClicks.toString(),
                "Today's clicks"
            )
        )

        tempList.add(
            Analytics(
                R.drawable.loc,
                topLocation,
                "Top Location"
            )
        )

        tempList.add(
            Analytics(
                R.drawable.internet,
                topSource,
                "Top Source"
            )
        )


        _analyticsList.postValue(Result.Success(tempList))
    }

    fun getGreeting(): String {
        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        return when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 12..16 -> {
                "Good Afternoon"
            }

            in 17..23 -> {
                "Good Evening"
            }

            else -> {
                "Good Morning"
            }
        }
    }

}