package com.avicodes.mylinks.data.models

data class Data(
    val overall_url_chart: Map<String, Long>,
    val recent_links: List<Link>,
    val top_links: List<Link>
)