package juja.domain.service;

import juja.domain.dao.ProgressDao;
import juja.domain.model.User;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

public class BlackListedProgressService implements ProgressService {

    @Inject
    ProgressDao progressDao;

    private Set<String> codesBlackList = Collections.emptySet();

    @Override
    public Set<String> fetchProgressCodes() {
        return progressDao.fetchProgressCodes().stream().
                filter(code -> !codesBlackList.contains(code)).collect(toSet());
    }

    @Override
    public void markProgress(User user, String... codes) {
        throw new UnsupportedOperationException();
    }

    public void setCodesBlackList(String codesBlackList) {
        this.codesBlackList = new HashSet<>(asList(codesBlackList.split(";")));
    }
}
