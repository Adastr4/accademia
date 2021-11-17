package cloud.adastraiot.demone;

import net.accademia.dolibarr.DemoneMediator;
import net.accademia.dolibarr.GoogleSheet;

public class AdastraGoogleSheet extends GoogleSheet {
    {
        idclient = "88319937008-ij2efgnkmddor000c0v77ntbsfa2feme.apps.googleusercontent.com";
        clientsecret = "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";
        refreshToken = "1//09_c2jlItZ3MICgYIARAAGAkSNwF-L9IrITPLzBmOBQpv2RroZkA6sg_v2fwf95rx7jSl4Hbyq6-XP0FrL7oLVWZQ3WpKjiVbT58";
    }

    public AdastraGoogleSheet(DemoneMediator dm) throws Exception {
        super(dm);
        // TODO Auto-generated constructor stub
    }
}
