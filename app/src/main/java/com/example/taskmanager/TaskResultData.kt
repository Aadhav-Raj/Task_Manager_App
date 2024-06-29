package com.example.taskmanager

import com.squareup.moshi.Json

data class TaskResultData(
    @field:Json(name="url") val task:List<DataModel>
)
