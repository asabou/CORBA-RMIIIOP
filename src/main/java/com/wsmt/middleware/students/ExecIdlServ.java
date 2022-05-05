package com.wsmt.middleware.students;

import com.wsmt.middleware.students.service.abstracts.ExecIdlInte;
import com.wsmt.middleware.students.service.abstracts.ExecIdlInteHelper;
import com.wsmt.middleware.students.service.impl.JavaIDLServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

@Slf4j
public class ExecIdlServ {
    public ExecIdlServ(String host, String port, String name) throws Exception {
        String[] t = {"-ORBInitialHost", host, "-ORBInitialPort", port};
        ORB orb = ORB.init(t, null);

        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootpoa.the_POAManager().activate();
        JavaIDLServiceImpl javaIDLServiceImpl = new JavaIDLServiceImpl();
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(javaIDLServiceImpl);
        ExecIdlInte href = ExecIdlInteHelper.narrow(ref);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        NameComponent[] path = ncRef.to_name(name);
        ncRef.rebind(path, href);

        log.info("Java IDL waiting: " + host + ":" + port + "/" + name);
        orb.run();
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 2)
            new ExecIdlServ(args[0], args[1], args[2]);
        else
            new ExecIdlServ("localhost", "3000", "Exec");
    }
}
