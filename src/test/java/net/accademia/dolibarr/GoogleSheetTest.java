package net.accademia.dolibarr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GoogleSheetTest {

    GoogleSheet gs = new GoogleSheet(null);

    @Test
    void testGetIscritti() {
        String[] iscrittiv2 = { "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc" };
        int formato[] = { 12, 10, 15 };
        gs.getIscritti(iscrittiv2[0], formato);
    }
}
