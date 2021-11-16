package net.accademia.demone.domain;

import static org.assertj.core.api.Assertions.assertThat;

import net.accademia.demone.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErrorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Error.class);
        Error error1 = new Error();
        error1.setId(1L);
        Error error2 = new Error();
        error2.setId(error1.getId());
        assertThat(error1).isEqualTo(error2);
        error2.setId(2L);
        assertThat(error1).isNotEqualTo(error2);
        error1.setId(null);
        assertThat(error1).isNotEqualTo(error2);
    }
}
