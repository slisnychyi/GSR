package juja.domain.dao;

import java.util.List;

/**
 * User: viktor
 * Date: 1/19/16
 */
public interface ProgressDao {

    List<String> fetchProgressCodes();

    void markProgressForUser(String slackNick, String ...codes);
}
