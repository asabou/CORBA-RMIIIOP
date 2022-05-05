package com.wsmt.middleware.students;

import com.wsmt.middleware.students.service.impl.JavaRMIIIOPServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

@Slf4j
public class ExecRmiIiopServ {
    public ExecRmiIiopServ(String host, String port, String name) throws Exception {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
        props.setProperty("java.naming.provider.url", "iiop://" + host + ":" + port);
        Context ctx = new InitialContext(props);
        ctx.rebind(name, new JavaRMIIIOPServiceImpl());
        log.info("Java RMI IIOP waiting: " + host + ":" + port + "/" + name);
    }

    public static void main(String args[]) throws Exception {
        if (args.length > 2)
            new ExecRmiIiopServ(args[0], args[1], args[2]);
        else
            new ExecRmiIiopServ("localhost", "3000", "Exec");
    }
}
