package com.ankit.scheduler.enums;

import com.ankit.scheduler.configurations.NotEmptyEnum;

public enum TaskFrequency {
    @NotEmptyEnum
    ONCE_A_DAY,

    @NotEmptyEnum
    TWICE_A_DAY,

    @NotEmptyEnum
    ONCE_A_WEEK,

    @NotEmptyEnum
    NO_OF_DAYS,

    @NotEmptyEnum
    DAYS_OF_WEEK
}
