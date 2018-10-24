package org.mockito.configuration;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 * Mockito loads this with reflection, so this class might appear unused.
 */

public class MockitoConfiguration extends DefaultMockitoConfiguration {

    /**
     * Disables the Mockito cache to prevent Mockito & Robolectric bugs.
     */

    public boolean enableClassCache() {
        return false;
    }

    /**
     * change default value returned in a method invocation
     */

    public Answer<Object> getDefaultAnswer() {
        return new ReturnsEmptyValues() {
            @Override
            public Object answer(InvocationOnMock inv) {
                Class<?> type = inv.getMethod().getReturnType();
                if (type.isAssignableFrom(Observable.class)) {
                    return Observable.error(createException(inv));
                } else if (type.isAssignableFrom(Flowable.class)) {
                    return Flowable.error(createException(inv));
                } else if (type.isAssignableFrom(Single.class)) {
                    return Single.error(createException(inv));
                } else {
                    return super.answer(inv);
                }
            }
        };
    }

    private RuntimeException createException(
        InvocationOnMock invocation) {
        String s = invocation.toString();
        return new RuntimeException("No mock defined for invocation " + s);
    }
}
