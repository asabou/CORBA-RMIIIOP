package com.wsmt.middleware.students;

import com.wsmt.middleware.students.service.abstracts.*;
import com.wsmt.middleware.students.utils.ClientUtilsIDL;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

@Slf4j
public class ExecIdlClie {
    public ExecIdlClie(String host, String port, String name) throws Exception {
        String[] t = {"-ORBInitialPort", "" + port, "-ORBInitialHost", host};
        ORB orb = ORB.init(t, null);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        ExecIdlInte proxy = ExecIdlInteHelper.narrow(ncRef.resolve_str(name));
        log.info("Client Java IDL: " + host + ":" + port + "/" + name);
        System.out.println("Creating initial data ...");
        ClientUtilsIDL.creatingData(proxy);
        System.out.println("Getting initial data ...");
        ClientUtilsIDL.gettingData(proxy);
        System.out.println("Updating data ...");
        ClientUtilsIDL.updatingData(proxy);
        System.out.println("Getting data after update ...");
        ClientUtilsIDL.gettingData(proxy);
        System.out.println("Deleting data ...");
        ClientUtilsIDL.deletingData(proxy);
        System.out.println("Getting data after delete ...");
        ClientUtilsIDL.gettingData(proxy);
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 2)
            new ExecIdlClie(args[0], args[1], args[2]);
        else
            new ExecIdlClie("localhost", "3000", "Exec");
    }
}
