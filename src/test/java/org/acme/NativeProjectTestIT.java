package org.acme;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeProjectTestIT extends GreetingResourceTest {

    // Execute the same tests but in native mode.
}
