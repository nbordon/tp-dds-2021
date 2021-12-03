package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public enum StringHelper implements Helper<String> {

    append{
        @Override
        public CharSequence apply(String arg0, Options arg1) throws IOException {
            return arg0+arg1.param(0);
        }
    }
}