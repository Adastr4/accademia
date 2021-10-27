package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GoogleSheetTest {

    GoogleSheet gs = new GoogleSheet(null);

    @Test
    void testGetIscritti() {
        gs.getIscritti("1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ");
    }
}
