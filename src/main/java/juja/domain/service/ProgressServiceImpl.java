package juja.domain.service;

import juja.domain.dao.ProgressDao;

import java.util.HashSet;
import java.util.Set;

public class ProgressServiceImpl implements ProgressService {

    ProgressDao progressDao;

    @Override
    public Set<String> fetchProgressCodes() {
        return new HashSet<>(progressDao.fetchProgressCodes());
    }
}
