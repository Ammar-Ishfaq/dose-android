package com.waseefakhtar.doseapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.waseefakhtar.doseapp.R
import com.waseefakhtar.doseapp.domain.model.Medication
import com.waseefakhtar.doseapp.extension.toFormattedDateString
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@Composable
fun getTimeRemaining(medication: Medication): String {
    val currentTime = Calendar.getInstance().time
    val dateBefore = medication.date
    val timeDiff = abs(currentTime.time - dateBefore.time)

    // If the medication is scheduled for a future date, display days remaining
    if (medication.date.toFormattedDateString() != medication.endDate.toFormattedDateString()) {
        val daysRemaining = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS) + 1L
        return stringResource(id = R.string.time_remaining, daysRemaining, stringResource(id = R.string.days))
    }

    // If the medication is scheduled for today, calculate time remaining in hours and minutes
    val hoursRemaining = TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS)
    val minutesRemaining = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS)
    return when {
        hoursRemaining > 1 -> stringResource(id = R.string.time_remaining, hoursRemaining, stringResource(id = R.string.hours))
        minutesRemaining > 1 -> stringResource(id = R.string.time_remaining, minutesRemaining, stringResource(id = R.string.days))
        else -> stringResource(id = R.string.take_dose_now)
    }
}
