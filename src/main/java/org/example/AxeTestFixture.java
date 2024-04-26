package org.example;


import com.deque.html.axecore.playwright.AxeBuilder;

public class AxeTestFixtures extends TestFixtures {
    AxeBuilder makeAxeBuilder() {
        return new AxeBuilder(page)
                .withTags(['wcag2a, 'wcag2aa', 'wcag21a', 'wcag21aa'])
      .exclude('#commonly-reused-element-with-known-issue');
    }
}