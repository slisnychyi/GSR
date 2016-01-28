package juja.domain.service;

import juja.domain.model.User;

import java.util.Set;

public interface ProgressService {

    Set<String> fetchProgressCodes();

    void markProgressDone(User user, String ...codes);
}
