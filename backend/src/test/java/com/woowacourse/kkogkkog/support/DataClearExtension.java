package com.woowacourse.kkogkkog.support;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DataClearExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DatabaseCleaner databaseCleaner = getDataBaseCleaner(context);
        databaseCleaner.execute();
    }

    private DatabaseCleaner getDataBaseCleaner(final ExtensionContext extensionContext) {
        return (DatabaseCleaner) SpringExtension.getApplicationContext(extensionContext)
            .getBean("databaseCleaner");
    }
}
