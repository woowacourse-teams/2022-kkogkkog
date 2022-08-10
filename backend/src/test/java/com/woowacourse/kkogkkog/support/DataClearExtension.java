package com.woowacourse.kkogkkog.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DataClearExtension implements AfterEachCallback {

    @Override
    public void afterEach(final ExtensionContext context) {
        DatabaseCleaner databaseCleaner = getDataBaseCleaner(context);
        databaseCleaner.execute();
    }

    private DatabaseCleaner getDataBaseCleaner(final ExtensionContext extensionContext) {
        return (DatabaseCleaner) SpringExtension.getApplicationContext(extensionContext)
            .getBean("databaseCleaner");
    }
}
