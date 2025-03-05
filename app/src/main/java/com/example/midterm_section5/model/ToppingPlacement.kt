package com.example.midterm_section5.model

import androidx.annotation. StringRes
import com.example.midterm_section5.R
enum class ToppingPlacement (
    @StringRes val label: Int
) {
    Left(R.string.placement_left),
    Right (R.string.placement_right),
    All(R.string.placement_all)
    }

