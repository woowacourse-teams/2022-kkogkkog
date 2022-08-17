package com.woowacourse.kkogkkog.support.setup;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DataClearExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        DatabaseCleaner databaseCleaner = getDatabaseCleaner(context);
        databaseCleaner.execute();
    }

    private DatabaseCleaner getDatabaseCleaner(final ExtensionContext extensionContext) {
        return SpringExtension.getApplicationContext(extensionContext)
            .getBean(DatabaseCleaner.class);
    }
}
