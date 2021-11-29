package net.accademia.dolibarr;

public class AccademiaGoogleSheet extends GoogleSheet {
    {
        // idclient = "88319937008-ij2efgnkmddor000c0v77ntbsfa2feme.apps.googleusercontent.com";
        //clientsecret = "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";
        //    	idclient ="117868993891-l9nidjia7v0u1n8gcuhu5bf5816mer6c.apps.googleusercontent.com";
        //    	clientsecret="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";
        //
        //        refreshToken = "1//09_c2jlItZ3MICgYIARAAGAkSNwF-L9IrITPLzBmOBQpv2RroZkA6sg_v2fwf95rx7jSl4Hbyq6-XP0FrL7oLVWZQ3WpKjiVbT58";
        CREDENTIALS_FILE_PATH = "/credential.accademia.json";
    }

    public AccademiaGoogleSheet(DemoneMediator dm) throws Exception {
        super(dm);
    }
}
