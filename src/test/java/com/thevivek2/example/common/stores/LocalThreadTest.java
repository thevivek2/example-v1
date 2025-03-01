package com.thevivek2.example.common.stores;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalThreadTest {

    @Test
    void put() {
        LocalThread.put("A", "BCD");
        assertThat(LocalThread.get("A")).isEqualTo("BCD");
    }

    @Test
    void accessingByAnotherThread() throws InterruptedException {
        Runnable runnable = () -> {
            LocalThread.put("A", "BCDAD");
            Assertions.assertThat(LocalThread.get("A")).isEqualTo("BCDAD");
        };
        new Thread(runnable).start();
        Thread.sleep(100);

        Runnable runnable2 = () -> Assertions.assertThatThrownBy(() ->
                LocalThread.get("A")).isExactlyInstanceOf(NullPointerException.class);
        new Thread(runnable2).start();

    }


}