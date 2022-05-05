package com.wsmt.middleware.students;

import com.wsmt.middleware.students.service.impl.ExecRmiInte;
import com.wsmt.middleware.students.utils.ClientUtilsRMIIIOP;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.rmi.PortableRemoteObject;

@Slf4j
public class ExecIdlClieForRmiIiop {
    public ExecIdlClieForRmiIiop(String host, String port, String name) throws Exception {
        String[] t = {"-ORBInitialPort", "" + port, "-ORBInitialHost", host};
        ORB orb = ORB.init(t, null);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        ExecRmiInte proxy = (ExecRmiInte) PortableRemoteObject.narrow(ncRef.resolve_str(name), ExecRmiInte.class);
        log.info("Client Java IDL: " + host + ":" + port + "/" + name);
        System.out.println("Creating initial data ...");
        ClientUtilsRMIIIOP.creatingData(proxy);
        System.out.println("Getting initial data ...");
        ClientUtilsRMIIIOP.gettingData(proxy);
        System.out.println("Updating data ...");
        ClientUtilsRMIIIOP.updatingData(proxy);
        System.out.println("Getting data after update ...");
        ClientUtilsRMIIIOP.gettingData(proxy);
        System.out.println("Deleting data ...");
        ClientUtilsRMIIIOP.deletingData(proxy);
        System.out.println("Getting data after delete ...");
        ClientUtilsRMIIIOP.gettingData(proxy);
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 2)
            new ExecIdlClieForRmiIiop(args[0], args[1], args[2]);
        else
            new ExecIdlClieForRmiIiop("localhost", "3000", "Exec");
    }
}
