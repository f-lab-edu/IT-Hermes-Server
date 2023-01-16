package com.hermes.ithermes.domain.functional;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.JobType;

@FunctionalInterface
public interface ParseSubscribeFunctional {
    Subscribe parseSubscribe(User user, ContentsProvider contentsProvider, ActiveType activeType, JobType jobType, String minYearOfExperience, String maxYearOfExperience);
}
