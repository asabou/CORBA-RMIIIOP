package com.wsmt.middleware.students;

import com.wsmt.middleware.students.service.abstracts.ExecIdlInte;
import com.wsmt.middleware.students.utils.ClientUtilsIDL;

import javax.naming.*;
import javax.rmi.*;
import java.util.*;

public class ExecRmiIiopClie {
    public ExecRmiIiopClie(String host, String port, String name) throws Exception {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
        props.setProperty("java.naming.provider.url", "iiop://" + host + ":" + port);
        Context ctx = new InitialContext(props);
        Object ref = ctx.lookup(name);
        ExecIdlInte proxy = (ExecIdlInte) PortableRemoteObject.narrow(ref, ExecIdlInte.class);
        System.out.println("Client Java RMI IIOP: " + host + ":" + port + "/" + name);
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
            new ExecRmiIiopClie(args[0], args[1], args[2]);
        else
            new ExecRmiIiopClie("localhost", "3000", "Exec");
    }
}
