package net.accademia.dolibarr;

import org.junit.jupiter.api.Test;

class GoogleSheetTest {

    GoogleSheet gs = new GoogleSheet(new AccademiaDemoneMediator());

    @Test
    void testGetIscrittiv2() {
        String[] iscrittiv2 = { "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc" };
        int formato[] = { 14, 11, 15, 13 };
        gs.getIscritti(iscrittiv2[0], formato);
    }

    @Test
    void testGetIscritti() {
        String[] iscritti = { "11ozxzipNGmx5GK2gLXaYFpZOlBMx7KQ30aQsuX74RWA" };

        gs.getIscritti(iscritti[0], null);
    }
}
